package com.example.android.steamsearchapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.android.steamsearchapp.data.SteamApp;
import com.example.android.steamsearchapp.data.SteamAppDataContainer;
import com.example.android.steamsearchapp.data.SteamAppDetails;
import com.example.android.steamsearchapp.utils.SliderItem;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;

public class AppDetailActivity extends AppCompatActivity {
    public static final String EXTRA_GITHUB_REPO = "GitHubRepo";
    public static final String EXTRA_APP = "ExtraApp";
    public static final String APP_ID = "AppId";

    private static final String TAG = AppDetailActivity.class.getSimpleName();

    private Toast errorToast;

    private SteamApp source;

    private SteamAppDataContainer data;
    private SliderAdapter sliderAdapter;
    
    private SteamSearchViewModel viewModel;
    private favsViewModel favsViewModelVar;
    private SteamAppDataContainer favItem;

    private boolean isBookmarked;
    private String thumb;
    private String highlightVid;

    public String VIDEO_SAMPLE =
            "https://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Usual create stuff
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_detail);

        this.viewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(SteamSearchViewModel.class);

        //Should actually check if bookmarked
        this.isBookmarked = false;

        this.favsViewModelVar = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(favsViewModel.class);

        TextView gameNameTV = findViewById(R.id.game_name);
        TextView shortDescTV = findViewById(R.id.short_desc);
        TextView priceTV = findViewById(R.id.game_price);
        //TextView longDescTV = findViewById(R.id.long_desc);
        SliderView sliderView = findViewById(R.id.image_slider);
        Button videoTrailer = findViewById(R.id.trailer_button);

        TextView longDescTV = findViewById(R.id.long_desc);
        TextView devTV = findViewById(R.id.developers);
        TextView pubTV = findViewById(R.id.publishers);


        sliderAdapter = new SliderAdapter(this);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();

        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                Log.i("GGG", "onIndicatorClicked: " + sliderView.getCurrentPagePosition());
            }
        });

        videoTrailer.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(highlightVid), "video/mp4");
                startActivity(intent);
            }
        });

        //

        favItem = new SteamAppDataContainer();

        //Set up Listeners
        this.viewModel.getSingleProduct().observe(
                this,
                new Observer<SteamAppDataContainer>() {
                    @Override
                    public void onChanged(SteamAppDataContainer steamApp) {
                        Log.d(TAG, "Inside onchanged: " + steamApp);
                        data = steamApp;
                        //Set display data

                        if(data != null) {

                            if (data.is_free == true) {

                                priceTV.setText("Free");
                            }
                            else {

                                priceTV.setText(data.finalPrice);
                            }

                            gameNameTV.setText(data.name);

                            shortDescTV.setText(data.shortDescription);
                            

                            longDescTV.setText(data.longDescription);
                            String pubs = "Publisher: ";
                            String devs = "Developer: ";
                            for(int i = 0; i < data.developers.size(); i++){
                                devs += data.developers.get(i) + " ";
                            }
                            for(int i = 0; i < data.publishers.size(); i++){
                                pubs += data.publishers.get(i) + " ";
                            }
                            pubTV.setText(pubs);
                            devTV.setText(devs);


                            favItem.appid = data.appid;
                            Log.d(TAG, "FAVVVV " + favItem.appid);
                            favItem.name = data.name;
                            favItem.finalPrice = data.finalPrice;
                            favItem.type = data.type;
                            favItem.headerImageUrl = data.headerImageUrl;

                            //favItem.videoUrls.get(0);

                            for(int r = 0; r < data.videoUrls.size(); r++){
                                if(data.videoUrls.get(r).highlight){
                                    highlightVid = data.videoUrls.get(r).steamAppDetailsVideosMp4.url480;
                                }
                            }

                            //repoStarsTV.setText(String.valueOf(repo.stars));
                            //longDescTV.setText(data.longDescription);
                            thumb = data.headerImageUrl;
                            for(int i = 0; i < data.screenshotUrls.size(); i++) {
                                SliderItem sliderItem = new SliderItem();
                                //sliderItem.setDescription("Slider Item Added Manually");
                                sliderItem.setImageUrl(data.screenshotUrls.get(i).screenshotUrl);
                                sliderAdapter.addItem(sliderItem);
                            }
                        }
                    }
                }
        );
        Log.d(TAG, "IMAGE NAME" + thumb);
        //SliderItem sliderItem = new SliderItem();
        //sliderItem.setDescription("Slider Item Added Manually");
        //sliderItem.setImageUrl("https://cdn.vox-cdn.com/thumbor/0et_fVbWuUeQBn3_3dwRGmfIsTI=/0x0:1920x1080/1200x800/filters:focal(807x387:1113x693)/cdn.vox-cdn.com/uploads/chorus_image/image/62615013/steam_stats.0.jpg");
        //sliderAdapter.addItem(sliderItem);


        Button searchButton = (Button)findViewById(R.id.steam_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Steam Button Clicked");
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse("https://store.steampowered.com/app/" + data.appid));
                startActivity(browserIntent);
            }
        });


        //Get intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(APP_ID)) {
            //this.source = (SteamApp)intent.getSerializableExtra(EXTRA_APP);

            String passAppid = intent.getStringExtra(APP_ID);

            //if bookmarked, check database

            if(this.isBookmarked){
                    //Get from db
            } else {
                Log.d(TAG, "Got app with appid" + passAppid);
                viewModel.loadSingleAppDetails(passAppid);
            }

        }
        if (intent != null && intent.hasExtra(EXTRA_APP)) {
            Log.d(TAG, "Extra App Intent Received");
            String bookmarkStatus = intent.getStringExtra(EXTRA_APP);
            if(bookmarkStatus.equals("true")){
                this.isBookmarked = true;
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.repo_detail, menu);

        //if(this.isBookmarked){
          //  menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_action_bookmark_checked));
        //}
        if(this.isBookmarked) {
            MenuItem bookmarkButton = menu.getItem(0);

            bookmarkButton.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_action_bookmark_checked));
        }
        //Log.d(TAG, "FAVID: " + favItem.appid.toString());
