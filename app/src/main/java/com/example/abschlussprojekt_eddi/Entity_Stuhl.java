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

    private boolean unverdauteNahrung;

    String schleim;

    private String menge;

    private String notizen;

    @ColumnInfo (name = "Foto-Referenz")
    private String fotoReferenz;

    //Konstruktor
    public Entity_Stuhl(int jahr, int monat, int tag, int stunde, int minute, String bristol, boolean schmerzen, boolean blut,
                        String farbe, boolean unverdauteNahrung, String schleim, String menge, String notizen, String fotoReferenz) {
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
        this.fotoReferenz = fotoReferenz;
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

    public String getFotoReferenz() {
        return fotoReferenz;
    }

    //Setter:

    //weil die ID nicht im Konstruktor vorkommt, braucht es einen setter dafür
    public void setId(int id) {
        this.id = id;
    }
}
