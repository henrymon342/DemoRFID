package com.example.Models;

public class Room {

    private String id;
    private String name;
    private int idBuilding;

    public Room(String id, String name, int idBuilding) {
        this.id = id;
        this.name = name;
        this.idBuilding = idBuilding;
    }

    public Room() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdBuilding() {
        return idBuilding;
    }

    public void setIdBuilding(int idBuilding) {
        this.idBuilding = idBuilding;
    }
}
