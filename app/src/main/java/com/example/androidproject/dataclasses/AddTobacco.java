package com.example.androidproject.dataclasses;

/**
 * Class that's used to add a tobacco smoking event from the AddTobaccoActivity into the
 * EventSingleton to keep track of all the events when tobacco was smoked
 */
public class AddTobacco extends AddVice {
    private TobaccoProduct tobaccoProduct;

    public AddTobacco(TobaccoProduct tobaccoProduct, double price) {
        this.tobaccoProduct = tobaccoProduct;
        setPrice(price);
    }

    public TobaccoProduct getTobaccoProduct() {
        return tobaccoProduct;
    }

    public String toString() {
        return tobaccoProduct.getName() + " " + super.getPrice() + " " + this.getDate();
    }

}
