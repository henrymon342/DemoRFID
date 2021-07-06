package com.example.Interfaces;

import com.example.Models.Room;
import com.example.Models.Stock;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface StockInterface {

    @GET("/api/stock")
    Call<List<Stock>> getStocks();

}
