package com.example.fitnessapp.model;

import android.util.Log;

import com.example.fitnessapp.entity.SportData;
import com.example.fitnessapp.viewmodel.SportViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SportActivity {
    private String sportName;
    private String suggestionTime;
    public SportActivity(String sportName, String suggestionTime) {
        this.sportName = sportName;
        this.suggestionTime = suggestionTime;
    }
    public String getSportName() {
        return sportName;
    }
    public String getSuggestingTime() {
        return suggestionTime;
    }

    //this is used to populate the list with a few items at the start of the application
//it is static so it can be called without instantiating the class
    public static List<SportActivity> createContactsList() {
        List<SportActivity> SportActivities = new ArrayList<SportActivity>();

        SportActivities.add(new SportActivity( "Yoga","0 mins"));
        SportActivities.add(new SportActivity( "Swimming","0 mins"));
        SportActivities.add(new SportActivity("Jogging", "0 mins"));
        SportActivities.add(new SportActivity("Boxing", "0 mins"));
        SportActivities.add(new SportActivity("Rope Skipping", "0 mins"));
        return SportActivities;
    }
}

