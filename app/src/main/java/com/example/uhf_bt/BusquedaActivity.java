package com.example.uhf_bt;

import android.annotation.TargetApi;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.uhf_bt.Utilidades.utilidades;
import com.example.uhf_bt.entidades.stockList;

import java.util.ArrayList;

public class BusquedaActivity extends BaseActivity{
    private Button btn_atras,btn_getlist;
    ArrayList<stockList> rfidList;
    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.busqueda);
        // Volver al menu
        btn_atras=findViewById(R.id.btn_atrasBusqueda);
        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_getlist=findViewById(R.id.btn_getlist);
        btn_getlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRfidSQLite();
            }
        });

    }


    /*MODIFICAR*/
    private boolean getRfidSQLite(){
        ConnectionSQLiteHelper conn=new ConnectionSQLiteHelper(this,"bdUser",null,1);
        SQLiteDatabase db=conn.getReadableDatabase();
        stockList RFID;
        rfidList =new ArrayList<stockList>();
        try {
            Cursor cursor=db.rawQuery("select * from "+utilidades.TABLA_STOCK,null);
            while (cursor.moveToNext()){
                RFID=new stockList();
                RFID.setId(cursor.getString(0));
                RFID.setEPC(cursor.getString(1));
                RFID.setTID(cursor.getString(2));
                RFID.setUserMemory(cursor.getString(3));
                RFID.setDescription(cursor.getString(4));
                RFID.setLastScanDate(cursor.getString(5));
                //RFID.setIdInventario(cursor.getString(5));
                Log.d("id ",RFID.getId());
                Log.d("epc ",RFID.getEPC());
                Log.d("tid ",RFID.getTID());
                Log.d("count ",RFID.getUserMemory());
                Log.d("descripcion ",RFID.getDescription());
                Log.d("last scan ",RFID.getLastScanDate());
                //Log.d("fId ",RFID.getIdInventario());
                rfidList.add(RFID);
            }
            cursor.close();
            db.close();
            return true;
        }catch (Exception e){
            db.close();
        }
        return false;
    }
}
