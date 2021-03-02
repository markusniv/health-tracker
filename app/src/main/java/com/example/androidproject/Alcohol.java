package com.example.androidproject;

public class Alcohol extends Vice {

    private String name;
    private double size;
    private double volPercent;


    public Alcohol(String name, double size, double volPercent) {
        this.name = name;
        this.size = size;
        this.volPercent = volPercent;

    }

    public String getName() {
        return name;
    }

    public double getSize() {
        return size;
    }

    public double getVolPercent() {
        return volPercent;
    }

    public String toString() {
        return this.name;
    }



}
