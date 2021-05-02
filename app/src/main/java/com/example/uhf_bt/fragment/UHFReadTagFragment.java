package com.example.uhf_bt.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Backend.APIUtils;

import com.example.Interfaces.LogeoInterface;
import com.example.Models.User;

import com.example.uhf_bt.DateUtils;
import com.example.uhf_bt.FileUtils;
import com.example.uhf_bt.MainActivity;
import com.example.uhf_bt.NumberTool;
import com.example.uhf_bt.R;
import com.example.uhf_bt.Utilidades.GLOBAL;
import com.example.uhf_bt.Utilidades.utilidades;
import com.example.uhf_bt.Utils;
import com.example.uhf_bt.view.Articulo;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.rscja.deviceapi.RFIDWithUHFBLE;
import com.rscja.deviceapi.entity.UHFTAGInfo;
import com.rscja.deviceapi.interfaces.ConnectionStatus;
import com.rscja.deviceapi.interfaces.KeyEventCallback;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import androidx.fragment.app.Fragment;


import static java.lang.String.valueOf;


public class UHFReadTagFragment extends Fragment implements View.OnClickListener {


    //private final String URL = "http://f2923df27d8e.ngrok.io/";
    private  final String URL = GLOBAL.URL;


    List<User> listUs;



    List<String> buildingNames = new ArrayList<>();
    List<String> roomNames = new ArrayList<>();

    //lista para guardar los datos
    private ArrayList<String> listaCsv;
    MaterialSpinner spinnerE;
    MaterialSpinner spinnerU;
    ArrayAdapter<String> adapterE;
    ArrayAdapter<String> adapterU;


    private String TAG = "UHFReadTagFragment";
    int lastIndex=-1;
    private boolean loopFlag = false;
    private ListView LvTags;
    private Button InventoryLoop, btInventory, btStop;//
    private Button btInventoryPerMinute;
    private Button btClear;
    private Button btnGenerar;
    private TextView tv_count, tv_total, tv_time;
    private boolean isExit = false;
    private long total = 0;
    private MainActivity mContext;
    private MyAdapter adapter;
    private HashMap<String, String> tagMap = new HashMap<>();
    private List<byte[]> tempDatas = new ArrayList<>();
    private ArrayList<HashMap<String, String>> tagList;

    private ConnectStatus mConnectStatus = new ConnectStatus();

    //--------------------------------------获取 解析数据-------------------------------------------------
    final int FLAG_START = 0;//开始
    final int FLAG_STOP = 1;//停止
    final int FLAG_UPDATE_TIME = 2; // 更新时间
    final int FLAG_UHFINFO = 3;
    final int FLAG_UHFINFO_LIST = 5;
    final int FLAG_SUCCESS = 10;//成功
    final int FLAG_FAIL = 11;//失败

    boolean isRuning = false;
    private long mStrTime;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case FLAG_STOP:
                    if (msg.arg1 == FLAG_SUCCESS) {
                        //停止成功
                        btClear.setEnabled(true);
                        btStop.setEnabled(false);
                        InventoryLoop.setEnabled(true);
                        btInventory.setEnabled(true);
                        btInventoryPerMinute.setEnabled(true);
                    } else {
                        //停止失败
                        Utils.playSound(2);
                        Toast.makeText(mContext, R.string.uhf_msg_inventory_stop_fail, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case FLAG_UHFINFO_LIST:
                    List<UHFTAGInfo> list = ( List<UHFTAGInfo>) msg.obj;
                    UHFTAGInfo cosa = list.get(0);

                    // la captura de objetos uno por uno
                    UHFTAGInfo infoAux = list.get(0);
                    if (infoAux != null) {
                        Message msgAux = handler.obtainMessage(FLAG_UHFINFO);
                        msgAux.obj = infoAux;
                        Log.d("HENRYLISTA   ", valueOf(msgAux));
                    }
                    listaCsv.add(msg.toString());
                    Log.d("GG:", msg.toString());
                    addEPCToList(list);
                    break;
                case FLAG_START:
                    if (msg.arg1 == FLAG_SUCCESS) {
                        //开始读取标签成功
                        btClear.setEnabled(false);
                        btStop.setEnabled(true);
                        InventoryLoop.setEnabled(false);
                        btInventory.setEnabled(false);
                        btInventoryPerMinute.setEnabled(false);
                    } else {
                        //开始读取标签失败
                        Utils.playSound(2);
                    }
                    break;
                case FLAG_UPDATE_TIME:
                    float useTime = (System.currentTimeMillis() - mStrTime) / 1000.0F;
                    tv_time.setText(NumberTool.getPointDouble(loopFlag ? 1 : 3, useTime) + "s");
                    break;
                case FLAG_UHFINFO:
                    UHFTAGInfo info = (UHFTAGInfo) msg.obj;
                    Log.d("HENRY", info.toString());

                    List<UHFTAGInfo> list2 = ( List<UHFTAGInfo>) msg.obj;
                    UHFTAGInfo cosa2 = list2.get(0);
                    String cc = cosa2.getEPC();
                    Log.d("HENRYLISTA   ", cc);
                    // la captura de objetos uno por uno
                    listaCsv.add(list2.get(0).toString());
                    //adicionarALista(cc);
                    List list1=new ArrayList<UHFTAGInfo>();
                    list1.add(info);
                    addEPCToList(list1);
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_uhfread_tag, container, false);


        listUs = new ArrayList<User>();
        //actualizarUsuariosEnSQLite();

        initFilter(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, "UHFReadTagFragment.onActivityCreated");
        super.onActivityCreated(savedInstanceState);

        lastIndex=-1;
        mContext = (MainActivity) getActivity();
        init();
        selectIndex=-1;
        mContext.selectEPC=null;


    }

