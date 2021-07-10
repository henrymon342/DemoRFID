package com.example.Models;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.uhf_bt.ConnectionSQLiteHelper;
import com.example.uhf_bt.LoginActivity;
import com.example.uhf_bt.MainActivity;
import com.example.uhf_bt.SincronizarActivity;
import com.example.uhf_bt.Utilidades.utilidades;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Building {

    private  int id;
    private String name;

    @Override
    public String toString() {
        return "Building{" +
                "name='" + name + '\'' +
                '}';
    }

    public static ArrayList<Building> getBuildings(Context mContext) {
        ConnectionSQLiteHelper conn = new ConnectionSQLiteHelper(mContext, "bdUser", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        Building BUILDING;
        ArrayList<Building> buildingList = new ArrayList<>();
        try {
            Cursor cursor = db.rawQuery("select * from " + utilidades.TABLA_BUILDING, null);
            while (cursor.moveToNext()) {
                BUILDING = new Building();
                BUILDING.setId(cursor.getInt(0));
                BUILDING.setName(cursor.getString(1));
                Log.d("Building Id", BUILDING.getId() + " ");
                Log.d("Building Name", BUILDING.getName());
                buildingList.add(BUILDING);
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            Log.d("getBuildings()", "ERROR");
            db.close();
        }
        return buildingList;
    }

    public static void registroBuilding(String nombreEdificio, Context context) {
        ConnectionSQLiteHelper conn = new ConnectionSQLiteHelper(context, "bdUser", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(utilidades.BUILDING_NAME, nombreEdificio);
        Long idResultante = db.insert(utilidades.TABLA_BUILDING, utilidades.BUILDING_ID, values);
        Toast.makeText(context, "Id Building: " + idResultante, Toast.LENGTH_SHORT).show();
        db.close();
    }

}
