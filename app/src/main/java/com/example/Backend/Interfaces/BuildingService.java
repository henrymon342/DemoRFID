package com.example.Backend.Interfaces;

import com.example.entidades.Building;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BuildingService {

    @GET("/api/Building")
    Call<List<Building>> getBuildings();

    @GET("/api/Building/{id}")
    Call<Building> getBuilding(@Path("id") int id);

}
