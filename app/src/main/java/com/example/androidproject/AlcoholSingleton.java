package com.example.androidproject;


import java.util.ArrayList;
import java.util.List;

public class AlcoholSingleton {
    public static final AlcoholSingleton AlcoholInstance = new AlcoholSingleton();
    private List<Alcohol> drinks;

    public AlcoholSingleton() {
        drinks = new ArrayList<Alcohol>();
        drinks.add(new Alcohol("Olut 0,5l", 0.5, 4.5));
        drinks.add(new Alcohol("Siideri/Lonkero 0,5l", 0.5, 5.5));
        drinks.add(new Alcohol("Tölkkiolut", 0.33, 4.5));
        drinks.add(new Alcohol("Tölkkisiideri/lonkero", 0.33, 5.5));
        drinks.add(new Alcohol("Viinilasi 12cl", 0.12, 11.0));
        drinks.add(new Alcohol("Viinilasi 16cl", 0.16, 11.0));
        drinks.add(new Alcohol("Kuohuviinilasi 12cl", 0.12, 12.0));
        drinks.add(new Alcohol("Kuohuviinilasi 16cl", 0.16, 12.0));
        drinks.add(new Alcohol("Väkevä viini 8cl", 0.08, 15.0));
        drinks.add(new Alcohol("Likööri shotti", 0.4, 16.0));
        drinks.add(new Alcohol("Vahva shotti 40%", 0.04, 40.0));
    }
    public List<Alcohol> getDrinks()    {
        return this.drinks;
    }
    public static AlcoholSingleton getAlcoholInstance() {
        return AlcoholInstance;
    }
}
