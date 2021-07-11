package com.example.uhf_bt;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.uhf_bt.Utilidades.utilidades;

public class ConnectionSQLiteHelper extends SQLiteOpenHelper {

    public ConnectionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static void deleteTableData(Context context, String table, String createTable) {
        ConnectionSQLiteHelper conn = new ConnectionSQLiteHelper(context, "bdUser", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + table + ";");
        db.execSQL(createTable);
    }

    public static void deleteRoomsData(Context context) {
        deleteTableData(context, "room", utilidades.CREAR_TABLA_ROOM);
    }

    public static void deleteBuildingsData(Context context) {
        deleteTableData(context, "building", utilidades.CREAR_TABLA_BUILDING);
    }

    public static void deleteUsersData(Context context) {
        deleteTableData(context, "user", utilidades.CREAR_TABLA_USER);
    }

    public static void deleteStocksData(Context context) {
        deleteTableData(context, "stock", utilidades.CREAR_TABLA_STOCK);
    }

    public static void deleteLectorsData(Context context) {
        deleteTableData(context, "lector", utilidades.CREAR_TABLA_LECTOR);
    }

    public static void deleteAssignationsLectorData(Context context) {
        deleteTableData(context, "assignation_lector", utilidades.CREAR_TABLA_ASSIGNATION_LECTOR);
    }

    public static void deleteSearchListData(Context context) {
        deleteTableData(context, "search_list", utilidades.CREAR_TABLA_SEARCH_LIST);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(utilidades.CREAR_TABLA_USER);
        db.execSQL(utilidades.CREAR_TABLA_LECTOR);
        db.execSQL(utilidades.CREAR_TABLA_ASSIGNATION_LECTOR);
        db.execSQL(utilidades.CREAR_TABLA_BUILDING);
        db.execSQL(utilidades.CREAR_TABLA_ROOM);
        db.execSQL(utilidades.CREAR_TABLA_STOCK);
        db.execSQL(utilidades.CREAR_TABLA_SEARCH_LIST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS lector");
        db.execSQL("DROP TABLE IF EXISTS assignation_lector");
        db.execSQL("DROP TABLE IF EXISTS building");
        db.execSQL("DROP TABLE IF EXISTS room");
        db.execSQL("DROP TABLE IF EXISTS stock");
        db.execSQL("DROP TABLE IF EXISTS search_list");
        onCreate(db);
    }
}
