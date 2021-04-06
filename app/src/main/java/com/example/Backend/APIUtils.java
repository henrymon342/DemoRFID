package com.example.Backend;

import com.example.Backend.Interfaces.UserService;

public class APIUtils {

    public static final String API_URL = "http://a2a256f3b766.ngrok.io";
    //public static final String API_URL = "https://localhost:5001";

    public APIUtils(){

    };

    public static String getApiUrl() {
        return API_URL;
    }

    public static UserService getUserService(){
        return RESTApiClient.getClient(API_URL).create(UserService.class);
    }

}
