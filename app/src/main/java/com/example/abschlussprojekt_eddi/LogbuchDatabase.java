package com.example.abschlussprojekt_eddi;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@androidx.room.Database(entities = {Entity_Stuhl.class}, version=1)
public abstract class LogbuchDatabase extends androidx.room.RoomDatabase {

    private static LogbuchDatabase instance;

    public abstract DAO_Stuhl dao_stuhl();

    public static synchronized LogbuchDatabase getInstance(Context context){
        //Instanz soll nur erzeugt werden, wenn es noch keine gibt
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    LogbuchDatabase.class, "Logbuch_Database")
                    //wird die Database löschen und erneut aufbauen, wenn die version-Nummer geändert wird. Ansonsten würde mann ein IllegaleStateException bekommen
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback) //Daten aus der PopulateDbAsyncTask() Methode wird generiert beim erstmaligen Aufrufen
                    .build();
        }
        //wenn eine Instanz bereits exisitiert, wirde diese ausgegeben
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        //Strg+O um super() Methoden anzuzeigen

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        private DAO_Stuhl dao_stuhl;

        private PopulateDbAsyncTask(LogbuchDatabase db){
            dao_stuhl = db.dao_stuhl();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao_stuhl.insertStuhl(new Entity_Stuhl(2021, 6, 8, 14, 20,
                    "2", false, false, "dunkelbraun", false, "0", "normal",
                    "keine Notizen", "Hier könnte dein Foto zu sehen sein"));
            return null;
        }
    }
}
