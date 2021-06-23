package com.example.abschlussprojekt_eddi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.abschlussprojekt_eddi.ui.main.SectionsPagerAdapter;
import com.example.abschlussprojekt_eddi.ui.main.Startseite_Fragment;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {

    public ViewModel_Stuhl viewModel_stuhl;
    RecyclerView recyclerView;
    private ViewModel_Essen viewModel_essen;



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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        System.out.println((requestCode == Startseite_Fragment.NEW_STUHL_ACTIVITY_REQUEST_CODE) + "request stuhl");
        System.out.println(requestCode + " das ist requestcode");
        System.out.println((resultCode == Eintrag_Stuhl.RESULT_OK) + " result stuhl?");

        System.out.println((requestCode == Startseite_Fragment.NEW_ESSEN_ACTIVITY_REQUEST_CODE ) + " request essen");
        System.out.println((resultCode == Eintrag_Essen.RESULT_OK) + " result essen");
        System.out.println(requestCode + " request code essen");

        if (requestCode == Startseite_Fragment.NEW_STUHL_ACTIVITY_REQUEST_CODE && (resultCode == Eintrag_Stuhl.RESULT_OK)){

            // Uhrzeit in Stunde und Minute trennen
            String uhrzeit = data.getStringExtra(Eintrag_Stuhl.EXTRA_UHRZEIT);
            String [] timeValues = uhrzeit.split(":", 2);
            int stunde = Integer.parseInt(timeValues[0]);
            int minute = Integer.parseInt(timeValues[1]);

            // Datum welches als ganzer String gespeichert ist wieder in einzelne Int zerteilen
            String datum = data.getStringExtra(Eintrag_Stuhl.EXTRA_DATUM);
            String [] dateValues = datum.split("\\.", 3);
            int tag = Integer.parseInt(dateValues[0]);
            int monat = Integer.parseInt(dateValues[1]);
            int jahr = Integer.parseInt(dateValues[2]);

            String bristol = data.getStringExtra(Eintrag_Stuhl.EXTRA_BRISTOL);
            boolean blut = data.getExtras().getBoolean(Eintrag_Stuhl.EXTRA_BLUT);
            boolean schmerz = data.getExtras().getBoolean(Eintrag_Stuhl.EXTRA_SCHMERZ);
            String farbe = data.getStringExtra(Eintrag_Stuhl.EXTRA_FARBE);
            boolean unverdauteNahrung = data.getExtras().getBoolean
                    (Eintrag_Stuhl.EXTRA_UNVERDAUTENAHRUNG);
            String schleim = data.getStringExtra(Eintrag_Stuhl.EXTRA_SCHLEIM);
            String menge = data.getStringExtra(Eintrag_Stuhl.EXTRA_MENGE);
            String notiz = data.getStringExtra(Eintrag_Stuhl.EXTRA_NOTIZ);

            // woher FotoReferenz?
            Entity_Stuhl entity_stuhl = new Entity_Stuhl(jahr, monat, tag, stunde, minute, bristol,
                    blut, schmerz, farbe, unverdauteNahrung, schleim, menge, notiz, "1");

            viewModel_stuhl.insertStuhl(entity_stuhl);
            Toast.makeText(this, "Stuhleintrag gespeichert", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Speichern ergab Probleme", Toast.LENGTH_SHORT).show();
        }

        if(requestCode == Startseite_Fragment.NEW_ESSEN_ACTIVITY_REQUEST_CODE && resultCode == Eintrag_Essen.RESULT_OK){
            Entity_Essen essen = new Entity_Essen(data.getStringExtra(Eintrag_Essen.EXTRA_ESSEN));
            viewModel_essen.insertEssen(essen);

        } else {
            Toast.makeText(getApplicationContext(), "Konnte nichts gespeichert werden",
                    Toast.LENGTH_SHORT).show();
        }
    }
}