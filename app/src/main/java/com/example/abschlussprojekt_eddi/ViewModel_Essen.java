package com.example.abschlussprojekt_eddi;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewModel_Essen extends AndroidViewModel {

    private EssenRepository essenRepository;
    private final LiveData<List<Entity_Essen>> mAllEssen;

    public ViewModel_Essen (Application application){
        super(application);
        essenRepository = new EssenRepository(application);
        mAllEssen = essenRepository.getAllEssen();
    }

    public LiveData<List<Entity_Essen>> getAllEssen() {
        return mAllEssen;
    }

    public LiveData<List<Entity_Essen>> getEssenByDate (int jahr, int montat, int tag){
        essenRepository = new EssenRepository(getApplication());
        LiveData<List<Entity_Essen>> essenByDate = essenRepository.getEssenByDate(jahr, montat, tag);
        return essenByDate;
    }

    public void insertEssen(Entity_Essen essen) {
        essenRepository.insertEssen(essen);
    }

    public void deleteEssen(Entity_Essen essen){
        essenRepository.delete(essen);
    }

    public void updateEssen(Entity_Essen essen){
        essenRepository.update(essen);
    }
}
