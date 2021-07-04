package com.example.abschlussprojekt_eddi;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

public class StuhlRepository {

    private DAO_Stuhl dao_stuhl;
    private LiveData<List<Entity_Stuhl>> allStuhl;
    private List<String> stuhlListe = new ArrayList<>();

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

    public LiveData<List<Entity_Stuhl>> countStuhlByDay (){
        allStuhl = dao_stuhl.countStuhlByDay();
        return allStuhl;
    }



    /*
    public Entity_Stuhl getStuhlByID(Integer id){
        Entity_Stuhl stuhlByID = null;
        GetStuhlByIdAsyncTask task = new GetStuhlByIdAsyncTask(dao_stuhl);
        task.execute(id);
        try{
            stuhlByID = task.get();
        } catch (ExecutionException | InterruptedException e){
            e.printStackTrace();
        }
        return stuhlByID;
    }

     */

    //Query-Logik wird in eine Async-Task Subklasse ausgelagert
    //AsyncTask <input für execute(), Fortschritt, Output von get()
    /*
    private static class InsertStuhlAsyncTask extends AsyncTask<Entity_Stuhl, Void, Void> {

        private DAO_Stuhl dao_stuhl;

        private InsertStuhlAsyncTask(DAO_Stuhl dao_stuhl){
            this.dao_stuhl = dao_stuhl;
        }

        @Override
        protected Void doInBackground(Entity_Stuhl... stuhl) {
            dao_stuhl.insertStuhl(stuhl[0]);
            return null;
        }
    }

    private static class UpdateStuhlAsyncTask extends AsyncTask<Entity_Stuhl, Void, Void>{

        private DAO_Stuhl dao_stuhl;

        private UpdateStuhlAsyncTask(DAO_Stuhl dao_stuhl){
            this.dao_stuhl = dao_stuhl;
        }

        @Override
        protected Void doInBackground(Entity_Stuhl... stuhl) {
            dao_stuhl.update(stuhl[0]);
            return null;
        }
    }

    private static class DeleteStuhlAsyncTask extends AsyncTask<Entity_Stuhl, Void, Void>{

        private DAO_Stuhl dao_stuhl;

        private DeleteStuhlAsyncTask(DAO_Stuhl dao_stuhl){
            this.dao_stuhl = dao_stuhl;
        }

        @Override
        protected Void doInBackground(Entity_Stuhl... stuhl) {
            dao_stuhl.delete(stuhl[0]);
            return null;
        }
    }

    private static class GetStuhlByIdAsyncTask extends AsyncTask<Integer, Void, Entity_Stuhl> {

        private DAO_Stuhl dao_stuhl;

        private GetStuhlByIdAsyncTask(DAO_Stuhl dao_stuhl){
            this.dao_stuhl = dao_stuhl;
        }

        @Override
        protected Entity_Stuhl doInBackground(Integer... id) {
            dao_stuhl.getStuhlByID(id[0]);
            return null;
        }
    }

     */
}
