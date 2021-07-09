package com.example.uhf_bt;


import android.annotation.TargetApi;

import android.content.DialogInterface;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;



import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.uhf_bt.fragment.UHFLocationFragment;
import com.example.uhf_bt.fragment.UHFReadTagFragment;
import com.example.uhf_bt.fragment.UHFSetFragment;
import com.rscja.deviceapi.RFIDWithUHFBLE;
import com.rscja.deviceapi.interfaces.ConnectionStatus;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTabHost;



public class MainActivity extends BaseActivity{


    public boolean seenvio = false;
    public Button btnseenvio;

    public boolean isScanning = false;
    public String selectEPC=null;
    public boolean isSupportRssi=false;
    private FragmentTabHost mTabHost;
    private FragmentManager fm;
    public RFIDWithUHFBLE uhf = RFIDWithUHFBLE.getInstance();
    public static final String SHOW_HISTORY_CONNECTED_LIST = "showHistoryConnectedList";
    public static final String TAG_DATA = "tagData";
    public static final String TAG_EPC = "tagEpc";
    public static final String TAG_COUNT = "tagCount";
    public static final String TAG_RSSI = "tagRssi";


    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*btn_login=(Button)findViewById(R.id.btn_login);*/

       /* if (BuildConfig.DEBUG) {
            setTitle(String.format("%s(v%s-debug)", getString(R.string.app_name), getVerName()));
        } else {
            setTitle(String.format("%s(v%s)", getString(R.string.app_name), getVerName() ));
        }*/

        initUI();
        uhf.init(getApplicationContext());
        Utils.initSound(getApplicationContext());
        btnseenvio = (Button) this.findViewById(R.id.btnseenvio);
        btnseenvio.setBackgroundColor(Color.YELLOW);
        btnseenvio.setTextColor(Color.GRAY);
        btnseenvio.setVisibility(View.INVISIBLE);
        btnseenvio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seEnvio();
            }
        });
    }


    @Override
    protected void onDestroy() {
        uhf.free();
        Utils.freeSound();
        connectStatusList.clear();
        super.onDestroy();
        //android.os.Process.killProcess(Process.myPid());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (!isScanning) {
            if (item.getItemId() == R.id.UHF_Battery) {
                String ver = getString(R.string.action_uhf_bat) + ":" + uhf.getBattery() + "%";
                Utils.alert(MainActivity.this, R.string.action_uhf_bat, ver, R.drawable.webtext);
            } else if (item.getItemId() == R.id.UHF_T) {
                String temp = getString(R.string.title_about_Temperature) + ":" + uhf.getTemperature() + "℃";
                Utils.alert(MainActivity.this, R.string.title_about_Temperature, temp, R.drawable.webtext);
            } else if (item.getItemId() == R.id.UHF_ver) {
                String ver = uhf.getVersion();
                Utils.alert(MainActivity.this, R.string.action_uhf_ver, ver, R.drawable.webtext);
            } else if (item.getItemId() == R.id.ble_ver) {
                HashMap<String, String> versionMap = uhf.getBluetoothVersion();
                if (versionMap != null) {
                    String verMsg = "固件版本：" + versionMap.get(RFIDWithUHFBLE.VERSION_BT_FIRMWARE)
                            + "\n硬件版本：" + versionMap.get(RFIDWithUHFBLE.VERSION_BT_HARDWARE)
                            + "\n软件版本：" + versionMap.get(RFIDWithUHFBLE.VERSION_BT_SOFTWARE);
                    Utils.alert(MainActivity.this, R.string.action_ble_ver, verMsg, R.drawable.webtext);
                }
            } else if (item.getItemId() == R.id.ble_disconnectTime) {
                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_disconnect_time, null);
                final Spinner spDisconnectTime = view.findViewById(R.id.spDisconnectTime);
                int index = SPUtils.getInstance(getApplicationContext()).getSPInt(SPUtils.DISCONNECT_TIME_INDEX, 0);
                spDisconnectTime.setSelection(index);
                Utils.alert(this, R.string.disconnectTime, view, R.drawable.webtext, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int index = spDisconnectTime.getSelectedItemPosition();
                        long time = 1000 * 60 * 60 * index;
                        SPUtils.getInstance(getApplicationContext()).setSPInt(SPUtils.DISCONNECT_TIME_INDEX, index);
                        SPUtils.getInstance(getApplicationContext()).setSPLong(SPUtils.DISCONNECT_TIME, time);
                        switch (index) {
                            case 0:
                                //cancelDisconnectTimer();
                                break;
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                                if (uhf.getConnectStatus() == ConnectionStatus.CONNECTED) {
                                    //cancelDisconnectTimer();
                                    //startDisconnectTimer(time);
                                }
                                break;
                        }
                    }
                });
            }
        } else {
            showToast(R.string.title_stop_read_card);
        }
        return true;
    }

    //NAVBAR DESLIZABLE
    protected void initUI() {
        fm = getSupportFragmentManager();
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, fm, R.id.realtabcontent);
        mTabHost.addTab(mTabHost.newTabSpec(getString(R.string.title_inventory)).setIndicator(getString(R.string.title_inventory)), UHFReadTagFragment.class, null);
       // mTabHost.addTab(mTabHost.newTabSpec(getString(R.string.title_inventory2)).setIndicator(getString(R.string.title_inventory2)), UHFNewReadTagFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec(getString(R.string.uhf_msg_tab_set)).setIndicator(getString(R.string.uhf_msg_tab_set)), UHFSetFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec(getString(R.string.location)).setIndicator(getString(R.string.location)), UHFLocationFragment.class, null);
    }


    private List<IConnectStatus> connectStatusList = new ArrayList<>();

    public void addConnectStatusNotice(IConnectStatus iConnectStatus) {
        connectStatusList.add(iConnectStatus);
    }

    public void removeConnectStatusNotice(IConnectStatus iConnectStatus) {
        connectStatusList.remove(iConnectStatus);
    }

    public interface IConnectStatus {
        void getStatus(ConnectionStatus connectionStatus);
    }

    public void seEnvio() {
        if(this.seenvio){
            btnseenvio.setVisibility(View.INVISIBLE);
            //Toast.makeText(this, "SE ENVIO A LA BD .NET", Toast.LENGTH_SHORT).show();
            this.seenvio = false;
        }
    }

}