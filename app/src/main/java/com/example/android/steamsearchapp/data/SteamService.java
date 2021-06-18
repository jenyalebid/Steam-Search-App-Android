package com.example.android.steamsearchapp.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SteamService {
    @GET("ISteamApps/GetAppList/v0002/")
    Call<SteamSearchResults> getSteamProductCatalogue();

    //    Call<SteamSearchResults> getSteamProductCatalogue(@Query("key") String key,
    //                                                      @Query("format") String format);

    @GET("api/appdetails")
    Call<SteamAppDetails> getSteamProductDetail(@Query("appids") String appid);
}
