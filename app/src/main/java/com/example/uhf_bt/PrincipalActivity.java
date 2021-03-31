package com.example.uhf_bt;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PrincipalActivity extends BaseActivity{
    private Button btn_inventario,btn_busqueda,btn_missing,btn_syncronize;
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
       /* btn_registro=(Button)findViewById(R.id.btn_registro);
        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar();
            }
        });*/
    }
    public void inventario(){
        Intent mainIntend= new Intent(this,MainActivity.class);
        startActivity(mainIntend);
    }
}
