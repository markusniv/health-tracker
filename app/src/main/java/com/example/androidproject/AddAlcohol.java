package com.example.androidproject;

/**
 * Class that's used to add an alcohol drink adding event from the AddAlcoholActivity into the
 * EventSingleton to keep track of all the events when alcohols were drunk.
 */
public class AddAlcohol extends AddVice{

    private Alcohol alcohol;
    private double price;

    public AddAlcohol(Alcohol alcohol, double price) {
        this.alcohol = alcohol;
        this.price = price;

    }

    /**
     * Get the specific alcohol drink that was added in the event
     * @return Alcohol drink that was drunk and added into the app
     */
    public Alcohol getAlcohol() {
        return alcohol;
    }

    /**
     * Get the price of the alcohol drink that was added in the event
     * @return Price of the alcohol drink that was drunk and added into the app
     */
    public double getPrice() {
        return price;
    }
}
