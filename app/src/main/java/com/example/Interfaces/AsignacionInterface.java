package com.example.Interfaces;

import com.example.Models.AssignationLector;
import com.example.Models.Building;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AsignacionInterface {

    @GET("/api/asiglector")
    Call<List<AssignationLector>> getAsignacion();

    @POST("/api/asiglector")
    Call<AssignationLector> createAsignacion(@Body AssignationLector asignacion );
}
