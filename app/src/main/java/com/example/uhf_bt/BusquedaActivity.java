package com.example.uhf_bt;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.Models.SearchItem;
import com.example.uhf_bt.Utilidades.utilidades;
import com.example.uhf_bt.entidades.stockList;

import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;

public class BusquedaActivity extends BaseActivity {
    private Button btn_atras, btn_getlist;
    ArrayList<stockList> rfidList;
    private ArrayList<SearchItem> searchList = new ArrayList<>();

    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.busqueda);
        // Volver al menu
        btn_atras = findViewById(R.id.btn_atrasBusqueda);
        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_getlist = findViewById(R.id.btn_getlist);
        btn_getlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pedirUbicacion();
                getSearchList();

                // MOSTRAR EL ARRAY "searchList" en el LIST-VIEW

                //getRfidSQLite();
            }
        });

    }

    private void pedirUbicacion() {
        String array[] = {"henry", "miranda", "choque"};
        int itemSelected = 0;
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Select your gender")
                .setSingleChoiceItems(array, itemSelected, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                        Log.d("DIALOG", selectedIndex + "");
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo, int id) {
                        Toast.makeText(BusquedaActivity.this, "bien", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo, int id) {
                        Toast.makeText(BusquedaActivity.this, "mal", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
        dialog.setCanceledOnTouchOutside(false);

        /*
        new AlertDialog.Builder(this)
                .setTitle("Nuke planet Jupiter?")
                .setMessage("Note that nuking planet Jupiter will destroy everything in there.")
                .setPositiveButton("Nuke", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("MainActivity", "Sending atomic bombs to Jupiter");
                    }
                })
                .setNegativeButton("Abort", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("MainActivity", "Aborting mission...");
                    }
                })
                .show();

         */
    }

    private void getSearchList() {
        searchList = new ArrayList<>(SearchItem.getItems(this));
    }

}
