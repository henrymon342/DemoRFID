package com.example.uhf_bt;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.Models.AsignacionLector;
import com.example.Models.Building;
import com.example.Models.Room;
import com.example.Models.Stock;
import com.example.Models.User;


import java.util.ArrayList;

public class SincronizarActivity extends BaseActivity {
    private Button btn_atras;
    private Button btn_syncToDB, btn_syncFromDB;
    private ArrayList<Building> buildingsArrayList = new ArrayList<>();
    private ArrayList<Room> roomsArrayList = new ArrayList<>();
    private ArrayList<User> usersArrayList = new ArrayList<>();
    private ArrayList<Stock> stocksArrayList;
    private ArrayList<AsignacionLector> asignacionLectorsArrayList;

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
                asignacionLectorsArrayList = new ArrayList<>(AsignacionLector.getAsignacionLector(SincronizarActivity.this));
                /*
                Enviamos el array
                 */
            }
        });
        btn_syncFromDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*
                llenar los arrays con info de la BD .NET

                buildingsArrayList =
                roomsArrayList =
                usersArrayList =
                asignacionLectorArrayList =
            */

                // Vaciamos la base de datos SQLite
                ConnectionSQLiteHelper.deleteBuildingsData(SincronizarActivity.this);
                ConnectionSQLiteHelper.deleteRoomsData(SincronizarActivity.this);
                ConnectionSQLiteHelper.deleteStocksData(SincronizarActivity.this);
                ConnectionSQLiteHelper.deleteUsersData(SincronizarActivity.this);

                // Llenamos los datos en la base de datos SQLite
                for (Building building : buildingsArrayList) {
                    Building.registroBuilding(building.getName(), SincronizarActivity.this);
                }
                for (Room room : roomsArrayList) {
                    Room.registroRoom(room.getIdBuilding(), room.getName(), SincronizarActivity.this);
                }
                for (User user : usersArrayList) {
                    User.registroUser(user.getName(), user.getClave(), SincronizarActivity.this);
                }
                for (Stock stock : stocksArrayList) {
                    Stock.registroStock(stock.getEpc(), stock.getTid(), stock.getUserMemory(), stock.getDescription(), stock.getLastScanDate(), stock.getIdRoom(), SincronizarActivity.this);
                }
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
}
