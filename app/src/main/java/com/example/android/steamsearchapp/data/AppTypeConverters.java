package com.example.android.steamsearchapp.data;

import androidx.room.TypeConverter;

import com.example.android.steamsearchapp.data.details.SteamAppDetailsGenre;
import com.example.android.steamsearchapp.data.details.SteamAppDetailsScreenshot;
import com.example.android.steamsearchapp.data.details.SteamAppDetailsVideos;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AppTypeConverters {

    public static Gson gson = new Gson();


    /*
    Genre Converters
    */

    @TypeConverter
    public static ArrayList<SteamAppDetailsGenre> stringToGenreList(String data) {
        if (data == null) {
            return new ArrayList<SteamAppDetailsGenre>();
        }
        Type listType = new TypeToken<ArrayList<SteamAppDetailsGenre>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String genreListToString(ArrayList<SteamAppDetailsGenre> someObjects) {
        return gson.toJson(someObjects);
    }

    /*
    Screenshot Converters
    */

    @TypeConverter
    public static ArrayList<SteamAppDetailsScreenshot> stringToScreenshotList(String data) {
        if (data == null) {
            return new ArrayList<SteamAppDetailsScreenshot>();
        }
        Type listType = new TypeToken<ArrayList<SteamAppDetailsScreenshot>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String screenshotListToString(ArrayList<SteamAppDetailsScreenshot> someObjects) {
        return gson.toJson(someObjects);
    }

    /*
    Video Converters
    */

    @TypeConverter
    public static ArrayList<SteamAppDetailsVideos> stringToVideoList(String data) {
        if (data == null) {
            return new ArrayList<SteamAppDetailsVideos>();
        }
        Type listType = new TypeToken<ArrayList<SteamAppDetailsVideos>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String videoListToString(ArrayList<SteamAppDetailsVideos> someObjects) {
        return gson.toJson(someObjects);
    }

    /*
    String Converters
    */

    @TypeConverter
    public static ArrayList<String> stringToStringList(String data) {
        if (data == null) {
            return new ArrayList<String>();
        }
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String stringListToString(ArrayList<String> someObjects) {
        return gson.toJson(someObjects);
    }


}
