package com.example.abschlussprojekt_eddi;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Einstellungen extends AppCompatActivity {

    private EditText etvorname;
    private EditText etnachname;
    private EditText etgeburtsdatum;
    private EditText etgroesse;
    private EditText etgewicht;

    protected EditText etnutzername;
    protected EditText etpinAlt;
    protected EditText etpinNeu;

    BenutzerdatenSpeicher bdSp;
    Benutzer ben;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_einstellungen);

        etvorname = findViewById(R.id.editText_einstellungen_vname);
        etnachname = findViewById(R.id.editText_einstellungen_nname);
        etgeburtsdatum = findViewById(R.id.editText_einstellungen_gebDat);
        etgroesse = findViewById(R.id.editText_einstellungen_groesse);
        etgewicht = findViewById(R.id.editText_einstellungen_gewicht);
        etnutzername = findViewById(R.id.editText_einstellungen_nutzername_ausgewaehlt);
        etpinAlt = findViewById(R.id.editText_einstellungen_pw_ausgewaehlt);
        etpinNeu = findViewById(R.id.editText_einstellungen_pw_ausgewaehlt2);


        Log.d("INFO", etpinAlt.getText().toString());

        //Zugriff auf shared preferences
        bdSp = new BenutzerdatenSpeicher(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (authenticate()){
            ben = bdSp.getLoggedInUser();
            etvorname.setText(ben.getVorname());
            etnachname.setText(ben.getNachname());
            etgeburtsdatum.setText(ben.getGeburtsdatum());
            etgroesse.setText(ben.getGroesse());
            etgewicht.setText(ben.getGewicht());
            etnutzername.setText(ben.getNutzername());
        }
    }

    // authentifiziert den eingeloggeten Benutzer
    // returnt true or false wenn Benutzer erfoglreich eingeloggt ist
    private boolean authenticate(){
        return bdSp.getUserLoggedIn();
    }

    // Benutzerdaten überschreiben
    public void benutzerDatenAendern (View view) {
        try {
            ben.setVorname(etvorname.getText().toString());
            ben.setNachname(etnachname.getText().toString());
            ben.setGeburtsdatum(etgeburtsdatum.getText().toString());
            ben.setGroesse(etgroesse.getText().toString());
            ben.setGewicht(etgewicht.getText().toString());
            ben.setNutzername(etnutzername.getText().toString());

            if(benutzerPruefen(etpinAlt) && isPinValid(etpinNeu)){
                    ben.setPin(etpinNeu.getText().toString());
                    Toast t = Toast.makeText(this, "PIN wurde geändert", Toast.LENGTH_SHORT);
                    t.show();
            }else {
                    Toast t = Toast.makeText(this, "keine Übereinstimmung bei PIN!", Toast.LENGTH_LONG);
                    t.show();
            }
            bdSp.storeUserData(ben);
            Toast toast = Toast.makeText(this, "Daten aktualisiert", Toast.LENGTH_SHORT);
            toast.show();

        }catch(Exception ex){
            Toast toast = Toast.makeText(this, "Fehler bei Eingabe", Toast.LENGTH_SHORT);
            toast.show();
            Log.d("ERROR", ex.toString());
        }
    }

    //vergleicht den 'pinAlt' im EditText mit dem Pin in den shared Preferences
    // gibt true zurück wenn beide übereinstimmen
    public boolean benutzerPruefen (EditText pinAlt){
        boolean pinStimmt = false;
        if (pinAlt.getText().toString().equalsIgnoreCase(ben.getPin())){
            pinStimmt = true;
        }
        return pinStimmt;
    }

    boolean isPinValid(EditText pin){
        boolean validPin = false;
        CharSequence string = pin.getText().toString();
        if ((string.length() == 4) && TextUtils.isDigitsOnly(string)){
            validPin = true;
        } else {
            pin.setError("4 Zahlen eingeben!");
        }
        return validPin;
    }
}