package com.example.fitnessapp.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.fitnessapp.dao.SportDAO;
import com.example.fitnessapp.database.SportDatabase;
import com.example.fitnessapp.entity.SportData;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class SportRepository {
    private SportDAO sportDao;
    private LiveData<List<SportData>> allData;
    public SportRepository(Application application){
        SportDatabase db = SportDatabase.getInstance(application);
        sportDao =db.sportDao();
        allData= sportDao.getAll();
    }
    // Room executes this query on a separate thread
    public LiveData<List<SportData>> getAllData() {
        return allData;
    }
    public void insert(final SportData sportData){
        SportDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                sportDao.insert(sportData);
            }
        });
    }

    public void deleteAll(){
        SportDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                sportDao.deleteAll();
            }
        });
    }

    public void delete(final SportData data){
        SportDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                sportDao.delete(data);
            }
        });
    }

    public void updateData(final SportData data){
        SportDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                sportDao.updateData(data);
            }
        });
    }

    public CompletableFuture<SportData> findByIDFuture(final String customerId) {
        return CompletableFuture.supplyAsync(new Supplier<SportData>() {
            @Override
            public SportData get() {

                return sportDao.findByID(customerId);
        }
        }, SportDatabase.databaseWriteExecutor);
    }
}
