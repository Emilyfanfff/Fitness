package com.example.fitnessapp.converter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DoubleConverter {
    Gson gson = new Gson();

    @TypeConverter
    public ArrayList<Double> stringToList(String data) {

        Type listType = new TypeToken<ArrayList<Double>>() {
        }.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public String ListToString(ArrayList<Double> someObjects) {
        return gson.toJson(someObjects);
    }
}
