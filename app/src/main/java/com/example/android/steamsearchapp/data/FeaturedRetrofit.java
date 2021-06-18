package com.example.android.steamsearchapp.data;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FeaturedRetrofit {

    private static final String TAG = FeaturedRetrofit.class.getSimpleName();
    final static String BASE_URL = "https://store.steampowered.com/api/";

    private MutableLiveData<List<FeaturedWin>> featuredWin;

    private FeaturedInterface featuredInterface;

    public FeaturedRetrofit() {

        this.featuredWin = new MutableLiveData<>();
        this.featuredWin.setValue(null);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.featuredInterface = retrofit.create(FeaturedInterface.class);
    }

    public LiveData<List<FeaturedWin>> getFeaturedResults() {

        return this.featuredWin;
    }

    public void loadFeaturedResults() {

        this.featuredWin.setValue(null);

        Call<Featured> featuredResults = this.featuredInterface.getData();

        featuredResults.enqueue(new Callback<Featured>() {
            @Override
            public void onResponse(Call<Featured> call, Response<Featured> response) {

                if (response.code() == 200) {

                    Log.d(TAG, "SUCCESS: CODE 200");
                    featuredWin.setValue(response.body().featured_win);
                }
            }

            @Override
            public void onFailure(Call<Featured> call, Throwable t) {

                Log.d(TAG, "GOT ERROR: NOT 200");
                t.printStackTrace();
            }
        });
    }
}
