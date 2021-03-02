package com.example.androidproject;

public class Tobacco extends TobaccoProduct {

    private double tar, carbonmonoxide;
    private int minutesSpent = 5;

    public Tobacco(String name, double tar, double nicotineAmount, double carbonmonoxide) {
        super(name, nicotineAmount);
        this.tar = tar;
        this.carbonmonoxide = carbonmonoxide;
    }

    public int getTimeSpent() {
        return minutesSpent;
    }

    public String toString() {
        return this.getName();
    }
}
