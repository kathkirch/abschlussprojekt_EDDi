package com.example.abschlussprojekt_eddi;

import java.util.Date;

public class Benutzer {

    private String nachname;
    private String vorname;
    private String geburtsdatum;
    private String groesse;
    private String gewicht;
    private String nutzername;
    private String pin;

    public Benutzer( String vorname, String nachname, String geburtsdatum, String groesse, String gewicht,
                    String nutzername, String pin) {
        this.nachname = nachname;
        this.vorname = vorname;
        this.geburtsdatum = geburtsdatum;
        this.groesse = groesse;
        this.gewicht = gewicht;
        this.nutzername = nutzername;
        this.pin = pin;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(String geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public String getGroesse() {
        return groesse;
    }

    public void setGroesse(String groesse) {
        this.groesse = groesse;
    }

    public String getGewicht() {
        return gewicht;
    }

    public void setGewicht(String gewicht) {
        this.gewicht = gewicht;
    }

    public String getNutzername() {
        return nutzername;
    }

    public void setNutzername(String nutzername) {
        this.nutzername = nutzername;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