    @Override
    public void onPause() {
        super.onPause();
        stopInventory();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isExit = true;
        mContext.removeConnectStatusNotice(mConnectStatus);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btClear: // limpiar
                clearData();
                break;
            case R.id.btInventoryPerMinute:
                inventoryPerMinute();
                break;
            case R.id.InventoryLoop: // auto
                //getUsuarios();
                //startThread();
                break;
            case R.id.btInventory: // single
                //inventory();
                //cargarBDSQLite();
                guardarInventario();
                break;
            case R.id.btStop: //stop
                if (mContext.uhf.getConnectStatus() == ConnectionStatus.CONNECTED) {
                    stopInventory();
                }
                break;
                /**
            case R.id.btnGenerar:
                Log.d("CSV", " entro!!!!");
                for (int i = 0; i < tagList.size(); i++) {

                    Log.d("CSV",  String.valueOf( tagList.get(i).get("tagData")));
                }
                break;
                 */
        }
    }


    private void guardarInventario(){
        for (int i = 0; i < tagList.size(); i++) {

            String tag_data = tagList.get(i).get(MainActivity.TAG_DATA);
            String tag_count = tagList.get(i).get(MainActivity.TAG_COUNT);
            String tag_epc_tid_user = tagList.get(i).get(MainActivity.TAG_EPC);
            //Log.d("INVENTARIO", tag_data);
            Log.d("INVENTARIO", tag_epc_tid_user);
            String the_tid = tag_epc_tid_user.substring(0,24);
            String the_epc = tag_epc_tid_user.substring(24,48);
            Log.d("INVENTARIO", the_epc);
            Log.d("INVENTARIO", the_tid);
            Log.d("INVENTARIO", tag_count);
        }
        String duracion = tv_time.getText().toString();
        String tag_total = tv_total.getText().toString();
        Log.d("INVENTARIO", duracion);
        Log.d("INVENTARIO", tag_total);
    }







    private void init() {

        isExit = false;
        mContext.addConnectStatusNotice(mConnectStatus);
        LvTags = (ListView) mContext.findViewById(R.id.LvTags);
        btInventory = (Button) mContext.findViewById(R.id.btInventory);
        InventoryLoop = (Button) mContext.findViewById(R.id.InventoryLoop);
        btStop = (Button) mContext.findViewById(R.id.btStop);
        btStop.setEnabled(false);
        btClear = (Button) mContext.findViewById(R.id.btClear);
        //btnGenerar = (Button) mContext.findViewById(R.id.btnGenerar);
        tv_count = (TextView) mContext.findViewById(R.id.tv_count);
        tv_total = (TextView) mContext.findViewById(R.id.tv_total);
        tv_time = (TextView) mContext.findViewById(R.id.tv_time);

        InventoryLoop.setOnClickListener(this);
        btInventory.setOnClickListener(this);
        btClear.setOnClickListener(this);
        btStop.setOnClickListener(this);
        //btnGenerar.setOnClickListener(this);

        btInventoryPerMinute = mContext.findViewById(R.id.btInventoryPerMinute);
        btInventoryPerMinute.setOnClickListener(this);

        listaCsv = new ArrayList<>();

        tagList = new ArrayList<>();
        adapter=new MyAdapter(mContext);
        LvTags.setAdapter(adapter);
        mContext.uhf.setKeyEventCallback(new KeyEventCallback() {
            @Override
            public void onKeyDown(int keycode) {
                Log.d(TAG, "  keycode =" + keycode + "   ,isExit=" + isExit);
                if (!isExit && mContext.uhf.getConnectStatus() == ConnectionStatus.CONNECTED) {
                    if(keycode==1) {
                        if (loopFlag) {
                            stopInventory();
                        } else {
                            startThread();
                        }
                    }else{
                        if (loopFlag) {
                            stopInventory();
                        }
                        inventory();
                    }
                }
            }
        });
        LvTags.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectIndex=position;
                adapter.notifyDataSetInvalidated();
                mContext.selectEPC=tagList.get(position).get(MainActivity.TAG_EPC);
            }
        });

        clearData();

    }


    private ViewGroup layout_filter;
    private Button btnSetFilter;
    private void initFilter(View view) {
        layout_filter = (ViewGroup) view.findViewById(R.id.layout_filter);


        final EditText etLen = (EditText) view.findViewById(R.id.etLen);
        final EditText etPtr = (EditText) view.findViewById(R.id.etPtr);
        final EditText etData = (EditText) view.findViewById(R.id.etData);

        //final RadioButton rbTID = (RadioButton) view.findViewById(R.id.rbTID);
        //final RadioButton rbUser = (RadioButton) view.findViewById(R.id.rbUser);
        btnSetFilter = (Button) view.findViewById(R.id.btSet);



        //SPINNER


        spinnerE = view.findViewById(R.id.spinnerE);
        spinnerU = view.findViewById(R.id.spinnerU);
        spinnerE.setItems("Choose");
        spinnerU.setItems("Choose");
    }







    @Override
    public void onResume() {
        super.onResume();
        if (mContext.uhf.getConnectStatus() == ConnectionStatus.CONNECTED) {
            InventoryLoop.setEnabled(true);
            btInventory.setEnabled(true);
            btInventoryPerMinute.setEnabled(true);


        } else {
            InventoryLoop.setEnabled(false);
            btInventory.setEnabled(false);
            btInventoryPerMinute.setEnabled(false);

        }
    }

    private void clearData() {
        total = 0;
        tv_count.setText("0");
        tv_total.setText("0");
        tv_time.setText("0s");
        tagList.clear();
        tempDatas.clear();
        adapter.notifyDataSetChanged();
        mContext.selectEPC=null;
        selectIndex=-1;
    }

    /**
     * 停止识别
     */
    private void stopInventory() {
        loopFlag = false;
        cancelInventoryTask();
        boolean result = mContext.uhf.stopInventory();
        if(mContext.isScanning) {
            ConnectionStatus connectionStatus = mContext.uhf.getConnectStatus();
            Message msg = handler.obtainMessage(FLAG_STOP);
            if (result || connectionStatus == ConnectionStatus.DISCONNECTED) {
                msg.arg1 = FLAG_SUCCESS;
            } else {
                msg.arg1 = FLAG_FAIL;
            }
            if (connectionStatus == ConnectionStatus.CONNECTED) {
                //在连接的情况下，结束之后继续接收未接收完的数据
                //getUHFInfoEx();
            }
            mContext.isScanning = false;
            handler.sendMessage(msg);
        }
    }

    class ConnectStatus implements MainActivity.IConnectStatus {
        @Override
        public void getStatus(ConnectionStatus connectionStatus) {
            if (connectionStatus == ConnectionStatus.CONNECTED) {
                if (!loopFlag) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    InventoryLoop.setEnabled(true);
                    btInventory.setEnabled(true);
                    btInventoryPerMinute.setEnabled(true);
                }


            } else if (connectionStatus == ConnectionStatus.DISCONNECTED) {
                loopFlag = false;
                mContext.isScanning = false;
                btClear.setEnabled(true);
                btStop.setEnabled(false);
                InventoryLoop.setEnabled(false);
                btInventory.setEnabled(false);
                btInventoryPerMinute.setEnabled(false);

            }
        }
    }

    public void startThread() {
        if (isRuning) {
            return;
        }
        isRuning = true;

        new TagThread().start();
    }

    class TagThread extends Thread {

        public void run() {
            Message msg = handler.obtainMessage(FLAG_START);
            if (mContext.uhf.startInventoryTag()) {
                loopFlag = true;
                mContext.isScanning = true;
                mStrTime = System.currentTimeMillis();
                msg.arg1 = FLAG_SUCCESS;
            } else {
                msg.arg1 = FLAG_FAIL;
            }
            handler.sendMessage(msg);
            isRuning = false;//执行完成设置成false
            long startTime=System.currentTimeMillis();
            while (loopFlag) {
                List<UHFTAGInfo> list = getUHFInfo();
                if(list==null || list.size()==0){
                    SystemClock.sleep(1);
                }else{
                    Utils.playSound(1);
                    handler.sendMessage(handler.obtainMessage(FLAG_UHFINFO_LIST, list));
                }
                if(System.currentTimeMillis()-startTime>100){
                    startTime=System.currentTimeMillis();
                    handler.sendEmptyMessage(FLAG_UPDATE_TIME);
                }

            }
            stopInventory();
        }
    }

    private synchronized   List<UHFTAGInfo> getUHFInfo() {
        List<UHFTAGInfo> list=null;
        if(mContext.isSupportRssi){
            //La placa base antigua solo necesita llamar a readTagFromBufferList_EpcTidUser para generar RSSI
            list = mContext.uhf.readTagFromBufferList_EpcTidUser();
        }else {
            //La función readTagFromBufferList de la placa base del lector versión 2.20-2.29 admite la salida Rssi, no es necesario llamar a readTagFromBufferList_EpcTidUser
           list = mContext.uhf.readTagFromBufferList();
        }
        return list;
    }


    void insertTag(UHFTAGInfo info, int index){

        String data=info.getEPC();
        if(!TextUtils.isEmpty(info.getTid())){
            StringBuilder stringBuilder=new StringBuilder();
            stringBuilder.append("EPC:");
            stringBuilder.append(info.getEPC());
            stringBuilder.append("\n");
            stringBuilder.append("TID:");
            stringBuilder.append(info.getTid());
            if(!TextUtils.isEmpty(info.getUser())){
                stringBuilder.append("\n");
                stringBuilder.append("USER:");
                stringBuilder.append(info.getUser());
            }
            data=stringBuilder.toString();
        }
        StringBuilder stringBuilder=new StringBuilder();
        tagMap = new HashMap<>();
        tagMap.put(MainActivity.TAG_EPC, info.getEPC());
        tagMap.put(MainActivity.TAG_DATA, data);
        tagMap.put(MainActivity.TAG_COUNT, valueOf(1));
        tagMap.put(MainActivity.TAG_RSSI, info.getRssi()==null?"":info.getRssi());
        //mostrar datos
        Log.d("HENRY",  tagMap.toString());
        tagList.add(index, tagMap);
        tempDatas.add(index,info.getEpcBytes());
        adapter.notifyDataSetChanged();
        tv_count.setText(valueOf(adapter.getCount()));
        tv_total.setText(valueOf(++total));
    }
    private void addEPCToList(List<UHFTAGInfo> list) {
        for(int i = 0; i < list.size(); i++){
            UHFTAGInfo info=list.get(i);
            byte[] tag_data = info.getEpcBytes();
            int startIndex = 0;
            int endIndex = tempDatas.size();
            int judgeIndex;
            if (endIndex == 0){
                insertTag(info, 0);
                continue;
            }
            endIndex --;
            while (true){
                judgeIndex = (startIndex + endIndex)/2;
                int ret = compareBytes(tag_data, tempDatas.get(judgeIndex));
                if (ret > 0){
                    if (judgeIndex == endIndex){
                        insertTag(info, judgeIndex+1);
                        break;
                    }
                    startIndex = judgeIndex+1;
                } else if (ret < 0){
                    if (judgeIndex == startIndex){
                        insertTag(info, judgeIndex);
                        break;
                    }
                    endIndex = judgeIndex-1;
                } else {
                    String data=info.getEPC();
                    if(!TextUtils.isEmpty(info.getTid())){
                        StringBuilder stringBuilder=new StringBuilder();
                        stringBuilder.append("EPC:");
                        stringBuilder.append(info.getEPC());
                        stringBuilder.append("\n");
                        stringBuilder.append("TID:");
                        stringBuilder.append(info.getTid());
                        if(!TextUtils.isEmpty(info.getUser())){
                            stringBuilder.append("\n");
                            stringBuilder.append("USER:");
                            stringBuilder.append(info.getUser());
                        }
                        data=stringBuilder.toString();
                        tagMap.put(MainActivity.TAG_DATA, data);
                    }

                    tagMap = tagList.get(judgeIndex);
                    int tagCount = Integer.parseInt(tagMap.get(MainActivity.TAG_COUNT), 10) + 1;
                    tagMap.put(MainActivity.TAG_COUNT, valueOf(tagCount));
                    tagMap.put(MainActivity.TAG_RSSI, info.getRssi()==null?"":info.getRssi());
                    tv_total.setText(valueOf(++total));
                    adapter.notifyDataSetChanged();
                    break;
                }
            }
        }

    }


    //return 1,2 b1>b2
    //return -1,-2 b1<b2
    //retrurn 0;b1 == b2
    public static int compareBytes(byte[] b1, byte[] b2){
        int len = b1.length < b2.length?b1.length:b2.length;
        int value1;
        int value2;
        for (int i = 0; i < len; i++){
            value1 = b1[i]&0xFF;
            value2 = b2[i]&0xFF;
            if (value1 > value2){
                return 1;
            } else if (value1 < value2){
                return -1;
            }
        }
        if (b1.length > b2.length){
            return 2;
        } else if (b1.length < b2.length){
            return -2;
        }else {
            return 0;
        }
    }





    private Timer mTimer = new Timer();
    private TimerTask mInventoryPerMinuteTask;
    private long period = 6 * 1000; // 每隔多少ms
    private String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "BluetoothReader" + File.separator;
    private String fileName;
    private void inventoryPerMinute() {
        cancelInventoryTask();
        btInventoryPerMinute.setEnabled(false);
        btInventory.setEnabled(false);
        InventoryLoop.setEnabled(false);
        btStop.setEnabled(true);
        mContext.isScanning = true;
        fileName = path + "battery_" + DateUtils.getCurrFormatDate(DateUtils.DATEFORMAT_FULL) + ".txt";
        mInventoryPerMinuteTask = new TimerTask() {
            @Override
            public void run() {
                String data = DateUtils.getCurrFormatDate(DateUtils.DATEFORMAT_FULL) + "\t电量：" + mContext.uhf.getBattery() + "%\n";
                FileUtils.writeFile(fileName, data, true);
                inventory();
            }
        };
        mTimer.schedule(mInventoryPerMinuteTask, 0, period);
    }

    private void cancelInventoryTask() {
        if(mInventoryPerMinuteTask != null) {
            mInventoryPerMinuteTask.cancel();
            mInventoryPerMinuteTask = null;
        }
    }

    private void inventory() { // ESTUDIAR ESTE METODO PARA LA BUSQUEDA UNO A UNO, YA QUE ESTE ES (SINGLE)
        mStrTime = System.currentTimeMillis();
        UHFTAGInfo info = mContext.uhf.inventorySingleTag();
        if (info != null) {
            Message msg = handler.obtainMessage(FLAG_UHFINFO);
            msg.obj = info;
            handler.sendMessage(msg);
        }
        handler.sendEmptyMessage(FLAG_UPDATE_TIME);
    }

    private void cargarBDSQLite(){
        for (int i = 0; i < listaCsv.size() ; i++) {
            Log.d(TAG, "cargarBDSQLite: "+ listaCsv.get(i));

        }
    }



    private Toast mToast;
    public void showToast(String text) {
        if(mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(getContext(), text, Toast.LENGTH_SHORT);
        mToast.show();
    }

    public void showToast(int resId) {
        showToast(getString(resId));
    }
    //-----------------------------
    private int  selectIndex=-1;
    public final class ViewHolder {
        public TextView tvEPCTID;
        public TextView tvTagCount;
        public TextView tvTagRssi;
    }

    public class MyAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        public MyAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }
        public int getCount() {
            // TODO Auto-generated method stub
            return tagList.size();
        }
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return tagList.get(arg0);
        }
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.listtag_items, null);
                holder.tvEPCTID = (TextView) convertView.findViewById(R.id.TvTagUii);
                holder.tvTagCount = (TextView) convertView.findViewById(R.id.TvTagCount);
                holder.tvTagRssi = (TextView) convertView.findViewById(R.id.TvTagRssi);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tvEPCTID.setText((String) tagList.get(position).get(MainActivity.TAG_DATA));
            holder.tvTagCount.setText((String) tagList.get(position).get(MainActivity.TAG_COUNT));
            holder.tvTagRssi.setText((String) tagList.get(position).get(MainActivity.TAG_RSSI));

            if (position == selectIndex) {
                convertView.setBackgroundColor(mContext.getResources().getColor(R.color.blue));
            }
            else {
                convertView.setBackgroundColor(Color.TRANSPARENT);
            }
            return convertView;
        }

    }




    private void addToListNombresBuilding(String name) {
        buildingNames.add(name);
        String aux = "";
        for (int i = 0; i < buildingNames.size(); i++) {
            aux = aux + "\n "+ buildingNames.get(i);
        }
        //Toast.makeText(mContext, aux, Toast.LENGTH_SHORT).show();
    }



}
