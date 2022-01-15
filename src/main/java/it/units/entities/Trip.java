package it.units.entities;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class Trip implements Serializable {


    private String id;
    private String date;
    private String vehicle;
    private List<Location> path;

    public Trip(String date, String vehicle, List<Location> path) {
        this.date = date;
        this.vehicle = vehicle;
        this.path = path;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Trip(String id, String date, String vehicle, List<Location> path) {
        this.id = UUID.randomUUID().toString().replace("-", "");
        this.date = date;
        this.vehicle = vehicle;
        this.path = path;
    }




    public Trip() {
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", vehicle='" + vehicle + '\'' +
                ", path=" + path +
                '}';
    }

    public String getId() {
        return id;
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

