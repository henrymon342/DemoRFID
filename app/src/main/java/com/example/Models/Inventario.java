package com.example.Models;

public class Inventario {

    private String id;
    private String duracion;
    private String cantidadTags;
    private String fechaScaneo;
    private String nombreBuilding;
    private String nombreRoom;

    public Inventario(String id, String duracion, String cantidadTags, String fechaScaneo, String nombreBuilding, String nombreRoom) {
        this.id = id;
        this.duracion = duracion;
        this.cantidadTags = cantidadTags;
        this.fechaScaneo = fechaScaneo;
        this.nombreBuilding = nombreBuilding;
        this.nombreRoom = nombreRoom;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getCantidadTags() {
        return cantidadTags;
    }

    public void setCantidadTags(String cantidadTags) {
        this.cantidadTags = cantidadTags;
    }

    public String getFechaScaneo() {
        return fechaScaneo;
    }

    public void setFechaScaneo(String fechaScaneo) {
        this.fechaScaneo = fechaScaneo;
    }

    public String getNombreBuilding() {
        return nombreBuilding;
    }

    public void setNombreBuilding(String nombreBuilding) {
        this.nombreBuilding = nombreBuilding;
    }

    public String getNombreRoom() {
        return nombreRoom;
    }

    public void setNombreRoom(String nombreRoom) {
        this.nombreRoom = nombreRoom;
    }
}
