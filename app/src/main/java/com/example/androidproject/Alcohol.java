package com.example.androidproject;

/**
 * Data class for all alcohol drinks
 */

public class Alcohol extends Vice {

    private String name;
    private double size;
    private double volPercent;
    private double defaultPrice;


    public Alcohol(String name, double size, double volPercent, double defaultPrice) {
        this.name = name;
        this.size = size;
        this.volPercent = volPercent;
        this.defaultPrice = defaultPrice;
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

    public double getDefaultPrice() {
        return defaultPrice;
    }

    public String toString() {
        return this.name;
    }



}
