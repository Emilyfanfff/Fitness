package com.example.fitnessapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.fitnessapp.dao.SportDAO;
import com.example.fitnessapp.entity.SportData;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {SportData.class}, version = 1, exportSchema = false)
public abstract class SportDatabase extends RoomDatabase {
    public abstract SportDAO sportDao();
    private static SportDatabase INSTANCE;


    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    public static synchronized SportDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    SportDatabase.class, "SportDatabase")
                    .fallbackToDestructiveMigration().build();
            }
        return INSTANCE;
    }
}
