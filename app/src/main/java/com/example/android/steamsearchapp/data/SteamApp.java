package com.example.android.steamsearchapp.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// For product catalogue call, list for all apps

@Entity(tableName = "wishlistedApps")
public class SteamApp implements Serializable{
    @SerializedName("appid")
    @PrimaryKey
    @NonNull
    public String appid;

    @SerializedName("name")
    @NonNull
    public String name;
}
