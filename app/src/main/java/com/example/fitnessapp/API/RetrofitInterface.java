package com.example.fitnessapp.API;

import com.example.fitnessapp.model.VideoModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitInterface {
    @GET("videos")
    Call<VideoModel> getVideos(@Query("part") String part,
                                 @Query("id") String id,
                                 @Query("key") String key);
}
