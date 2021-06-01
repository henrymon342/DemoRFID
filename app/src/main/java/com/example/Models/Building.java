package com.example.Models;

import java.util.Arrays;

public class Building {

    private  int id;
    private String name;


    public Building(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Building() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
