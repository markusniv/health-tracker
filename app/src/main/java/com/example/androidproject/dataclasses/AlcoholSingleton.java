package com.example.androidproject.dataclasses;


import java.util.ArrayList;
import java.util.List;

/**
 * A singleton that stores all the alcohol drinks
 */
public class AlcoholSingleton {
    public static final AlcoholSingleton AlcoholInstance = new AlcoholSingleton();
    private List<Alcohol> drinks;


    public AlcoholSingleton() {
        drinks = new ArrayList<Alcohol>();
        drinks.add(new Alcohol("Olut 0,5l", 0.5, 4.5, 6));
        drinks.add(new Alcohol("Siideri/Lonkero 0,5l", 0.5, 5.5, 7.5));
        drinks.add(new Alcohol("Tölkkiolut", 0.33, 4.5, 1.5));
        drinks.add(new Alcohol("Tölkkisiideri/lonkero", 0.33, 5.5, 2.5));
        drinks.add(new Alcohol("Viinilasi 12cl", 0.12, 11.0, 7));
        drinks.add(new Alcohol("Viinilasi 16cl", 0.16, 11.0, 10));
        drinks.add(new Alcohol("Kuohuviinilasi 12cl", 0.12, 12.0, 8));
        drinks.add(new Alcohol("Kuohuviinilasi 16cl", 0.16, 12.0, 11));
        drinks.add(new Alcohol("Väkevä viini 8cl", 0.08, 15.0, 8));
        drinks.add(new Alcohol("Likööri shotti", 0.04, 16.0, 6));
        drinks.add(new Alcohol("Vahva shotti 40%", 0.04, 40.0, 6));
    }

    /**
     * Get the list of all the alcohol drinks
     * @return A list containing all the alcohol drinks
     */
    public List<Alcohol> getDrinks()    {
        return this.drinks;
    }

    /**
     * Get the singleton instance
     * @return Instance of the AlcoholSingleton
     */
    public static AlcoholSingleton getAlcoholInstance() {
        return AlcoholInstance;
    }
}
