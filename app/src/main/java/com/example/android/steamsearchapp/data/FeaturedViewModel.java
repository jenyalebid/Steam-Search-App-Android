package com.example.android.steamsearchapp.data;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.steamsearchapp.MainActivity;

import java.util.List;

public class FeaturedViewModel extends ViewModel {

    private FeaturedRetrofit retrofitData;
    private LiveData<List<FeaturedWin>> featuredDataResults;

    private static final String TAG = FeaturedViewModel.class.getSimpleName();

    public FeaturedViewModel() {

        this.retrofitData = new FeaturedRetrofit();
        this.featuredDataResults = this.retrofitData.getFeaturedResults();
    }

    public LiveData<List<FeaturedWin>> getFeaturedDataResults() {

        return this.featuredDataResults;
    }

    public void loadFeaturedData() {

        Log.d(TAG, "IN VIEW MODEL");

        this.retrofitData.loadFeaturedResults();
    }
}
