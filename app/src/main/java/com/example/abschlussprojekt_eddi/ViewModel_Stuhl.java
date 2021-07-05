package com.example.abschlussprojekt_eddi;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.sql.Date;
import java.util.List;

public class ViewModel_Stuhl extends AndroidViewModel {

    private StuhlRepository stuhlRepository;
    private LiveData<List<Entity_Stuhl>> allStuhl;

    public ViewModel_Stuhl(@NonNull Application application) {
        super(application);
        stuhlRepository = new StuhlRepository(application);
        allStuhl = stuhlRepository.getAllStuhl();
    }

    public LiveData<List<Entity_Stuhl>> getAllStuhl(){
        return allStuhl;
    }

    public void insertStuhl(Entity_Stuhl stuhl){
        stuhlRepository.insertStuhl(stuhl);
    }


    public void update (Entity_Stuhl stuhl){
        stuhlRepository.update(stuhl);
    }

    public void delete (Entity_Stuhl stuhl){
        stuhlRepository.delete(stuhl);
    }


    public LiveData<List<Entity_Stuhl>> getStuhlByDate(int jahr, int monat, int tag){
        stuhlRepository = new StuhlRepository(getApplication());
        LiveData<List<Entity_Stuhl>> stuhlByDate = stuhlRepository.getStuhlByDate(jahr, monat, tag);
        return stuhlByDate;
    }

    public LiveData<List<Entity_Stuhl>> getStuhlLastMonth (int vormonat){
        stuhlRepository = new StuhlRepository(getApplication());
        LiveData<List<Entity_Stuhl>> stuhlLastMonth = stuhlRepository.getStuhlLastMonth(vormonat);
        return stuhlLastMonth;
    }

}
