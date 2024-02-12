package com.example.fitnessapp.entity;

import androidx.annotation.NonNull;

public class User {
    @NonNull
    public String uid;
    @NonNull
    public String name;
    @NonNull
    public String email;
    @NonNull
    public String phone;
    @NonNull
    public String address;
    @NonNull
    public String sport;
    @NonNull
    public String gender;
    @NonNull
    public String birthday;

    public User( @NonNull String uid, @NonNull String name, @NonNull String email,@NonNull String phone,@NonNull String address,@NonNull String sport,@NonNull String gender,@NonNull String birthday) {
        this.uid=uid;
        this.name=name;
        this.email = email;
        this.phone=phone;
        this.address=address;
        this.sport = sport;
        this.gender = gender;
        this.birthday = birthday;
    }
}
