package com.example.androidproject;

/**
 * Data class for all alcohol drinks
 */
public class Alcohol extends Vice {

    private String name;
    private double size;
    private double volPercent;


    public Alcohol(String name, double size, double volPercent) {
        this.name = name;
        this.size = size;
        this.volPercent = volPercent;

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

    /**
     * Override toString to return only the name of the alcohol drink
     * @return Name of the drink
     */
    public String toString() {
        return this.name;
    }



}
