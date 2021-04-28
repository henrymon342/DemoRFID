package com.example.Backend;

//import com.example.Backend.Interfaces.UserService;
import com.example.Interfaces.BuildingInterface;
import com.example.Interfaces.LogeoInterface;
import com.example.Interfaces.RoomInterface;
import com.example.uhf_bt.Utilidades.GLOBAL;

public class APIUtils {

    //public static final String API_URL = "http://734d5d5284c2.ngrok.io";

    //public static final String API_URL = "http://f2923df27d8e.ngrok.io";
    public static final String API_URL = GLOBAL.URL;


    public APIUtils(){

    };

    public static String getApiUrl() {
        return API_URL;
    }

    public static LogeoInterface getUsers(){
        return RESTApiClient.getClient(API_URL).create(LogeoInterface.class);
    }

    public static BuildingInterface getBuildings(){
        return RESTApiClient.getClient(API_URL).create(BuildingInterface.class);
    }

    public static RoomInterface getRooms(){
        return RESTApiClient.getClient(API_URL).create(RoomInterface.class);
    }


}
