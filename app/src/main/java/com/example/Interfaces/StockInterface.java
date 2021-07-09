package com.example.Interfaces;

import com.example.Models.Room;
import com.example.Models.Stock;




import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface StockInterface {

    @GET("/api/stock")
    Call<List<Stock>> getStocks();

    @POST("/api/stock")
    Call<com.example.uhf_bt.entidades.Stock> createStock(@Body com.example.uhf_bt.entidades.Stock stock );
}
