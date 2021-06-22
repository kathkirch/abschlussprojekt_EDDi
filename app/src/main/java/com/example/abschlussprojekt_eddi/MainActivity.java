package com.example.abschlussprojekt_eddi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.abschlussprojekt_eddi.ui.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    public ViewModel_Stuhl viewModel_stuhl;
    public static final int ADD_NOTE_REQUEST = 1;
    RecyclerView recyclerView;


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

        // create recycler
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_kalender_essen);
        //System.out.println(recyclerView+ " recdk");
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);

        // define adapter
        final StuhlAdapter adapterStuhl = new StuhlAdapter();
        recyclerView.setAdapter(adapterStuhl);

        //VM wird zerstört, wenn die Aktivity (this) geschlossen wird
        //anstatt "this" kann man auch einfach ein fragement aufrufen
        viewModel_stuhl = new ViewModelProvider(this).get(ViewModel_Stuhl.class);
        //observe ist eine LiveData Methode und wird nur aktiviert, wenn die Aktivity im Vordergrund ist
        //anstatt "this" kann man auch ein Fragement übergeben
        try {
            viewModel_stuhl.getAll().observe(this, new Observer<List<Entity_Stuhl>>() {
                @Override
                public void onChanged(List<Entity_Stuhl> entity_stuhls) {
                    //update RecyclerView
                    adapterStuhl.setStuhlNotes(entity_stuhls);
                    Toast.makeText(getApplicationContext(), "onChanged", Toast.LENGTH_SHORT).show();
                }
            });

        }catch(NullPointerException npx){
            System.out.println(npx + " bububu");
        }
    }

    /*
    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

     */



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && (resultCode == Eintrag_Stuhl.RESULT_OK)){

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
            Boolean blut = data.getExtras().getBoolean(Eintrag_Stuhl.EXTRA_BLUT);
            Boolean schmerz = data.getExtras().getBoolean(Eintrag_Stuhl.EXTRA_SCHMERZ);
            String farbe = data.getStringExtra(Eintrag_Stuhl.EXTRA_FARBE);
            Boolean unverdauteNahrung = data.getExtras().getBoolean
                    (Eintrag_Stuhl.EXTRA_UNVERDAUTENAHRUNG);
            String schleim = data.getStringExtra(Eintrag_Stuhl.EXTRA_SCHLEIM);
            String menge = data.getStringExtra(Eintrag_Stuhl.EXTRA_MENGE);
            String notiz = data.getStringExtra(Eintrag_Stuhl.EXTRA_NOTIZ);

            // woher FotoReferenz?
            Entity_Stuhl entity_stuhl = new Entity_Stuhl(jahr, monat, tag, stunde, minute, bristol,
                    blut, schmerz, farbe, unverdauteNahrung, schleim, menge, notiz, "1");

            //da is fehler context irgendwas, hier abbruch...
            viewModel_stuhl.insertStuhl(entity_stuhl);
            Toast.makeText(this, "Stuhleintrag gespeichert", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Speichern ergab Probleme", Toast.LENGTH_SHORT).show();
        }
    }
}