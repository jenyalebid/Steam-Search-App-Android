package com.example.android.steamsearchapp.data.details;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SteamAppDetailsScreenshot implements Serializable {

    @SerializedName("id")
    public int id;

    @SerializedName("path_thumbnail")
    public String thumbnailUrl;

    @SerializedName("path_full")
    public String screenshotUrl;
}
