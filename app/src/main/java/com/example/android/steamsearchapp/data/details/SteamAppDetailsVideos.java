package com.example.android.steamsearchapp.data.details;

import com.google.gson.annotations.SerializedName;

public class SteamAppDetailsVideos {

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String videoName;

    @SerializedName("thumbnail")
    public String thumbnailUrl;

    @SerializedName("mp4")
    public SteamAppDetailsVideosMp4 steamAppDetailsVideosMp4;

    @SerializedName("highlight")
    public boolean highlight;
}
