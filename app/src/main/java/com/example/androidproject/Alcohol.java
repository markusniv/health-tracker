package com.example.androidproject;

/**
 * Data class for all alcohol drinks
 */

public class Alcohol extends Vice {

    private String name;
    private double size;
    private double volPercent;
    private double defaultPrice;


    public Alcohol(String name, double size, double volPercent, double defaultPrice) {
        this.name = name;
        this.size = size;
        this.volPercent = volPercent;
        this.defaultPrice = defaultPrice;
    }

    /**
     * Get the name of the alcohol drink
     * @return Name of the drink
     */
    public String getName() {
        return name;
    }

    /**
     * Get the size of the alcohol drink
     * @return Size of the drink
     */
    public double getSize() {
        return size;
    }

    /**
     * Get the alcohol percentage of the alcohol drink
     * @return Alcohol percentage of the drink
     */
    public double getVolPercent() {
        return volPercent;
    }

    public double getDefaultPrice() {
        return defaultPrice;
    }

    /**
     * Override toString to return only the name of the alcohol drink
     * @return Name of the drink
     */
    public String toString() {
        return this.name;
    }



}
