package com.example.uhf_bt;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.rscja.deviceapi.RFIDWithUHFBLE;
import com.rscja.deviceapi.interfaces.ConnectionStatus;
import com.rscja.deviceapi.interfaces.ConnectionStatusCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PrincipalActivity extends BaseActivity implements View.OnClickListener{

    public String remoteBTName = "";
    public String remoteBTAdd = "";

    public boolean seenvio = false;


    public boolean isScanning = false;
    public RFIDWithUHFBLE uhf = RFIDWithUHFBLE.getInstance();
    private DisconnectTimerTask timerTask;
    private long timeCountCur;
    public BluetoothAdapter mBtAdapter = null;
    private boolean mIsActiveDisconnect = true;
    private Button btn_inventario,btn_busqueda,btn_missing,btn_sincronizar;
    private TextView tvAddress;
    private Button btn_connect, btn_search;
    private static final int WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST = 101;
    private static final int READ_EXTERNAL_STORAGE_PERMISSION_REQUEST=102;
    private static final int REQUEST_ENABLE_BT = 2;
    private static final int REQUEST_SELECT_DEVICE = 1;
    private static final int RUNNING_DISCONNECT_TIMER = 10;
    private static final int ACCESS_FINE_LOCATION_PERMISSION_REQUEST = 100;
    private static final int REQUEST_ACTION_LOCATION_SETTINGS = 3;
    private long period = 1000 * 30;
    private long lastTouchTime = System.currentTimeMillis();
    public static final String SHOW_HISTORY_CONNECTED_LIST = "showHistoryConnectedList";
    private final static String TAG = "PrincipalActivity";
    public BluetoothDevice mDevice = null;
    private Timer mDisconnectTimer = new Timer();
    BTStatus btStatus = new BTStatus();
    private List<MainActivity.IConnectStatus> connectStatusList = new ArrayList<>();

    private static final int RECONNECT_NUM = Integer.MAX_VALUE;
    private int mReConnectCount = RECONNECT_NUM;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RUNNING_DISCONNECT_TIMER:
                    long time = (long) msg.obj;
                    formatConnectButton(time);
                    break;
            }
        }
    };

    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        checkReadWritePermission();
        btn_inventario=(Button)findViewById(R.id.btn_inventario);
        btn_inventario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setContentView(MainActivity);
                inventario();
            }
        });
        btn_missing=(Button)findViewById(R.id.btn_missing);
        btn_missing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                missing();
            }
        });
        btn_sincronizar=(Button)findViewById(R.id.btn_sincronizar);
        btn_sincronizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sincronizar();
            }
        });
        btn_busqueda=(Button)findViewById(R.id.btn_busqueda);
        btn_busqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                busqueda();
            }
        });
        initUI();
        uhf.init(getApplicationContext());
        Utils.initSound(getApplicationContext());
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_SELECT_DEVICE:
                //When the DeviceListActivity return, with the selected device address
                if (resultCode == Activity.RESULT_OK && data != null) {
                    if (uhf.getConnectStatus() == ConnectionStatus.CONNECTED) {
                        disconnect(true);
                    }
                    String deviceAddress = data.getStringExtra(BluetoothDevice.EXTRA_DEVICE);
                    mDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(deviceAddress);
                    tvAddress.setText(String.format("%s(%s)\nconnecting", mDevice.getName(), deviceAddress));
                    connect(deviceAddress);
                }
                break;
            case REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK) {
                    showToast("Bluetooth has turned on ");
                } else {
                    showToast("Problem in BT Turning ON ");
                }
                break;
            default:
                break;
        }
    }
    public void inventario(){
        Intent mainIntend= new Intent(this,MainActivity.class);
        startActivity(mainIntend);
    }
    public void missing(){
        Intent mainIntend= new Intent(this,MissingActivity.class);
        startActivity(mainIntend);
    }
    public void sincronizar(){
        Intent mainIntend= new Intent(this,SincronizarActivity.class);
        startActivity(mainIntend);
    }
    public void busqueda() {
        Intent mainIntend = new Intent(this, BusquedaActivity.class);
        startActivity(mainIntend);
    }

    protected void initUI() {
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        btn_connect = (Button) findViewById(R.id.btn_connect);
        btn_connect.setOnClickListener(this);
        btn_search = (Button) findViewById(R.id.btn_search);
        btn_search.setOnClickListener(this);
    }
    private void checkReadWritePermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST);
            }
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_PERMISSION_REQUEST);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_connect:
                if (isScanning) {
                    showToast(R.string.title_stop_read_card);
                } else if (uhf.getConnectStatus() == ConnectionStatus.CONNECTING) {
                    showToast(R.string.connecting);
                } else if (uhf.getConnectStatus() == ConnectionStatus.CONNECTED) {
                    disconnect(true);
                } else {
                    showBluetoothDevice(true);
                }
                break;
            case R.id.btn_search:
                if (isScanning) {
                    showToast(R.string.title_stop_read_card);
                } else if (uhf.getConnectStatus() == ConnectionStatus.CONNECTING) {
                    showToast(R.string.connecting);
                } else {
                    if(checkLocationEnable()) {
                        showBluetoothDevice(false);
                    }
                }
                break;
        }
    }
    @Override
    protected void onDestroy() {
        uhf.free();
        Utils.freeSound();
        super.onDestroy();
        //android.os.Process.killProcess(Process.myPid());
    }
    public void cancelDisconnectTimer() {
        timeCountCur = 0;
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
    }
    public void connect(String deviceAddress) {
        if (uhf.getConnectStatus() == ConnectionStatus.CONNECTING) {
            showToast(R.string.connecting);
        } else {
            uhf.connect(deviceAddress, btStatus);
        }
    }
    public void disconnect(boolean isActiveDisconnect) {
        cancelDisconnectTimer();
        mIsActiveDisconnect = isActiveDisconnect; // 主动断开为true
        uhf.disconnect();
    }
    private void showBluetoothDevice(boolean isHistory) {
        if (mBtAdapter == null) {
            showToast("Bluetooth is not available");
            return;
        }
        if (!mBtAdapter.isEnabled()) {
            Log.i(TAG, "onClick - BT not enabled yet");
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        } else {
            Intent newIntent = new Intent(PrincipalActivity.this, DeviceListActivity.class);
            newIntent.putExtra(SHOW_HISTORY_CONNECTED_LIST, isHistory);
            startActivityForResult(newIntent, REQUEST_SELECT_DEVICE);
            cancelDisconnectTimer();
        }
    }
    private class DisconnectTimerTask extends TimerTask {
        @Override
        public void run() {
            Log.e(TAG, "timeCountCur = " + timeCountCur);
            Message msg = mHandler.obtainMessage(RUNNING_DISCONNECT_TIMER, timeCountCur);
            mHandler.sendMessage(msg);
            if(isScanning) {
                resetDisconnectTime();
            } else if (timeCountCur <= 0){
                disconnect(true);
            }
            timeCountCur -= period;
        }
    }
    public void resetDisconnectTime() {
        timeCountCur = SPUtils.getInstance(getApplicationContext()).getSPLong(SPUtils.DISCONNECT_TIME, 0);
        if (timeCountCur > 0) {
            formatConnectButton(timeCountCur);
        }
    }
    private void formatConnectButton(long disconnectTime) {
        if (uhf.getConnectStatus() == ConnectionStatus.CONNECTED) {
            if (!isScanning && System.currentTimeMillis() - lastTouchTime > 1000 * 30 && timerTask != null) {
                long minute = disconnectTime / 1000 / 60;
                if(minute > 0) {
                    btn_connect.setText(getString(R.string.disConnectForMinute, minute)); //倒计时分
                } else {
                    btn_connect.setText(getString(R.string.disConnectForSecond, disconnectTime / 1000)); // 倒计时秒
                }
            } else {
                btn_connect.setText(R.string.disConnect);
            }
        } else {
            btn_connect.setText(R.string.Connect);
        }
    }
    private boolean isLocationEnabled() {
        int locationMode = 0;
        String locationProviders;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }
    private boolean checkLocationEnable() {
        boolean result=true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_FINE_LOCATION_PERMISSION_REQUEST);
                result=false;
            }
        }
        if (!isLocationEnabled()) {
            Utils.alert(this, R.string.get_location_permission, getString(R.string.tips_open_the_ocation_permission), R.drawable.webtext, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(intent, REQUEST_ACTION_LOCATION_SETTINGS);
                }
            });
        }
        return result;
    }

    class BTStatus implements ConnectionStatusCallback<Object> {
        @Override
        public void getStatus(final ConnectionStatus connectionStatus, final Object device1) {
            runOnUiThread(new Runnable() {
                public void run() {
                    BluetoothDevice device = (BluetoothDevice) device1;
                    remoteBTName = "";
                    remoteBTAdd = "";
                    if (connectionStatus == ConnectionStatus.CONNECTED) {
                        remoteBTName = device.getName();
                        remoteBTAdd = device.getAddress();

                        tvAddress.setText(String.format("%s(%s)\nconnected", remoteBTName, remoteBTAdd));
                        if (shouldShowDisconnected()) {
                            showToast(R.string.connect_success);
                        }

                        timeCountCur = SPUtils.getInstance(getApplicationContext()).getSPLong(SPUtils.DISCONNECT_TIME, 0);
                        if (timeCountCur > 0) {
                            startDisconnectTimer(timeCountCur);
                        } else {
                            formatConnectButton(timeCountCur);
                        }

                        if (!TextUtils.isEmpty(remoteBTAdd)) {
                            saveConnectedDevice(remoteBTAdd, remoteBTName);
                        }

                        mIsActiveDisconnect = false;
                        mReConnectCount = RECONNECT_NUM;
                    } else if (connectionStatus == ConnectionStatus.DISCONNECTED) {
                        cancelDisconnectTimer();
                        formatConnectButton(timeCountCur);
                        if (device != null) {
                            remoteBTName = device.getName();
                            remoteBTAdd = device.getAddress();
//                            if (shouldShowDisconnected())
                            tvAddress.setText(String.format("%s(%s)\ndisconnected", remoteBTName, remoteBTAdd));
                        } else {
//                            if (shouldShowDisconnected())
                            tvAddress.setText("disconnected");
                        }
                        if (shouldShowDisconnected())
                            showToast(R.string.disconnect);

                        boolean reconnect = SPUtils.getInstance(getApplicationContext()).getSPBoolean(SPUtils.AUTO_RECONNECT, false);
                        if (mDevice != null && reconnect) {
                            reConnect(mDevice.getAddress()); // 重连
                        }
                    }

                    for (MainActivity.IConnectStatus iConnectStatus : connectStatusList) {
                        if (iConnectStatus != null) {
                            iConnectStatus.getStatus(connectionStatus);
                        }
                    }
                }
            });
        }
    }
    private boolean shouldShowDisconnected() {
        return mIsActiveDisconnect || mReConnectCount == 0;
    }
    private void startDisconnectTimer(long time) {
        timeCountCur = time;
        timerTask = new DisconnectTimerTask();
        mDisconnectTimer.schedule(timerTask, 0, period);
    }
    public void saveConnectedDevice(String address, String name) {
        List<String[]> list = FileUtils.readXmlList();
        for (int k = 0; k < list.size(); k++) {
            if (address.equals(list.get(k)[0])) {
                list.remove(list.get(k));
                break;
            }
        }
        String[] strArr = new String[]{address, name};
        list.add(0, strArr);
        FileUtils.saveXmlList(list);
    }
    private void reConnect(String deviceAddress) {
        if (!mIsActiveDisconnect && mReConnectCount > 0) {
            connect(deviceAddress);
            mReConnectCount--;
        }
    }




}
