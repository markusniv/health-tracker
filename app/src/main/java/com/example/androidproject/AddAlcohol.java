package com.example.androidproject;

public class AddAlcohol extends AddVice{

    private Alcohol alcohol;

    public AddAlcohol(Alcohol alcohol) {
        this.alcohol = alcohol;
    }

    public Alcohol getAlcohol() {
        return alcohol;
    }
}
