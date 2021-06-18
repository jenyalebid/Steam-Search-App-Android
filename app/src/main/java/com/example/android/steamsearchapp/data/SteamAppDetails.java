package com.example.android.steamsearchapp.data;

import android.util.Log;

import com.example.android.steamsearchapp.data.details.SteamAppDetailsGenre;
import com.example.android.steamsearchapp.data.details.SteamAppDetailsScreenshot;
import com.example.android.steamsearchapp.data.details.SteamAppDetailsVideos;
import com.example.android.steamsearchapp.data.details.SteamAppDetailsVideosMp4;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SteamAppDetails {
    private static final String TAG = SteamAppDetails.class.getSimpleName();

    @SerializedName("data")
    public SteamAppDataContainer data;


    public SteamAppDetails() {
        this.data = new SteamAppDataContainer();
    }

    public SteamAppDetails(SteamAppDataContainer data) {
        this.data = data;
    }


    public static class JsonDeserializer implements com.google.gson.JsonDeserializer<SteamAppDetails> {
        @Override
        public SteamAppDetails deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            //Get Object
            JsonObject listObj = json.getAsJsonObject();
            Log.d(TAG, "List Obj: " + listObj);

            //Get object keyset
            Set<String> keySet = listObj.keySet();
            ArrayList<String> keyList = new ArrayList<String>(keySet);
            String appid;

            //if object if full
            if(keySet.size() > 0){
                appid = keyList.get(0);

                //set up objects
                JsonObject app = listObj.getAsJsonObject(appid);

                //Create container
                SteamAppDataContainer container = new SteamAppDataContainer();

                //Set Data Variables
                JsonObject data = app.getAsJsonObject("data");
                if(data != null) {
                    container.name = data.getAsJsonPrimitive("name").getAsString();
                    container.type = data.getAsJsonPrimitive("type").getAsString();
                    container.appid = data.getAsJsonPrimitive("steam_appid").getAsInt();
                    container.is_free = data.getAsJsonPrimitive("is_free").getAsBoolean();
                    container.longDescription = data.getAsJsonPrimitive("detailed_description").getAsString();
                    container.shortDescription = data.getAsJsonPrimitive("short_description").getAsString();
                    container.aboutTheGame = data.getAsJsonPrimitive("about_the_game").getAsString();
                    container.languages = data.getAsJsonPrimitive("supported_languages").getAsString();
                    if(!data.get("header_image").isJsonNull()) {
                        container.headerImageUrl = data.getAsJsonPrimitive("header_image").getAsString();
                    }
                    if(!data.get("website").isJsonNull()){
                        Log.d(TAG, "Website: " + data.get("website"));
                        container.websiteUrl = data.getAsJsonPrimitive("website").getAsString();
                    }
                }

                //Set price
                JsonObject price = data.getAsJsonObject("price_overview");
                if(!(price == null) && !price.isJsonNull()) {
                    container.finalPrice = price.getAsJsonPrimitive("final_formatted").getAsString();
                }

                //Set Video Vars
                ArrayList<SteamAppDetailsVideos> videoUrls = new ArrayList<SteamAppDetailsVideos>();
                JsonArray movieArray = data.getAsJsonArray("movies");
                if(movieArray.size() > 0){
                    JsonObject movie = movieArray.get(0).getAsJsonObject();
                    SteamAppDetailsVideos vidInfo = new SteamAppDetailsVideos();
                    vidInfo.id = movie.getAsJsonPrimitive("id").getAsInt();
                    vidInfo.videoName = movie.getAsJsonPrimitive("name").getAsString();
                    vidInfo.thumbnailUrl = movie.getAsJsonPrimitive("thumbnail").getAsString();
                    vidInfo.highlight = movie.getAsJsonPrimitive("highlight").getAsBoolean();

                    SteamAppDetailsVideosMp4 mp4details = new SteamAppDetailsVideosMp4();
                    mp4details.max = movie.getAsJsonObject("mp4").getAsJsonPrimitive("max").getAsString();
                    mp4details.url480 = movie.getAsJsonObject("mp4").getAsJsonPrimitive("480").getAsString();
                    vidInfo.steamAppDetailsVideosMp4 = mp4details;



                    videoUrls.add(vidInfo);
                }
                container.videoUrls = videoUrls;

                //Set Screenshots
                JsonArray screenshots = data.getAsJsonArray("screenshots");
                ArrayList<SteamAppDetailsScreenshot> ssList = new ArrayList<SteamAppDetailsScreenshot>();
                for(int i = 0; i < screenshots.size(); i++) {
                    JsonObject singleThumbnail = screenshots.get(i).getAsJsonObject();
                    SteamAppDetailsScreenshot ss = new SteamAppDetailsScreenshot();
                    ss.id = singleThumbnail.getAsJsonPrimitive("id").getAsInt();
                    ss.screenshotUrl = singleThumbnail.getAsJsonPrimitive("path_full").getAsString();
                    ss.thumbnailUrl = singleThumbnail.getAsJsonPrimitive("path_thumbnail").getAsString();
                    ssList.add(ss);
                }
                container.screenshotUrls = ssList;

                //Set Genres

                JsonArray genres = data.getAsJsonArray("genres");
                ArrayList<SteamAppDetailsGenre> gList = new ArrayList<SteamAppDetailsGenre>();
                for(int i = 0; i < genres.size(); i++) {
                    JsonObject singleGenre = genres.get(i).getAsJsonObject();
                    SteamAppDetailsGenre g = new SteamAppDetailsGenre();
                    g.id = singleGenre.getAsJsonPrimitive("id").getAsInt();
                    g.description = singleGenre.getAsJsonPrimitive("description").getAsString();
                    gList.add(g);
                }
                container.appGenres = gList;

                //Set devs and pubs
                JsonArray devs = data.getAsJsonArray("developers");
                ArrayList<String> developers = new ArrayList<String>();
                for(int i = 0; i < devs.size(); i++) {
                    developers.add(devs.get(i).getAsJsonPrimitive().getAsString());
                }

                JsonArray pubs = data.getAsJsonArray("publishers");
                ArrayList<String> publishers = new ArrayList<String>();
                for(int i = 0; i < pubs.size(); i++) {
                    publishers.add(pubs.get(i).getAsJsonPrimitive().getAsString());
                }

                container.developers = developers;
                container.publishers = publishers;



                return new SteamAppDetails(container);

            }
            return new SteamAppDetails();
        }
    }

}
