package com.example.uhf_bt;

import android.annotation.TargetApi;
import android.content.DialogInterface;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.Models.SearchItem;
import com.example.uhf_bt.Utilidades.VariablesGlobales;
import com.example.uhf_bt.entidades.stockList;
import com.rscja.deviceapi.RFIDWithUHFBLE;
import com.rscja.deviceapi.entity.UHFTAGInfo;
import java.util.ArrayList;
import androidx.appcompat.app.AlertDialog;


public class BusquedaActivity extends BaseActivity {

    String TAG = "UHF_LocationFragment";

    private long valorX;
    private long mStrTime;

    public RFIDWithUHFBLE uhf = RFIDWithUHFBLE.getInstance();
    final int FLAG_START = 0;//开始
    final int FLAG_STOP = 1;//停止
    final int FLAG_UPDATE_TIME = 2; // 更新时间
    final int FLAG_UHFINFO = 3;
    final int FLAG_UHFINFO_LIST = 5;
    final int FLAG_SUCCESS = 10;//成功
    final int FLAG_FAIL = 11;//失败

    private Button btn_atras, btn_getlist,btnlocate;
    private ListView listSearch;

    private EditText inputepc;
    ProgressBar progressBar;


    ArrayList<stockList> rfidList;
    private ArrayList<SearchItem> searchList = new ArrayList<>();

    ArrayAdapter<SearchItem> searchAdapter ;


    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.busqueda);



        listSearch = findViewById(R.id.lvitems);
        inputepc = findViewById(R.id.inputepc);
        btnlocate = findViewById(R.id.btnlocate);
        progressBar = findViewById(R.id.progressBar);

        progressBar.setMax(100);
        progressBar.setProgress(20);

        // Volver al menu
        btn_atras = findViewById(R.id.btn_atrasBusqueda);
        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_getlist = findViewById(R.id.btn_getlist);
        btn_getlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pedirUbicacion();
                getSearchList();
                for (int i = 0; i < searchList.size(); i++) {
                    Log.d("SEARCH", searchList.get(i).toString());
                }

                // MOSTRAR EL ARRAY "searchList" en el LIST-VIEW
                searchAdapter = new ArrayAdapter<SearchItem>(BusquedaActivity.this, android.R.layout.simple_list_item_1 , searchList);
                listSearch.setAdapter(searchAdapter);
                //getRfidSQLite();
            }
        });

        btnlocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLocation();
            }
        });




        listSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                inputepc.setText(searchList.get(position).getEpc());
                Toast.makeText(BusquedaActivity.this,listSearch.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {


                case FLAG_UPDATE_TIME:
                    float useTime = (System.currentTimeMillis() - mStrTime) / 1000.0F;
                    Log.d(TAG, "TIMMER "+ useTime);
                    //tv_time.setText(NumberTool.getPointDouble(loopFlag ? 1 : 3, useTime) + "s");
                    break;

                case FLAG_UHFINFO:
                    UHFTAGInfo info = (UHFTAGInfo) msg.obj;
                    //listAux.add(info);

                    Log.d(TAG, "TIMMER-RSSI "+ info.getRssi());
                    Log.d(TAG, "TIMMER-EPC "+ info.getEPC());
                    break;
            }
        }
    };

    private void pedirUbicacion() {
        String array[] = {"henry", "miranda", "choque"};
        int itemSelected = 0;
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Select your gender")
                .setSingleChoiceItems(array, itemSelected, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                        Log.d("DIALOG", selectedIndex + "");

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo, int id) {
                        Toast.makeText(BusquedaActivity.this, "bien", Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo, int id) {
                        Toast.makeText(BusquedaActivity.this, "mal", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
        dialog.setCanceledOnTouchOutside(false);

        /*
        new AlertDialog.Builder(this)
                .setTitle("Nuke planet Jupiter?")
                .setMessage("Note that nuking planet Jupiter will destroy everything in there.")
                .setPositiveButton("Nuke", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("MainActivity", "Sending atomic bombs to Jupiter");
                    }
                })
                .setNegativeButton("Abort", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("MainActivity", "Aborting mission...");
                    }
                })
                .show();

         */
    }

    private void getSearchList() {
        searchList = new ArrayList<>(SearchItem.getItems(this));
    }


    private void startLocation(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 50; i++) {

                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    valorX = inventory();
                    runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            Log.d("VALORX", String.valueOf(valorX));
                            int valorProgress = valorEnProgress(valorX);
                            progressBar.setProgress(valorProgress);
                        }
                    });

                    Log.d(TAG, "run: "+" contando");
                }
                Log.d(TAG, "run: "+" terminado!!!");
                progressBar.setProgress(0);
                // PARA CAMBIAR LA INTERFAZ

            }
        }).start();

    }

    public Long inventory() { // ESTUDIAR ESTE METODO PARA LA BUSQUEDA UNO A UNO, YA QUE ESTE ES (SINGLE)
        Long rssiValue = Long.valueOf(0);
        mStrTime = System.currentTimeMillis();
        UHFTAGInfo info = uhf.inventorySingleTag();
        if (info != null) {
            Message msg = handler.obtainMessage(FLAG_UHFINFO);
            msg.obj = info;
            Log.d("BUSQUEDA", info.getEPC());
            //listAux.add(info);
            if(info.getEPC().equals(inputepc.getText().toString())){
                Log.d(TAG, " ENCONTRADO...");

                rssiValue = Long.parseLong(info.getRssi().substring(0, 3))*(-1);
                Log.d("VALOR", rssiValue.toString());
            }

            Log.d("BUSQUEDA", info.getEPC());
            Log.d("BUSQUEDA", info.getRssi());
            //Log.d("BUSQUEDA", info.getTid());
            handler.sendMessage(msg);
        }
        handler.sendEmptyMessage(FLAG_UPDATE_TIME);
        return rssiValue;
    }

    private int valorEnProgress(long valorX) {

        int valor = (int) valorX;
        int asombroso = 100;
        int muy_bueno = 80;
        int de_acuerdo = 60;
        int no_es_bueno = 40;
        int inutilizable= 0;

        if(valor > 29 && valor < 67){
            Utils.playSound(1);
            Utils.playSound(1);
            Utils.playSound(1);
            return asombroso;
        }else if(valor > 66 && valor < 71){
            Utils.playSound(1);
            Utils.playSound(1);
            return muy_bueno;
        }else if(valor > 70 && valor < 81){
            Utils.playSound(1);
            return de_acuerdo;
        }else if(valor > 80 && valor < 91){
            Utils.playSound(1);
            return no_es_bueno;
        }else if(valor > 90 ){
            return inutilizable;
        }

        return -1;
    }
}
