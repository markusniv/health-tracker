package com.example.androidproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.androidproject.R;

/**
 * Activity that shows user information about the risks and harm of vices in ScrollView.
 * Text depends on which button was clicked in previous activity
 */

public class RisksActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risks_activity);

        Intent intent = getIntent();

        String buttonType = intent.getStringExtra(MainActivity.btnType);
        if (buttonType.equals("Alcohol"))    {

            TextView riskText = findViewById(R.id.infoField);
            riskText.setText(getRisks(1));
        }
        if(buttonType.equals("Tobacco")){
            TextView riskText = findViewById(R.id.infoField);
            riskText.setText(getRisks(2));
        }

    }

    // Buttons to return to main or statistics activities
    public void buttonsClicked(View v) {
        if (v == findViewById(R.id.btnBack)) {
            Log.i("Note", "Back to main");

            Intent backToMain = new Intent(RisksActivity.this, MainActivity.class);
            startActivity(backToMain);

        }
        if (v == findViewById(R.id.btnToStats)) {
            Log.i("Note", "Going to statistics");

            Intent goToStats = new Intent(RisksActivity.this, StatisticsActivity.class);
            startActivity(goToStats);
        }
    }
    // Get all texts with risk information
    public String getRisks(int textId)  {
        if (textId == 1){
            return "Ongelmakäytöstä puhutaan, kun riskikäytön rajat ylittyvät. Ongelmakäyttäjällä ei välttämättä ole vielä haittoja, mutta niiden ilmaantumiseen on suuri todennäköisyys. Siitä syystä näiden rajojen ylittyminen ilman haittojakin edellyttää ehdottomasti juomisen vähentämistä. Jos tämä ei omin voimin, läheisten tai vertaistuen avulla onnistu on syytä hakeutua hoitoon.  Suomessa korken riskin taso terveelle, keskikokoiselle miehelle on 23-24 alkoholiannosta viikossa tai noin kolme annosta säännöllisesti päivittäin juotuna. Vastaavat luvut naisille ovat 12-16 viikossa tai 2 annosta säännöllisesti päivittäin. Kohtalaisen riskin taso miehillä on 14 annosta ja naisilla 7 annosta viikossa." + "\n" + "Suomessa yksi alkoholiannos vastaa 33 cl keskiolutta, 12 cl mietoa viiniä tai 4 cl väkeviä. Riskikäyttö on kyseessä silloin, jos nämä rajat ylittyvät säännöllisesti.";
        }
        if (textId == 2){
            return "Tupakointi kuormittaa koko elimistöä ja aiheuttaa lukuisia sairauksia. Lisäksi se heikentää lääkehoitojen tehoa ja sairauden ennustetta. Tupakointi suurentaa leikkauskomplikaatioiden riskiä ja hidastaa toipumista." + "\n" + "Tupakansavu sisältää yli 4000 yhdistettä, joista noin 70 tiedetään syöpää aiheuttaviksi aineiksi. Terveydelle haitallisia yhdisteitä ovat muun muassa häkä, terva, tupakkaspesifiset nitrosamiinit, asetaldehydi, lyijy, bentseeni ja nikotiini." + "\n" + "Tupakointi on yleisin estettävissä oleva ennenaikaisen kuolleisuuden aiheuttaja länsimaissa. Tupakkariippuvuuden keskeinen tekijä on tupakkakasvin sisältämä nikotiini, jonka imeytymistä muut tupakkaan valmistusvaiheessa lisätyt kemikaalit edistävät.\n" + "\n" + "Suomessa kuolee vuosittain noin 4000 henkilöä tupakoinnin aiheuttamiin sairauksiin. Tupakointi on keskeisin muokattavissa oleva syövän riskitekijä. Suomessa joka kolmas syöpäsairaus aiheutuu tupakasta, ja joka viidennessä sydänperäisessä kuolemassa tupakointi on tärkein riskitekijä. Tupakointi lyhentää elinajanodotetta yli 6 vuotta." + "\n" + "Myös suussa käytettävä tupakka, nuuska, sisältää haitallisia ja syöpävaarallisia aineita.";
        }
        return "";
    }
}