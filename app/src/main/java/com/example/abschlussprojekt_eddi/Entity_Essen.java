package com.example.abschlussprojekt_eddi;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "essen")
public class Entity_Essen {

    @PrimaryKey
    @NonNull
    private int essenID;

    private int jahr;

    private int monat;

    private int tag;

    private int stunde;

    private int minute;

    private String essen;

    public Entity_Essen(@NonNull String essen){
        this.essen = essen;
        this.jahr = jahr;
        this.monat = monat;
        this.tag = tag;
        this.stunde = stunde;
        this.minute = minute;
    }

    public int getEssenID() {
        return essenID;
    }

    public void setEssenID(int essenID) {
        this.essenID = essenID;
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

    public void setEssen(String essen) {
        this.essen = essen;
    }

    public String getEssen (){
        return essen;
    }
}
