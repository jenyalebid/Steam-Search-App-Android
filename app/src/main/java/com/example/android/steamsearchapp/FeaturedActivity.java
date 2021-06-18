package com.example.android.steamsearchapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.android.steamsearchapp.data.FeaturedViewModel;
import com.example.android.steamsearchapp.data.FeaturedWin;
import com.example.android.steamsearchapp.data.SteamApp;
import com.example.android.steamsearchapp.data.SteamAppDataContainer;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class FeaturedActivity extends AppCompatActivity implements FeaturedAdapter.OnFeaturedClickListener{

    private FeaturedViewModel featuredViewModel;
    private BottomNavigationView bottomNavigationView;
    private RecyclerView featuredGamesRV;
    private FeaturedAdapter featuredAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_featured);

        bottomNavigationView = findViewById(R.id.bottom_nav_bot);

        bottomNavigationView.setSelectedItemId(R.id.featured_i);

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.wish:
                        startActivity(new Intent(getApplicationContext(), SteamWishlistActivity.class));

                        overridePendingTransition(0, 0);
                        return;

                    case R.id.featured_i:
                        return;

                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        overridePendingTransition(0, 0);
                        return;
                }
            }
        });

        setTitle("Steam Featured");


        featuredGamesRV = findViewById(R.id.rv_featured);
        featuredGamesRV.setAdapter(featuredAdapter);


        featuredGamesRV.setLayoutManager(new LinearLayoutManager(this));
        featuredGamesRV.setHasFixedSize(true);

        featuredAdapter = new FeaturedAdapter(this);
        featuredGamesRV.setAdapter(featuredAdapter);


        this.featuredViewModel = new ViewModelProvider(this)
                .get(FeaturedViewModel.class);

//        this.featuredViewModel = new ViewModelProvider(
//                this,
//                new ViewModelProvider.AndroidViewModelFactory(getApplication())
//        ).get(FeaturedViewModel.class);

        this.featuredViewModel.getFeaturedDataResults().observe(
                this,
                new Observer<List<FeaturedWin>>() {
                    @Override
                    public void onChanged(List<FeaturedWin> featuredWins) {

                        featuredAdapter.updateFeatured(featuredWins);
                    }
                }
        );

        featuredViewModel.loadFeaturedData();
    }

    @Override
    public void onFeaturedClick(SteamApp app) {

        Intent intent = new Intent(this, AppDetailActivity.class);
        intent.putExtra(AppDetailActivity.APP_ID, app.appid);
        startActivity(intent);

    }

//    @Override
//    public void onFeaturedClick(SteamApp fav) {
//
//    }
}