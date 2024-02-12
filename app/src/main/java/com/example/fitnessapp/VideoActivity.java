package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.fitnessapp.API.RetrofitClient;
import com.example.fitnessapp.databinding.ActivityDataRecordBinding;
import com.example.fitnessapp.databinding.ActivityVideoBinding;
import com.example.fitnessapp.model.Items;
import com.example.fitnessapp.model.VideoModel;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoActivity extends AppCompatActivity {
    private RetrofitClient client = new RetrofitClient();
    private ActivityVideoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        binding = ActivityVideoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        ImageView imageview = findViewById(R.id.image_view);
        ImageView imageview2 = findViewById(R.id.image_view2);
        ImageView imageview3 = findViewById(R.id.image_view3);
        ImageView imageview4 = findViewById(R.id.image_view4);
        ImageView imageview5 = findViewById(R.id.image_view5);
        ImageView imageview6 = findViewById(R.id.image_view6);
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideoActivity.this, WebActivity.class);
                startActivity(intent);
            }
        });

        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideoActivity.this, WebActivity2.class);
                startActivity(intent);
            }
        });

        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideoActivity.this, WebActivity3.class);
                startActivity(intent);
            }
        });

        binding.button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideoActivity.this, WebActivity4.class);
                startActivity(intent);
            }
        });

        binding.button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideoActivity.this, WebActivity5.class);
                startActivity(intent);
            }
        });

        binding.button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideoActivity.this, WebActivity6.class);
                startActivity(intent);
            }
        });

        String[] videoIds = {
                "1f8yoFFdkcY",
                "UItWltVZZmE",
                "gh5mAtmeR3Y",
                "kVnyY17VS9Y",
                "1BZM2Vre5oc",
                "D8DouKeOkfI"
        };

        for (int i = 0; i<6; i++) {
            int finalI = i;
            client.getVideos(videoIds[i], new Callback<VideoModel>() {
                @Override
                public void onResponse(Call<VideoModel> call, Response<VideoModel> response) {
                    if (response.isSuccessful()) {
                        Log.i("t", "3");
                        VideoModel channelResponse = response.body();
                        Items item[] = channelResponse.getItems();
                        Log.i("t", item[0].getSnippet().getTitle());
                        if (finalI == 0) {
                            Picasso.get().load(item[0].getSnippet().getThumbnails().getHigh().getUrl()).into(imageview);
                            binding.information.setText(item[0].getSnippet().getTitle());
                        } else if (finalI == 1) {
                            Picasso.get().load(item[0].getSnippet().getThumbnails().getHigh().getUrl()).into(imageview2);
                            binding.information2.setText(item[0].getSnippet().getTitle());
                        } else if (finalI == 2) {
                            Picasso.get().load(item[0].getSnippet().getThumbnails().getHigh().getUrl()).into(imageview3);
                            binding.information3.setText(item[0].getSnippet().getTitle());
                        } else if (finalI == 3){
                            Picasso.get().load(item[0].getSnippet().getThumbnails().getHigh().getUrl()).into(imageview4);
                            binding.information4.setText(item[0].getSnippet().getTitle());
                        } else if (finalI == 4) {
                        Picasso.get().load(item[0].getSnippet().getThumbnails().getHigh().getUrl()).into(imageview5);
                        binding.information5.setText(item[0].getSnippet().getTitle());
                        }else{
                            Picasso.get().load(item[0].getSnippet().getThumbnails().getHigh().getUrl()).into(imageview6);
                            binding.information6.setText(item[0].getSnippet().getTitle());
                        }
                    // Handle the channel response
                    } else {
                        int statusCode = response.code();
                        String s = String.valueOf(statusCode);
                        Log.i("status", s);
                        // You can check the status code to determine the specific error
                        switch (statusCode) {
                            case 401:
                                binding.information.setText("401 Error");
                                // Unauthorized: handle invalid API key error
                                break;
                            case 404:
                                binding.information.setText("404 Error");
                                // Not found: handle incorrect URL error
                                break;
                            // Add more cases for other possible status codes
                            case 400:
                                binding.information.setText("400 Error");
                                break;
                            default:
                                // Handle other errors
                                break;
                        }
                    }
                }

                @Override
                public void onFailure(Call<VideoModel> call, Throwable t) {
                    // Handle API call failure
                    Log.e("API Call", "Failed", t);
                }
            });
        }
    }
}