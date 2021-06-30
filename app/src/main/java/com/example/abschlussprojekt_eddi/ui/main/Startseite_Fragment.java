package com.example.abschlussprojekt_eddi.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abschlussprojekt_eddi.Benutzer;
import com.example.abschlussprojekt_eddi.BenutzerdatenSpeicher;
import com.example.abschlussprojekt_eddi.EssenListAdapter;
import com.example.abschlussprojekt_eddi.EssenViewHolder;
import com.example.abschlussprojekt_eddi.StuhlListAdapter;
import com.example.abschlussprojekt_eddi.ViewModel_Essen;
import com.example.abschlussprojekt_eddi.ViewModel_Stuhl;

import static com.example.abschlussprojekt_eddi.R.id;
import static com.example.abschlussprojekt_eddi.R.layout;

public class Startseite_Fragment extends Fragment { //implements View.OnClickListener {

    public ViewModel_Stuhl viewModel_stuhl;
    public ViewModel_Essen viewModel_essen;

    Intent intentStuhl;
    Intent intentEssen;
    Intent intentEinstellungen;

    ImageButton bV1;
    ImageButton bV2;
    ImageButton bV3;
    ImageButton bV4;
    ImageButton bV5;
    Benutzer ben;
    BenutzerdatenSpeicher bdsp;

    //public static final int NEW_STUHL_ACTIVITY_REQUEST_CODE = 1;
    //public static final int NEW_ESSEN_ACTIVITY_REQUEST_CODE = 2;

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

        View view = inflater.inflate(layout.fragment_startseite_, container, false);

        RecyclerView recyclerView1 = (RecyclerView) view.findViewById(id.recycler_view_startseite_essen);
        RecyclerView recyclerView2 = (RecyclerView) view.findViewById(id.recycler_view_startseite_stuhl);

        final EssenListAdapter adapter = new EssenListAdapter(new EssenListAdapter.EssenDiff());
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setAdapter(adapter);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel_essen = new ViewModelProvider(getActivity()).get(ViewModel_Essen.class);
        viewModel_essen.getAllEssen().observe(getViewLifecycleOwner(), entity_essens -> {
            adapter.submitList(entity_essens);
        });

        //macht den RecyclerView wischbar
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                //Löschen bei Rechts-Wischen
                ItemTouchHelper.RIGHT){
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //Eintrag wird nicht gelöscht???
                try {
                    viewModel_essen.deleteEssen(adapter.getEssenAt(viewHolder.getAdapterPosition()));
                    Toast.makeText(getActivity(), "Essen Eintrag gelöscht", Toast.LENGTH_SHORT).show();
                }catch (IndexOutOfBoundsException e){
                    e.printStackTrace();
                }
            }
        }).attachToRecyclerView(recyclerView1);

        final StuhlListAdapter stuhlAdapter = new StuhlListAdapter(new StuhlListAdapter.StuhlDiff());
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setAdapter(stuhlAdapter);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel_stuhl = new ViewModelProvider(this).get(ViewModel_Stuhl.class);
        viewModel_stuhl.getAllStuhl().observe(getViewLifecycleOwner(), entity_stuhls -> {
            stuhlAdapter.submitList(entity_stuhls);
        });

        /*
        Button btStuhl = view.findViewById(id.stuhl_button);
        Button btEssen = view.findViewById(id.essen_button);
        Button btEinstellungen = view.findViewById(id.einstellungenButton);


         */
        bV1 = view.findViewById(id.VAS_0);
        bV2 = view.findViewById(id.VAS_1);
        bV3 = view.findViewById(id.VAS_2);
        bV4 = view.findViewById(id.VAS_3);
        bV5 = view.findViewById(id.VAS_4);

        bV1.setOnClickListener(this::stimmung);
        bV2.setOnClickListener(this::stimmung);
        bV3.setOnClickListener(this::stimmung);
        bV4.setOnClickListener(this::stimmung);
        bV5.setOnClickListener(this::stimmung);

        /*
        btStuhl.setOnClickListener(this::onClick);
        btEssen.setOnClickListener(this::onClick);
        btEinstellungen.setOnClickListener(this::onClick);


         */
        bdsp = new BenutzerdatenSpeicher(getContext());
        ben = bdsp.getLoggedInUser();

        return view;
    }


    // entgültige Ansicht noch anpassen, schaut noch komisch aus
    public void stimmung(View view) {
        Log.d("INFO", "Can you do this?");
        switch (view.getId()) {
            case id.VAS_0:
                //stimmung wo abspeichern?
                break;
            case id.VAS_1:
                //stimmung wo abspeichern?
                break;
            case id.VAS_2:
                //stimmung wo abspeichern?
                break;
            case id.VAS_3:
                //stimmung wo abspeichern?
                break;
            case id.VAS_4:
                //stimmung wo abspeichern?
                break;
        }
    }

    /*
    @SuppressLint("NonConstantResourceId")
    public void onClick(View view){
        switch (view.getId()){
            case id.stuhl_button:
                intentStuhl = new Intent(getActivity(), Eintrag_Stuhl.class);
                getActivity().startActivityForResult(intentStuhl, NEW_STUHL_ACTIVITY_REQUEST_CODE);
                break;
            case id.essen_button:
                intentEssen = new Intent(getActivity(), Eintrag_Essen.class);
                getActivity().startActivityForResult(intentEssen, NEW_ESSEN_ACTIVITY_REQUEST_CODE);
                break;
            case id.einstellungenButton:
                intentEinstellungen = new Intent(getActivity(), Einstellungen.class);
                startActivity(intentEinstellungen);
                break;
        }
    }

     */
}

