package com.example.android.steamsearchapp.data;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FeaturedInterface {

    @GET("featured")
    Call<Featured> getData();
}
