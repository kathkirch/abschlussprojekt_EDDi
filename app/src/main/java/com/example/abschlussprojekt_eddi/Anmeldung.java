package com.example.abschlussprojekt_eddi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.CharSequence;



public class Anmeldung extends AppCompatActivity {

    Button bSpeichern;
    private EditText vorname;
    private EditText nachname;
    private EditText geburtsdatum;
    private EditText groesse;
    private EditText gewicht;
    protected EditText nutzername;
    protected EditText pin;


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
        Intent MainIntent = new Intent(Anmeldung.this, MainActivity.class);

        bSpeichern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDataEntered();
                /*startActivity(MainIntent);*/
            }
        });
    }


    public void checkDataEntered(){

        if (isEmpty(vorname)){
            Toast t = Toast.makeText(this, "Vorname muss angegeben werden!",
                    Toast.LENGTH_SHORT);
            t.show();
        }
        if (isEmpty(nachname)){
            Toast t = Toast.makeText(this, "Nachname muss angegeben werden!",
                    Toast.LENGTH_SHORT);
            t.show();
        }
        if (isEmpty(geburtsdatum)){
            Toast t = Toast.makeText(this, "Geburtsdatum muss angegeben werden!",
                    Toast.LENGTH_SHORT);
            t.show();
        }
        if (isEmpty(groesse)){
            Toast t = Toast.makeText(this, "Bitte Größe angeben!",
                    Toast.LENGTH_SHORT);
            t.show();
        }
        if (isEmpty(gewicht)){
            Toast t = Toast.makeText(this, "Bitte Gewicht angeben!",
                    Toast.LENGTH_SHORT);
            t.show();
        }
        if (isEmpty(nutzername)){
            nutzername.setError("Nutzername erfoderlich!");
        }
        if (isPin(pin) == false){
            pin.setError("PIN ungültig! Nur Zahlen!");
        }
    }

    boolean isEmpty(EditText text){
        CharSequence string = text.getText().toString();
        return TextUtils.isEmpty(string);
    }

    boolean isPin(EditText text){
        CharSequence string = text.getText().toString();
        return TextUtils.isDigitsOnly(string);
    }
}