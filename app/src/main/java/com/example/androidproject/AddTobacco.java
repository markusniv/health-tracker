package com.example.androidproject;

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
