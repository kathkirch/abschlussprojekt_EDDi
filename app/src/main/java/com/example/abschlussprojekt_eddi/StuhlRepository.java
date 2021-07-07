package com.example.abschlussprojekt_eddi;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class StuhlRepository {

    private DAO_Stuhl dao_stuhl;
    private LiveData<List<Entity_Stuhl>> allStuhl;
    private LiveData<List<AnzahlByDay>> anzahl;

    public StuhlRepository(Application application){
        StuhlRoomDatabase srdb = StuhlRoomDatabase.getDatabaseStuhl(application);
        dao_stuhl = srdb.dao_stuhl();
        allStuhl = dao_stuhl.getStuhlOrderbyTime();
    }

    public LiveData<List<Entity_Stuhl>> getAllStuhl(){
        return allStuhl;
    }


    //das ViewModel muss später nurmehr diese Methoden aufrufen
    //das Repository kümmert sich darum, woher die Daten kommen
    public void insertStuhl(Entity_Stuhl stuhl){
        StuhlRoomDatabase.databaseWriteExecuter.execute(() -> {
            dao_stuhl.insertStuhl(stuhl);
        });
    }

    public void update(Entity_Stuhl stuhl){
        StuhlRoomDatabase.databaseWriteExecuter.execute(() -> {
            dao_stuhl.update(stuhl);
        });
    }

    public void delete(Entity_Stuhl stuhl){
        StuhlRoomDatabase.databaseWriteExecuter.execute(() -> {
            dao_stuhl.delete(stuhl);
        });
    }

    public LiveData<List<Entity_Stuhl>> getStuhlByDate (int jahr, int monat, int tag){
        allStuhl = dao_stuhl.getStuhlByDate(jahr, monat, tag);
        return allStuhl;
    }

    public LiveData<List<Entity_Stuhl>> getStuhlLastMonth (int vormonat){
        allStuhl = dao_stuhl.getStuhlLastMonth(vormonat);
        return allStuhl;
    }

    public LiveData<List<AnzahlByDay>> getAnzahlByDay(int vormonat){
        anzahl = dao_stuhl.getAnzahlByDay(vormonat);
        return anzahl;
    }

}
