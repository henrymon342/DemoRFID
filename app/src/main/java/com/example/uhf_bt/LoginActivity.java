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
import com.example.Backend.APIUtils;
import com.example.Backend.Interfaces.UserService;
import com.example.Models.User;
import com.example.uhf_bt.Utilidades.utilidades;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends BaseActivity {

    final String URL = "http://a2a256f3b766.ngrok.io";
    UserService userService;
    List<User> listUs;

    private Boolean swGlobal = false;
    private Boolean swUpdate = false;

    private Button btn_login,btn_registro,btn_sqlite;
    private TextView nombre,password;
    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ConectionSQLiteHelper conn=new ConectionSQLiteHelper(this,"bdUser",null,1);
        btn_login=(Button)findViewById(R.id.btn_login);
        nombre=findViewById(R.id.nombre);
        password=findViewById(R.id.password);
        btn_registro=(Button)findViewById(R.id.btn_registro);
        btn_sqlite=(Button)findViewById(R.id.btn_sqlite);

        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarDatosUsuarioDeDotNet();
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logear();
            }
        });
        btn_sqlite.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              registrarUsuarioSQLite();
          }
        });
        //codigoHenry
        userService = APIUtils.getUserService();

    }

    public void logear(){
        Intent loginIntend= new Intent(this,PrincipalActivity.class);
        Boolean estaEnSQLite=false;
        if (estaEnUsuarioSQLite(nombre.getText().toString(),password.getText().toString()))  {
            startActivity(loginIntend);
            nombre.setText("");
            password.setText("");
            estaEnSQLite=true;
        }
        nombre.setText("");
        password.setText("");
        if (!estaEnSQLite){
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
                            //registrarUsuarioSQLite(listUs.get(i));
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

    private void registrarUsuarioSQLite() {
        ConectionSQLiteHelper conn=new ConectionSQLiteHelper(this,"bdUser",null,1);
        SQLiteDatabase db=conn.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(utilidades.CAMPO_NOMBRE,nombre.getText().toString());
        values.put(utilidades.CAMPO_PASSWORD,password.getText().toString());
        Long idResultante = db.insert(utilidades.TABLA_USUARIO,utilidades.CAMPO_ID_USER,values);
        Toast.makeText(getApplicationContext(),"Id Registro: "+idResultante,Toast.LENGTH_SHORT).show();
        db.close();
    }
    private boolean estaEnUsuarioSQLite(String name,String pass){
        ConectionSQLiteHelper conn=new ConectionSQLiteHelper(this,"bdUser",null,1);
        SQLiteDatabase db=conn.getReadableDatabase();
        String[] parametros={name};
        String[] campos={utilidades.CAMPO_NOMBRE,utilidades.CAMPO_PASSWORD};
        String resUser="";
        String resPass="";
        try {
            Cursor cursor=db.query(utilidades.TABLA_USUARIO,campos,utilidades.CAMPO_NOMBRE+"=?",parametros,null,null,null);
            cursor.moveToFirst();
            resUser=cursor.getString(0);
            resPass=cursor.getString(1);
            Log.d("LOGEO user", resUser);
            Log.d("LOGEO pass", resPass);
            cursor.close();
            db.close();
            if (resUser.equals(name)&& resPass.equals(pass) ){
                return true;
            }
        }catch (Exception e){
            db.close();
        }
        return false;
    }
    private void eliminar(){
        ConectionSQLiteHelper conn=new ConectionSQLiteHelper(this,"bdUser",null,1);
        SQLiteDatabase db=conn.getWritableDatabase();
        String delete = "DROP TABLE "+utilidades.TABLA_USUARIO;
        db.execSQL(delete);
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
                            //registrarUsuarioSQLite(listUs.get(i));
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





}
