package com.example.fitnessapp.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.fitnessapp.entity.SportData;
import com.example.fitnessapp.repository.SportRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SportViewModel extends AndroidViewModel {
    private SportRepository sRepository;
    private LiveData<List<SportData>> allData;
    public SportViewModel (Application application) {
        super(application);
        sRepository = new SportRepository(application);
        allData = sRepository.getAllData();
    }
    public CompletableFuture<SportData> findByIDFuture(final String customerId){
        return sRepository.findByIDFuture(customerId);
    }

    public LiveData<List<SportData>> getAllData() {
        return allData;
    }

    public void insert(SportData data) {
        sRepository.insert(data);
    }

    public void deleteAll() {
        sRepository.deleteAll();
    }

    public void update(SportData data) {
        sRepository.updateData(data);
    }
}
