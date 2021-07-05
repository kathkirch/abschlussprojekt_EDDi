package com.example.abschlussprojekt_eddi;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(tableName = "stuhl")
public class Entity_Stuhl {

    //sind als private deklariert, für mehr Sicherheit (Encapsulation)

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int jahr;

    private int monat;

    private int tag;

    private int stunde;

    private int minute;

    private String bristol;

    private boolean schmerzen;

    private boolean blut;

    private String farbe;

    @ColumnInfo (name = "Unverdaute_Nahrung")
    private boolean unverdauteNahrung;

    private String schleim;

    private String menge;

    private String notizen;




    //Konstruktor
    public Entity_Stuhl(int jahr, int monat, int tag, int stunde, int minute, String bristol, boolean schmerzen, boolean blut,
                        String farbe, boolean unverdauteNahrung, String schleim, String menge, String notizen) {
        this.jahr = jahr;
        this.monat = monat;
        this.tag = tag;
        this.stunde = stunde;
        this.minute = minute;
        this.bristol = bristol;
        this.schmerzen = schmerzen;
        this.blut = blut;
        this.farbe = farbe;
        this.unverdauteNahrung = unverdauteNahrung;
        this.schleim = schleim;
        this.menge = menge;
        this.notizen = notizen;
    }

    //Getter:
    public int getId() {
        return id;
    }

    public int getJahr() {
        return jahr;
    }

    public int getMonat() {
        return monat;
    }

    public int getTag() {
        return tag;
    }

    public int getStunde() {
        return stunde;
    }

    public int getMinute() {
        return minute;
    }

    public String getBristol() {
        return bristol;
    }

    public boolean getSchmerzen() {
        return schmerzen;
    }

    public boolean getBlut() {
        return blut;
    }

    public String getFarbe() {
        return farbe;
    }

    public boolean getUnverdauteNahrung() {
        return unverdauteNahrung;
    }

    public String getSchleim() {
        return schleim;
    }

    public String getMenge() {
        return menge;
    }

    public String getNotizen() {
        return notizen;
    }

    //Setter:

    public void setId(int id) {
        this.id = id;
    }

    public void setJahr(int jahr) {
        this.jahr = jahr;
    }

    public void setMonat(int monat) {
        this.monat = monat;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public void setStunde(int stunde) {
        this.stunde = stunde;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setBristol(String bristol) {
        this.bristol = bristol;
    }

    public void setSchmerzen(boolean schmerzen) {
        this.schmerzen = schmerzen;
    }

    public void setBlut(boolean blut) {
        this.blut = blut;
    }

    public void setFarbe(String farbe) {
        this.farbe = farbe;
    }

    public void setUnverdauteNahrung(boolean unverdauteNahrung) {
        this.unverdauteNahrung = unverdauteNahrung;
    }

    public void setSchleim(String schleim) {
        this.schleim = schleim;
    }

    public void setMenge(String menge) {
        this.menge = menge;
    }

    public void setNotizen(String notizen) {
        this.notizen = notizen;
    }

    public Date getDatefromEntity (Entity_Stuhl entity_stuhl){
        Date date = null;
        String jahr = String.valueOf(entity_stuhl.getJahr());
        String monat = String.valueOf(entity_stuhl.getMonat());
        String tag = String.valueOf(entity_stuhl.getTag());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date = sdf.parse(tag+"/"+monat+"/"+jahr);
        }catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}
