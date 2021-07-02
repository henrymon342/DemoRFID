package com.example.Models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.uhf_bt.ConnectionSQLiteHelper;
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
public class AsignacionLector {

    private String id;
    private String fechaInicio;
    private String fechaFin;
    private String idUsuario;
    private String idLector;

    public static ArrayList<AsignacionLector> getAsignacionLector(SincronizarActivity mContext) {
        ConnectionSQLiteHelper conn = new ConnectionSQLiteHelper(mContext, "bdUser", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        AsignacionLector asignacionLector;
        ArrayList<AsignacionLector> asignacionList = new ArrayList<AsignacionLector>();
        try {
            Cursor cursor = db.rawQuery("select * from " + utilidades.TABLA_ASIGNACION, null);
            while (cursor.moveToNext()) {
                asignacionLector = new AsignacionLector();
                asignacionLector.setId(cursor.getString(0));
                asignacionLector.setFechaInicio(cursor.getString(1));
                asignacionLector.setFechaFin(cursor.getString(2));
                asignacionLector.setIdUsuario(cursor.getString(3));
                asignacionLector.setIdLector(cursor.getString(4));
                asignacionList.add(asignacionLector);
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            Log.d("getStocks()", "ERROR");
            db.close();
        }
        return asignacionList;
    }

    // method for MainActivity
    public static void registroAsignacionLector(String fechaInicio, String fechaFin,String fk_idUsuario,String fk_idLector,  MainActivity context) {
        ConnectionSQLiteHelper conn = new ConnectionSQLiteHelper(context, "bdUser", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(utilidades.CAMPO_FECHA_INI_ASIGNACION, fechaInicio);
        values.put(utilidades.CAMPO_FECHA_FIN_ASIGNACION, fechaFin);
        values.put(utilidades.CAMPO_FK_USUARIO_ASIGNACION, fk_idUsuario);
        values.put(utilidades.CAMPO_FK_LECTOR_ASIGNACION, fk_idLector);
        db.insert(utilidades.TABLA_ASIGNACION, utilidades.CAMPO_ID_ASIGNACION, values);
        db.close();
    }
}
