package com.example.Models;

public class Rfid {

    private String id;
    private String epc;
    private String tid;
    private String count;
    private String descripcion;
    private String idInventario;


    public Rfid(String id, String epc, String tid, String count, String descripcion, String idInventario) {
        this.id = id;
        this.epc = epc;
        this.tid = tid;
        this.count = count;
        this.descripcion = descripcion;
        this.idInventario = idInventario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEpc() {
        return epc;
    }

    public void setEpc(String epc) {
        this.epc = epc;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(String idInventario) {
        this.idInventario = idInventario;
    }
}
