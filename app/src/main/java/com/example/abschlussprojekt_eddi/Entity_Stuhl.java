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

    private String bristol;

    private boolean schmerzen;

    private boolean blut;

    private String farbe;

    @ColumnInfo (name = "Unverdaute Nahrung")
    private boolean unverdauteNahrung;

    private String schleim;

    private String menge;

    private String notizen;

    @ColumnInfo (name = "Foto-Referenz")
    private String fotoReferenz;

    //Konstruktor ist leer, weil es dafür die Setter gibt

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

    public String getFotoReferenz() {
        return fotoReferenz;
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

    public void setFotoReferenz(String fotoReferenz) {
        this.fotoReferenz = fotoReferenz;
    }
}
