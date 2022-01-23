package it.units.entities;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class Trip implements Serializable {


    private final String id = UUID.randomUUID().toString().replace("-", "");
    private String name;
    private String date;
    private String vehicle;
    private List<Location> path;


    public Trip(String name, String date, String vehicle, List<Location> path) {
        this.name = name;
        this.date = date;
        this.vehicle = vehicle;
        this.path = path;
    }


    public Trip() {
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public List<Location> getPath() {
        return path;
    }

    public void setPath(List<Location> path) {
        this.path = path;
    }
}

