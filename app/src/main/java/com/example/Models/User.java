package com.example.Models;

import java.util.ArrayList;
import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

    private int id;
    private String name;
    private String clave;


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", clave='" + clave + '\'' +
                '}';
    }

    public static boolean contains(ArrayList<User> users, int id) {
        for (User user : users) {
            if (user.id==id)
                return true;
        }
        return false;
    }


}
