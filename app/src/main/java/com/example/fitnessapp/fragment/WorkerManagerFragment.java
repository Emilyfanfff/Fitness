package com.example.fitnessapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.example.fitnessapp.databinding.WorkManagerFragmentBinding;
import com.example.fitnessapp.workermanager.MyWorker;

public class WorkerManagerFragment extends Fragment {
    private WorkManagerFragmentBinding binding;
    public WorkerManagerFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = WorkManagerFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.workerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkRequest myWorkRequest = OneTimeWorkRequest.from(MyWorker.class);
                WorkManager
                        .getInstance(getContext())
                        .enqueue(myWorkRequest);
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