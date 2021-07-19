package com.example.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.uhf_bt.ConnectionSQLiteHelper;
import com.example.uhf_bt.LoginActivity;
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
public class User {

    private int id;
    private String nombre;
    private String clave;

    public static int actualUser = 0;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + nombre + '\'' +
                ", clave='" + clave + '\'' +
                '}';
    }

    public static void registroUser(String nombre, String password, Context context) {
        ConnectionSQLiteHelper conn = new ConnectionSQLiteHelper(context, "bdUser", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(utilidades.USER_NOMBRE, nombre);
        values.put(utilidades.USER_PASSWORD, password);
        db.insert(utilidades.TABLA_USER, utilidades.USER_ID, values);
        db.close();
    }

    public static boolean contains(ArrayList<User> users, int id) {
        for (User user : users) {
            if (user.id == id)
                return true;
        }
        return false;
    }

    public static boolean existeUser(String name, String pass, Context context) {
        ConnectionSQLiteHelper conn = new ConnectionSQLiteHelper(context, "bdUser", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] parametros = {name};
        String[] campos = {utilidades.USER_NOMBRE, utilidades.USER_PASSWORD, utilidades.USER_ID};
        String resUser = "";
        String resPass = "";
        try {
            Cursor cursor = db.query(utilidades.TABLA_USER, campos, utilidades.USER_NOMBRE + "=?", parametros, null, null, null);
            cursor.moveToFirst();
            resUser = cursor.getString(0);
            resPass = cursor.getString(1);
            actualUser = cursor.getInt(2);
            cursor.close();
            db.close();
            if (resUser.equals(name) && resPass.equals(pass)) {
                return true;
            }
        } catch (Exception e) {
            db.close();
        }
        return false;
    }

}
