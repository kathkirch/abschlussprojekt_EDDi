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

    public void insertEssen(Entity_Essen essen) {
        essenRepository.insertEssen(essen);
    }
}