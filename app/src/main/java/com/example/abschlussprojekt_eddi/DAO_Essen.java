package com.example.abschlussprojekt_eddi;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DAO_Essen {

    @Insert
    void insertEssen(Entity_Essen essen);

    @Update
    void updateEssen(Entity_Essen essen);

    @Delete
    void deleteEssen(Entity_Essen essen);

    @Query("SELECT * FROM essen ORDER BY jahr, monat, tag DESC")
    LiveData<List<Entity_Essen>> getEssenOrderByTime();

    @Query("SELECT * FROM essen WHERE (jahr IN (:eintragJahr) AND monat IN (:eintragMonat) AND " +
            "tag IN(:eintragTag) )ORDER BY stunde, minute DESC")
    LiveData<List<Entity_Essen>>getEssenByDate(int eintragJahr, int eintragMonat, int eintragTag);

    @Query("SELECT * FROM essen WHERE essenID in (:eintragID)")
    Entity_Essen getEssenByID(int eintragID);
}
