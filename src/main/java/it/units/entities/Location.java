package it.units.entities;

import java.io.Serializable;

public class Location implements Serializable {

    private double longitude;
    private double latitude;
    private boolean important;
    private String name;

    public Location(double longitude, double latitude, boolean important, String name) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.important = important;
        this.name = name;
    }

    public Location() {
    }


    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public boolean isImportant() {
        return important;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

