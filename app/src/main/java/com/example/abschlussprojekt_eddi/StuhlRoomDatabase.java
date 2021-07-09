package com.example.abschlussprojekt_eddi;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@androidx.room.Database(entities = {Entity_Stuhl.class}, views = {AnzahlByDay.class}, version=1, exportSchema = false)
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
                        2, false, false, 2, false, 1,
                        1,"keine Notizen");
                daoStuhl.insertStuhl(stuhl);
                Entity_Stuhl stuhl2 = new Entity_Stuhl(2021, 6, 24, 14, 21,
                        5, false, false, 4,
                        false, 2, 2,
                        "testeintrag");
                daoStuhl.insertStuhl(stuhl2);
            });
        }
    };
}
