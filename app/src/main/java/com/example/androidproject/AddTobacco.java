package com.example.androidproject;

public class AddTobacco extends AddVice {
    private TobaccoProduct tobaccoProduct;
    private double price;

    public AddTobacco(TobaccoProduct tobaccoProduct, double price) {
        this.tobaccoProduct = tobaccoProduct;
        this.price = price;
    }

    public TobaccoProduct getTobaccoProduct() {
        return tobaccoProduct;
    }

    public String toString() {
        return tobaccoProduct.getName() + " " + price + " " + this.getCurrentTime();
    }
}
