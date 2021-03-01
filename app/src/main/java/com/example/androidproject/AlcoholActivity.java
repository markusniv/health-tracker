package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AlcoholActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alcohol);


        AlcoholSingleton alcoholSingleton = AlcoholSingleton.getAlcoholInstance();
        ListView alcoholList = (ListView) findViewById(R.id.alcoholList);

        alcoholList.setAdapter(new ArrayAdapter<Alcohol>(this,
                android.R.layout.simple_list_item_1,
                AlcoholSingleton.getAlcoholInstance().getDrinks()));

    }
}