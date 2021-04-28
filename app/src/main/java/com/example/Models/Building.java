package com.example.Models;

import java.util.Arrays;

public class Building {

    private  int id;
    private String name;
    private String rooms[];

    @Override
    public String toString() {
        return "Building{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rooms=" + Arrays.toString(rooms) +
                '}';
    }

    public Building(int id, String name, String[] rooms) {
        this.id = id;
        this.name = name;
        this.rooms = rooms;
    }

    public Building(){

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

    public String[] getRooms() {
        return rooms;
    }

    public void setRooms(String[] rooms) {
        this.rooms = rooms;
    }
}
