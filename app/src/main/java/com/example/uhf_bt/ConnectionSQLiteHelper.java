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

    public static void deleteRoomsData(SincronizarActivity context){
        ConnectionSQLiteHelper conn = new ConnectionSQLiteHelper(context, "bdUser", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS room;");
        db.execSQL(utilidades.CREAR_TABLA_ROOM);
    }
    public static void deleteBuildingsData(SincronizarActivity context){
        ConnectionSQLiteHelper conn = new ConnectionSQLiteHelper(context, "bdUser", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS building;");
        db.execSQL(utilidades.CREAR_TABLA_BUILDING);
    }
    public static void deleteRoomsData(LoginActivity context){
        ConnectionSQLiteHelper conn = new ConnectionSQLiteHelper(context, "bdUser", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS room;");
        db.execSQL(utilidades.CREAR_TABLA_ROOM);
    }
    public static void deleteBuildingsData(LoginActivity context){
        ConnectionSQLiteHelper conn = new ConnectionSQLiteHelper(context, "bdUser", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS building;");
        db.execSQL(utilidades.CREAR_TABLA_BUILDING);
    }
    public static void deleteUsersData(SincronizarActivity context) {
        ConnectionSQLiteHelper conn = new ConnectionSQLiteHelper(context, "bdUser", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS user;");
        db.execSQL(utilidades.CREAR_TABLA_USUARIO);
    }
    public static void deleteUsersData(LoginActivity context) {
        ConnectionSQLiteHelper conn = new ConnectionSQLiteHelper(context, "bdUser", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS user;");
        db.execSQL(utilidades.CREAR_TABLA_USUARIO);
    }
    public static void deleteStocksData(SincronizarActivity context) {
        ConnectionSQLiteHelper conn = new ConnectionSQLiteHelper(context, "bdUser", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS stock;");
        db.execSQL(utilidades.CREAR_TABLA_STOCK);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(utilidades.CREAR_TABLA_USUARIO);
        db.execSQL(utilidades.CREAR_TABLA_LECTOR);
        db.execSQL(utilidades.CREAR_TABLA_ASIGNACION_LECTOR);
        db.execSQL(utilidades.CREAR_TABLA_BUILDING);
        db.execSQL(utilidades.CREAR_TABLA_ROOM);
        db.execSQL(utilidades.CREAR_TABLA_STOCK);
        db.execSQL(utilidades.CREAR_TABLA_SEARCH_LIST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS lector");
        db.execSQL("DROP TABLE IF EXISTS asignacion_lector");
        db.execSQL("DROP TABLE IF EXISTS building");
        db.execSQL("DROP TABLE IF EXISTS room");
        db.execSQL("DROP TABLE IF EXISTS stock");
        db.execSQL("DROP TABLE IF EXISTS searchList");
        onCreate(db);
    }
}
