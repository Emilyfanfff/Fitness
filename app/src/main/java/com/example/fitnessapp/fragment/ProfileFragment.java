package com.example.fitnessapp.fragment;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.fitnessapp.R;
import com.example.fitnessapp.databinding.ProfileFragmentBinding;
import com.example.fitnessapp.entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class ProfileFragment extends Fragment {
    private ProfileFragmentBinding binding;
    public ProfileFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the View for this fragment using the binding
        binding = ProfileFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://profound-ripsaw-384110-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference myRef = database.getReference();
        FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
        String uid = currentUser.getUid();
        myRef.child("users").child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Gson gson = new Gson();
                    User user = gson.fromJson(new Gson().toJson(task.getResult().getValue()), User.class);
                    if(user.gender.equals("male")){
                        binding.userImage.setBackgroundResource(R.mipmap.male);
                    }else{
                        binding.userImage.setBackgroundResource(R.mipmap.female);
                    }
                    binding.profileName.setText(user.name);
                    binding.profileEmail.setText(user.email);
                    binding.profileAddress.setText("Address:"+user.address);
                    binding.profilePhone.setText("Phone:"+user.phone);
                    binding.profileSport.setText("Sport:"+user.sport);
                    binding.profileBirthday.setText("Birthday:"+user.birthday);
                }
            }
        });
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
