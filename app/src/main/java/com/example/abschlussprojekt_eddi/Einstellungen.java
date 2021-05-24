package com.example.abschlussprojekt_eddi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class Einstellungen extends AppCompatActivity {

    private EditText etvorname;
    private EditText etnachname;
    private EditText etgeburtsdatum;
    private EditText etgroesse;
    private EditText etgewicht;

    protected EditText etnutzername;
    protected EditText etpin;

    BenutzerdatenSpeicher bdSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_einstellungen);

        etvorname = (EditText) findViewById(R.id.editText_einstellungen_vname);
        etnachname = (EditText) findViewById(R.id.editText_einstellungen_nname);
        etgeburtsdatum = (EditText) findViewById(R.id.editText_einstellungen_gebDat);
        etgroesse = (EditText) findViewById(R.id.editText_einstellungen_groesse);
        etgewicht = (EditText) (findViewById(R.id.editText_einstellungen_gewicht));

        bdSp = new BenutzerdatenSpeicher(getApplicationContext());
    }

    //weiterarbeiten
    @Override
    protected void onStart() {
        super.onStart();
        if (authenticate()){
            Benutzer ben = bdSp.getLoggedInUser();
            etvorname.setText(ben.vorname);
            etnachname.setText(ben.nachname);
            etgeburtsdatum.setText(ben.geburtsdatum);
            etgroesse.setText(ben.groesse);
            etgewicht.setText((int) ben.gewicht);
        }
    }

    private boolean authenticate(){
        return bdSp.getUserLoggedIn();
    }


}