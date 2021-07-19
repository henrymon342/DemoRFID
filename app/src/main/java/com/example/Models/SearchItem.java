package com.example.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.uhf_bt.ConnectionSQLiteHelper;
import com.example.uhf_bt.Utilidades.utilidades;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class SearchItem {
    private int id;
    private String description;
    private String epc;
    private String estado;

    public static ArrayList<SearchItem> getItems(Context mContext) {
        ConnectionSQLiteHelper conn = new ConnectionSQLiteHelper(mContext, "bdUser", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        SearchItem items;
        ArrayList<SearchItem> itemsList = new ArrayList<>();
        try {
            Cursor cursor = db.rawQuery("select * from " + utilidades.TABLA_SEARCH_LIST, null);
            while (cursor.moveToNext()) {
                items = new SearchItem();
                items.setId(cursor.getInt(0));
                items.setDescription(cursor.getString(1));
                items.setEpc(cursor.getString(2));
                items.setEstado(cursor.getString(3));
                itemsList.add(items);
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            Log.d("getItems()", "ERROR");
            db.close();
        }
        return itemsList;
    }

    public static void registroSearchItem(String description, String epc, String estado, Context context) {
        ConnectionSQLiteHelper conn = new ConnectionSQLiteHelper(context, "bdUser", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(utilidades.SEARCH_LIST_DESCRIPTION, description);
        values.put(utilidades.SEARCH_LIST_EPC, epc);
        values.put(utilidades.SEARCH_LIST_ESTADO, estado);
        Long idResultante = db.insert(utilidades.TABLA_SEARCH_LIST, utilidades.SEARCH_LIST_ID, values);
        Toast.makeText(context, "Id SEARCH ITEM: " + idResultante, Toast.LENGTH_SHORT).show();
        db.close();
    }

    @Override
    public String toString(){
        return this.epc+ "   "+this.estado;
    }

}
