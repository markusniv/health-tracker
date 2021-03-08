package com.example.androidproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.androidproject.dataclasses.AlcoholSingleton;
import com.example.androidproject.dataclasses.EventSingleton;
import com.example.androidproject.R;
import com.example.androidproject.dataclasses.AddAlcohol;
import com.example.androidproject.dataclasses.Alcohol;
import com.google.gson.Gson;

import java.util.ArrayList;

import static com.example.androidproject.activities.MainActivity.PREFS_NAME;

/**
 * Activity in which the user chooses an alcohol type and size that was drunk and enters its price.
 * From this data, an AddAlcohol-object is created, which is then added into the EventSingleton viceEventList.
 */

public class AlcoholActivity extends AppCompatActivity {

    private Alcohol alcohol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alcohol);


        AlcoholSingleton alcoholSingleton = AlcoholSingleton.getAlcoholInstance();
        ListView alcoholList = (ListView) findViewById(R.id.alcoholList);

        alcoholList.setAdapter(new ArrayAdapter<Alcohol>(this,
                android.R.layout.simple_list_item_1,
                AlcoholSingleton.getAlcoholInstance().getDrinks()
        ));

        alcoholList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EditText alcoholPriceField = findViewById(R.id.priceField);
                alcohol = AlcoholSingleton.getAlcoholInstance().getDrinks().get(position);
                alcoholPriceField.setText(Double.toString(alcohol.getDefaultPrice()), TextView.BufferType.EDITABLE);

            }
        });
    }

    /**
     * Creates a new AddAlcohol-object when clicking the add button using the input the user has made
     * @param v The Add-button
     */
    public void onClick(View v)     {
        EditText alcoholPriceField = findViewById(R.id.priceField);
        String alcoholPriceString = alcoholPriceField.getText().toString();

        double alcoholPrice = Double.parseDouble(alcoholPriceString);

        AddAlcohol addAlcoholEvent = new AddAlcohol(alcohol, alcoholPrice);
        EventSingleton.getEventInstance().AddViceEvent(addAlcoholEvent);

        Log.i("AddAlcohol event", EventSingleton.getEventInstance().getViceEventList().toString());

        SharedPreferences prefGet = getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEdit = prefGet.edit();
        Gson gson = new Gson();

        ArrayList<AddAlcohol> addAlcohols = new ArrayList<>();
        for (int i = 0; i < EventSingleton.getEventInstance().getSpecificViceEvents("Alcohol").size(); i++) {
            addAlcohols.add((AddAlcohol)EventSingleton.getEventInstance().getSpecificViceEvents("Alcohol").get(i));
        }
        String viceEventJson = gson.toJson(addAlcohols);
        prefEdit.putString("EVENT_SINGLETON_ALCOHOL" , viceEventJson);
        prefEdit.commit();

        Intent intent = new Intent(AlcoholActivity.this, MainActivity.class);
        startActivity(intent);
    }
}