package com.example.abschlussprojekt_eddi;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@androidx.room.Database(entities = {Entity_Essen.class}, version = 1, exportSchema = false)
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
                            .fallbackToDestructiveMigration()
                            .addCallback(essenRoomDatabaseCallback)
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
                Entity_Essen essen = new Entity_Essen("Nudelsuppe", 2021, 6, 8, 14, 20);
                daoEssen.insertEssen(essen);
            });
        }
    };

}
