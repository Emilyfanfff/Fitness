package com.example.fitnessapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapp.DataRecord;
import com.example.fitnessapp.databinding.RvLayoutBinding;
import com.example.fitnessapp.model.SportActivity;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter
        <RecyclerViewAdapter.ViewHolder> {
    private static List<SportActivity> sportActivities;
    public RecyclerViewAdapter(List<SportActivity> sportActivities) {
        this.sportActivities = sportActivities;
    }
    //creates a new viewholder that is constructed with a new View, inflated from a layout
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                             int viewType) {
        RvLayoutBinding binding=
                RvLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }
    // this method binds the view holder created with data that will be displayed
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder viewHolder, int
            position) {
        final SportActivity sportActivity = sportActivities.get(position);
        viewHolder.binding.tvRvname.setText(sportActivity.getSportName());
        viewHolder.binding.tvRvtime.setText((sportActivity.getSuggestingTime()));
        viewHolder.binding.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DataRecord.class);
                intent.putExtra("sportName", sportActivity.getSportName());
                intent.putExtra("suggestingTime", sportActivity.getSuggestingTime());
                //String position =String.valueOf(getAbsoluteAdapterPosition());
                String details=sportActivity.getSportName()+ " " + sportActivity.getSuggestingTime();
                //Log.i("position ",position);
                Log.i("details: ",details);
                v.getContext().startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return sportActivities.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private RvLayoutBinding binding;
        public ViewHolder(RvLayoutBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
