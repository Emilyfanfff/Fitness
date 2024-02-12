package com.example.fitnessapp.API;

import android.util.Log;

import com.example.fitnessapp.model.VideoModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private static final String BASE_URL="https://youtube.googleapis.com/youtube/v3/";
    public static RetrofitInterface getRetrofitService(){
        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(RetrofitInterface.class);
    }
    public void getVideos(String VideoId, Callback<VideoModel> callback) {
        retrofitInterface = getRetrofitService();
        Call<VideoModel> call = retrofitInterface.getVideos(
                "snippet",
                VideoId,
                "AIzaSyDkH0uvL4jLcl_uzF3moDzNmfQJBwEJv0g"
        );
        Log.d("URL", call.request().url().toString());
        call.enqueue(callback);
    }
}
