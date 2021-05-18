package com.example.abschlussprojekt_eddi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Anmeldung extends AppCompatActivity {

    Button bSpeichern;
    EditText vorname;
    EditText nachname;
    EditText geburtsdatum;
    EditText groesse;
    EditText gewicht;
    EditText nutzername;
    EditText pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anmeldung);

        vorname = (EditText) findViewById(R.id.editVorname);
        nachname = (EditText) findViewById(R.id.editNachname);
        geburtsdatum = (EditText) findViewById(R.id.editGeburtsdatum);
        groesse = (EditText) findViewById(R.id.editGroesse);
        gewicht = (EditText) (findViewById(R.id.editGewicht));
        nutzername = (EditText) findViewById(R.id.editBenutzername);
        pin = (EditText) findViewById(R.id.editTextNumberPassword);
        bSpeichern = (Button) findViewById(R.id.speichernButton);

        bSpeichern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}