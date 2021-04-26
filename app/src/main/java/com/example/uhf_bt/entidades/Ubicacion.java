package com.example.uhf_bt.entidades;

public class Ubicacion {
    private String edificio;
    private String room;

    public Ubicacion(String edificio, String room) {
        this.edificio = edificio;
        this.room = room;
    }

    public String getEdificio() {
        return edificio;
    }

    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
