package com.example.android.steamsearchapp.data.details;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SteamAppDetailsGenre implements Serializable {
    @SerializedName("id")
    public int id;

    @SerializedName("description")
    public String description;
}
