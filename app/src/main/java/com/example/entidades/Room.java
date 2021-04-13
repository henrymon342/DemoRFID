package com.example.entidades;

import java.io.Serializable;

public class Room implements Serializable {

    private Integer id;
    private Integer buildingId;
    private String name;

    public Room(Integer id, Integer buildingId, String name) {
        this.id = id;
        this.buildingId = buildingId;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Integer buildingId) {
        this.buildingId = buildingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
