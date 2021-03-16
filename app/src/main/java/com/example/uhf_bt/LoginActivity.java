package com.example.uhf_bt;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import no.nordicsemi.android.dfu.BuildConfig;
import androidx.constraintlayout.widget.ConstraintLayout;

public class LoginActivity extends BaseActivity {
    private Button btn_login;
    private TextView correo,password;
    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        btn_login=(Button)findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setContentView(MainActivity);
                logear();
            }
        });
    }

    public void logear(){
        Intent loginIntend= new Intent(this,MainActivity.class);
        correo=findViewById(R.id.correo);
        password=findViewById(R.id.password);
        Log.d("RESPUESTA1",  correo.getText().toString());
        Log.d("RESPUESTA2",  password.getText().toString());
        if(correo.getText().toString().equals("admin@admin")&&password.getText().toString().equals("1234")){
            startActivity(loginIntend);
        }else{
            Toast.makeText(this, "usuario o constraseña invalido", Toast.LENGTH_SHORT).show();
        }

    }



}
