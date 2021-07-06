package com.example.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.uhf_bt.ConnectionSQLiteHelper;
import com.example.uhf_bt.LoginActivity;
import com.example.uhf_bt.SincronizarActivity;
import com.example.uhf_bt.Utilidades.utilidades;

import java.util.ArrayList;
import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

    private int id;
    private String nombre;
    private String clave;


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + nombre + '\'' +
                ", clave='" + clave + '\'' +
                '}';
    }

    // method for LoginActivity
    public static void registroUser(String nombre, String password, LoginActivity context) {
        ConnectionSQLiteHelper conn = new ConnectionSQLiteHelper(context, "bdUser", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(utilidades.CAMPO_NOMBRE, nombre);
        values.put(utilidades.CAMPO_PASSWORD, password);
        Long idResultante = db.insert(utilidades.TABLA_USUARIO, utilidades.CAMPO_ID_USER, values);
        Toast.makeText(context, "Id Registro: " + idResultante, Toast.LENGTH_SHORT).show();
        db.close();
    }
    // method for SincronizarActivity
    public static void registroUser(String nombre, String password, SincronizarActivity context) {
        ConnectionSQLiteHelper conn = new ConnectionSQLiteHelper(context, "bdUser", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(utilidades.CAMPO_NOMBRE, nombre);
        values.put(utilidades.CAMPO_PASSWORD, password);
        Long idResultante = db.insert(utilidades.TABLA_USUARIO, utilidades.CAMPO_ID_USER, values);
        Toast.makeText(context, "Id Registro: " + idResultante, Toast.LENGTH_SHORT).show();
        db.close();
    }

    public static boolean contains(ArrayList<User> users, int id) {
        for (User user : users) {
            if (user.id==id)
                return true;
        }
        return false;
    }


}
