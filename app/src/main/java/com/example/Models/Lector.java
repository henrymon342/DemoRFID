package com.example.Models;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.uhf_bt.ConnectionSQLiteHelper;
import com.example.uhf_bt.LoginActivity;
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
    private String id;
    private String alias;
    private String marca;
    private String modelo;
    private String description;
    private String macAddress;


    // method for MainActivity
    public static void registroLector(String alias, String marca,String modelo,String description,String macAddress, MainActivity context) {
        ConnectionSQLiteHelper conn = new ConnectionSQLiteHelper(context, "bdUser", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(utilidades.CAMPO_ALIAS_lECTOR, alias);
        values.put(utilidades.CAMPO_MARCA_lECTOR, marca);
        values.put(utilidades.CAMPO_MODELO_lECTOR, modelo);
        values.put(utilidades.CAMPO_DESCRIPCION_lECTOR, description);
        values.put(utilidades.CAMPO_MAC_lECTOR, macAddress);
        db.insert(utilidades.TABLA_LECTOR, utilidades.CAMPO_ID_lECTOR, values);
        db.close();
    }
}
