package com.example.android.steamsearchapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.steamsearchapp.data.LoadingStatus;
import com.example.android.steamsearchapp.data.SteamApp;
import com.example.android.steamsearchapp.data.SteamAppDataContainer;
import com.example.android.steamsearchapp.data.SteamSearchCatalogue;

import java.util.List;

public class SteamSearchViewModel extends ViewModel {
    private LiveData<List<SteamApp>> searchResults;
    private LiveData<LoadingStatus> loadingStatus;
    private LiveData<SteamAppDataContainer> appDetails;
    private SteamSearchCatalogue catalogue;

    public SteamSearchViewModel() {
        this.catalogue = new SteamSearchCatalogue();
        this.searchResults = this.catalogue.getSearchResults();
        this.loadingStatus = this.catalogue.getLoadingStatus();
        this.appDetails = this.catalogue.getSingleAppDetails();
    }

    public void loadSearchResults(String query) {
        this.catalogue.loadSearchResults(query);
    }

    public void loadSingleAppDetails(String appid) {
        this.catalogue.loadAppDetails(appid);
    }

    public LiveData<List<SteamApp>> getSearchResults() {
        return this.searchResults;
    }

    public LiveData<SteamAppDataContainer> getSingleProduct() {
        return this.appDetails;
    }

    public LiveData<LoadingStatus> getLoadingStatus() {
        return this.loadingStatus;
    }
}