package com.example.abschlussprojekt_eddi.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.abschlussprojekt_eddi.Entity_Stuhl;
import com.example.abschlussprojekt_eddi.R;
import com.example.abschlussprojekt_eddi.ViewModel_Essen;
import com.example.abschlussprojekt_eddi.ViewModel_Stuhl;
import com.jjoe64.graphview.GraphView;

import java.util.ArrayList;
import java.util.List;

/*
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Statistik_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class Statistik_Fragment extends Fragment {

    private ViewModel_Essen viewModel_essen;
    private ViewModel_Stuhl viewModel_stuhl;


    public Statistik_Fragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_statistik_, container, false);

        final GraphView graphView = view.findViewById(R.id.graph);
        graphView.setTitle("Stuhlstatistik");
        graphView.setVisibility(View.VISIBLE);

        //methode noch ändern, neue query logik
        //methode anpassen
        viewModel_stuhl = new ViewModelProvider(this).get(ViewModel_Stuhl.class);
        viewModel_stuhl.getAllStuhl().observe(getViewLifecycleOwner(), new Observer<List<Entity_Stuhl>>() {
            @Override
            public void onChanged(List<Entity_Stuhl> entity_stuhls) {
                List<Entity_Stuhl> stuhlEintraege = new ArrayList<>();

                for(Entity_Stuhl entity_stuhl : entity_stuhls){
                    stuhlEintraege.add(entity_stuhl);
                }
                System.out.println(stuhlEintraege);
            }
        });


        return view;
    }
}