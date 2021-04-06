package com.example.uhf_bt;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SincronizarActivity extends BaseActivity {
    private Button btn_atras;

    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sincronizar);
        final Intent atrasIntend= new Intent(this,PrincipalActivity.class);
        btn_atras=(Button)findViewById(R.id.btn_atrasSincro);
        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(atrasIntend);
            }
        });
    }
}
