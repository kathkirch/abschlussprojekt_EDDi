package com.example.abschlussprojekt_eddi;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@androidx.room.Database(entities = {Entity_Stuhl.class}, version=2, exportSchema = false)
public abstract class StuhlRoomDatabase extends RoomDatabase {

    public abstract DAO_Stuhl dao_stuhl();
    private static volatile StuhlRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecuter =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    // gibt dann eine Instanz von DAO_Essen zurück, wenn schon eine besteht wird diese
    // zurückgeliefert.
    public static StuhlRoomDatabase getDatabaseStuhl(final Context context){
       if(INSTANCE == null) {
           synchronized (StuhlRoomDatabase.class){
               if (INSTANCE == null){
                   INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                           StuhlRoomDatabase.class, "stuhl_database")
                           .fallbackToDestructiveMigration()
                           .addCallback(stuhlRoomDatabaseCallback)
                           .build();
               }
           }
       }
       return INSTANCE;
   }

    private static RoomDatabase.Callback stuhlRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecuter.execute(() -> {
                DAO_Stuhl daoStuhl = INSTANCE.dao_stuhl();
                Entity_Stuhl stuhl = new Entity_Stuhl(2021, 6, 8, 14, 20,
                        "2", false, false, "dunkelbraun", false, "0", "normal",
                        "keine Notizen", "Hier könnte dein Foto zu sehen sein");
                daoStuhl.insertStuhl(stuhl);
                Entity_Stuhl stuhl2 = new Entity_Stuhl(2021, 6, 24, 14, 21,
                        "5", false, false, "grün",
                        false, "kein", "normal",
                        "testeintrag", "refTest" );
                daoStuhl.insertStuhl(stuhl2);
            });
        }
    };


    /*
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        //Strg+O um super() Methoden anzuzeigen

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(INSTANCE).execute();
        }
    };
    */


    /*
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private DAO_Stuhl dao_stuhl;

        private PopulateDbAsyncTask(StuhlRoomDatabase db){
            dao_stuhl = db.dao_stuhl();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

        /*

        @Override
        protected Void doInBackground(Void... voids) {
            dao_stuhl.insertStuhl(new Entity_Stuhl(2021, 6, 8, 14, 20,
                    "2", false, false, "dunkelbraun", false, "0", "normal",
                    "keine Notizen", "Hier könnte dein Foto zu sehen sein"));
            return null;
        }


    }*/


     /*
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
   */
}
