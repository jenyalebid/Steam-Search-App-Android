package com.example.android.steamsearchapp.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SteamAppList {
    @SerializedName("apps")
    ArrayList<SteamApp> steamApps;
}
