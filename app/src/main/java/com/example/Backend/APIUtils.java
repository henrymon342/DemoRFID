package com.example.Backend;

//import com.example.Backend.Interfaces.UserService;
import android.text.TextUtils;

import com.example.Interfaces.BuildingInterface;
import com.example.Interfaces.LogeoInterface;
import com.example.Interfaces.RoomInterface;
import com.example.uhf_bt.Utilidades.GLOBAL;

import java.util.List;

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


    public static int checkIsExist(String epc, List<String> tempData) {
        if (TextUtils.isEmpty(epc)) {
            return -1;
        }
        return binarySearch(tempData, epc);
    }

    public static int binarySearch(List<String> array, String src) {
        int left = 0;
        int right = array.size() - 1;
        // 这里必须是 <=
        while (left <= right) {
            if (compareString(array.get(left), src)) {
                return left;
            } else if (left != right) {
                if (compareString(array.get(right), src))
                    return right;
            }
            left++;
            right--;
        }
        return -1;
    }

    public static boolean compareString(String str1, String str2) {
        if (str1.length() != str2.length()) {
            return false;
        } else if (str1.hashCode() != str2.hashCode()) {
            return false;
        } else {
            char[] value1 = str1.toCharArray();
            char[] value2 = str2.toCharArray();
            int size = value1.length;
            for (int k = 0; k < size; k++) {
                if (value1[k] != value2[k]) {
                    return false;
                }
            }
            return true;
        }
    }

}
