package com.example.uhf_bt;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.Backend.APIUtils;
import com.example.Interfaces.BuildingInterface;
import com.example.Interfaces.LectorInterface;
import com.example.Interfaces.LogeoInterface;
import com.example.Interfaces.RoomInterface;
import com.example.Interfaces.StockInterface;
import com.example.Models.AssignationLector;
import com.example.Models.Building;
import com.example.Models.Lector;
import com.example.Models.Room;
import com.example.Models.Stock;
import com.example.Models.User;
import com.example.uhf_bt.Utilidades.GLOBAL;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private ArrayList<Stock> stocksArrayList = new ArrayList<>();
    private ArrayList<Lector> lectorsArrayList = new ArrayList<>();
    private ArrayList<AssignationLector> assignationLectorsArrayList = new ArrayList<>();

    private Retrofit retrofit;
    BuildingInterface buildingInterface;
    StockInterface stockInterface;
    StockInterface userService;

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
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                // Preparamos el array para enviarlo a la BD .NET
                /*
                    stocksArrayList = Stock.getStocks(SincronizarActivity.this);
                    sendBDDotNet(stocksArrayList);
                */

                assignationLectorsArrayList = new ArrayList<>(AssignationLector.getAssignationLector(SincronizarActivity.this));
                /*
                    completar
                 */
            }
        });

        btn_syncFromDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Vaciamos la base de datos SQLite
                ConnectionSQLiteHelper.deleteBuildingsData(SincronizarActivity.this);
                ConnectionSQLiteHelper.deleteRoomsData(SincronizarActivity.this);
                ConnectionSQLiteHelper.deleteStocksData(SincronizarActivity.this);
                ConnectionSQLiteHelper.deleteUsersData(SincronizarActivity.this);

                // metodos que llenan los arrayList correspondientes
                getBuildingsDotNet();
                getRoomsDotNet();
                getUsersDotNet();
                getStocksDotNet();
                getLectorsDotNet();
            }
        });
        userService = APIUtils.getUserService();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendBDDotNet(ArrayList<Stock> stocksArrayList) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String fecha = dtf.format(LocalDateTime.now());
        fecha = fecha.replace(" ", "T");
        Log.d("FECHA", fecha);
        for (Stock var : stocksArrayList) {
            Log.d("STOCKVAR", var.toString());
            com.example.uhf_bt.entidades.Stock s = new com.example.uhf_bt.entidades.Stock(var.getEpc(), var.getTid(), var.getDescription(), var.getIdRoom(), var.getUserMemory(), fecha);
            enviarStock(s);
        }

        //com.example.uhf_bt.entidades.Stock s = new com.example.uhf_bt.entidades.Stock("epcPRUEBA", "tidPRUEBA", "descriptionPRUEBA", 1, " ", fecha);

        //2021-07-09 11:23:22
        //2021-07-02T18:22:07
    }

    public void enviarStock(com.example.uhf_bt.entidades.Stock u) {
        Call<com.example.uhf_bt.entidades.Stock> call = userService.createStock(u);
        call.enqueue(new Callback<com.example.uhf_bt.entidades.Stock>() {
            @Override
            public void onResponse(Call<com.example.uhf_bt.entidades.Stock> call, Response<com.example.uhf_bt.entidades.Stock> response) {
                if (response.isSuccessful()) {
                    Log.d("COSA", String.valueOf(response.body()));
                    Toast.makeText(getApplicationContext(), "User created successfully!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<com.example.uhf_bt.entidades.Stock> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    private void getLectorsDotNet() {
        retrofit = this.construirRetrofit();
        LectorInterface lectorInterface = retrofit.create(LectorInterface.class);
        Call<List<Lector>> call = lectorInterface.getLectors();
        call.enqueue(new Callback<List<Lector>>() {
            @Override
            public void onResponse(Call<List<Lector>> call, Response<List<Lector>> response) {
                if (response.isSuccessful()) {
                    Log.d("RESPONSE->   ", String.valueOf(response.body()));
                    lectorsArrayList = new ArrayList<>(response.body());
                    for (Lector lector : lectorsArrayList) {
                        Lector.registroLector(lector.getAlias(), lector.getMarca(), lector.getModelo(), lector.getDesc(), lector.getMacAddr(), lector.getId(), SincronizarActivity.this);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Lector>> call, Throwable t) {
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
                    for (Stock stock : stocksArrayList) {
                        Stock.registroStock(stock.getEpc(), stock.getTid(), stock.getUserMemory(), stock.getDescription(), stock.getLastScanDate(), stock.getIdRoom(), SincronizarActivity.this);
                    }
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
                    for (User user : usersArrayList) {
                        User.registroUser(user.getNombre(), user.getClave(), user.getId(), SincronizarActivity.this);
                    }
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
                    for (Room room : roomsArrayList) {
                        Room.registroRoom(room.getBuildingId(), room.getName(), SincronizarActivity.this);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    private void getBuildingsDotNet() {
        retrofit = this.construirRetrofit();
        BuildingInterface buildingInterface = retrofit.create(BuildingInterface.class);
        Call<List<Building>> call = buildingInterface.getBuildings();
        call.enqueue(new Callback<List<Building>>() {
            @Override
            public void onResponse(Call<List<Building>> call, Response<List<Building>> response) {
                if (response.isSuccessful()) {
                    Log.d("RESPONSE->   ", String.valueOf(response.body()));
                    buildingsArrayList = new ArrayList<>(response.body());
                    for (Building building : buildingsArrayList) {
                        Building.registroBuilding(building.getName(), SincronizarActivity.this);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Building>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    public Retrofit construirRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(GLOBAL.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
