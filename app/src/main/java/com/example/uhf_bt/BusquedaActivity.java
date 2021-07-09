package com.example.uhf_bt;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.uhf_bt.Utilidades.utilidades;
import com.example.uhf_bt.entidades.stockList;

import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;

public class BusquedaActivity extends BaseActivity {
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
                pedirUbicacion();
                //getRfidSQLite();
            }
        });

    }




    private void pedirUbicacion() {
        String array[] = {"henry", "miranda", "choque"};
        int itemSelected = 0;
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Select your gender")
                .setSingleChoiceItems(array, itemSelected, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                            Log.d("DIALOG", selectedIndex+"");
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogo, int id) {
                                Toast.makeText(BusquedaActivity.this, "bien", Toast.LENGTH_SHORT).show();
                            }})
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo, int id) {
                        Toast.makeText(BusquedaActivity.this, "mal", Toast.LENGTH_SHORT).show();
                    }})
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
