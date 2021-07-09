package com.example.abschlussprojekt_eddi.ui.main;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.example.abschlussprojekt_eddi.Eintrag_Essen;
import com.example.abschlussprojekt_eddi.Eintrag_Stuhl;
import com.example.abschlussprojekt_eddi.Entity_Essen;
import com.example.abschlussprojekt_eddi.Entity_Stuhl;
import com.example.abschlussprojekt_eddi.EssenListAdapter;
import com.example.abschlussprojekt_eddi.R;
import com.example.abschlussprojekt_eddi.StuhlListAdapter;
import com.example.abschlussprojekt_eddi.ViewModel_Essen;
import com.example.abschlussprojekt_eddi.ViewModel_Stuhl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Kalender_Fragment extends Fragment {

    public ViewModel_Stuhl viewModel_stuhl;
    public ViewModel_Essen viewModel_essen;
    int curDay;
    int curYear;
    int curMonth;

    private CalendarView calendarView;

    public static final int NEW_STUHL_EDIT_REQUEST_CODE = 11;
    public static final int NEW_ESSEN_EDIT_REQUEST_CODE = 22;

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

        calendarView = view.findViewById(R.id.calendarView);

        // Methode für die Punkte bei den Kalendereinträgen wird aufgerufen
        getStuhlEintraege();

        // um den aktuellen Tag zu bekommen und dessen Einträge anzuzugeigen
        Calendar today = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
        String curDate = sdf.format(today.getTime());
        String [] dateValues = curDate.split("-", 3);
        curYear = Integer.parseInt("20" + dateValues[0]);
        curMonth = Integer.parseInt(dateValues[1]);
        curDay = Integer.parseInt(dateValues[2]);

        viewModel_stuhl.getStuhlByDate(curYear,curMonth, curDay).observe(getViewLifecycleOwner(), entity_stuhls -> {
            stuhlAdapter.submitList(entity_stuhls);
        });
        viewModel_essen.getEssenByDate(curYear, curMonth, curDay).observe(getViewLifecycleOwner(), entity_essens -> {
            adapter.submitList(entity_essens);
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
                    stuhlAdapter.submitList(entity_stuhls);
                });
                viewModel_essen.getEssenByDate(curYear, curMonth, curDay).observe(getViewLifecycleOwner(), entity_essens -> {
                    adapter.submitList(entity_essens);
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
                try {
                    viewModel_essen.deleteEssen(adapter.getEssenAt(viewHolder.getAdapterPosition()));
                    Toast.makeText(getActivity(), "Essen Eintrag gelöscht", Toast.LENGTH_SHORT).show();
                }catch (IndexOutOfBoundsException e){
                    e.printStackTrace();
                }
            }
        }).attachToRecyclerView(recyclerView1);

        //um auf den Essen-Eintrag zu klicken und ihn zu ändern
        //Daten aus dem Eintrag werden übergeben
        adapter.setOnItemClickListener(new EssenListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Entity_Essen essen) {
                Intent intent = new Intent(getContext(), Eintrag_Essen.class);
                intent.putExtra(Eintrag_Essen.EXTRA_ESSEN_ID, essen.getEssenID());
                intent.putExtra(Eintrag_Essen.EXTRA_ESSEN, essen.getEssen());
                intent.putExtra(Eintrag_Essen.EXTRA_STUNDE, essen.getStunde());
                intent.putExtra(Eintrag_Essen.EXTRA_MINUTE, essen.getMinute());
                intent.putExtra(Eintrag_Essen.EXTRA_JAHR, essen.getJahr());
                intent.putExtra(Eintrag_Essen.EXTRA_MONAT, essen.getMonat());
                intent.putExtra(Eintrag_Essen.EXTRA_TAG, essen.getTag());
                getActivity().startActivityForResult(intent, NEW_ESSEN_EDIT_REQUEST_CODE); //startet Methode in der MainActivity
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

        //Daten aus dem Stuhl Eintrag werden übergeben
        stuhlAdapter.setOnItemClickListener(new StuhlListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Entity_Stuhl stuhl) {
                Intent intent = new Intent(getContext(), Eintrag_Stuhl.class);
                intent.putExtra(Eintrag_Stuhl.EXTRA_ID, stuhl.getId());
                intent.putExtra(Eintrag_Essen.EXTRA_STUNDE, stuhl.getStunde());
                intent.putExtra(Eintrag_Essen.EXTRA_MINUTE, stuhl.getMinute());
                intent.putExtra(Eintrag_Essen.EXTRA_JAHR, stuhl.getJahr());
                intent.putExtra(Eintrag_Essen.EXTRA_MONAT, stuhl.getMonat());
                intent.putExtra(Eintrag_Essen.EXTRA_TAG, stuhl.getTag());
                intent.putExtra(Eintrag_Stuhl.EXTRA_BRISTOL, stuhl.getBristol());
                intent.putExtra(Eintrag_Stuhl.EXTRA_BLUT, stuhl.getBlut());
                intent.putExtra(Eintrag_Stuhl.EXTRA_SCHMERZ, stuhl.getSchmerzen());
                intent.putExtra(Eintrag_Stuhl.EXTRA_FARBE, stuhl.getFarbe());
                intent.putExtra(Eintrag_Stuhl.EXTRA_UNVERDAUTENAHRUNG, stuhl.getUnverdauteNahrung());
                intent.putExtra(Eintrag_Stuhl.EXTRA_SCHLEIM, stuhl.getSchleim());
                intent.putExtra(Eintrag_Stuhl.EXTRA_MENGE, stuhl.getMenge());
                intent.putExtra(Eintrag_Stuhl.EXTRA_NOTIZ, stuhl.getNotizen());
                getActivity().startActivityForResult(intent, NEW_STUHL_EDIT_REQUEST_CODE); //startet Methode in der MainActivity
            }
        });

        return view;
    }

    public void getStuhlEintraege () {
        viewModel_stuhl.getAllStuhl().observe(getViewLifecycleOwner(), new Observer<List<Entity_Stuhl>>() {
            @Override
            public void onChanged(List<Entity_Stuhl> entity_stuhls) {
                Date date = null;
                List<EventDay> stuhlEvents = new ArrayList<>();

                for (Entity_Stuhl entity_stuhl : entity_stuhls){
                    String jahr = String.valueOf(entity_stuhl.getJahr());
                    String monat = String.valueOf(entity_stuhl.getMonat());
                    String tag = String.valueOf(entity_stuhl.getTag());
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        date = sdf.parse(tag+"/"+monat+"/"+jahr);
                    }catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    EventDay stuhlEvent = new EventDay(calendar, R.drawable.icon_stuhl_eintrag);
                    stuhlEvents.add(stuhlEvent);
                }
                calendarView.setEvents(stuhlEvents);
            }
        });
    }
}
