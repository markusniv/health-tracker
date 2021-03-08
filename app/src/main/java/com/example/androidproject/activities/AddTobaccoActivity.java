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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.androidproject.dataclasses.EventSingleton;
import com.example.androidproject.R;
import com.example.androidproject.dataclasses.Tobacco;
import com.example.androidproject.dataclasses.TobaccoListSingleton;
import com.example.androidproject.dataclasses.AddTobacco;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

import static com.example.androidproject.activities.MainActivity.PREFS_NAME;

/**
 * Activity in which the user chooses a tobacco they've smoked and enters its price. From this data,
 * a AddTobacco-object is created, which is then added into the EventSingleton viceEventList.
 */
public class AddTobaccoActivity extends AppCompatActivity {

    private Tobacco tobacco;
    private int regularPack = 20;
    private int maxiPack = 27;
    private DecimalFormat df = new DecimalFormat("0.00");
    private DecimalFormatSymbols dfs = new DecimalFormatSymbols();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tobacco);

        dfs.setDecimalSeparator('.');
        df.setDecimalFormatSymbols(dfs);

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
        String tobaccoPriceString = tobaccoPriceField.getText().toString();
        RadioGroup tobaccoPackRadioGroup = findViewById(R.id.radioTobaccoType);
        RadioButton chosenTobaccoPack = findViewById(tobaccoPackRadioGroup.getCheckedRadioButtonId());

        int tobaccoPackAmount;
        if (chosenTobaccoPack == findViewById(R.id.radioRegularPack)) {
            tobaccoPackAmount = regularPack;
        } else {
            tobaccoPackAmount = maxiPack;
        }

        double tobaccoPrice;
        if (!tobaccoPriceString.equals("")) {
            tobaccoPrice = Double.parseDouble(tobaccoPriceField.getText().toString()) / tobaccoPackAmount;
        } else {
            tobaccoPrice = 10.0 / tobaccoPackAmount;
        }

        tobaccoPrice = Double.parseDouble(df.format(tobaccoPrice));

        AddTobacco addTobaccoEvent = new AddTobacco(tobacco, tobaccoPrice);
        EventSingleton.getEventInstance().AddViceEvent(addTobaccoEvent);

        Log.i("AddTobacco event", EventSingleton.getEventInstance().getViceEventList().toString());

        SharedPreferences prefGet = getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEdit = prefGet.edit();
        Gson gson = new Gson();

        ArrayList<AddTobacco> addTobaccos = new ArrayList<>();
        for (int i = 0; i < EventSingleton.getEventInstance().getSpecificViceEvents("Tobacco").size(); i++) {
            addTobaccos.add((AddTobacco)EventSingleton.getEventInstance().getSpecificViceEvents("Tobacco").get(i));
        }
        String viceEventJson = gson.toJson(addTobaccos);
        prefEdit.putString("EVENT_SINGLETON_TOBACCO" , viceEventJson);
        prefEdit.commit();

        Intent intent = new Intent(AddTobaccoActivity.this, MainActivity.class);
        startActivity(intent);
    }
}