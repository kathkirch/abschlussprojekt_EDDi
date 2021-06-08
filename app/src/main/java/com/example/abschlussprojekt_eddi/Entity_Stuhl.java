package com.example.abschlussprojekt_eddi;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;

@Entity(tableName = "stuhl")
public class Entity_Stuhl {

    //sind als private deklariert, für mehr Sicherheit (Encapsulation)

    @PrimaryKey(autoGenerate = true)
    private int id;

    private SimpleDateFormat datum;

    private SimpleDateFormat uhrzeit;

    private int bristol;

    private int schmerzen;

    private int blut;

    private String farbe;

    private int unverdauteNahrung;

    private int schleim;

    private String menge;

    private String notizen;

    @ColumnInfo (name = "Foto-Referenz")
    private String fotoReferenz;

    //Konstruktor
    public Entity_Stuhl(SimpleDateFormat datum, SimpleDateFormat uhrzeit, int bristol, int schmerzen, int blut, String farbe, int unverdauteNahrung, int schleim, String menge, String notizen, String fotoReferenz) {
        this.datum = datum;
        this.uhrzeit = uhrzeit;
        this.bristol = bristol;
        this.schmerzen = schmerzen;
        this.blut = blut;
        this.farbe = farbe;
        this.unverdauteNahrung = unverdauteNahrung;
        this.schleim = schleim;
        this.menge = menge;
        this.notizen = notizen;
        this.fotoReferenz = fotoReferenz;
    }


    //Getter:

    public int getId() {
        return id;
    }

    public SimpleDateFormat getDatum() {
        return datum;
    }

    public SimpleDateFormat getUhrzeit() {
        return uhrzeit;
    }

    public int getBristol() {
        return bristol;
    }

    public int getSchmerzen() {
        return schmerzen;
    }

    public int getBlut() {
        return blut;
    }

    public String getFarbe() {
        return farbe;
    }

    public int getUnverdauteNahrung() {
        return unverdauteNahrung;
    }

    public int getSchleim() {
        return schleim;
    }

    public String getMenge() {
        return menge;
    }

    public String getNotizen() {
        return notizen;
    }

    public String getFotoReferenz() {
        return fotoReferenz;
    }

    //Setter:

    //weil die ID nicht im Konstruktor vorkommt, braucht es einen setter dafür
    public void setId(int id) {
        this.id = id;
    }
}
