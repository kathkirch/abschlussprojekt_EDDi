package com.example.abschlussprojekt_eddi.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.abschlussprojekt_eddi.R;
import com.example.abschlussprojekt_eddi.ViewModel_Essen;

/*
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Statistik_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class Statistik_Fragment extends Fragment {

    private ViewModel_Essen viewModel_essen;


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
        return view;
    }
}