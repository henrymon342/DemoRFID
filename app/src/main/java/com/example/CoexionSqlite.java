package com.example;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CoexionSqlite extends SQLiteOpenHelper {

    final String CREAR_TABLA_USUARIO = "CREATE TABLE usuarios (id INTERGER, nombre TEXT, apellido TEXT, correo TEXT, password TEXT)";


    public CoexionSqlite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREAR_TABLA_USUARIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        db.execSQL(CREAR_TABLA_USUARIO);
    }
}
