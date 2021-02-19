package com.example.androidproject;

import android.content.Context;
import android.util.Log;

import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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