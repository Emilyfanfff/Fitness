package com.example.fitnessapp.workermanager;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.fitnessapp.entity.SportData;
import com.example.fitnessapp.viewmodel.SportViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

public class MyWorker extends Worker {

    private SportViewModel sportViewModel;
    public MyWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @Override
    public Result doWork() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://profound-ripsaw-384110-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference myRef = database.getReference();
        FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
        String uid = currentUser.getUid();
        sportViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance((Application) getApplicationContext())
                .create(SportViewModel.class);
        CompletableFuture<SportData> dataCompletableFuture = sportViewModel.findByIDFuture(uid);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            dataCompletableFuture.thenApply(data -> {
                if(data != null){
                    myRef.child("sport-data").child(uid).setValue(data);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date();

                    String dateString = dateFormat.format(date);
                    StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
                    String methodName = stackTraceElements[2].getMethodName();
                    Log.d("my-worker", "date: " + dateString + ", Method: " + methodName);
                }
                return data;
            });
        }

        // Indicate whether the work finished successfully with the Result
        return Result.success();
    }
}
