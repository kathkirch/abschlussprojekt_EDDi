package com.example.abschlussprojekt_eddi.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abschlussprojekt_eddi.EssenListAdapter;
import com.example.abschlussprojekt_eddi.R;
import com.example.abschlussprojekt_eddi.StuhlListAdapter;
import com.example.abschlussprojekt_eddi.ViewModel_Essen;
import com.example.abschlussprojekt_eddi.ViewModel_Stuhl;

public class Kalender_Fragment extends Fragment {

    Context context = getActivity();
    public ViewModel_Stuhl viewModel_stuhl;
    public ViewModel_Essen viewModel_essen;


    public Kalender_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("FragmentLiveDataObserve")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_kalender_, container, false);


        RecyclerView recyclerView1 = (RecyclerView) view.findViewById(R.id.recycler_view_kalender_essen);
        RecyclerView recyclerView2 = (RecyclerView) view.findViewById(R.id.recycler_view_kalender_stuhl);

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

        return view;
    }


}
