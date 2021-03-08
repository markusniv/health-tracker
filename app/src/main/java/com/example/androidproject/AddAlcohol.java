package com.example.androidproject;

/**
 * Class that's used to add an alcohol drink adding event from the AddAlcoholActivity into the
 * EventSingleton to keep track of all the events when alcohols were drunk.
 */
public class AddAlcohol extends AddVice{

    private Alcohol alcohol;

    public AddAlcohol(Alcohol alcohol, double price) {
        this.alcohol = alcohol;
        setPrice(price);

    }

    public Alcohol getAlcohol() {
        return alcohol;
    }

}
