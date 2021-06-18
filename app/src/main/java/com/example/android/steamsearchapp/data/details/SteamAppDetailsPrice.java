package com.example.android.steamsearchapp.data.details;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SteamAppDetailsPrice implements Serializable {

    @SerializedName("final_formatted")
    private String finalPrice;
}
