package com.example.abschlussprojekt_eddi;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class LogbuchRepository {
    private DAO_Stuhl dao_stuhl;
    private LiveData<List<Entity_Stuhl>> allStuhl;
    private Entity_Stuhl stuhl;

    public LogbuchRepository(Application application){
        LogbuchDatabase database = LogbuchDatabase.getInstance(application);
        //Methode in LogbuchDatabase erstellt
        dao_stuhl = database.dao_stuhl();
        allStuhl = dao_stuhl.getAll();
    }

    //das ViewModel muss später nurmehr diese Methoden aufrufen
    //das Repository kümmert sich darum, woher die Daten kommen

    public void insertAll(Entity_Stuhl...stuhl){
        new InsertStuhlAsyncTask(dao_stuhl).execute(stuhl);
    }

    public void insertStuhl(Entity_Stuhl stuhl){
        new InsertStuhlAsyncTask(dao_stuhl).execute(stuhl);
    }

    public void update(Entity_Stuhl stuhl){
        new UpdateStuhlAsyncTask(dao_stuhl).execute(stuhl);
    }

    public void delete(Entity_Stuhl stuhl){
        new DeleteStuhlAsyncTask(dao_stuhl).execute(stuhl);
    }

    public LiveData<List<Entity_Stuhl>> getAll(){
        return allStuhl;
    }

    public LiveData<List<Entity_Stuhl>> getStuhlByDate(SimpleDateFormat datum){
        LiveData<List<Entity_Stuhl>> stuhlByDate = allStuhl; //macht diese Zweisung Sinn? Eher nicht...aber sonst gäbe es probleme mit dem return wert
        GetStuhlByDateAsyncTask task = new GetStuhlByDateAsyncTask(dao_stuhl);
        task.execute(datum);
        try{
            stuhlByDate = task.get();
        } catch (ExecutionException | InterruptedException e){
            e.printStackTrace();
        }
        return stuhlByDate;
    }

    public Entity_Stuhl getStuhlByID(Integer id){
        Entity_Stuhl stuhlByID = stuhl; //macht diese Zweisung Sinn? Eher nicht...aber sonst gäbe es probleme mit dem return wert
        GetStuhlByIdAsyncTask task = new GetStuhlByIdAsyncTask(dao_stuhl);
        task.execute(id);
        try{
            stuhlByID = task.get();
        } catch (ExecutionException | InterruptedException e){
            e.printStackTrace();
        }
        return stuhlByID;
    }


    //Query-Logik wird in eine Async-Task Subklasse ausgelagert
    //AsyncTask <input für execute(), Fortschritt, Output von get()
    private static class InsertStuhlAsyncTask extends AsyncTask<Entity_Stuhl, Void, Void>{

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

    private static class GetStuhlByDateAsyncTask extends AsyncTask<SimpleDateFormat, Void, LiveData<List<Entity_Stuhl>>>{

        private DAO_Stuhl dao_stuhl;

        private GetStuhlByDateAsyncTask(DAO_Stuhl dao_stuhl){
            this.dao_stuhl = dao_stuhl;
        }

        @Override
        protected LiveData<List<Entity_Stuhl>> doInBackground(SimpleDateFormat... datum) {
            dao_stuhl.getStuhlByDate(datum);
            return null;
        }
    }

    private static class GetStuhlByIdAsyncTask extends AsyncTask<Integer, Void, Entity_Stuhl>{

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

}
