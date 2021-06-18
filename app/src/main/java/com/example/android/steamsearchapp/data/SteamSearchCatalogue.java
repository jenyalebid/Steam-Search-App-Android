package com.example.android.steamsearchapp.data;

import android.text.TextUtils;
import android.util.Log;

import com.example.android.steamsearchapp.utils.GitHubUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SteamSearchCatalogue {
    private static final String TAG = SteamSearchCatalogue.class.getSimpleName();
    private static final String BASE_URL = "https://api.steampowered.com";
    private static final String STORE_BASE_URL = "https://store.steampowered.com";

    private MutableLiveData<List<SteamApp>> searchResults;
    private MutableLiveData<SteamAppDataContainer> appDetails;
    private MutableLiveData<LoadingStatus> loadingStatus;

    private String currentQuery;
    private String currentAppid;

    private SteamService steamService;
    private SteamService detailService;

    public SteamSearchCatalogue() {
        this.searchResults = new MutableLiveData<>();
        this.searchResults.setValue(null);

        this.appDetails = new MutableLiveData<>();
        this.appDetails.setValue(null);

        this.loadingStatus = new MutableLiveData<>();
        this.loadingStatus.setValue(LoadingStatus.SUCCESS);




        //Set up retrofit for catalog search
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.steamService = retrofit.create(SteamService.class);


        //Set up gson builder
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(SteamAppDetails.class, new SteamAppDetails.JsonDeserializer())
                .create();

        //Set up retrofit for app search
        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(STORE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        this.detailService = retrofit2.create(SteamService.class);
    }

    public LiveData<List<SteamApp>> getSearchResults() {
        return this.searchResults;
    }

    public LiveData<LoadingStatus> getLoadingStatus() {
        return this.loadingStatus;
    }

    public LiveData<SteamAppDataContainer> getSingleAppDetails(){ return this.appDetails; }

    private boolean shouldExecuteSearch(String query) {
        return !TextUtils.equals(query, this.currentQuery)
                || this.getLoadingStatus().getValue() == LoadingStatus.ERROR;
    }

    private boolean shouldLoadDetails(String appid){
        return !TextUtils.equals(appid, this.currentAppid)
                || this.getLoadingStatus().getValue() == LoadingStatus.ERROR;
    }

    public void loadAppDetails(String appid){
        if (this.shouldLoadDetails(appid)) {
            Log.d(TAG, "running new search for this appid: " + appid);
            this.currentAppid = appid;
            this.executeProductDetails(appid);
        } else {
            Log.d(TAG, "using cached search results for this appid: " + appid);
        }
    }

    public void loadSearchResults(String query) {
        if (this.shouldExecuteSearch(query)) {
            Log.d(TAG, "running new search for this query: " + query);
            this.currentQuery = query;
            this.executeSearch(query, "json");
        } else {
            Log.d(TAG, "using cached search results for this query: " + query);
        }
    }

    public void executeProductDetails(String appid){
        Call<SteamAppDetails> results;

        //Set up the call
        results = this.detailService.getSteamProductDetail(appid);
        this.appDetails.setValue(null);
        this.loadingStatus.setValue(LoadingStatus.LOADING);

        //Api Call
        results.enqueue(new Callback<SteamAppDetails>() {
            @Override
            public void onResponse(Call<SteamAppDetails> call, Response<SteamAppDetails> response) {
                if (response.code() == 200) {
                    //Set app details and loading status on success
                    Log.d(TAG, "Got Response: " + response.body());
                    Log.d(TAG, "Got Response Data: " + response.body().data);
                    appDetails.setValue(response.body().data);
                    loadingStatus.setValue(LoadingStatus.SUCCESS);
                } else {
                    Log.d(TAG, "Bad Response: " + response);
                    Log.d(TAG, "Bad Response: " + response.body());
                    loadingStatus.setValue(LoadingStatus.ERROR);
                }
            }

            @Override
            public void onFailure(Call<SteamAppDetails> call, Throwable t) {
                t.printStackTrace();
                loadingStatus.setValue(LoadingStatus.ERROR);
            }
        });
    }



    private void executeSearch(String queryTerm, @Nullable String format) {
        Call<SteamSearchResults> results;

        results = this.steamService.getSteamProductCatalogue();

        this.searchResults.setValue(null);
        this.loadingStatus.setValue(LoadingStatus.LOADING);
        results.enqueue(new Callback<SteamSearchResults>() {
            @Override
            public void onResponse(Call<SteamSearchResults> call, Response<SteamSearchResults> response) {
                if (response.code() == 200) {
                    Log.d(TAG, "Got Response" + response.body());
                    searchResults.setValue(queryCatalogueSort(queryTerm, response.body().products.steamApps));
                    loadingStatus.setValue(LoadingStatus.SUCCESS);
                } else {
                    Log.d(TAG, "Bad Response");
                    loadingStatus.setValue(LoadingStatus.ERROR);
                }
            }

            @Override
            public void onFailure(Call<SteamSearchResults> call, Throwable t) {
                t.printStackTrace();
                loadingStatus.setValue(LoadingStatus.ERROR);
            }
        });
    }

    public ArrayList<SteamApp> queryCatalogueSort(String query, ArrayList<SteamApp> catalogue){
        ArrayList<SteamApp> sortedList = new ArrayList<>();

        for (int i = 0; i < catalogue.size(); i++ ) {
            if(catalogue.get(i).name.contains(query)){
                sortedList.add(catalogue.get(i));
            }
        }
        return sortedList;
    }
}
