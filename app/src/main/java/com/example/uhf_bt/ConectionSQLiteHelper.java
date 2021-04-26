package com.example.uhf_bt;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.uhf_bt.Utilidades.utilidades;

public class ConectionSQLiteHelper extends SQLiteOpenHelper {

    public ConectionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(utilidades.CREAR_TABLA_USUARIO);
        db.execSQL(utilidades.CREAR_TABLA_RFID);
        db.execSQL(utilidades.CREAR_TABLA_UBICACION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS rfid");
        db.execSQL("DROP TABLE IF EXISTS ubicacion");
        onCreate(db);
    }
}
