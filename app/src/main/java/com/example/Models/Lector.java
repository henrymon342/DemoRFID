package com.example.Models;

import android.content.ContentValues;
import android.content.Context;
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
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Lector {
    private int id;
    private String alias;
    private String marca;
    private String modelo;
    private String desc;
    private String macAddr;
    private int id_net;

    public static void registroLector(String alias, String marca,String modelo,String description,String macAddress,int id_net, Context context) {
        ConnectionSQLiteHelper conn = new ConnectionSQLiteHelper(context, "bdUser", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(utilidades.LECTOR_ALIAS, alias);
        values.put(utilidades.LECTOR_MARCA, marca);
        values.put(utilidades.LECTOR_MODELO, modelo);
        values.put(utilidades.LECTOR_DESCRIPTION, description);
        values.put(utilidades.LECTOR_MAC, macAddress);
        values.put(utilidades.LECTOR_NET_ID, id_net);
        db.insert(utilidades.TABLA_LECTOR, utilidades.LECTOR_ID, values);
        db.close();
    }

        public static ArrayList<Lector> getLectors(Context mContext) {
        ConnectionSQLiteHelper conn = new ConnectionSQLiteHelper(mContext, "bdUser", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        Lector LECTOR;
        ArrayList<Lector> lectorList = new ArrayList<>();
        try {
            Cursor cursor = db.rawQuery("select * from " + utilidades.TABLA_LECTOR, null);
            while (cursor.moveToNext()) {
                LECTOR = new Lector();
                LECTOR.setId(cursor.getInt(0));
                LECTOR.setAlias(cursor.getString(1));
                LECTOR.setMarca(cursor.getString(2));
                LECTOR.setModelo(cursor.getString(3));
                LECTOR.setDesc(cursor.getString(4));
                LECTOR.setMacAddr(cursor.getString(5));
                LECTOR.setId_net(cursor.getInt(6));
                lectorList.add(LECTOR);
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            Log.d("getLectors()", "ERROR");
            db.close();
        }
        return lectorList;
    }
}
