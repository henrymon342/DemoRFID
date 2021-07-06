package com.example.Interfaces;

import com.example.Models.Building;

import java.util.List;

import retrofit2.Call;

import retrofit2.http.GET;


public interface BuildingInterface {

    @GET("/api/building")
    Call<List<Building>> getBuildings();

}
