package com.example.Backend.Interfaces;


import com.example.entidades.Room;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RoomService {
    @GET("/api/Room")
    Call<List<Room>> getRooms();

    @GET("/api/Room/{id}")
    Call<Room> getRoom(@Path("id") int id);

}
