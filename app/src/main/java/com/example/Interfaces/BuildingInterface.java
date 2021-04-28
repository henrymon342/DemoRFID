package com.example.Interfaces;

import com.example.Models.Building;
import com.example.Models.User;

import java.util.List;

import retrofit2.Call;

import retrofit2.http.GET;


public interface BuildingInterface {

    @GET("/api/Building")
    Call<List<Building>> getBuildings();

}
