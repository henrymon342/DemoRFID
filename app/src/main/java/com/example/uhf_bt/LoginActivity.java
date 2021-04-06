package com.example.uhf_bt;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Backend.APIUtils;
import com.example.Backend.Interfaces.UserService;
import com.example.Interfaces.LogeoInterface;
import com.example.Models.User;
import com.example.uhf_bt.Utilidades.utilidades;
import com.example.uhf_bt.tool.ToastUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import no.nordicsemi.android.dfu.BuildConfig;
import androidx.constraintlayout.widget.ConstraintLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends BaseActivity {

    final String URL = "http://a2a256f3b766.ngrok.io";
    //final APIUtils urls = new APIUtils();
    //final String URL = urls.getApiUrl();


    UserService userService;
    List<User> listUs;

    private Boolean swGlobal = false;
    private Boolean swUpdate = false;

    private Button btn_login,btn_registro;
    private TextView nombre,password;
    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //ConectionSQLiteHelper conn=new ConectionSQLiteHelper(this,"bdUser",null,1);

        btn_login=(Button)findViewById(R.id.btn_login);
        nombre=findViewById(R.id.nombre);
        password=findViewById(R.id.password);
        btn_registro=(Button)findViewById(R.id.btn_registro);

        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //registrar();
                prueba();
                actualizarDatosUsuarioDeDotNet();
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setContentView(MainActivity);
                logear();
                //vaciarTablaUsuarios();  // vacia la tabla usuarios de la BD SQLite
            }
        });


        //codigoHenry
        userService = APIUtils.getUserService();

    }

    private void vaciarTablaUsuarios() {
        ConectionSQLiteHelper conn=new ConectionSQLiteHelper(this,"bdUser",null,1);
        SQLiteDatabase db=conn.getWritableDatabase();

        db.execSQL(utilidades.VACIAR_TABLA_USUARIO);

        db.close();
    }

    public void logear(){
        Boolean entro = false;
        Intent loginIntend= new Intent(this,PrincipalActivity.class);
        ArrayList<User> usuarios = new ArrayList<>();

        ConectionSQLiteHelper conn=new ConectionSQLiteHelper(this,"bdUser",null,1);
        SQLiteDatabase db= conn.getReadableDatabase();

        String[] parametros={nombre.getText().toString()};
        String[] campos={utilidades.CAMPO_ID,utilidades.CAMPO_NOMBRE,utilidades.CAMPO_EMAIL,utilidades.CAMPO_PASSWORD};

        try {
            Log.d("RESPUESTA3",  "entro pero no funciono");
            //Cursor cursor = db.query(utilidades.TABLA_USUARIO,campos, utilidades.CAMPO_ID+"=?",parametros,null,null,null);
            //Cursor cursor = db.query(utilidades.TABLA_USUARIO,campos,utilidades.CAMPO_EMAIL+"=?",parametros,null,null,null);


            Cursor c = db.rawQuery("SELECT name,password FROM user ", null);

            if (c.moveToFirst()){

                do {
                    Log.d("INDIVIDUO",  c.getString(0));
                    String corr = c.getString(0);
                    String pass = c.getString(1);
                    if(corr.equals(parametros[0]) && pass.equals(password.getText().toString())){
                        Log.d("Entro->", "entro");

                        Log.d("USER", pass);
                        entro = true;
                    }

                } while(c.moveToNext());
            }
            if(entro == true) {
                startActivity(loginIntend);
            }else{
                Toast.makeText(getApplicationContext(),"el usuario o contraseña son incorrectos",Toast.LENGTH_LONG).show();
                nombre.setText("");
                password.setText("");
            }

            c.close();
            db.close();

        }catch (Exception e){
            nombre.setText("");
            password.setText("");
            Log.d("RESPUESTA4",  "nunca entro");
            if(buscarBDDotNet(nombre.toString(), password.toString()) == true){
              startActivity(loginIntend);
            }else{
                Toast.makeText(getApplicationContext(),"el usuario o contraseña son incorrectos",Toast.LENGTH_LONG).show();
            }
        }
    }

    private Boolean buscarBDDotNet(final String correo, final String password) {
         Boolean sw = false;
        Call<List<User>> call = userService.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful()){
                    Log.d("RESPONSE->   ", String.valueOf(response.body()));
                    listUs = response.body();

                    for (int i = 0; i < listUs.size(); i++) {
                        Log.d("RESPUESTAAPIitem", listUs.get(i).getName());
                        Log.d("RESPUESTAAPIitem", listUs.get(i).getClave());
                        Log.d("RESPUESTAAPIitem", String.valueOf(listUs.get(i).getId()));
                        if(listUs.get(i).getName().equals(correo) && listUs.get(i).getClave().equals(password)){
                            registrarUsuarioSQLite(listUs.get(i));
                            swGlobal = true;
                            break;
                        }
                    }
                    swGlobal = false;
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
        return swGlobal;
    }

    private void registrarUsuarioSQLite(User user) {
        ConectionSQLiteHelper conn=new ConectionSQLiteHelper(this,"bdUser",null,1);
        SQLiteDatabase db=conn.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(utilidades.CAMPO_ID,user.getId());
        values.put(utilidades.CAMPO_NOMBRE,user.getName());
        values.put(utilidades.CAMPO_EMAIL,user.getName());
        values.put(utilidades.CAMPO_PASSWORD,user.getClave());

        Long idResultante = db.insert(utilidades.TABLA_USUARIO,utilidades.CAMPO_ID,values);

        Toast.makeText(getApplicationContext(),"Id Registro: "+idResultante,Toast.LENGTH_SHORT).show();
        db.close();
    }

    public void registrar(){

        ConectionSQLiteHelper conn=new ConectionSQLiteHelper(this,"bdUser",null,1);
        SQLiteDatabase db=conn.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(utilidades.CAMPO_ID,"u4");
        values.put(utilidades.CAMPO_NOMBRE,"juan");
        values.put(utilidades.CAMPO_EMAIL,"juan@gmail.com");
        values.put(utilidades.CAMPO_PASSWORD,"12345");

        Long idResultante=db.insert(utilidades.TABLA_USUARIO,utilidades.CAMPO_ID,values);

        Toast.makeText(getApplicationContext(),"Id Registro: "+idResultante,Toast.LENGTH_SHORT).show();
        db.close();

    }

    private void actualizarDatosUsuarioDeDotNet(){

        Log.d("URL", URL);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserService userService = retrofit.create(UserService.class);
        Call<List<User>> call = userService.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful()){
                    Log.d("RESPONSE->   ", String.valueOf(response.body()));
                    listUs = response.body();

                    for (int i = 0; i < listUs.size(); i++) {
                        Log.d("RESPUESTAAPIitem", listUs.get(i).getName());
                        Log.d("RESPUESTAAPIitem", listUs.get(i).getClave());
                        Log.d("RESPUESTAAPIitem", String.valueOf(listUs.get(i).getId()));
                        User aux = new User(listUs.get(i).getId(), listUs.get(i).getName(), listUs.get(i).getClave());
                        if(verSiEstaEnSQLite(listUs.get(i).getId())== false){
                            registrarUsuarioSQLite(listUs.get(i));
                            swUpdate = true;
                        }
                    }
                }
                if(swUpdate == true){
                    Toast.makeText(getApplicationContext(), "SE ACTUALIZO LA BD SQLITE", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "SQLITE NADA QUE ACTUALIZAR", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    private boolean verSiEstaEnSQLite(int ide) {
        ConectionSQLiteHelper conn=new ConectionSQLiteHelper(this,"bdUser",null,1);
        SQLiteDatabase db = conn.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT id FROM user ", null);
        if (c.moveToFirst()){
            do {
                String idd = c.getString(0);
                if(idd.equals(String.valueOf(ide))){
                    Log.d("LORA->", "son iguales "+ ide +" == "+ idd);
                    return true;
                }

            } while(c.moveToNext());
        }
        c.close();
        db.close();

        return false;
    }

    private void prueba(){
        //verSiEstaEnSQLite(8);
        ConectionSQLiteHelper conn=new ConectionSQLiteHelper(this,"bdUser",null,1);
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT id,password FROM user ", null);
        if (c.moveToFirst()){
            do {
                // Passing values
                String idd = c.getString(0);
                Log.d("LORA->", idd+ c.getString(1));
                // Do something Here with values
            } while(c.moveToNext());
        }
        c.close();
        db.close();
    }


    private void enviarSQLite(List<User> listUs) {

        ConectionSQLiteHelper conn = new ConectionSQLiteHelper(this,"bdUser",null,1);
        SQLiteDatabase db=conn.getWritableDatabase();

        for (int i = 0; i < listUs.size(); i++) {
            Log.d("RESPUESTAAPIitem", listUs.get(i).getName());
            Log.d("RESPUESTAAPIitem", listUs.get(i).getClave());
            Log.d("RESPUESTAAPIitem", String.valueOf(listUs.get(i).getId()));

            ContentValues values=new ContentValues();
            values.put(utilidades.CAMPO_ID, String.valueOf(listUs.get(i).getId()));
            values.put(utilidades.CAMPO_NOMBRE, listUs.get(i).getName());
            values.put(utilidades.CAMPO_EMAIL,listUs.get(i).getName());
            values.put(utilidades.CAMPO_PASSWORD,listUs.get(i).getClave());

            long idResultante = db.insert(utilidades.TABLA_USUARIO,utilidades.CAMPO_ID,values);
            Toast.makeText(this,"Id Registro: "+idResultante,Toast.LENGTH_SHORT).show();
            db.close();
        }
    }

}
