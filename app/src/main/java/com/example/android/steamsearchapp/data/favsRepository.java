package com.example.android.steamsearchapp.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class favsRepository {

    private FavsDao dao;

    public favsRepository(Application application) {

        AppDatabase db = AppDatabase.getDatabase(application);
        this.dao = db.favsDao();
    }

    public void insertFav(SteamAppDataContainer fav) {

        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {

                dao.insert(fav);
            }
        });
    }

    public void deleteFav(SteamAppDataContainer fav) {

        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {

                dao.delete(fav);
            }
        });
    }

    public LiveData<List<SteamAppDataContainer>> getAllFavs() {

        return this.dao.getAllFavs();
    }

    public LiveData<SteamAppDataContainer> getFavById(String appid) {

        return this.dao.getFavByID(appid);
    }
}
