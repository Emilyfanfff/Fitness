package com.example.fitnessapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapp.VideoActivity;
import com.example.fitnessapp.adapter.RecyclerViewAdapter;
import com.example.fitnessapp.databinding.HomeFragmentBinding;
import com.example.fitnessapp.entity.SportData;
import com.example.fitnessapp.entity.User;
import com.example.fitnessapp.model.SportActivity;
import com.example.fitnessapp.viewmodel.SharedViewModel;
import com.example.fitnessapp.viewmodel.SportViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;


public class HomeFragment extends Fragment {
    private SharedViewModel model;
    private HomeFragmentBinding binding;
    private SportViewModel sportViewModel;

    public HomeFragment(){}

    private RecyclerView.LayoutManager layoutManager;
    private List<SportActivity> sportActivities;
    private RecyclerViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the View for this fragment
        super.onCreate(savedInstanceState);
        binding = HomeFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://profound-ripsaw-384110-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference myRef = database.getReference();
        FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
        String uid = currentUser.getUid();
        sportViewModel = new ViewModelProvider(this).get(SportViewModel.class);

        binding.videoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), VideoActivity.class);
                startActivity(intent);
            }
        });
        myRef.child("users").child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Gson gson = new Gson();
                    User user = gson.fromJson(new Gson().toJson(task.getResult().getValue()), User.class);
                    binding.welcomeMax.setText("welcome, " + user.name);
                }
            }
        });
        sportActivities=new ArrayList<SportActivity>();
        sportActivities = SportActivity.createContactsList();
        CompletableFuture<SportData> dataCompletableFuture = sportViewModel.findByIDFuture(uid);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            dataCompletableFuture.thenApply(data -> {
                if(data != null){
                    int index = data.date.size() - 1;
                    String yoga_t = data.yoga.get(index).toString() + " mins";
                    sportActivities.set(0, new SportActivity( "Yoga", yoga_t));
                    String swimming_t = data.swimming.get(index).toString() + " mins";
                    sportActivities.set(1, new SportActivity( "Swimming", swimming_t));
                    String jogging_t = data.jogging.get(index).toString() + " mins";
                    sportActivities.set(2, new SportActivity( "Jogging", jogging_t));
                    String boxing_t = data.boxing.get(index).toString() + " mins";
                    sportActivities.set(3, new SportActivity( "Boxing", boxing_t));
                    String rope_t = data.rope_skipping.get(index).toString() + " mins";
                    sportActivities.set(4, new SportActivity( "Rope Skipping", rope_t));
                }
                return data;
            });
        }
        adapter = new RecyclerViewAdapter(sportActivities);
//this just creates a line divider between rows
        binding.recyclerView.addItemDecoration(new
                DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        binding.recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(layoutManager);
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}