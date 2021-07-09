package com.example.abschlussprojekt_eddi;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class EssenRepository {

    private DAO_Essen m_dao_essen;
    private LiveData<List<Entity_Essen>> allEssen;
    private int anzahl;

    EssenRepository (Application application) {
        EssenRoomDatabase erdb = EssenRoomDatabase.getDatabase(application);
        m_dao_essen = erdb.dao_essen();
        allEssen = m_dao_essen.getEssenOrderByTime();
    }

    // Room gibt alle queries an einen separaten Thread
    // Observed LiveData gibt Observer bescheid wenn sich Daten geändert haben
    public LiveData<List<Entity_Essen>> getAllEssen() {
        return allEssen;
    }

    public void insertEssen (Entity_Essen essen){
        EssenRoomDatabase.databaseWriteExecuter.execute(() -> {
            m_dao_essen.insertEssen(essen);
        });
    }

    public void update(Entity_Essen essen){
        EssenRoomDatabase.databaseWriteExecuter.execute(() -> {
            m_dao_essen.updateEssen(essen);
        });
    }

    public void delete (Entity_Essen essen){
        EssenRoomDatabase.databaseWriteExecuter.execute(() -> {
            m_dao_essen.deleteEssen(essen);
        });
    }

    public LiveData<List<Entity_Essen>> getEssenByDate (int jahr, int monat, int tag){
        allEssen = m_dao_essen.getEssenByDate(jahr, monat, tag);
        return allEssen;
    }
}
