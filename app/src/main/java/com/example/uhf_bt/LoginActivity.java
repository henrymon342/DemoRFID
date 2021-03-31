package com.example.uhf_bt;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uhf_bt.Utilidades.utilidades;
import no.nordicsemi.android.dfu.BuildConfig;
import androidx.constraintlayout.widget.ConstraintLayout;

public class LoginActivity extends BaseActivity {
    private Button btn_login,btn_registro;
    private TextView correo,password;
    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ConectionSQLiteHelper conn=new ConectionSQLiteHelper(this,"bdUser",null,1);
        btn_login=(Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setContentView(MainActivity);
                logear();
            }
        });

        btn_registro=(Button)findViewById(R.id.btn_registro);
        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar();
            }
        });
    }

    public void logear(){
        Intent loginIntend= new Intent(this,PrincipalActivity.class);
        correo=findViewById(R.id.correo);
        password=findViewById(R.id.password);
        Log.d("RESPUESTA1",  correo.getText().toString());
        Log.d("RESPUESTA2",  password.getText().toString());
        ConectionSQLiteHelper conn=new ConectionSQLiteHelper(this,"bdUser",null,1);
        SQLiteDatabase db= conn.getReadableDatabase();
        String[] parametros={correo.getText().toString()};
        String[] campos={utilidades.CAMPO_EMAIL,utilidades.CAMPO_PASSWORD};


        try {
            Log.d("RESPUESTA3",  "entro pero no funciono");
            Cursor cursor = db.query(utilidades.TABLA_USUARIO,campos,utilidades.CAMPO_EMAIL+"=?",parametros,null,null,null);
            cursor.moveToFirst();
            String email=cursor.getString(0);
            String pass=cursor.getString(1);
            cursor.close();
            if(correo.getText().toString().equals(email)&&password.getText().toString().equals(pass)){
                startActivity(loginIntend);
            }else{
                Toast.makeText(this, "usuario o constraseña invalido", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Log.d("RESPUESTA4",  "nunca entro");
            Toast.makeText(getApplicationContext(),"el usuario o contraseña son incorrectos",Toast.LENGTH_LONG).show();
            correo.setText("");
            password.setText("");
        }
    }
    public void registrar(){

        ConectionSQLiteHelper conn=new ConectionSQLiteHelper(this,"bdUser",null,1);
        SQLiteDatabase db=conn.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(utilidades.CAMPO_ID,"u3");
        values.put(utilidades.CAMPO_NOMBRE,"pepe");
        values.put(utilidades.CAMPO_EMAIL,"pepe@gmail.com");
        values.put(utilidades.CAMPO_PASSWORD,"1234");

        Long idResultante=db.insert(utilidades.TABLA_USUARIO,utilidades.CAMPO_ID,values);

        Toast.makeText(getApplicationContext(),"Id Registro: "+idResultante,Toast.LENGTH_SHORT).show();
        db.close();
    }



}
