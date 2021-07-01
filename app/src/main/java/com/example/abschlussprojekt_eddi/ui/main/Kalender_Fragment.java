package com.example.abschlussprojekt_eddi.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.example.abschlussprojekt_eddi.EssenListAdapter;
import com.example.abschlussprojekt_eddi.R;
import com.example.abschlussprojekt_eddi.StuhlListAdapter;
import com.example.abschlussprojekt_eddi.ViewModel_Essen;
import com.example.abschlussprojekt_eddi.ViewModel_Stuhl;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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

        //RecyclerView für Essen
        RecyclerView recyclerView1 = (RecyclerView) view.findViewById(R.id.recycler_view_kalender_essen);
        EssenListAdapter adapter = new EssenListAdapter();
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setAdapter(adapter);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));

        //RecyclerView für Stuhl
        RecyclerView recyclerView2 = (RecyclerView) view.findViewById(R.id.recycler_view_kalender_stuhl);
        StuhlListAdapter stuhlAdapter = new StuhlListAdapter();
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setAdapter(stuhlAdapter);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));

        // ViewModel Stuhl & Essen
        viewModel_stuhl = new ViewModelProvider(this).get(ViewModel_Stuhl.class);
        viewModel_essen = new ViewModelProvider(this).get(ViewModel_Essen.class);

        // um den aktuellen Tag zu bekommen und dessen Einträge anzuzugeigen
        CalendarView calendarView = view.findViewById(R.id.calendarView);
        Calendar today = calendarView.getCurrentPageDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
        String curDate = sdf.format(today.getTime());
        String [] dateValues = curDate.split("-", 3);
        curYear = Integer.parseInt("20" + dateValues[0]);
        curMonth = Integer.parseInt(dateValues[1]);
        curDay = Integer.parseInt(dateValues[2]);
        viewModel_stuhl.getStuhlByDate(curYear,curMonth, curDay).observe(getViewLifecycleOwner(), entity_stuhls -> {
            stuhlAdapter.setStuhl(entity_stuhls);
        });
        viewModel_essen.getEssenByDate(curYear, curMonth, curDay).observe(getViewLifecycleOwner(), entity_essens -> {
            adapter.setEssen(entity_essens);
        });


        // um die Einträge des angeklichten Tages zu erhalten
        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Calendar clickedDayCalendar = eventDay.getCalendar();
                SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
                String curDate = sdf.format(clickedDayCalendar.getTime());
                String [] dateValues = curDate.split("-", 3);
                curYear = Integer.parseInt("20" + dateValues[0]);
                curMonth = Integer.parseInt(dateValues[1]);
                curDay = Integer.parseInt(dateValues[2]);

                viewModel_stuhl.getStuhlByDate(curYear,curMonth, curDay).observe(getViewLifecycleOwner(), entity_stuhls -> {
                    stuhlAdapter.setStuhl(entity_stuhls);
                });
                viewModel_essen.getEssenByDate(curYear, curMonth, curDay).observe(getViewLifecycleOwner(), entity_essens -> {
                    adapter.setEssen(entity_essens);
                });
            }
        });

        //macht den RecyclerView wischbar (für Essen-Löschung)
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

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                //Löschen bei Rechts-Wischen
                ItemTouchHelper.RIGHT){
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                try {
                    viewModel_stuhl.delete(stuhlAdapter.getStuhlAt(viewHolder.getAdapterPosition()));
                    Toast.makeText(getActivity(), "Stuhl Eintrag gelöscht", Toast.LENGTH_SHORT).show();
                }catch (IndexOutOfBoundsException e){
                    e.printStackTrace();
                }
            }
        }).attachToRecyclerView(recyclerView2);

        return view;
    }
}
