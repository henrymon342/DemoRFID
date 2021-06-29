package com.example.Models;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.uhf_bt.ConnectionSQLiteHelper;
import com.example.uhf_bt.MainActivity;
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
}
