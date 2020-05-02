package com.hareeshwar_karthikeyan.planarena;

import java.util.ArrayList;

public class Guilds {
    private String name;
    private String id;
    private String description;
    private ArrayList<String> eventids;

    public Guilds()
    {
        name = "name";
        id = "id";
        description = "des";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addEventid(String id){
        eventids.add(id);
    }

    public String getEventid(int index){
        return eventids.get(index);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

