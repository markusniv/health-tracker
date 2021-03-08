package com.example.androidproject;

import android.util.Log;

import java.util.TimerTask;

public class AddMovementTimerTask extends TimerTask {
    @Override
    public void run() {
        AddMovement movement = new AddMovement(TrackMovement.getMovementInstance().getDataToStore());
        EventSingleton.getEventInstance().AddMovementEvent(movement);
        TrackMovement.getMovementInstance().resetData();

        Log.i("MOVEMENT EVENTS", EventSingleton.getEventInstance().getMovementEventList().toString());
    }

}
