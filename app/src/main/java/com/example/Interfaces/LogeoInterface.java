package com.example.Interfaces;

import com.example.Models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LogeoInterface {

    @GET("/api/logueo")
    Call<List<User>> getUsers();

}
