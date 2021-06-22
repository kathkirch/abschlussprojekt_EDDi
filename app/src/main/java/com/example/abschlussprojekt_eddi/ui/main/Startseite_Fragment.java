package com.example.abschlussprojekt_eddi.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abschlussprojekt_eddi.Benutzer;
import com.example.abschlussprojekt_eddi.BenutzerdatenSpeicher;
import com.example.abschlussprojekt_eddi.Einstellungen;
import com.example.abschlussprojekt_eddi.Eintrag_Essen;
import com.example.abschlussprojekt_eddi.Eintrag_Stuhl;
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
        recyclerView1.setAdapter(adapter);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel_essen = new ViewModelProvider(this).get(ViewModel_Essen.class);
        viewModel_essen.getAllEssen().observe(getViewLifecycleOwner(), entity_essens -> {
            adapter.submitList(entity_essens);
        });

        //recyclerView2.setHasFixedSize(true);
        final StuhlListAdapter stuhlAdapter = new StuhlListAdapter(new StuhlListAdapter.StuhlDiff());
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


    public void onClick(View view){
        switch (view.getId()){
            case R.id.stuhl_button:
                intentStuhl = new Intent(getActivity(), Eintrag_Stuhl.class);
                startActivityForResult(intentStuhl, NEW_STUHL_ACTIVITY_REQUEST_CODE);
                break;
            case R.id.essen_button:
                intentEssen = new Intent(getActivity(), Eintrag_Essen.class);
                startActivityForResult(intentEssen, NEW_ESSEN_ACTIVITY_REQUEST_CODE);
                break;
            case R.id.einstellungenButton:
                intentEinstellungen = new Intent(getActivity(), Einstellungen.class);
                startActivity(intentEinstellungen);
                break;
        }
    }
}

