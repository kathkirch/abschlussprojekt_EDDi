package com.example.abschlussprojekt_eddi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.abschlussprojekt_eddi.ui.main.SectionsPagerAdapter;
import com.example.abschlussprojekt_eddi.ui.main.Startseite_Fragment;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {

    private ViewModel_Essen viewModel_essen;
    private ViewModel_Stuhl viewModel_stuhl;

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

        viewModel_stuhl = new ViewModelProvider(this).get(ViewModel_Stuhl.class);
        viewModel_essen = new ViewModelProvider(this).get(ViewModel_Essen.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if ((resultCode == Eintrag_Stuhl.RESULT_OK) &&
                requestCode == Startseite_Fragment.NEW_STUHL_ACTIVITY_REQUEST_CODE) {

            // Uhrzeit in Stunde und Minute trennen
            String uhrzeit = data.getStringExtra(Eintrag_Stuhl.EXTRA_UHRZEIT);
            String[] timeValues = uhrzeit.split(":", 2);
            int stunde = Integer.parseInt(timeValues[0]);
            int minute = Integer.parseInt(timeValues[1]);

            // Datum welches als ganzer String gespeichert ist wieder in einzelne Int zerteilen
            String datum = data.getStringExtra(Eintrag_Stuhl.EXTRA_DATUM);
            String[] dateValues = datum.split("\\.", 3);
            int tag = Integer.parseInt(dateValues[0]);
            int monat = Integer.parseInt(dateValues[1]);
            int jahr = Integer.parseInt(dateValues[2]);

            int bristol = data.getIntExtra(Eintrag_Stuhl.EXTRA_BRISTOL, 1);
            boolean blut = data.getBooleanExtra(Eintrag_Stuhl.EXTRA_BLUT, false);
            boolean schmerz = data.getBooleanExtra(Eintrag_Stuhl.EXTRA_SCHMERZ, false);
            int farbe = data.getIntExtra(Eintrag_Stuhl.EXTRA_FARBE, 1);
            boolean unverdauteNahrung = data.getBooleanExtra(Eintrag_Stuhl.EXTRA_UNVERDAUTENAHRUNG, false);
            int schleim = data.getIntExtra(Eintrag_Stuhl.EXTRA_SCHLEIM, 1);
            int menge = data.getIntExtra(Eintrag_Stuhl.EXTRA_MENGE, 1);
            String notiz = data.getStringExtra(Eintrag_Stuhl.EXTRA_NOTIZ);

            Entity_Stuhl entity_stuhl = new Entity_Stuhl(jahr, monat, tag, stunde, minute, bristol,
                     schmerz, blut, farbe, unverdauteNahrung, schleim, menge, notiz);

            viewModel_stuhl.insertStuhl(entity_stuhl);
            Toast.makeText(this, "Stuhleintrag gespeichert", Toast.LENGTH_SHORT).show();

        }else if (resultCode == Eintrag_Essen.RESULT_OK &&
                requestCode == Startseite_Fragment.NEW_ESSEN_ACTIVITY_REQUEST_CODE) {
            String essenNeu = data.getStringExtra(Eintrag_Essen.EXTRA_ESSEN);

            // Uhrzeit in Stunde und Minute trennen
            String uhrzeit = data.getStringExtra(Eintrag_Essen.EXTRA_UHRZEIT);
            String[] timeValues = uhrzeit.split(":", 2);
            int stunde = Integer.parseInt(timeValues[0]);
            int minute = Integer.parseInt(timeValues[1]);

            // Datum welches als ganzer String gespeichert ist wieder in einzelne Int zerteilen
            String datum = data.getStringExtra(Eintrag_Essen.EXTRA_DATUM);
            String[] dateValues = datum.split("\\.", 3);
            int tag = Integer.parseInt(dateValues[0]);
            int monat = Integer.parseInt(dateValues[1]);
            int jahr = Integer.parseInt(dateValues[2]);

            Entity_Essen essen = new Entity_Essen(essenNeu, jahr, monat, tag, stunde, minute);
            viewModel_essen.insertEssen(essen);
            Toast.makeText(this, "Essen gespeichert", Toast.LENGTH_SHORT).show();

        }else if (resultCode == Eintrag_Essen.RESULT_OK &&
                requestCode == Startseite_Fragment.NEW_ESSEN_EDIT_REQUEST_CODE){
            int id = data.getIntExtra(Eintrag_Essen.EXTRA_ESSEN_ID, -1);
            if (id == -1){
                Toast.makeText(this, "Eintrag kann nicht aktualisiert werden", Toast.LENGTH_SHORT).show();
                return;
            }
            String essenNeu = data.getStringExtra(Eintrag_Essen.EXTRA_ESSEN);

            // Uhrzeit in Stunde und Minute trennen
            String uhrzeit = data.getStringExtra(Eintrag_Essen.EXTRA_UHRZEIT);
            String[] timeValues = uhrzeit.split(":", 2);
            int stunde = Integer.parseInt(timeValues[0]);
            int minute = Integer.parseInt(timeValues[1]);

            // Datum welches als ganzer String gespeichert ist wieder in einzelne Int zerteilen
            String datum = data.getStringExtra(Eintrag_Essen.EXTRA_DATUM);
            String[] dateValues = datum.split("\\.", 3);
            int tag = Integer.parseInt(dateValues[0]);
            int monat = Integer.parseInt(dateValues[1]);
            int jahr = Integer.parseInt(dateValues[2]);

            Entity_Essen essen = new Entity_Essen(essenNeu, jahr, monat, tag, stunde, minute);
            essen.setEssenID(id);
            viewModel_essen.updateEssen(essen);
            Toast.makeText(this, "Essenseintrag aktualisiert", Toast.LENGTH_SHORT).show();

        }else if (resultCode == Eintrag_Stuhl.RESULT_OK &&
                requestCode == Startseite_Fragment.NEW_STUHL_EDIT_REQUEST_CODE){

            System.out.println("NEW STUHL EDIT REQUEST CODE");

            int id = data.getIntExtra(Eintrag_Stuhl.EXTRA_ID, -1);
            if (id == -1){
                Toast.makeText(this, "Eintrag kann nicht aktualisiert werden", Toast.LENGTH_SHORT).show();
                return;
            }
            // Uhrzeit in Stunde und Minute trennen
            String uhrzeit = data.getStringExtra(Eintrag_Stuhl.EXTRA_UHRZEIT);
            String[] timeValues = uhrzeit.split(":", 2);
            int stunde = Integer.parseInt(timeValues[0]);
            int minute = Integer.parseInt(timeValues[1]);

            // Datum welches als ganzer String gespeichert ist wieder in einzelne Int zerteilen
            String datum = data.getStringExtra(Eintrag_Stuhl.EXTRA_DATUM);
            String[] dateValues = datum.split("\\.", 3);
            int tag = Integer.parseInt(dateValues[0]);
            int monat = Integer.parseInt(dateValues[1]);
            int jahr = Integer.parseInt(dateValues[2]);

            int bristol = data.getIntExtra(Eintrag_Stuhl.EXTRA_BRISTOL, 1);
            boolean blut = data.getBooleanExtra(Eintrag_Stuhl.EXTRA_BLUT, false);
            boolean schmerz = data.getBooleanExtra(Eintrag_Stuhl.EXTRA_SCHMERZ, false);
            int farbe = data.getIntExtra(Eintrag_Stuhl.EXTRA_FARBE, 1);
            boolean unverdauteNahrung = data.getBooleanExtra(Eintrag_Stuhl.EXTRA_UNVERDAUTENAHRUNG, false);
            int schleim = data.getIntExtra(Eintrag_Stuhl.EXTRA_SCHLEIM, 1);
            int menge = data.getIntExtra(Eintrag_Stuhl.EXTRA_MENGE, 1);
            String notiz = data.getStringExtra(Eintrag_Stuhl.EXTRA_NOTIZ);

            Entity_Stuhl entity_stuhl = new Entity_Stuhl(jahr, monat, tag, stunde, minute, bristol,
                    blut, schmerz, farbe, unverdauteNahrung, schleim, menge, notiz);
            entity_stuhl.setId(id);
            viewModel_stuhl.update(entity_stuhl);
            Toast.makeText(this, "Stuhleintrag aktualisiert", Toast.LENGTH_SHORT).show();

        }else {
            super.onActivityResult(requestCode, resultCode, data);
            Toast.makeText(this, "Speichern ergab Probleme", Toast.LENGTH_SHORT).show();
        }
    }
}



