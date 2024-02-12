package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;

import com.example.fitnessapp.databinding.ActivityDataRecordBinding;
import com.example.fitnessapp.entity.SportData;
import com.example.fitnessapp.viewmodel.SportViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.concurrent.CompletableFuture;

public class DataRecord extends AppCompatActivity {
    private ActivityDataRecordBinding binding;
    private SportViewModel sportViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDataRecordBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Intent intent = getIntent();
        binding.sport.setText(intent.getStringExtra("sportName"));
        String type = binding.sport.getText().toString();

        sportViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())
                .create(SportViewModel.class);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://profound-ripsaw-384110-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference myRef = database.getReference();
        FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
        String uid = currentUser.getUid();
        CompletableFuture<SportData> dataCompletableFuture = sportViewModel.findByIDFuture(uid);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            dataCompletableFuture.thenApply(data -> {
                if(data != null){
                    int index = data.date.size() - 1;
                    if(type.equals("Yoga")){
                        binding.minute.setText(data.yoga.get(index).toString());
                    } else if (type.equals("Swimming")) {
                        binding.minute.setText(data.swimming.get(index).toString());
                    }else if(type.equals("Jogging")){
                        binding.minute.setText(data.jogging.get(index).toString());
                    } else if (type.equals("Boxing")) {
                        binding.minute.setText(data.boxing.get(index).toString());
                    } else{
                        binding.minute.setText(data.rope_skipping.get(index).toString());
                    }
                }
                return data;
            });
        }

        binding.returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DataRecord.this, AppMain.class);
                startActivity(intent);
            }
        });

        binding.button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String min = binding.insert.getText().toString();
                Double min_int = Integer.valueOf(min).doubleValue();
                //get today's date
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH) + 1;
                int day = c.get(Calendar.DATE);
                String today_date = Integer.toString(year) + " " + Integer.toString(month) + " " + Integer.toString(day);

                //get userid
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://profound-ripsaw-384110-default-rtdb.asia-southeast1.firebasedatabase.app");
                DatabaseReference myRef = database.getReference();
                FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
                String uid = currentUser.getUid();
                CompletableFuture<SportData> dataCompletableFuture = sportViewModel.findByIDFuture(uid);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    dataCompletableFuture.thenApply(data -> {
                        if(data != null){
                            int index = data.date.size() - 1;
                            String latest_date = data.date.get(index);
                            Log.i("id", "1");
                            Log.i("id", today_date);
                            Log.i("id", data.yoga.get(index).toString());
                            if(latest_date.equals(today_date)){
                                if(type.equals("Yoga")){
                                    data.yoga.set(index, min_int);
                                    binding.minute.setText(data.yoga.get(index).toString());
                                } else if (type.equals("Swimming")) {
                                    data.swimming.set(index, min_int);
                                    binding.minute.setText(data.swimming.get(index).toString());
                                }else if(type.equals("Jogging")){
                                    data.jogging.set(index, min_int);
                                    binding.minute.setText(data.jogging.get(index).toString());
                                } else if (type.equals("Boxing")) {
                                    data.boxing.set(index, min_int);
                                    binding.minute.setText(data.boxing.get(index).toString());
                                } else{
                                    data.rope_skipping.set(index, min_int);
                                    binding.minute.setText(data.rope_skipping.get(index).toString());
                                }
                                data.daily_minute.set(index, data.yoga.get(index)+ data.swimming.get(index) + data.jogging.get(index) + data.boxing.get(index) + data.rope_skipping.get(index));
                            }else{
                                Log.i("id", today_date);
                                data.date.add(today_date);
                                Log.i("id", "5");
                                int newIndex = index + 1;
                                if(type.equals("Yoga")){
                                    Log.i("id", "5");
                                    data.yoga.add(min_int);
                                    data.swimming.add(0.0);
                                    data.jogging.add(0.0);
                                    data.boxing.add(0.0);
                                    data.rope_skipping.add(0.0);
                                    binding.minute.setText(data.yoga.get(newIndex).toString());
                                } else if (type.equals("Swimming")) {
                                    data.swimming.add(min_int);
                                    data.yoga.add(0.0);
                                    data.jogging.add(0.0);
                                    data.boxing.add(0.0);
                                    data.rope_skipping.add(0.0);
                                    binding.minute.setText(data.swimming.get(newIndex).toString());
                                }else if(type.equals("Jogging")){
                                    data.jogging.add(min_int);
                                    data.swimming.add(0.0);
                                    data.yoga.add(0.0);
                                    data.boxing.add(0.0);
                                    data.rope_skipping.add(0.0);
                                    binding.minute.setText(data.jogging.get(newIndex).toString());
                                } else if (type.equals("Boxing")) {
                                    data.boxing.add(min_int);
                                    data.swimming.add(0.0);
                                    data.jogging.add(0.0);
                                    data.yoga.add(0.0);
                                    data.rope_skipping.add(0.0);
                                    binding.minute.setText(data.boxing.get(newIndex).toString());
                                } else{
                                    data.rope_skipping.add(min_int);
                                    data.swimming.add(0.0);
                                    data.jogging.add(0.0);
                                    data.boxing.add(0.0);
                                    data.yoga.add(0.0);
                                    binding.minute.setText(data.rope_skipping.get(newIndex).toString());
                                }
                                data.daily_minute.add(data.yoga.get(newIndex)+ data.swimming.get(newIndex) + data.jogging.get(newIndex) + data.boxing.get(newIndex) + data.rope_skipping.get(newIndex));
                            }
                            sportViewModel.update(data);
                        }
                        return data;
                    });
                }
            }
        });
    }
}