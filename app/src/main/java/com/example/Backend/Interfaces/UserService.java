package com.example.Backend.Interfaces;

import com.example.Models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {

    @GET("/api/logueo")
    Call<List<User>> getUsers();

    @POST("/api/logueo")
    Call<User> addUser(@Body User user);

    @GET("/api/logueo/{id}")
    Call<User> getUser(@Path("id") int id);

    @PUT("update/{id}")
    Call<User> updateUser(@Path("id") int id, @Body User user);

    @DELETE("delete/{id}")
    Call<User> deleteUser(@Path("id") int id);

}
