package com.example.Models;

public class Room {

    private  int id;
    private  int buildingId;
    private String name;

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", buildingId=" + buildingId +
                ", name='" + name + '\'' +
                '}';
    }

    public Room(int id, int buildingId, String name) {
        this.id = id;
        this.buildingId = buildingId;
        this.name = name;
    }
    public Room(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
