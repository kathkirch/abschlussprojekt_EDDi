package com.example.abschlussprojekt_eddi;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.text.SimpleDateFormat;
import java.util.List;

@Dao
public interface DAO_Stuhl {

    @Insert
    void insertAll(Entity_Stuhl...stuhl);

    @Insert
    void insertStuhl(Entity_Stuhl stuhl);

    @Update
    void update(Entity_Stuhl stuhl);

    @Delete
    void delete(Entity_Stuhl stuhl);

    @Query("SELECT * FROM stuhl ORDER BY datum DESC")
    LiveData<List<Entity_Stuhl>> getAll();

    //um die Stuhl-Einträge im Logbuch für den jeweiligen Tag anzuzeigen
    //LiveData wird automatisch Änderungen übernehmen, ohne dass man extra aktualisieren muss
    @Query("SELECT * FROM stuhl WHERE datum IN (:eintragDatum) ORDER BY uhrzeit DESC")
    LiveData<List<Entity_Stuhl>>getStuhlByDate(SimpleDateFormat[] eintragDatum);

    @Query("SELECT * FROM stuhl WHERE id in (:eintragID)")
    Entity_Stuhl getStuhlByID(int eintragID);


}
