package com.example.androidproject;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import static com.example.androidproject.MainActivity.PREFS_NAME;

/**
 * An activity that is created to get access to a static Context that can be used in all classes, and
 * also to load the vice consumption for the EventSingleton from the SharedPreferences once when the
 * app launches
 */
public class MyApplication extends Application {
    private static Context context;

    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();

        SharedPreferences prefGet = getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);
        Gson gson = new Gson();

        TypeToken<List<AddTobacco>> tokenTobacco = new TypeToken<List<AddTobacco>>() {};
        String jsonTobacco = prefGet.getString("EVENT_SINGLETON_TOBACCO","");
        List<AddTobacco> addTobaccoList = gson.fromJson(jsonTobacco, tokenTobacco.getType());

        if (!jsonTobacco.equals("")) {
            for (int i = 0; i < addTobaccoList.size(); i++) {
                EventSingleton.getEventInstance().AddViceEvent(addTobaccoList.get(i));
            }
        }

        TypeToken<List<AddAlcohol>> tokenAlcohol = new TypeToken<List<AddAlcohol>>() {};
        String jsonAlcohol = prefGet.getString("EVENT_SINGLETON_ALCOHOL","");
        List<AddAlcohol> addAlcoholList = gson.fromJson(jsonAlcohol, tokenAlcohol.getType());

        if (!jsonAlcohol.equals("")) {
            for (int i = 0; i < addAlcoholList.size(); i++) {
                EventSingleton.getEventInstance().AddViceEvent(addAlcoholList.get(i));
            }
        }
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }

}
