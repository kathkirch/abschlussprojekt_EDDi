package com.example.abschlussprojekt_eddi;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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

    private int bristol;

    private boolean blut;

    private boolean schmerzen;

    private int farbe;

    @ColumnInfo (name = "Unverdaute_Nahrung")
    private boolean unverdauteNahrung;

    private int schleim;

    private int menge;

    private String notizen;

    //Konstruktor
    public Entity_Stuhl(int jahr, int monat, int tag, int stunde, int minute, int bristol, boolean blut, boolean schmerzen,
                        int farbe, boolean unverdauteNahrung, int schleim, int menge, String notizen) {
        this.jahr = jahr;
        this.monat = monat;
        this.tag = tag;
        this.stunde = stunde;
        this.minute = minute;
        this.bristol = bristol;
        this.blut = blut;
        this.schmerzen = schmerzen;
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

    public int getBristol() {
        return bristol;
    }

    public boolean getBlut() {
        return blut;
    }

    public boolean getSchmerzen() {
        return schmerzen;
    }

    public int getFarbe() {
        return farbe;
    }

    public boolean getUnverdauteNahrung() {
        return unverdauteNahrung;
    }

    public int getSchleim() {
        return schleim;
    }

    public int getMenge() {
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

    public void setBristol(int bristol) {
        this.bristol = bristol;
    }

    public void setBlut(boolean blut) {
        this.blut = blut;
    }

    public void setSchmerzen(boolean schmerzen) {
        this.schmerzen = schmerzen;
    }

    public void setFarbe(int farbe) {
        this.farbe = farbe;
    }

    public void setUnverdauteNahrung(boolean unverdauteNahrung) {
        this.unverdauteNahrung = unverdauteNahrung;
    }

    public void setSchleim(int schleim) {
        this.schleim = schleim;
    }

    public void setMenge(int menge) {
        this.menge = menge;
    }

    public void setNotizen(String notizen) {
        this.notizen = notizen;
    }

}
