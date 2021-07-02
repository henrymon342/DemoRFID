package com.example.Models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.uhf_bt.ConnectionSQLiteHelper;
import com.example.uhf_bt.LoginActivity;
import com.example.uhf_bt.MainActivity;
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
public class Stock {

    private String id;
    private String epc;
    private String tid;
    private String userMemory;
    private String description;
    private String lastScanDate;
    private String idRoom;

    public static ArrayList<Stock> getStocks(SincronizarActivity mContext) {
        ConnectionSQLiteHelper conn = new ConnectionSQLiteHelper(mContext, "bdUser", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        Stock stock;
        ArrayList<Stock> stockList = new ArrayList<Stock>();
        try {
            Cursor cursor = db.rawQuery("select * from " + utilidades.TABLA_STOCK, null);
            while (cursor.moveToNext()) {
                stock = new Stock();
                stock.setId(cursor.getString(0));
                stock.setEpc(cursor.getString(1));
                stock.setTid(cursor.getString(2));
                stock.setUserMemory(cursor.getString(3));
                stock.setDescription(cursor.getString(4));
                stock.setLastScanDate(cursor.getString(5));
                stock.setIdRoom(cursor.getString(6));
                stockList.add(stock);
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            Log.d("getStocks()", "ERROR");
            db.close();
        }
        return stockList;
    }

    public static void registroStock(String epc, String tid, String userMemory, String description, String lastScan, String idRoom, SincronizarActivity context) {
        ConnectionSQLiteHelper conn = new ConnectionSQLiteHelper(context, "bdUser", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(utilidades.CAMPO_EPC_STOCK, epc);
        values.put(utilidades.CAMPO_TID_STOCK, tid);
        values.put(utilidades.CAMPO_USER_MEMORY_STOCK, userMemory);
        values.put(utilidades.CAMPO_DESCRIPCION_STOCK, description);
        values.put(utilidades.CAMPO_LAST_SCAN_STOCK, lastScan);
        values.put(utilidades.CAMPO_FK_ROOM_STOCK, idRoom);
        db.insert(utilidades.TABLA_STOCK, utilidades.CAMPO_ID_STOCK, values);
        db.close();
    }
    public static void registroStock(String epc, String tid, String userMemory, String description, String lastScan, String idRoom, MainActivity context) {
        ConnectionSQLiteHelper conn = new ConnectionSQLiteHelper(context, "bdUser", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(utilidades.CAMPO_EPC_STOCK, epc);
        values.put(utilidades.CAMPO_TID_STOCK, tid);
        values.put(utilidades.CAMPO_USER_MEMORY_STOCK, userMemory);
        values.put(utilidades.CAMPO_DESCRIPCION_STOCK, description);
        values.put(utilidades.CAMPO_LAST_SCAN_STOCK, lastScan);
        values.put(utilidades.CAMPO_FK_ROOM_STOCK, idRoom);
        db.insert(utilidades.TABLA_STOCK, utilidades.CAMPO_ID_STOCK, values);
        db.close();
    }
}
