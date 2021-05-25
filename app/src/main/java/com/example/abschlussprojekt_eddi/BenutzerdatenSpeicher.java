package com.example.abschlussprojekt_eddi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class BenutzerdatenSpeicher {

    public static final String SP_NAME = "benutzerDaten";
    SharedPreferences userLocalDatabase;

    public BenutzerdatenSpeicher(Context context) {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public void storeUserData (Benutzer benutzer){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("vorname", benutzer.getVorname());
        spEditor.putString("nachname", benutzer.getNachname());
        spEditor.putString("geburtsdatum", benutzer.getGeburtsdatum());
        spEditor.putString("groesse", benutzer.getGroesse());
        spEditor.putString("gewicht",  benutzer.getGewicht());
        spEditor.putString("nutzername", benutzer.getNutzername());
        spEditor.putString("pin", benutzer.getPin());
        spEditor.apply();
    }

    public Benutzer getLoggedInUser(){
        String vorname = userLocalDatabase.getString("vorname", "");
        String nachname = userLocalDatabase.getString("nachname", "");
        String geburtsdatum = userLocalDatabase.getString("geburtsdatum", "");
        String groesse = userLocalDatabase.getString("groesse", "");
        String gewicht = userLocalDatabase.getString("gewicht", "");
        String nutzername = userLocalDatabase.getString("nutzername", "");
        String pin = userLocalDatabase.getString("pin", "");

        return new Benutzer(vorname, nachname, geburtsdatum,
                groesse, gewicht, nutzername, pin);
    }

    public void setUserLoggedIn(boolean loggedIn){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.apply();
    }

    public boolean getUserLoggedIn(){
        if (userLocalDatabase.getBoolean("loggedIn", false) == true){
            return true;
        }else{
            return false;
        }
    }

    public void clearUserData(){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.apply();
    }

}
