package com.example.androidproject;

public class Alcohol extends Vice {

    private String name;
    private int size;
    private double volPercent;

    public Alcohol(String name, int size, double volPercent) {
        this.name = name;
        this.size = size;
        this.volPercent = volPercent;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public double getVolPercent() {
        return volPercent;
    }

}
