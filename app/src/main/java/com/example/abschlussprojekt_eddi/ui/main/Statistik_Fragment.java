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

import com.example.abschlussprojekt_eddi.AnzahlByDay;
import com.example.abschlussprojekt_eddi.R;
import com.example.abschlussprojekt_eddi.ViewModel_Stuhl;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
        viewModel_stuhl = new ViewModelProvider(this).get(ViewModel_Stuhl.class);

        try{
            viewModel_stuhl.getAnzahlByDay(getVormonat()).observe(getViewLifecycleOwner(), new Observer<List<AnzahlByDay>>() {
                @Override
                public void onChanged(List<AnzahlByDay> anzahlByDays) {
                    // holt sich jeden Eintrag im View getAnzahlByDay - mit der if abfrage wird benötigt
                    // um für Tage an denen kein Eintrag vorhanden ist den Wert "0" eintragen zu können
                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
                    int dayIs = 0;
                    for (AnzahlByDay abd : anzahlByDays) {
                        String date = abd.dateAdded;
                        String [] dateValues = date.split("\\.", 3);
                        int day = Integer.parseInt(dateValues[2]);
                        int diff = day - dayIs;
                        int dayInsert;
                        if(diff > 1){
                            for(int i = 0; i < diff; i++){
                                dayInsert = dayIs + 1 + i;
                                DataPoint newdataPoint = new DataPoint(dayInsert, 0);
                                series.appendData(newdataPoint,true, 35);
                            }
                        }
                        DataPoint newdataPoint = new DataPoint(day, abd.anzahl);
                        series.appendData(newdataPoint,true, 35);
                        dayIs = day;
                    }

                    //addSeries um die DataPoint hinzuzufügen
                    graphView.addSeries(series);
                    graphView.getViewport().setYAxisBoundsManual(true);
                    graphView.getGridLabelRenderer().setNumVerticalLabels(15);
                    graphView.getViewport().setXAxisBoundsManual(true);
                    graphView.getViewport().setMinX(0);
                    graphView.getViewport().setMinY(0);
                    graphView.getViewport().setScalable(true);
                    graphView.getViewport().setScrollable(true);
                    series.setThickness(7);
                }
            });
        }catch (IllegalArgumentException e){
            System.out.println(e);
        }
        return view;
    }

    //um den Vormonat zu erhalten
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


    //um immer den passenden Vormonat im Titel anzeigen zu können
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
}