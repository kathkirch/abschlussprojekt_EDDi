package com.example.abschlussprojekt_eddi;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class ViewModel_Stuhl extends ViewModel {

    private LogbuchRepository repository;
    private LiveData<List<Entity_Stuhl>> allStuhl;

    public ViewModel_Stuhl() {
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

    public LiveData<List<Entity_Stuhl>> getStuhlByDate(int jahr, int monat, int tag){
        return repository.getStuhlByDate(jahr, monat, tag);
    }

    public Entity_Stuhl getStuhlById(int id){
        return repository.getStuhlByID(id);
    }


}
