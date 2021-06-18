package com.example.android.steamsearchapp;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.android.steamsearchapp.data.SteamAppDataContainer;
import com.example.android.steamsearchapp.data.favsRepository;

import java.util.List;

public class favsViewModel extends AndroidViewModel {

    private favsRepository repository;

    public favsViewModel(Application application) {

        super(application);
        this.repository = new favsRepository(application);
    }

    public void insertFav(SteamAppDataContainer fav) {

        this.repository.insertFav(fav);
    }

    public void deleteFav(SteamAppDataContainer fav) {

        this.repository.deleteFav(fav);
    }

    public LiveData<List<SteamAppDataContainer>> getAllFavs() {

        return this.repository.getAllFavs();
    }

    public LiveData<SteamAppDataContainer> getFavById(String appid) {

        return this.repository.getFavById(appid);
    }
}
