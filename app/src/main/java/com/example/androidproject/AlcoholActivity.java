package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

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
                alcohol = AlcoholSingleton.getAlcoholInstance().getDrinks().get(position);
            }
        });
    }
    public void onClick(View v)     {
        EditText alcoholPriceField = findViewById(R.id.priceField);
        String alcoholPriceString = alcoholPriceField.getText().toString();

        double alcoholPrice = Double.parseDouble(alcoholPriceString);
        AddAlcohol addAlcoholEvent = new AddAlcohol(alcohol, alcoholPrice);
        EventSingleton.getEventInstance().AddViceEvent(addAlcoholEvent);

        Log.i("AddAlcohol event", EventSingleton.getEventInstance().getViceEventList().toString());

        Intent intent = new Intent(AlcoholActivity.this, MainActivity.class);
        startActivity(intent);
    }
}