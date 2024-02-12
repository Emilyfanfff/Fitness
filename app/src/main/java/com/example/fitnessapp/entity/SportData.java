package com.example.fitnessapp.entity;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.fitnessapp.converter.DoubleConverter;
import com.example.fitnessapp.converter.SportConverter;

import java.util.ArrayList;

@Entity
public class SportData {
    @PrimaryKey @NonNull
    public String uid;

    @NonNull
    public String name;
    @TypeConverters(SportConverter.class)
    public ArrayList<String> date;
    @TypeConverters(DoubleConverter.class)
    public ArrayList<Double> daily_minute;
    @TypeConverters(DoubleConverter.class)
    public ArrayList<Double> yoga;
    @TypeConverters(DoubleConverter.class)
    public ArrayList<Double> swimming;
    @TypeConverters(DoubleConverter.class)
    public ArrayList<Double> jogging;
    @TypeConverters(DoubleConverter.class)
    public ArrayList<Double> boxing;
    @TypeConverters(DoubleConverter.class)
    public ArrayList<Double> rope_skipping;

    public SportData(String uid, @NonNull String name, ArrayList date,
                     ArrayList daily_minute, ArrayList yoga, ArrayList swimming, ArrayList jogging,
                     ArrayList boxing, ArrayList rope_skipping) {
        this.uid = uid;
        this.name=name;
        this.date = date;
        this.daily_minute = daily_minute;
        this.yoga = yoga;
        this.swimming = swimming;
        this.jogging = jogging;
        this.boxing = boxing;
        this.rope_skipping = rope_skipping;
    }
}
