package com.example.Interfaces;

import com.example.Models.Room;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface InventarioInterface {


    @GET("/api/")
    Call<List<Room>> getInventario();
}
