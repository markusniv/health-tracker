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

/**
 * Activity in which the user chooses a tobacco they've smoked and enters its price. From this data,
 * a AddTobacco-object is created, which is then added into the EventSingleton viceEventList.
 */
public class AddTobaccoActivity extends AppCompatActivity {

    private Tobacco tobacco;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tobacco);

        ListView lv = findViewById(R.id.tobaccoList);

        lv.setAdapter(new ArrayAdapter<Tobacco>(
                this,
                android.R.layout.simple_list_item_1,
                TobaccoListSingleton.getTobaccoListInstance().getTobaccoList()
        ));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tobacco = TobaccoListSingleton.getTobaccoListInstance().getTobaccoList().get(position);
            }
        });
    }

    public void onClick(View v) {
        EditText tobaccoPriceField = findViewById(R.id.editTobaccoPrice);
        double tobaccoPrice = Double.parseDouble(tobaccoPriceField.getText().toString());
        AddTobacco addTobaccoEvent = new AddTobacco(tobacco, tobaccoPrice);
        EventSingleton.getEventInstance().AddViceEvent(addTobaccoEvent);

        Log.i("AddTobacco event", EventSingleton.getEventInstance().getViceEventList().toString());

        Intent intent = new Intent(AddTobaccoActivity.this, MainActivity.class);
        startActivity(intent);
    }
}