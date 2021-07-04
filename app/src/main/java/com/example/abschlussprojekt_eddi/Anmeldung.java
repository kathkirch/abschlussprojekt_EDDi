package com.example.abschlussprojekt_eddi;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Anmeldung extends AppCompatActivity {

    Button bSpeichern;
    private EditText vorname;
    private EditText nachname;
    private EditText geburtsdatum;
    private EditText groesse;
    private EditText gewicht;
    protected EditText nutzername;
    protected EditText pin1;
    protected EditText pin2;
    protected String checkedPin;
    BenutzerdatenSpeicher bdSp;
    Benutzer benutzer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anmeldung);

        vorname = findViewById(R.id.editVorname);
        nachname = findViewById(R.id.editNachname);
        geburtsdatum = findViewById(R.id.editGeburtsdatum);
        groesse =  findViewById(R.id.editGroesse);
        gewicht = findViewById(R.id.editGewicht);
        nutzername =  findViewById(R.id.editBenutzername);
        pin1 = findViewById(R.id.editTextNumberPassword1);
        pin2 =  findViewById(R.id.editTextNumberPassword2);
        bSpeichern =  findViewById(R.id.speichernButton);

        bdSp = new BenutzerdatenSpeicher(this);
    }

    public void registrieren (View view) {
        if (checkDataEntered()){
            benutzerAnlegen();
            bdSp.setUserLoggedIn(true);
            Intent mainIntent = new Intent(Anmeldung.this, MainActivity.class);
            startActivity(mainIntent);
        }
    }


    public boolean checkDataEntered(){
        boolean dataValid = true;
        if (isEmpty(vorname)){
            Toast t = Toast.makeText(this, "Vorname muss angegeben werden!",
                    Toast.LENGTH_SHORT);
            t.show();
            dataValid = false;
        }
        if (isEmpty(nachname)){
            Toast t = Toast.makeText(this, "Nachname muss angegeben werden!",
                    Toast.LENGTH_SHORT);
            t.show();
            dataValid = false;
        }
        if (isEmpty(geburtsdatum)){
            Toast t = Toast.makeText(this, "Geburtsdatum muss angegeben werden!",
                    Toast.LENGTH_SHORT);
            t.show();
            dataValid = false;
        }
        if (isEmpty(groesse)){
            Toast t = Toast.makeText(this, "Bitte Größe angeben!",
                    Toast.LENGTH_SHORT);
            t.show();
            dataValid = false;
        }
        if (isEmpty(gewicht)){
            Toast t = Toast.makeText(this, "Bitte Gewicht angeben!",
                    Toast.LENGTH_SHORT);
            t.show();
            dataValid = false;
        }
        if (isEmpty(nutzername)){
            nutzername.setError("Nutzername erfoderlich!");
            dataValid = false;
        }
        if (!checkPin(pin1, pin2)){
            pin2.setError("stimmt nich überein!");
            dataValid = false;
        }
        return dataValid;
    }

    boolean isEmpty(EditText text){
        CharSequence string = text.getText().toString();
        return TextUtils.isEmpty(string);
    }

    boolean checkPin(EditText pin1, EditText pin2){
        boolean pinOK = false;
        if ( (isPinValid(pin1) && isPinValid(pin2))){
            if (pin1.getText().toString().equals(pin2.getText().toString())){
                pinOK = true;
                checkedPin = pin1.getText().toString();
            }
        }
        return pinOK;
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

    public void benutzerAnlegen (){
        String vName = vorname.getText().toString();
        String nName = nachname.getText().toString();
        String gebdat = geburtsdatum.getText().toString();
        String gr = groesse.getText().toString();
        String gew = gewicht.getText().toString();
        String nutzName = nutzername.getText().toString();
        String pinCode = checkedPin;

        Benutzer registrierterBenutzer = new Benutzer(vName, nName, gebdat, gr, gew,
                nutzName, pinCode);
        try {
            bdSp.storeUserData(registrierterBenutzer);
        } catch (NullPointerException ex){
            Log.d("ERROR", "Fehler bei Dateneingabe");
        }
    }
}