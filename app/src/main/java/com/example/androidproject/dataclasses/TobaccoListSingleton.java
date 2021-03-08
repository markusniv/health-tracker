package com.example.androidproject.dataclasses;

import android.util.Log;

import com.example.androidproject.R;
import com.example.androidproject.activities.MyApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * A singleton that creates and stores all the different Tobacco-objects the user can add into the
 * AddTobacco-event. The tobacco types are stored in res/raw/tobaccoharmfulsubstance.csv and are converted
 * into objects in the constructor line by line.
 */
public class TobaccoListSingleton {
    private static final TobaccoListSingleton tobaccoListInstance = new TobaccoListSingleton();
    private ArrayList<Tobacco> tobaccoList;

    private TobaccoListSingleton() {
        tobaccoList = new ArrayList<>();
        InputStream is = MyApplication.getAppContext().getResources().openRawResource(R.raw.tobaccoharmfulsubstance);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr, 8192);
        try {
            String line;
            int counter = 0;
            while (true) {
                line = br.readLine();
                if (line == null) {
                    break;
                }
                String[] arrOfLine = line.split(";");
                tobaccoList.add(new Tobacco(arrOfLine[0], Double.parseDouble(arrOfLine[1]), Double.parseDouble(arrOfLine[2]), Double.parseDouble(arrOfLine[3])));
                Log.i("TobaccoListNumber: " + counter, tobaccoList.get(counter).toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static TobaccoListSingleton getTobaccoListInstance() {
        return tobaccoListInstance;
    }

    public ArrayList<Tobacco> getTobaccoList() {
        return tobaccoList;
    }
}
