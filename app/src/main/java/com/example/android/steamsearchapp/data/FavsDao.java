package com.example.android.steamsearchapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SteamAppDataContainer fav);

    @Delete
    void delete(SteamAppDataContainer fav);

    @Query("SELECT * FROM favs_table ORDER BY name DESC")
    LiveData<List<SteamAppDataContainer>> getAllFavs();

    @Query("SELECT * FROM favs_table WHERE appid = :appid LIMIT 1 ")
    LiveData<SteamAppDataContainer> getFavByID(String appid);
}
