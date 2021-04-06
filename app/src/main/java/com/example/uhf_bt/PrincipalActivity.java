package com.example.uhf_bt;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PrincipalActivity extends BaseActivity{
    private Button btn_inventario,btn_busqueda,btn_missing,btn_sincronizar;
    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ConectionSQLiteHelper conn=new ConectionSQLiteHelper(this,"bdUser",null,1);
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
    }public void busqueda(){
        Intent mainIntend= new Intent(this,BusquedaActivity.class);
        startActivity(mainIntend);
    }
}
