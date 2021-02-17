package com.example.androidproject;

public class TobaccoProduct extends Vice {

    private String name;
    private double nicotineAmount;

    public TobaccoProduct(String name, double nicotineAmount) {
        this.name = name;
        this.nicotineAmount = nicotineAmount;
    }

    public String getName() {
        return name;
    }

    public double getNicotineAmount() {
        return nicotineAmount;
    }

}
