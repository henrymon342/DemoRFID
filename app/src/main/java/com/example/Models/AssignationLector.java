package com.example.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.uhf_bt.ConnectionSQLiteHelper;
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
public class AssignationLector {

    private int id;
    private String fechaInicio;
    private String fechaFin;
    private String idUsuario;
    private String idLector;

    public static ArrayList<AssignationLector> getAssignationLector(Context mContext) {
        ConnectionSQLiteHelper conn = new ConnectionSQLiteHelper(mContext, "bdUser", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        AssignationLector assignationLector;
        ArrayList<AssignationLector> asignacionList = new ArrayList<AssignationLector>();
        try {
            Cursor cursor = db.rawQuery("select * from " + utilidades.TABLA_ASSIGNATION_LECTOR, null);
            while (cursor.moveToNext()) {
                assignationLector = new AssignationLector();
                assignationLector.setId(Integer.parseInt(cursor.getString(0)));
                assignationLector.setFechaInicio(cursor.getString(1));
                assignationLector.setFechaFin(cursor.getString(2));
                assignationLector.setIdUsuario(cursor.getString(3));
                assignationLector.setIdLector(cursor.getString(4));
                asignacionList.add(assignationLector);
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            Log.d("getStocks()", "ERROR");
            db.close();
        }
        return asignacionList;
    }

    public static void registroAssignationLector(String fechaInicio, String fechaFin, String fk_idUsuario, String fk_idLector, Context context) {
        ConnectionSQLiteHelper conn = new ConnectionSQLiteHelper(context, "bdUser", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(utilidades.ASSIGNATION_FECHA_INI, fechaInicio);
        values.put(utilidades.ASSIGNATION_FECHA_FIN, fechaFin);
        values.put(utilidades.ASSIGNATION_FK_USUARIO, fk_idUsuario);
        values.put(utilidades.ASSIGNATION_FK_LECTOR, fk_idLector);
        db.insert(utilidades.TABLA_ASSIGNATION_LECTOR, utilidades.ASSIGNATION_ID, values);
        db.close();
    }
}
