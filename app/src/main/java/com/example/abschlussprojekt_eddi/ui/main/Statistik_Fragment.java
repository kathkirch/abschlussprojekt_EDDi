package com.example.abschlussprojekt_eddi.ui.main;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.abschlussprojekt_eddi.Entity_Stuhl;
import com.example.abschlussprojekt_eddi.R;
import com.example.abschlussprojekt_eddi.ViewModel_Stuhl;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/*
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Statistik_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class Statistik_Fragment extends Fragment {

    private ViewModel_Stuhl viewModel_stuhl;
    private GraphView graphView;
    private TextView textViewStatistik;

    public Statistik_Fragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_statistik_, container, false);

        textViewStatistik = view.findViewById(R.id.textView_statistik);

        graphView = view.findViewById(R.id.graph);
        setMonthTitle(getVormonat());


        graphView.setVisibility(View.VISIBLE);

        List<Entity_Stuhl> stuhlEintraege = new ArrayList<>();

        viewModel_stuhl = new ViewModelProvider(this).get(ViewModel_Stuhl.class);
        viewModel_stuhl.getStuhlLastMonth(getVormonat()).observe(getViewLifecycleOwner(), new Observer<List<Entity_Stuhl>>() {
            @Override
            public void onChanged(List<Entity_Stuhl> entity_stuhls) {
                System.out.println(entity_stuhls.size());
                for (Entity_Stuhl entity_stuhl :  entity_stuhls){
                    stuhlEintraege.add(entity_stuhl);

                }
                System.out.println(stuhlEintraege.size());
            }
        });

        try{
            LineGraphSeries <DataPoint> series = new LineGraphSeries<>
                    (new DataPoint[] {
                            new DataPoint (0, 1),

                    });

        } catch (IllegalArgumentException e){
            System.out.println(e);
        }

        return view;
    }

    public int getVormonat(){
        int vormonat;
        Calendar today = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
        String curDate = sdf.format(today.getTime());
        String [] dateValues = curDate.split("-", 3);
        int curMonth = Integer.parseInt(dateValues[1]);
        if (curMonth==1){
            vormonat = 12;
        } else {
            vormonat = curMonth - 1;
        }
        return vormonat;
    }

    @SuppressLint("SetTextI18n")
    public void setMonthTitle(int monat){
        switch (monat){
            case 1 :
                textViewStatistik.setText("Ihre Stuhl-Statistik vom Januar");
                break;
            case 2:
                textViewStatistik.setText("Ihre Stuhl-Statistik vom Februar");
                break;
            case 3:
                textViewStatistik.setText("Ihre Stuhl-Statistik vom März");
                break;
            case 4:
                textViewStatistik.setText("Ihre Stuhl-Statistik vom April");
                break;
            case 5:
                textViewStatistik.setText("Ihre Stuhl-Statistik vom Mai");
                break;
            case 6:
                textViewStatistik.setText("Ihre Stuhl-Statistik vom Juni");
                break;
            case 7:
                textViewStatistik.setText("Ihre Stuhl-Statistik vom Juli");
                break;
            case 8:
                textViewStatistik.setText("Ihre Stuhl-Statistik vom August");
                break;
            case 9:
                textViewStatistik.setText("Ihre Stuhl-Statistik vom September");
                break;
            case 10:
                textViewStatistik.setText("Ihre Stuhl-Statistik vom Oktober");
                break;
            case 11:
                textViewStatistik.setText("Ihre Stuhl-Statistik vom November");
                break;
            case 12:
                textViewStatistik.setText("Ihre Stuhl-Statistik vom Dezember");
                break;

        }
    }

    // evtl in Entity_Stuhl? wird auch in Kalender_Fragment gebraucht
    public Date getDatefromEntity (Entity_Stuhl entity_stuhl){
        Date date = null;
        String jahr = String.valueOf(entity_stuhl.getJahr());
        String monat = String.valueOf(entity_stuhl.getMonat());
        String tag = String.valueOf(entity_stuhl.getTag());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date = sdf.parse(tag+"/"+monat+"/"+jahr);
        }catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}