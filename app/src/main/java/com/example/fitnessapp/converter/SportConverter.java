package com.example.fitnessapp.converter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SportConverter {
    Gson gson = new Gson();

    @TypeConverter
    public ArrayList<String> stringToList(String data) {

        Type listType = new TypeToken<ArrayList<String>>() {
        }.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public String ListToString(ArrayList<String> someObjects) {
        return gson.toJson(someObjects);
    }
}
