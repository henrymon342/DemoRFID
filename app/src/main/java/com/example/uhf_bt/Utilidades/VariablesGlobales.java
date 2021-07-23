package com.example.uhf_bt.Utilidades;

import android.app.Application;

public class VariablesGlobales extends Application {

    private String idLector;

    public String getIdLector() {
        return idLector;
    }

    public void setIdLector(String idLector) {
        this.idLector = idLector;
    }
}
