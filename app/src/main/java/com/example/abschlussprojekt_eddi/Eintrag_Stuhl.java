package com.example.abschlussprojekt_eddi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.StringBufferInputStream;

public class Eintrag_Stuhl extends AppCompatActivity {

    Spinner spinner_bristol;

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
}