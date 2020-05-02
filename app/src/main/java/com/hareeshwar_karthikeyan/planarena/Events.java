package com.hareeshwar_karthikeyan.planarena;

public class Events {
    private String description;
    private String name;
    private String location;
    private String time;
    private String id;

    public void Events()
    {
        setDescription("description");
        setName("name");
        location = "location";
        time = "time";
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
