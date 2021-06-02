package com.example.abschlussprojekt_eddi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class BenutzerdatenSpeicher {

    public static final String SP_NAME = "benutzerDaten";
    public static final String V_NAME = "Vorname";
    public static final String N_NAME = "Nachname";
    public static final String G_DATUM = "Geburtsdatum";
    public static final String GEWICHT = "Gewicht";
    public static final String GROESSE = "Größe";
    public static final String NZ_NAME = "Nutzername";
    public static final String PIN = "PIN";
    SharedPreferences userLocalDatabase;

    public BenutzerdatenSpeicher(Context context) {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public void storeUserData (Benutzer benutzer){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString(V_NAME, benutzer.getVorname());
        spEditor.putString(N_NAME, benutzer.getNachname());
        spEditor.putString(G_DATUM, benutzer.getGeburtsdatum());
        spEditor.putString(GROESSE, benutzer.getGroesse());
        spEditor.putString(GEWICHT,  benutzer.getGewicht());
        spEditor.putString(NZ_NAME, benutzer.getNutzername());
        spEditor.putString(PIN, benutzer.getPin());
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
