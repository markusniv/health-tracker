package com.example.androidproject;

public class Tobacco extends TobaccoProduct {

    private int minutesSpent = 5;

    public Tobacco(String name, double nicotineAmount, double price) {
        super(name, nicotineAmount, price);
    }

    public int getTimeSpent() {
        return minutesSpent;
    }
}
