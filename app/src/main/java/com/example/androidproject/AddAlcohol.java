package com.example.androidproject;

public class AddAlcohol extends AddVice{

    private Alcohol alcohol;
    private double price;

    public AddAlcohol(Alcohol alcohol, double price) {
        this.alcohol = alcohol;
        this.price = price;

    }

    public Alcohol getAlcohol() {
        return alcohol;
    }

    public double getPrice() {
        return price;
    }
}
