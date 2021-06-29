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
public class Room {

    private String id;
    private String name;
    private int idBuilding;

    public static ArrayList<Room> getRooms(MainActivity mContext) {
        ConnectionSQLiteHelper conn = new ConnectionSQLiteHelper(mContext, "bdUser", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        Room ROOM;
        ArrayList<Room> roomList = new ArrayList<Room>();
        try {
            Cursor cursor = db.rawQuery("select * from " + utilidades.TABLA_ROOM, null);
            while (cursor.moveToNext()) {
                ROOM = new Room();
                ROOM.setId(cursor.getString(0));
                ROOM.setName(cursor.getString(1));
                ROOM.setIdBuilding(Integer.parseInt(cursor.getString(2)));
                Log.d("Room id", ROOM.getId() + " ");
                Log.d("Room Name", ROOM.getName());
                Log.d("Room fkBuildingId ", ROOM.getIdBuilding() + "");
                roomList.add(ROOM);
            }
            cursor.close();
            db.close();

        } catch (Exception e) {
            Log.d("getRooms()", "ERROR");
            db.close();
        }
        return roomList;
    }
}
