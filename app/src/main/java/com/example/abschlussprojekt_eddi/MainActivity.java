package com.example.abschlussprojekt_eddi;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.abschlussprojekt_eddi.ui.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {

    BenutzerdatenSpeicher bdSp;
    public static final String INPUT_STRING = "InputString";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SectionsPagerAdapter sectionsPagerAdapter = new
                SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    /*
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        System.out.println("dududu" + (requestCode == ADD_NOTE_REQUEST && (resultCode == RESULT_OK)));
        System.out.println(requestCode + "requestcode");
        System.out.println(ADD_NOTE_REQUEST + "ADD NOTE");
        System.out.println(resultCode + "resultCode");
        System.out.println(RESULT_OK+ " result ok");

        if (requestCode == ADD_NOTE_REQUEST && (resultCode == RESULT_OK)){

            // Datum welches als ganzer String gespeichert ist wieder in einzelne Int zerteilen
            String datum = data.getStringExtra(Eintrag_Stuhl.EXTRA_DATUM);
            String [] dateValues = datum.split(".");
            int jahr = Integer.parseInt(dateValues[0]);
            int monat = Integer.parseInt(dateValues[1]);
            int tag = Integer.parseInt(dateValues[2]);

            // Uhrzeit in Stunde und Minute trennen
            String uhrzeit = data.getStringExtra(Eintrag_Stuhl.EXTRA_UHRZEIT);
            String [] timeValues = uhrzeit.split(":");
            int stunde = Integer.parseInt(timeValues[0]);
            int minute = Integer.parseInt(timeValues[1]);

            String bristol = data.getStringExtra(Eintrag_Stuhl.EXTRA_BRISTOL);
            Boolean blut = getIntent().getExtras().getBoolean(Eintrag_Stuhl.EXTRA_BLUT);
            Boolean schmerz = getIntent().getExtras().getBoolean(Eintrag_Stuhl.EXTRA_SCHMERZ);
            String farbe = data.getStringExtra(Eintrag_Stuhl.EXTRA_FARBE);
            Boolean unverdauteNahrung = getIntent().getExtras().getBoolean
                    (Eintrag_Stuhl.EXTRA_UNVERDAUTENAHRUNG);
            String schleim = data.getStringExtra(Eintrag_Stuhl.EXTRA_SCHLEIM);
            String menge = data.getStringExtra(Eintrag_Stuhl.EXTRA_MENGE);
            String notiz = data.getStringExtra(Eintrag_Stuhl.EXTRA_NOTIZ);

            // woher FotoReferenz?
            Entity_Stuhl entity_stuhl = new Entity_Stuhl(jahr, monat, tag, stunde, minute, bristol,
                    blut, schmerz, farbe, unverdauteNahrung, schleim, menge, notiz, "1");

            Toast.makeText(this, "Stuhleintrag gespeichert", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Speichern ergab Probleme", Toast.LENGTH_SHORT).show();
        }
    }

     */
}