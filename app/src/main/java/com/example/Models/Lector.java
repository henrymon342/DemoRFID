package com.example.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.uhf_bt.ConnectionSQLiteHelper;
import com.example.uhf_bt.MainActivity;
import com.example.uhf_bt.Utilidades.utilidades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Lector {
    private int id;
    private String alias;
    private String marca;
    private String modelo;
    private String description;
    private String macAddress;

    public static void registroLector(String alias, String marca,String modelo,String description,String macAddress, Context context) {
        ConnectionSQLiteHelper conn = new ConnectionSQLiteHelper(context, "bdUser", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(utilidades.LECTOR_ALIAS, alias);
        values.put(utilidades.LECTOR_MARCA, marca);
        values.put(utilidades.LECTOR_MODELO, modelo);
        values.put(utilidades.LECTOR_DESCRIPTION, description);
        values.put(utilidades.LECTOR_MAC, macAddress);
        db.insert(utilidades.TABLA_LECTOR, utilidades.LECTOR_ID, values);
        db.close();
    }
}
