package com.example.Models;

import android.content.ContentValues;
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
public class Room {

    private int id;
    private String name;
    private int buildingId;

    public static ArrayList<Room> getRooms(MainActivity mContext) {
        ConnectionSQLiteHelper conn = new ConnectionSQLiteHelper(mContext, "bdUser", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        Room ROOM;
        ArrayList<Room> roomList = new ArrayList<>();
        try {
            Cursor cursor = db.rawQuery("select * from " + utilidades.TABLA_ROOM, null);
            while (cursor.moveToNext()) {
                ROOM = new Room();
                ROOM.setId(cursor.getInt(0));
                ROOM.setName(cursor.getString(1));
                ROOM.setBuildingId(cursor.getInt(2));
                Log.d("Room id", ROOM.getId() + " ");
                Log.d("Room Name", ROOM.getName());
                Log.d("Room fkBuildingId ", ROOM.getBuildingId() + "");
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

    // method for LoginActivity
    public static void registroRoom(int fk_idBuilding, String nombreRoom, LoginActivity context) {
        ConnectionSQLiteHelper conn = new ConnectionSQLiteHelper(context, "bdUser", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(utilidades.ROOM_NAME, nombreRoom);
        values.put(utilidades.ROOM_FK_BUILDING, fk_idBuilding);
        Long idResultante = db.insert(utilidades.TABLA_ROOM, utilidades.ROOM_ID, values);
        Toast.makeText(context, "Id Room: " + idResultante, Toast.LENGTH_SHORT).show();
        db.close();
    }

    // method for SincronizarActivity
    public static void registroRoom(int fk_idBuilding, String nombreRoom, SincronizarActivity context) {
        ConnectionSQLiteHelper conn = new ConnectionSQLiteHelper(context, "bdUser", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(utilidades.ROOM_NAME, nombreRoom);
        values.put(utilidades.ROOM_FK_BUILDING, fk_idBuilding);
        Long idResultante = db.insert(utilidades.TABLA_ROOM, utilidades.ROOM_ID, values);
        Toast.makeText(context, "Id Room: " + idResultante, Toast.LENGTH_SHORT).show();
        db.close();
    }
}
