package com.example.abschlussprojekt_eddi;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DAO_Stuhl {


    @Insert
    void insertStuhl(Entity_Stuhl stuhl);

    @Update
    void update(Entity_Stuhl stuhl);

    @Delete
    void delete(Entity_Stuhl stuhl);

    @Query("SELECT * FROM stuhl ORDER BY jahr, monat, tag DESC")
    LiveData<List<Entity_Stuhl>> getStuhlOrderbyTime();

    //um die Stuhl-Einträge im Logbuch für den jeweiligen Tag anzuzeigen
    //LiveData wird automatisch Änderungen übernehmen, ohne dass man extra aktualisieren muss
    //verwenden wir LiveData???
    @Query("SELECT * FROM stuhl WHERE (jahr IN (:eintragJahr) AND monat IN (:eintragMonat) AND tag IN(:eintragTag) )ORDER BY stunde, minute DESC")
    public LiveData<List<Entity_Stuhl>>getStuhlByDate(int eintragJahr, int eintragMonat, int eintragTag);

    @Query("SELECT * FROM stuhl WHERE id in (:eintragID)")
    public Entity_Stuhl getStuhlByID(int eintragID);


}
