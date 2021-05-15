package com.example.uhf_bt;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.uhf_bt.Utilidades.utilidades;


public class ConeectionSQLHelperI extends SQLiteOpenHelper {
    public ConeectionSQLHelperI(MainActivity context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(utilidades.CREAR_TABLA_RFID);
        db.execSQL(utilidades.CREAR_TABLA_INVENTARIO);
        db.execSQL(utilidades.CREAR_TABLA_BUILDING);
        db.execSQL(utilidades.CREAR_TABLA_ROOM);
        //db.execSQL(utilidades.CREAR_TABLA_ROOM);
       // db.execSQL(utilidades.CREAR_TABLA_BUILDING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS rfid");
        db.execSQL("DROP TABLE IF EXISTS inventario");
        //db.execSQL("DROP TABLE IF EXISTS room");
        onCreate(db);
    }
}
