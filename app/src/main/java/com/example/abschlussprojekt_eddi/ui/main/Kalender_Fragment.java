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
    int curDay;
    int curYear;
    int curMonth;


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

        /*
        CalendarView calendarView = view.findViewById(R.id.calendarView);

        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Calendar clickedDayCalendar = eventDay.getCalendar();
                SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
                String curDate = sdf.format(clickedDayCalendar.getTime());
                String [] dateValues = curDate.split("-", 3);
                curYear = Integer.parseInt(dateValues[0]);
                curMonth = Integer.parseInt(dateValues[1]);
                curDay = Integer.parseInt(dateValues[2]);
            }
        });
         */

        //RecyclerView für Essen
        RecyclerView recyclerView1 = (RecyclerView) view.findViewById(R.id.recycler_view_kalender_essen);
        RecyclerView recyclerView2 = (RecyclerView) view.findViewById(R.id.recycler_view_kalender_stuhl);


        final EssenListAdapter adapter = new EssenListAdapter(new EssenListAdapter.EssenDiff());
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setAdapter(adapter);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));

        //ViewModel für Essen
        viewModel_essen = new ViewModelProvider(this).get(ViewModel_Essen.class);
        viewModel_essen.getAllEssen().observe(getViewLifecycleOwner(), entity_essens -> {
            adapter.submitList(entity_essens);
        });

        //RecyclerView für Stuhl

        final StuhlListAdapter stuhlAdapter = new StuhlListAdapter(new StuhlListAdapter.StuhlDiff());
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setAdapter(stuhlAdapter);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));
        
        //ViewModel für Stuhl
        viewModel_stuhl = new ViewModelProvider(this).get(ViewModel_Stuhl.class);
        viewModel_stuhl.getAllStuhl().observe(getViewLifecycleOwner(), entity_stuhls -> {
            stuhlAdapter.submitList(entity_stuhls);
        });

        // funktioniert noch nicht
        /*
        viewModel_stuhl.getStuhlByDate(curYear, curMonth, curDay).observe(getViewLifecycleOwner(), entity_stuhls -> {
            stuhlAdapter.submitList(entity_stuhls);
        });*/
        return view;
    }
}
