package com.example.android.steamsearchapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.steamsearchapp.data.LoadingStatus;
import com.example.android.steamsearchapp.data.SteamApp;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

// https://api.steampowered.com/ISteamApps/GetAppList/v0002/?key=STEAMKEY&format=json

public class MainActivity extends AppCompatActivity
        implements SteamSearchAdapter.SearchResultClickListener,
        SharedPreferences.OnSharedPreferenceChangeListener,
        NavigationView.OnNavigationItemSelectedListener {
    //Main activity to replace other one
    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView searchResultsRV;
    private EditText searchBoxET;
    private ProgressBar loadingIndicatorPB;
    private TextView errorMessageTV;
    private DrawerLayout drawerLayout;

    private SteamSearchAdapter steamSearchAdapter;
    private SteamSearchViewModel steamSearchViewModel;
    private List<SteamApp> steamLoadedCatalogue;

    private SharedPreferences sharedPreferences;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_nav_bot);

        bottomNavigationView.setSelectedItemId(R.id.search);

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.wish:
                        startActivity(new Intent(getApplicationContext(), SteamWishlistActivity.class));

                        overridePendingTransition(0, 0);
                        return;

                    case R.id.featured_i:
                        startActivity(new Intent(getApplicationContext(), FeaturedActivity.class));

                        overridePendingTransition(0, 0);
                        return;

                    case R.id.search:
                        return;
                }
            }
        });

        setTitle("Steam Search");

        this.searchBoxET = findViewById(R.id.et_search_box);
        this.searchResultsRV = findViewById(R.id.rv_search_results);
        this.loadingIndicatorPB = findViewById(R.id.pb_loading_indicator);
        this.errorMessageTV = findViewById(R.id.tv_error_message);
        //this.drawerLayout = findViewById(R.id.drawer_layout);

        this.searchResultsRV.setLayoutManager(new LinearLayoutManager(this));
        this.searchResultsRV.setHasFixedSize(true);

        this.steamSearchAdapter = new SteamSearchAdapter(this);
        this.searchResultsRV.setAdapter(this.steamSearchAdapter);

        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        this.steamSearchViewModel = new ViewModelProvider(this).get(SteamSearchViewModel.class);

        this.steamSearchViewModel.getSearchResults().observe(
                this,
                new Observer<List<SteamApp>>() {
                    @Override
                    public void onChanged(List<SteamApp> steamApps) {
                        steamLoadedCatalogue = steamApps;
                        steamSearchAdapter.updateSearchResults(steamApps);
                    }
                }
        );

        this.steamSearchViewModel.getLoadingStatus().observe(
                this,
                new Observer<LoadingStatus>() {
                    @Override
                    public void onChanged(LoadingStatus loadingStatus) {
                        Log.d(TAG, "errormessage: " + errorMessageTV);
                        if (loadingStatus == LoadingStatus.LOADING) {
                            loadingIndicatorPB.setVisibility(View.VISIBLE);
                        } else if (loadingStatus == LoadingStatus.SUCCESS) {
                            loadingIndicatorPB.setVisibility(View.INVISIBLE);
                            searchResultsRV.setVisibility(View.VISIBLE);
                            errorMessageTV.setVisibility(View.INVISIBLE);
                        } else {
                            loadingIndicatorPB.setVisibility(View.INVISIBLE);
                            searchResultsRV.setVisibility(View.INVISIBLE);
                            errorMessageTV.setVisibility(View.VISIBLE);
                        }
                    }
                }
        );

//        NavigationView navigationView = findViewById(R.id.nv_nav_drawer);
//        navigationView.setNavigationItemSelectedListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        Button searchButton = (Button)findViewById(R.id.btn_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchQuery = searchBoxET.getText().toString();
                steamSearchViewModel.loadSearchResults(searchQuery);
            }
        });
    }

    @Override
    protected void onDestroy() {
        this.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
        super.onDestroy();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        this.drawerLayout.closeDrawers();
        switch (item.getItemId()) {
            case R.id.nav_app_wishlist:
                Intent intentWish = new Intent(this, SteamWishlistActivity.class);
                startActivity(intentWish);
                return true;
            case R.id.nav_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (TextUtils.equals(key, getString(R.string.pref_in_name_key))
                || TextUtils.equals(key, getString(R.string.pref_in_description_key))
                || TextUtils.equals(key, getString(R.string.pref_in_readme_key))) {
            Log.d(TAG, "shared preference changed, key: " + key + ", value: " + sharedPreferences.getBoolean(key, false));
        } else {
            Log.d(TAG, "shared preference changed, key: " + key + ", value: " + sharedPreferences.getString(key, ""));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case android.R.id.home:
                this.drawerLayout.openDrawer(GravityCompat.START);


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSearchResultClicked(SteamApp app) {
        Intent intent = new Intent(this, AppDetailActivity.class);
        intent.putExtra(AppDetailActivity.APP_ID, app.appid);
        //change repoDetailActivity for displaying other ingo
        startActivity(intent);
    }
}