//        this.favsViewModelVar.getFavById(this.favItem.appid.toString()).observe(
//                this,
//                new Observer<SteamAppDataContainer>() {
//                    @Override
//                    public void onChanged(SteamAppDataContainer steamAppDataContainer) {
//
//                        MenuItem menuItem = menu.findItem(R.id.action_bookmark);
//                        if (favItem == null) {
//                            isBookmarked = false;
//                            menuItem.setIcon(R.drawable.ic_action_bookmark_border);
//                        } else {
//                            isBookmarked = true;
//                            menuItem.setIcon(R.drawable.ic_action_bookmark_checked);
//                        }
//                    }
//                }
//        );

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_view_on_web:
                viewRepoOnWeb();
                return true;
          /*  case R.id.action_share:
                shareRepo();
                return true;*/
            case R.id.action_bookmark:
                toggleRepoBookmark(item);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void toggleRepoBookmark(MenuItem menuItem) {

        if (this.data != null) {
            this.isBookmarked = !this.isBookmarked;
            menuItem.setChecked(this.isBookmarked);
            if (this.isBookmarked) {
                menuItem.setIcon(R.drawable.ic_action_bookmark_checked);
                this.favsViewModelVar.insertFav(this.favItem);
            } else {
                menuItem.setIcon(R.drawable.ic_action_bookmark_border);
                this.favsViewModelVar.deleteFav(this.favItem);
            }
        }
    }

    private void viewRepoOnWeb() {
        Log.d(TAG, "Steam Button Clicked");
        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
        browserIntent.setData(Uri.parse("https://store.steampowered.com/app/" + data.appid));
        startActivity(browserIntent);
    }

    private void shareRepo() {
        /*
        if (this.repo != null) {
            String shareText = getString(
                    R.string.share_repo_text,
                    this.repo.fullName,
                    this.repo.htmlUrl
            );
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, shareText);
            intent.setType("text/plain");

            Intent chooserIntent = Intent.createChooser(intent, null);
            startActivity(chooserIntent);
        }*/
    }


}