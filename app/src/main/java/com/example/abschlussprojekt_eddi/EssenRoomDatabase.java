package com.example.abschlussprojekt_eddi;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@androidx.room.Database(entities = {Entity_Essen.class}, version = 2, exportSchema = false)
public abstract class EssenRoomDatabase extends RoomDatabase {

    public abstract DAO_Essen dao_essen();

    private static volatile EssenRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecuter =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    // gibt dann eine Instanz von DAO_Essen zurück, wenn schon eine besteht wird diese
    // zurückgeliefert.
    static EssenRoomDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (EssenRoomDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            EssenRoomDatabase.class, "essen_database")
                            .addCallback(essenRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback essenRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecuter.execute(() -> {
                DAO_Essen daoEssen = INSTANCE.dao_essen();
                Entity_Essen essen = new Entity_Essen("Nudelsuppe");
                daoEssen.insertEssen(essen);
            });
        }
    };

    /*

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private DAO_Essen dao_essen;

        private PopulateDbAsyncTask(EssenRoomDatabase db) {
            dao_essen = db.dao_essen();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }

     */
}
