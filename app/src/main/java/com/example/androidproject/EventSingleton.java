package com.example.androidproject;

import java.util.ArrayList;

public class EventSingleton {
    private static final EventSingleton eventInstance = new EventSingleton();
    private ArrayList<AddVice> viceEventList;

    private ArrayList<AddMovement> movementEventList;

    private EventSingleton() {
        viceEventList = new ArrayList<>();
        movementEventList = new ArrayList<>();
    }

    public static EventSingleton getEventInstance() {
        return eventInstance;
    }

    public void AddViceEvent(AddVice addVice) {
        viceEventList.add(addVice);
    }

    public void AddMovementEvent(AddMovement addMovement) {
        movementEventList.add(addMovement);
    }

    public ArrayList<AddVice> getViceEventList() {
        return viceEventList;
    }

    public AddVice getViceEvent(int index) {
        return viceEventList.get(index);
    }

    public ArrayList<AddMovement> getMovementEventList() {
        return movementEventList;
    }

    public AddMovement getMovementEvent(int index) {
        return movementEventList.get(index);
    }



}
