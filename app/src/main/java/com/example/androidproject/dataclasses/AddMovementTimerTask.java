package com.example.androidproject.dataclasses;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.androidproject.activities.MyApplication;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.TimerTask;

import static com.example.androidproject.activities.MainActivity.PREFS_NAME;

public class AddMovementTimerTask extends TimerTask {
    @Override
    public void run() {
        Log.i("Movement data", String.valueOf(TrackMovement.getMovementInstance().getDataToStore()));
        AddMovement movement = new AddMovement(TrackMovement.getMovementInstance().getDataToStore());

        EventSingleton.getEventInstance().AddMovementEvent(movement);
        TrackMovement.getMovementInstance().setDataToStore(0);

        SharedPreferences prefGet = MyApplication.getAppContext().getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEdit = prefGet.edit();
        Gson gson = new Gson();

        ArrayList<AddMovement> addMovements = new ArrayList<>();
        for (int i = 0; i < EventSingleton.getEventInstance().getMovementEventList().size(); i++) {
            addMovements.add(EventSingleton.getEventInstance().getMovementEventList().get(i));
        }
        String movementEventJson = gson.toJson(addMovements);
        prefEdit.putString("EVENT_SINGLETON_MOVEMENT" , movementEventJson);
        prefEdit.commit();


        Log.i("MOVEMENT EVENTS", EventSingleton.getEventInstance().getMovementEventList().toString());
    }

}
