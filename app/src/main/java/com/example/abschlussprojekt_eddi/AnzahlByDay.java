package com.example.abschlussprojekt_eddi;

import androidx.room.DatabaseView;

@DatabaseView("SELECT jahr || '.' || monat || '.' || tag  as dateAdded, " +
                    "count(id) as anzahl, " +
                    "monat, tag " +
                    "FROM stuhl " +
                    "GROUP BY dateAdded "+
                    "ORDER BY tag ASC")

public class AnzahlByDay {
    public int anzahl;
    public String dateAdded;
    public int monat;
    public int tag;

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }
}


