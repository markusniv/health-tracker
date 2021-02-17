package com.example.androidproject;

public class Tobacco extends TobaccoProduct {

    private int minutesSpent = 5;

    public Tobacco(String name, double nicotineAmount) {
        super(name, nicotineAmount);
    }

    public int getTimeSpent() {
        return minutesSpent;
    }
}
