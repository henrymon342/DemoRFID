package com.example.uhf_bt;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.uhf_bt.Utilidades.utilidades;

public class SincronizarActivity extends BaseActivity {
    private Button btn_atras;

    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sincronizar);
        btn_atras=findViewById(R.id.btn_atrasBusqueda);
        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void actualizarRoom(int idBuilding, String nombreRoom){
        ConnectionSQLiteHelper conn=new ConnectionSQLiteHelper(this,"bdUser",null,1);
        SQLiteDatabase db=conn.getWritableDatabase();
        String[] parametros={idBuilding+"",nombreRoom};
        ContentValues values=new ContentValues();
        values.put(utilidades.CAMPO_ROOM_NAME, nombreRoom);
        values.put(utilidades.CAMPO_FID_BUILDING, idBuilding);
        db.update(utilidades.TABLA_ROOM,values,utilidades.CAMPO_ID_ROOM+"=?",parametros);
        Toast.makeText(getApplicationContext(),"room actualizado",Toast.LENGTH_LONG).show();
        db.close();
    }
    private void eliminarRoom(int id){
        ConnectionSQLiteHelper conn=new ConnectionSQLiteHelper(this,"bdUser",null,1);
        SQLiteDatabase db=conn.getWritableDatabase();
        String[] parametros={id+""};
        db.delete(utilidades.TABLA_ROOM,utilidades.CAMPO_ID_ROOM+"=?",parametros);
        Toast.makeText(getApplicationContext(),"room eliminado",Toast.LENGTH_LONG).show();
        db.close();
    }
}
