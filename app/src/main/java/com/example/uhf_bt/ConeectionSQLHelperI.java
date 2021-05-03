package com.example.uhf_bt;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.uhf_bt.Utilidades.utilidades;
import com.example.uhf_bt.fragment.UHFReadTagFragment;


public class ConeectionSQLHelperI extends SQLiteOpenHelper {
    public ConeectionSQLHelperI(MainActivity context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(utilidades.CREAR_TABLA_RFID);
        db.execSQL(utilidades.CREAR_TABLA_UBICACION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS rfid");
        db.execSQL("DROP TABLE IF EXISTS ubicacion");
        onCreate(db);
    }
}