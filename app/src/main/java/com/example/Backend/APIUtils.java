package com.example.Backend;

import com.example.Backend.Interfaces.UserService;

public class APIUtils {

    //public static final String API_URL = "http://734d5d5284c2.ngrok.io";

    public static final String API_URL = "http://f2923df27d8e.ngrok.io";

    public APIUtils(){

    };

    public static String getApiUrl() {
        return API_URL;
    }

    public static UserService getUserService(){
        return RESTApiClient.getClient(API_URL).create(UserService.class);
    }

}
