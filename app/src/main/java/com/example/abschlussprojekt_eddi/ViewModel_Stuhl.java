package com.example.abschlussprojekt_eddi;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.text.SimpleDateFormat;
import java.util.List;

public class ViewModel_Stuhl extends AndroidViewModel {

    private LogbuchRepository repository;
    private LiveData<List<Entity_Stuhl>> allStuhl;

    public ViewModel_Stuhl(@NonNull Application application) {
        super(application);
        allStuhl = repository.getAll();
    }

    public void insertAll(Entity_Stuhl stuhl){
        repository.insertAll(stuhl);
    }

    public void insertStuhl(Entity_Stuhl stuhl){
        repository.insertStuhl(stuhl);
    }

    public void update (Entity_Stuhl stuhl){
        repository.update(stuhl);
    }

    public void delete (Entity_Stuhl stuhl){
        repository.delete(stuhl);
    }

    public LiveData<List<Entity_Stuhl>> getAll(){
        return allStuhl;
    }

    public LiveData<List<Entity_Stuhl>> getStuhlByDate(SimpleDateFormat date){
        return repository.getStuhlByDate(date);
    }

    public Entity_Stuhl getStuhlById(int id){
        return repository.getStuhlByID(id);
    }


}
