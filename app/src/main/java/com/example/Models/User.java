package com.example.Models;

public class User {

    private  int id;
    private String name;
    private String clave;

    public User(int id, String name, String clave) {
        this.id = id;
        this.name = name;
        this.clave = clave;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getClave() {
        return clave;
    }
}
