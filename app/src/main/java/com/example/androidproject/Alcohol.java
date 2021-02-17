package com.example.androidproject;

public class Alcohol extends Vice {

    private String name;
    private int size;
    private double volPercent;
    private double price;

    public Alcohol(String name, int size, double volPercent, double price) {
        this.name = name;
        this.size = size;
        this.volPercent = volPercent;
        this.price = price;
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

    public double getPrice() {
        return price;
    }

}
