package com.example.abschlussprojekt_eddi.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abschlussprojekt_eddi.Benutzer;
import com.example.abschlussprojekt_eddi.BenutzerdatenSpeicher;
import com.example.abschlussprojekt_eddi.Einstellungen;
import com.example.abschlussprojekt_eddi.Eintrag_Essen;
import com.example.abschlussprojekt_eddi.Eintrag_Stuhl;
import com.example.abschlussprojekt_eddi.Entity_Stuhl;
import com.example.abschlussprojekt_eddi.LogbuchDatabase;
import com.example.abschlussprojekt_eddi.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Startseite_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Startseite_Fragment extends Fragment {

    Context context = getActivity();
    public static final int ADD_NOTE_REQUEST = 1;


    Intent intentStuhl;
    Intent intentEssen;
    Intent intentEinstellungen;

    Button btStuhl;
    Button btEssen;
    Button btEinstellungen;

    TextView tvStuhl;
    ImageButton bV1;
    ImageButton bV2;
    ImageButton bV3;
    ImageButton bV4;
    ImageButton bV5;
    Benutzer ben;
    BenutzerdatenSpeicher bdsp;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Startseite_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Startseite_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Startseite_Fragment newInstance(String param1, String param2) {
        Startseite_Fragment fragment = new Startseite_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_startseite_, container, false);


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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
                startActivityForResult(intentStuhl, ADD_NOTE_REQUEST);
                break;
            case R.id.essen_button:
                intentEssen = new Intent(getActivity(), Eintrag_Essen.class);
                startActivity(intentEssen);
                break;
            case R.id.einstellungenButton:
                intentEinstellungen = new Intent(getActivity(), Einstellungen.class);
                startActivity(intentEinstellungen);
                break;
        }
    }
}

