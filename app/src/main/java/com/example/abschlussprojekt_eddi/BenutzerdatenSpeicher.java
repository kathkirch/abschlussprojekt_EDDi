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
        spEditor.putString("vorname", benutzer.vorname);
        spEditor.putString("nachname", benutzer.nachname);
        spEditor.putString("geburtsdatum", benutzer.geburtsdatum);
        spEditor.putInt("groesse", benutzer.groesse);
        spEditor.putInt("gewicht", (int) benutzer.gewicht);
        spEditor.putString("nutzername", benutzer.nutzername);
        spEditor.putInt("pin", benutzer.pin);
        spEditor.apply();
    }

    public Benutzer getLoggedInUser(){
        String vorname = userLocalDatabase.getString("vorname", "");
        String nachname = userLocalDatabase.getString("nachname", "");
        String geburtsdatum = userLocalDatabase.getString("geburtsdatum", "");
        int groesse = userLocalDatabase.getInt("groesse", Integer.parseInt(""));
        int gewicht = userLocalDatabase.getInt("gewicht", Integer.parseInt(""));
        String nutzername = userLocalDatabase.getString("nutzername", "");
        int pin = userLocalDatabase.getInt("pin", Integer.parseInt(""));

        Benutzer angelegterBenutzer = new Benutzer(vorname, nachname, geburtsdatum,
                groesse, gewicht, nutzername, pin);

        return angelegterBenutzer;
    }

    public void setUserLoggedIn(boolean loggedIn){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.commit();
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
        spEditor.commit();
    }

}
