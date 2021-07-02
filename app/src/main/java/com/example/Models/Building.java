package com.example.Models;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
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

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Building {

    private  int id;
    private String name;

    public static ArrayList<Building> getBuildings(MainActivity mContext) {
        ConnectionSQLiteHelper conn = new ConnectionSQLiteHelper(mContext, "bdUser", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        Building BUILDING;
        ArrayList<Building> buildingList = new ArrayList<Building>();
        try {
            Cursor cursor = db.rawQuery("select * from " + utilidades.TABLA_BUILDING, null);
            while (cursor.moveToNext()) {
                BUILDING = new Building();
                BUILDING.setId(Integer.parseInt(cursor.getString(0)));
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

    public static void registroBuilding(String nombreEdificio, LoginActivity context) {
        ConnectionSQLiteHelper conn = new ConnectionSQLiteHelper(context, "bdUser", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(utilidades.CAMPO_BUILDING_NAME, nombreEdificio);
        Long idResultante = db.insert(utilidades.TABLA_BUILDING, utilidades.CAMPO_ID_BUILDING, values);
        Toast.makeText(context, "Id Building: " + idResultante, Toast.LENGTH_SHORT).show();
        db.close();
    }
    public static void registroBuilding(String nombreEdificio, SincronizarActivity context) {
        ConnectionSQLiteHelper conn = new ConnectionSQLiteHelper(context, "bdUser", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(utilidades.CAMPO_BUILDING_NAME, nombreEdificio);
        Long idResultante = db.insert(utilidades.TABLA_BUILDING, utilidades.CAMPO_ID_BUILDING, values);
        Toast.makeText(context, "Id Building: " + idResultante, Toast.LENGTH_SHORT).show();
        db.close();
    }

}
