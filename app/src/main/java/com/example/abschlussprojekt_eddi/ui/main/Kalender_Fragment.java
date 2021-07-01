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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.example.abschlussprojekt_eddi.Entity_Essen;
import com.example.abschlussprojekt_eddi.Entity_Stuhl;
import com.example.abschlussprojekt_eddi.EssenListAdapter;
import com.example.abschlussprojekt_eddi.R;
import com.example.abschlussprojekt_eddi.StuhlListAdapter;
import com.example.abschlussprojekt_eddi.ViewModel_Essen;
import com.example.abschlussprojekt_eddi.ViewModel_Stuhl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

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

        //RecyclerView für Essen und Stuhl erstellen
        RecyclerView recyclerView1 = (RecyclerView) view.findViewById(R.id.recycler_view_kalender_essen);
        RecyclerView recyclerView2 = (RecyclerView) view.findViewById(R.id.recycler_view_kalender_stuhl);

        //RecyclerView für Essen
        EssenListAdapter adapter = new EssenListAdapter();
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setAdapter(adapter);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));

        //ViewModel für Essen
        viewModel_essen = new ViewModelProvider(this).get(ViewModel_Essen.class);
        viewModel_essen.getAllEssen().observe(getViewLifecycleOwner(), new Observer<List<Entity_Essen>>() {
            @Override
            //um den Adapter zu aktualisieren
            public void onChanged(List<Entity_Essen> entity_essens) {
                adapter.setEssen(entity_essens);
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
                try {
                    viewModel_essen.deleteEssen(adapter.getEssenAt(viewHolder.getAdapterPosition()));
                    Toast.makeText(getActivity(), "Essen Eintrag gelöscht", Toast.LENGTH_SHORT).show();
                }catch (IndexOutOfBoundsException e){
                    e.printStackTrace();
                }
            }
        }).attachToRecyclerView(recyclerView1);

        //RecyclerView für Stuhl
        StuhlListAdapter stuhlAdapter = new StuhlListAdapter();
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setAdapter(stuhlAdapter);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));

        //ViewModel für Stuhl
        viewModel_stuhl = new ViewModelProvider(this).get(ViewModel_Stuhl.class);
        viewModel_stuhl.getAllStuhl().observe(getViewLifecycleOwner(), new Observer<List<Entity_Stuhl>>() {
            @Override
            public void onChanged(List<Entity_Stuhl> entity_stuhl) {
                stuhlAdapter.setStuhl(entity_stuhl);
            }
        });

        CalendarView calendarView = view.findViewById(R.id.calendarView);
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
                System.out.println(curYear + " Y" + curMonth + " M" + curDay + " D");

                viewModel_stuhl.getStuhlByDate(curYear,curMonth, curDay).observe(getViewLifecycleOwner(), entity_stuhls -> {
                    stuhlAdapter.setStuhl(entity_stuhls);
                });
            }
        });

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
