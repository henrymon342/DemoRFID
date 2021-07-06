package com.example.uhf_bt;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.Interfaces.BuildingInterface;
import com.example.Interfaces.LogeoInterface;
import com.example.Interfaces.RoomInterface;
import com.example.Interfaces.StockInterface;
import com.example.Models.AsignacionLector;
import com.example.Models.Building;
import com.example.Models.Room;
import com.example.Models.Stock;
import com.example.Models.User;
import com.example.uhf_bt.Utilidades.GLOBAL;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SincronizarActivity extends BaseActivity {
    private Button btn_atras;
    private Button btn_syncToDB, btn_syncFromDB;
    private ArrayList<Building> buildingsArrayList = new ArrayList<>();
    private ArrayList<Room> roomsArrayList = new ArrayList<>();
    private ArrayList<User> usersArrayList = new ArrayList<>();
    private ArrayList<Stock> stocksArrayList;
    private ArrayList<AsignacionLector> asignacionLectorsArrayList;

    private Retrofit retrofit;
    BuildingInterface buildingInterface;

    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sincronizar);
        btn_atras = findViewById(R.id.btn_atrasBusqueda);
        btn_syncFromDB = findViewById(R.id.btn_syncFromDB);
        btn_syncToDB = findViewById(R.id.btn_syncToDB);
        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_syncToDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Preparamos el array para enviarlo a la BD .NET
                // PARA BORRAR asignacionLectorsArrayList = new ArrayList<>(AsignacionLector.getAsignacionLector(SincronizarActivity.this));
                /*
                Enviamos el array
                 */
            }
        });
        btn_syncFromDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // metodos que llenan los arrayList correspondientes
                getBuildingsDotNet();
                getRoomsDotNet();
                getUsersDotNet();
                getStocksDotNet();
                getLectorsDotNet();

            /*
                llenar los arrays con info de la BD .NET

                buildingsArrayList =
                roomsArrayList =
                usersArrayList =
                stocksArrayList =
            */

                // Vaciamos la base de datos SQLite
                ConnectionSQLiteHelper.deleteBuildingsData(SincronizarActivity.this);
                ConnectionSQLiteHelper.deleteRoomsData(SincronizarActivity.this);
                ConnectionSQLiteHelper.deleteStocksData(SincronizarActivity.this);
                ConnectionSQLiteHelper.deleteUsersData(SincronizarActivity.this);

                // Llenamos los datos en la base de datos SQLite
                /*
                for (Building building : buildingsArrayList) {
                    Building.registroBuilding(building.getName(), SincronizarActivity.this);
                }
                for (Room room : roomsArrayList) {
                    Room.registroRoom(room.getBuildingId(), room.getName(), SincronizarActivity.this);
                }
                for (User user : usersArrayList) {
                    User.registroUser(user.getName(), user.getClave(), SincronizarActivity.this);
                }
                for (Stock stock : stocksArrayList) {
                    Stock.registroStock(stock.getEpc(), stock.getTid(), stock.getUserMemory(), stock.getDescription(), stock.getLastScanDate(), stock.getIdRoom(), SincronizarActivity.this);
                }

                 */
            }
        });
    }

    private void getLectorsDotNet() {
        retrofit = this.construirRetrofit();
        StockInterface stockInterface = retrofit.create(StockInterface.class);
        Call<List<Stock>> call = stockInterface.getStocks();
        call.enqueue(new Callback<List<Stock>>() {
            @Override
            public void onResponse(Call<List<Stock>> call, Response<List<Stock>> response) {
                if (response.isSuccessful()) {
                    Log.d("RESPONSE->   ", String.valueOf(response.body()));
                    stocksArrayList = new ArrayList<>(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Stock>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    private void getStocksDotNet() {
        retrofit = this.construirRetrofit();
        StockInterface stockInterface = retrofit.create(StockInterface.class);
        Call<List<Stock>> call = stockInterface.getStocks();
        call.enqueue(new Callback<List<Stock>>() {
            @Override
            public void onResponse(Call<List<Stock>> call, Response<List<Stock>> response) {
                if (response.isSuccessful()) {
                    Log.d("RESPONSE->   ", String.valueOf(response.body()));
                    stocksArrayList = new ArrayList<>(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Stock>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    private void getUsersDotNet() {
        retrofit = this.construirRetrofit();
        LogeoInterface logeoInterface = retrofit.create(LogeoInterface.class);
        Call<List<User>> call = logeoInterface.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    Log.d("RESPONSE->   ", String.valueOf(response.body()));
                    usersArrayList = new ArrayList<>(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    private void getRoomsDotNet() {
        retrofit = this.construirRetrofit();
        RoomInterface roomInterface = retrofit.create(RoomInterface.class);
        Call<List<Room>> call = roomInterface.getRooms();
        call.enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if (response.isSuccessful()) {
                    Log.d("RESPONSE->   ", String.valueOf(response.body()));
                    roomsArrayList = new ArrayList<>(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    /*
    private void eliminarRoom(int id) {
        ConnectionSQLiteHelper conn = new ConnectionSQLiteHelper(this, "bdUser", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros = {id + ""};
        db.delete(utilidades.TABLA_ROOM, utilidades.CAMPO_ID_ROOM + "=?", parametros);
        Toast.makeText(getApplicationContext(), "room eliminado", Toast.LENGTH_LONG).show();
        db.close();
    }
    */

    private void getBuildingsDotNet(){
        retrofit = this.construirRetrofit();
        BuildingInterface buildingInterface = retrofit.create(BuildingInterface.class);
        Call<List<Building>> call = buildingInterface.getBuildings();
        call.enqueue(new Callback<List<Building>>() {
            @Override
            public void onResponse(Call<List<Building>> call, Response<List<Building>> response) {
                if (response.isSuccessful()) {
                    Log.d("RESPONSE->   ", String.valueOf(response.body()));
                    buildingsArrayList = new ArrayList<>(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Building>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    public Retrofit construirRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(GLOBAL.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
