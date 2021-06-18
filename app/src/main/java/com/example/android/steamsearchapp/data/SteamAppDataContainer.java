package com.example.android.steamsearchapp.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;


import com.example.android.steamsearchapp.data.details.SteamAppDetailsGenre;
import com.example.android.steamsearchapp.data.details.SteamAppDetailsScreenshot;
import com.example.android.steamsearchapp.data.details.SteamAppDetailsVideos;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity(tableName = "favs_table")
public class SteamAppDataContainer implements Serializable {

    @SerializedName("name")
    public String name;

    @SerializedName("type")
    public String type;

    @PrimaryKey
    @NonNull
    @SerializedName("steam_appid")
    public Integer appid;

    @SerializedName("is_free")
    public Boolean is_free;

    @SerializedName("detailed_description")
    public String longDescription;

    @SerializedName("short_description")
    public String shortDescription;

    @SerializedName("about_the_game")
    String aboutTheGame;

    @SerializedName("supported_languages")
    String languages;

    @SerializedName("header_image")
    public String headerImageUrl;

    @SerializedName("website")
    String websiteUrl;

    @TypeConverters(AppTypeConverters.class)
    public ArrayList<String> developers;

    @TypeConverters(AppTypeConverters.class)
    public ArrayList<String> publishers;

    @SerializedName("final_formatted")
    public String finalPrice;

    //public Integer id;

//    @SerializedName("path_full")
//    public String pathFull;

    @SerializedName("genres")
    @TypeConverters(AppTypeConverters.class)
    public ArrayList<SteamAppDetailsGenre> appGenres;
//
    @SerializedName("screenshots")
    @TypeConverters(AppTypeConverters.class)
    public ArrayList<SteamAppDetailsScreenshot> screenshotUrls;
//
    @SerializedName("movies")
    @TypeConverters(AppTypeConverters.class)
    public ArrayList<SteamAppDetailsVideos> videoUrls;
}
