package com.example.uhf_bt.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.uhf_bt.MainActivity;
import com.example.uhf_bt.NumberTool;
import com.example.uhf_bt.R;
import com.example.uhf_bt.Utils;
import com.example.uhf_bt.view.UhfLocationCanvasView;
import com.rscja.deviceapi.entity.UHFTAGInfo;
import com.rscja.deviceapi.interfaces.ConnectionStatus;
import com.rscja.deviceapi.interfaces.IUHF;
import com.rscja.deviceapi.interfaces.IUHFLocationCallback;
import com.rscja.utility.LogUtility;

import java.util.List;
import java.util.Random;

import static java.lang.String.valueOf;


public class UHFLocationFragment extends Fragment {

    private long mStrTime;

    private long valorX;

    List<UHFTAGInfo> listAux;

    final

    boolean isRuning = false;

    final int FLAG_START = 0;//开始
    final int FLAG_STOP = 1;//停止
    final int FLAG_UPDATE_TIME = 2; // 更新时间
    final int FLAG_UHFINFO = 3;
    final int FLAG_UHFINFO_LIST = 5;
    final int FLAG_SUCCESS = 10;//成功
    final int FLAG_FAIL = 11;//失败

    String TAG = "UHF_LocationFragment";
    private MainActivity mContext;
    private UhfLocationCanvasView llChart;

    ProgressBar progressBar1;
    private boolean loopFlag = false;

    FragmentActivity uhfContext;


    private EditText etEPC;
    private Button btStart,btStop;
    final int EPC=2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_uhflocation, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mContext = (MainActivity) getActivity();

        progressBar1 = (ProgressBar) mContext.findViewById(R.id.progressBar1);

        progressBar1.setMax(100);

        Log.d(TAG, " ACTIVIDAD BUSCADA: "+ ((Object)mContext).getClass().getSimpleName());
        etEPC=mContext.findViewById(R.id.etEPC);
        btStart=mContext.findViewById(R.id.btStart);
        btStop=mContext.findViewById(R.id.btStop);
        getView().post(new Runnable() {
            @Override
            public void run() {
                //llChart.clean();
            }
        });
        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLocation();
            }
        });
        btStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopLocation();
            }
        });

        getView().post(new Runnable() {
            @Override
            public void run() {
                if(mContext.selectEPC!=null && !mContext.selectEPC.equals("")){
                    etEPC.setText(mContext.selectEPC);
                }else{
                    etEPC.setText("");
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView");
        stopLocation();
        Log.i(TAG, "onDestroyView end");
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



                    getActivity().runOnUiThread(new Runnable(){
                        @Override
                        public void run(){

                            Log.d("VALORX", String.valueOf(valorX));
                            progressBar1.setProgress((int) valorX);

                        }
                    });






                    Log.d(TAG, "run: "+" contando");
                }
                Log.d(TAG, "run: "+" terminado!!!");

                // PARA CAMBIAR LA INTERFAZ

            }
        }).start();

    }

   public void stopLocation(){


       stopInventory();
       mContext.uhf.stopLocation();
       btStart.setEnabled(true);
       etEPC.setEnabled(true);
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

    private void actual(final String rssi) {

        getView().post(new Runnable() {
            @Override
            public void run() {

                progressBar1.setProgress(Integer.parseInt(rssi));
            }
        });
    }


    public Long inventory() { // ESTUDIAR ESTE METODO PARA LA BUSQUEDA UNO A UNO, YA QUE ESTE ES (SINGLE)
        Long rssiValue = Long.valueOf(0);
        mStrTime = System.currentTimeMillis();
        UHFTAGInfo info = mContext.uhf.inventorySingleTag();
        if (info != null) {
            Message msg = handler.obtainMessage(FLAG_UHFINFO);
            msg.obj = info;
            //listAux.add(info);

            if(info.getEPC().equals(etEPC.getText().toString())){
                Log.d(TAG, " ENCONTRADO...");
                Utils.playSound(1);
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




    private void stopInventory() {
        //isRuning= false;
        //loopFlag = false;
        //boolean result = mContext.uhf.stopInventory();
        //mostrarLista();
    }

    private void mostrarLista() {

        for (int i = 0; i < listAux.size() ; i++) {
            Log.d("BUSQUEDA-lista", listAux.get(i).getEPC());
            Log.d("BUSQUEDA-lista", listAux.get(i).getRssi());
            Log.d("BUSQUEDA-lista", listAux.get(i).getTid());
        }

    }

}
