package com.example.androidproject.dataclasses;

/**
 * Data class for all tobaccos (cigarettes). In addition to name and nicotine amount from TobaccoProduct,
 * has variables for the amount of tar and carbonmonoxide and the amount of time spent smoking one
 */

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
