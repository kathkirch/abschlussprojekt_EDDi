package com.example.abschlussprojekt_eddi.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abschlussprojekt_eddi.Benutzer;
import com.example.abschlussprojekt_eddi.BenutzerdatenSpeicher;
import com.example.abschlussprojekt_eddi.Einstellungen;
import com.example.abschlussprojekt_eddi.Eintrag_Essen;
import com.example.abschlussprojekt_eddi.Eintrag_Stuhl;
import com.example.abschlussprojekt_eddi.Entity_Essen;
import com.example.abschlussprojekt_eddi.Entity_Stuhl;
import com.example.abschlussprojekt_eddi.EssenListAdapter;
import com.example.abschlussprojekt_eddi.R;
import com.example.abschlussprojekt_eddi.StuhlListAdapter;
import com.example.abschlussprojekt_eddi.ViewModel_Essen;
import com.example.abschlussprojekt_eddi.ViewModel_Stuhl;

public class Startseite_Fragment extends Fragment {

    public ViewModel_Stuhl viewModel_stuhl;
    public ViewModel_Essen viewModel_essen;

    Intent intentStuhl;
    Intent intentEssen;
    Intent intentEinstellungen;

    Button btStuhl;
    Button btEssen;
    Button btEinstellungen;

    ImageButton bV1;
    ImageButton bV2;
    ImageButton bV3;
    ImageButton bV4;
    ImageButton bV5;
    Benutzer ben;
    BenutzerdatenSpeicher bdsp;

    public static final int NEW_STUHL_ACTIVITY_REQUEST_CODE = 1;
    public static final int NEW_ESSEN_ACTIVITY_REQUEST_CODE = 1;

    public Startseite_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_startseite_, container, false);

        RecyclerView recyclerView1 = (RecyclerView) view.findViewById(R.id.recycler_view_startseite_essen);
        RecyclerView recyclerView2 = (RecyclerView) view.findViewById(R.id.recycler_view_startseite_stuhl);

        final EssenListAdapter adapter = new EssenListAdapter(new EssenListAdapter.EssenDiff());
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setAdapter(adapter);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel_essen = new ViewModelProvider(this).get(ViewModel_Essen.class);
        viewModel_essen.getAllEssen().observe(getViewLifecycleOwner(), entity_essens -> {
            adapter.submitList(entity_essens);
        });

        final StuhlListAdapter stuhlAdapter = new StuhlListAdapter(new StuhlListAdapter.StuhlDiff());
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setAdapter(stuhlAdapter);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel_stuhl = new ViewModelProvider(this).get(ViewModel_Stuhl.class);
        viewModel_stuhl.getAllStuhl().observe(getViewLifecycleOwner(), entity_stuhls -> {
            stuhlAdapter.submitList(entity_stuhls);
        });

        btStuhl = view.findViewById(R.id.stuhl_button);
        btEssen = view.findViewById(R.id.essen_button);
        btEinstellungen = view.findViewById(R.id.einstellungenButton);

        bV1 = view.findViewById(R.id.VAS_0);
        bV2 = view.findViewById(R.id.VAS_1);
        bV3 = view.findViewById(R.id.VAS_2);
        bV4 = view.findViewById(R.id.VAS_3);
        bV5 = view.findViewById(R.id.VAS_4);

        bV1.setOnClickListener(this::stimmung);
        bV2.setOnClickListener(this::stimmung);
        bV3.setOnClickListener(this::stimmung);
        bV4.setOnClickListener(this::stimmung);
        bV5.setOnClickListener(this::stimmung);

        btStuhl.setOnClickListener(this::onClick);
        btEssen.setOnClickListener(this::onClick);
        btEinstellungen.setOnClickListener(this::onClick);

        bdsp = new BenutzerdatenSpeicher(getContext());
        ben = bdsp.getLoggedInUser();

        return view;
    }


    // entgültige Ansicht noch anpassen, schaut noch komisch aus
    public void stimmung(View view) {
        Log.d("INFO", "Can you do this?");
        switch (view.getId()) {
            case R.id.VAS_0:
                //stimmung wo abspeichern?
                break;
            case R.id.VAS_1:
                //stimmung wo abspeichern?
                break;
            case R.id.VAS_2:
                //stimmung wo abspeichern?
                break;
            case R.id.VAS_3:
                //stimmung wo abspeichern?
                break;
            case R.id.VAS_4:
                //stimmung wo abspeichern?
                break;
        }
    }

    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    System.out.println(result.getResultCode() == Activity.RESULT_OK);

                    if (result.getResultCode() == Activity.RESULT_OK) {

                        System.out.println("r u doing this?");

                        //getData returnt den Intent
                        Intent data = result.getData();

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
                        Toast.makeText(getContext(), "Stuhleintrag gespeichert", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getContext(), "Speichern ergab Probleme", Toast.LENGTH_SHORT).show();
                    }

                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Entity_Essen essen = new Entity_Essen(data.getStringExtra(Eintrag_Essen.EXTRA_ESSEN));
                        viewModel_essen.insertEssen(essen);
                        Toast.makeText(getContext(), "Essen gespeichert", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Konnte nichts gespeichert werden",
                                Toast.LENGTH_SHORT).show();
                    }
                }
    });

    public void onClick(View view){
        switch (view.getId()){
            case R.id.stuhl_button:
                intentStuhl = new Intent(getActivity(), Eintrag_Stuhl.class);
                mStartForResult.launch(intentStuhl);
            case R.id.essen_button:
                intentEssen = new Intent(getActivity(), Eintrag_Essen.class);
                mStartForResult.launch(intentEssen);
                break;
            case R.id.einstellungenButton:
                intentEinstellungen = new Intent(getActivity(), Einstellungen.class);
                startActivity(intentEinstellungen);
                break;
        }
    }
}

