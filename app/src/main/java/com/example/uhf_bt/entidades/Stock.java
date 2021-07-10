package com.example.uhf_bt.entidades;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stock {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("epc")
    @Expose
    private String epc;
    @SerializedName("tid")
    @Expose
    private String tid;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("roomId")
    @Expose
    private int roomId;
    @SerializedName("userMemory")
    @Expose
    private String userMemory;
    @SerializedName("lastScanDate")
    @Expose
    private String lastScanDate;

    public Stock( String epc, String tid, String description, int room_id, String userMemory, String lastScanDate) {
        this.epc = epc;
        this.tid = tid;
        this.description = description;
        this.roomId = room_id;
        this.userMemory = userMemory;
        this.lastScanDate = lastScanDate;
    }
}
