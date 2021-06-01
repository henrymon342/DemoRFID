package com.example.uhf_bt;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.uhf_bt.Utilidades.utilidades;

public class ConnectionSQLiteHelper extends SQLiteOpenHelper {

    public ConnectionSQLiteHelper(LoginActivity context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public ConnectionSQLiteHelper(SincronizarActivity context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public ConnectionSQLiteHelper(MainActivity context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public ConnectionSQLiteHelper(BusquedaActivity context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(utilidades.CREAR_TABLA_USUARIO);
        db.execSQL(utilidades.CREAR_TABLA_BUILDING);
        db.execSQL(utilidades.CREAR_TABLA_ROOM);
        db.execSQL(utilidades.CREAR_TABLA_INVENTARIO);
        db.execSQL(utilidades.CREAR_TABLA_RFID);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS building");
        db.execSQL("DROP TABLE IF EXISTS room");
        db.execSQL("DROP TABLE IF EXISTS inventario");
        db.execSQL("DROP TABLE IF EXISTS rfid");
        onCreate(db);
    }
}