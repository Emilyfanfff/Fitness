package com.example.fitnessapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fitnessapp.entity.SportData;

import java.util.List;
@Dao
public interface SportDAO {
    @Query("SELECT * FROM SportData ORDER BY name ASC")
    LiveData<List<SportData>> getAll();
    @Query("SELECT * FROM SportData WHERE uid = :customerId LIMIT 1")
    SportData findByID(String customerId);

    @Insert
    void insert(SportData data);

    @Delete
    void delete(SportData data);

    @Update
    void updateData(SportData data);

    @Query("DELETE FROM SportData")
    void deleteAll();
}
