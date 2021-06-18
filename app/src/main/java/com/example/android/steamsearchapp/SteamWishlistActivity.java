package com.example.android.steamsearchapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.steamsearchapp.data.SteamApp;
import com.example.android.steamsearchapp.data.SteamAppDataContainer;
import com.example.android.steamsearchapp.data.SteamAppDetails;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class SteamWishlistActivity extends AppCompatActivity
        implements favsAdapter.OnFavClickListener{

    private favsViewModel favsViewModel;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarked_repos);

        bottomNavigationView = findViewById(R.id.bottom_nav_bot);

        bottomNavigationView.setSelectedItemId(R.id.wish);

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.wish:
                        return;

                    case R.id.featured_i:
                        startActivity(new Intent(getApplicationContext(), FeaturedActivity.class));

                        overridePendingTransition(0, 0);
                        return;

                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        overridePendingTransition(0, 0);
                        return;
                }
            }
        });

        setTitle("Your Steam WishList");

        RecyclerView wishlistedAppsRV = findViewById(R.id.rv_bookmarked_repos);
        wishlistedAppsRV.setLayoutManager(new LinearLayoutManager(this));
        wishlistedAppsRV.setHasFixedSize(true);

        favsAdapter adapter = new favsAdapter(this);
        wishlistedAppsRV.setAdapter(adapter);

        this.favsViewModel = new ViewModelProvider(
                this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())
        ).get(favsViewModel.class);

        this.favsViewModel.getAllFavs().observe(
                this,
                new Observer<List<SteamAppDataContainer>>() {
                    @Override
                    public void onChanged(List<SteamAppDataContainer> steamApps) {
                        adapter.updateFavs(steamApps);
                    }
                }
        );
    }

    @Override
    public void onFavClick(SteamApp app) {
        Intent intent = new Intent(this, AppDetailActivity.class);
        intent.putExtra(AppDetailActivity.APP_ID, app.appid);
        intent.putExtra(AppDetailActivity.EXTRA_APP, "true");
        startActivity(intent);
    }

}
