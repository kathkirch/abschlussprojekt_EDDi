package com.example.abschlussprojekt_eddi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Eintrag_Stuhl extends AppCompatActivity {

    Intent intent;

    Spinner spinner_bristol;
    ArrayList<BristolItem> arrayList_bristol;
    SpinnerAdapterBristol aA_bristol;

    Spinner spinner_farbe;
    String [] array_farbe = new String [] {"braun", "grün", "grau-lehmfarben", "gelb", "rot", "schwarz"};
    ArrayAdapter <String> aA_farbe;

    Spinner spinner_schleim;
    String [] array_schleim = new String [] {"wenig", "mittel", "viel", "nur Schleim"};
    ArrayAdapter <String> aA_schleim;

    Spinner spinner_menge;
    String [] array_menge = new String [] {"wenig", "normal", "viel"};
    ArrayAdapter <String> aA_menge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eintrag__stuhl);

        initListBristol();

        intent = getIntent();

        spinner_bristol = findViewById(R.id.spinner_bristol);
        aA_bristol = new SpinnerAdapterBristol(this, arrayList_bristol);
        //eigenes Layout erstellt für die Darstellung der Bristol-Skala + Text
        aA_bristol.setDropDownViewResource(R.layout.dropdown_item_bristol);
        spinner_bristol.setAdapter(aA_bristol);

        spinner_farbe = findViewById(R.id.spinner_farben);
        aA_farbe = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, array_farbe);
        spinner_farbe.setAdapter(aA_farbe);

        spinner_schleim = findViewById(R.id.spinner_schleim);
        aA_schleim = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, array_schleim);
        spinner_schleim.setAdapter(aA_schleim);

        spinner_menge = findViewById(R.id.spinner_menge);
        aA_menge = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, array_menge);
        spinner_menge.setAdapter(aA_menge);

    }

    private void initListBristol(){

        arrayList_bristol = new ArrayList<>();
        arrayList_bristol.add(new BristolItem("einzelne, fest Kügelchen, schwer auszuscheiden", R.drawable.type01));
        arrayList_bristol.add(new BristolItem("wurstartig, klumpig", R.drawable.type02));
        arrayList_bristol.add(new BristolItem("wurstartig mit rissiger Oberfläche", R.drawable.type03));
        arrayList_bristol.add(new BristolItem("wurstartig mit glatter, durchgehender Oberfläche", R.drawable.type04));
        arrayList_bristol.add(new BristolItem("einzelne weiche glattrandige Klümpchen, leicht auszuscheiden", R.drawable.type05));
        arrayList_bristol.add(new BristolItem("einzelne weiche Klümpchen mit unregelmäßigem Rand", R.drawable.type06));
        arrayList_bristol.add(new BristolItem("flüssig, ohne feste Bestandteile", R.drawable.type07));

    }
}