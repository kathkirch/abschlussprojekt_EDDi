package com.example.abschlussprojekt_eddi;

import java.util.Date;

public class Benutzer {

    String nachname;
    String vorname;
    String geburtsdatum;
    int groesse;
    double gewicht;
    String nutzername;
    int pin;

    public Benutzer(String nachname, String vorname, String geburtsdatum, int groesse, double gewicht,
                    String nutzername, int pin) {
        this.nachname = nachname;
        this.vorname = vorname;
        this.geburtsdatum = geburtsdatum;
        this.groesse = groesse;
        this.gewicht = gewicht;
        this.nutzername = nutzername;
        this.pin = pin;
    }
}
