package com.example.uhf_bt.entidades;

public class RFIDTagList {
    private String id;
    private String EPC;
    private String TID;
    private String Count;
    private String Descripcion;
    private String idInventario;


    public RFIDTagList(String id, String EPC, String TID,String Count, String Descripcion,String idInventario) {
        this.id = id;
        this.EPC = EPC;
        this.TID=TID;
        this.Count=Count;
        this.Descripcion=Descripcion;
        this.idInventario=idInventario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEPC() {
        return EPC;
    }

    public void setEPC(String EPC) {
        this.EPC = EPC;
    }

    public String getTID() {
        return TID;
    }

    public void setTID(String TID) {
        this.TID = TID;
    }

    public String getCount() {
        return Count;
    }

    public void setCount(String count) {
        Count = count;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(String idInventario) {
        this.idInventario = idInventario;
    }

    public RFIDTagList(){

    }
}
