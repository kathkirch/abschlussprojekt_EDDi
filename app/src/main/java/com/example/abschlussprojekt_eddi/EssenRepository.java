package com.example.abschlussprojekt_eddi;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class EssenRepository {

    private DAO_Essen m_dao_essen;
    private LiveData<List<Entity_Essen>> allEssen;

    EssenRepository (Application application) {
        EssenRoomDatabase erdb = EssenRoomDatabase.getDatabase(application);
        m_dao_essen = erdb.dao_essen();
        allEssen = m_dao_essen.getEssenOrderByTime();
    }

    // Room gibt alle queries an einen separaten Thread
    // Observed LiveData gibt Observer bescheid wenn sich Daten geändert haben
    LiveData<List<Entity_Essen>> getAllEssen() {
        return allEssen;
    }

    void insert (Entity_Essen essen){
        EssenRoomDatabase.databaseWriteExecuter.execute(() -> {
            m_dao_essen.insertEssen(essen);
        });
    }
}
