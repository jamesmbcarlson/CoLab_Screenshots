package com.example.screenshotmanager;

import static com.example.screenshotmanager.R.*;

import com.example.screenshotmanager.Fragments.AlbumFragment;
import com.example.screenshotmanager.Fragments.SettingsFragment;
import com.example.screenshotmanager.R;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.screenshotmanager.Fragments.CardFragment;
import com.example.screenshotmanager.Fragments.HomeFragment;
import com.example.screenshotmanager.Fragments.InternalFragment;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private HashMap<String, Object> imageCollection = new HashMap<String, Object>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); // disable dark mode... temporarily for testing purposes of course!
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        drawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.closer_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(id.fragment_container, new HomeFragment()).commit();
        navigationView.setCheckedItem(id.nav_home);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==id.nav_home) {
            HomeFragment homeFragment = new HomeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).addToBackStack(null).commit();
        } else if (item.getItemId()== id.nav_roll) {
            InternalFragment rollFragment = new InternalFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, rollFragment).addToBackStack(null).commit();
        } else if (item.getItemId()== id.nav_albums) {
            AlbumFragment albumsFragment = new AlbumFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, albumsFragment).addToBackStack(null).commit();
        } else if (item.getItemId()== id.nav_settings) {
            SettingsFragment settingsFragment = new SettingsFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, settingsFragment).addToBackStack(null).commit();
        }

//        switch (item.getItemId()){
//            case R.id.nav_home:
//                HomeFragment homeFragment = new HomeFragment();
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).addToBackStack(null).commit();
//                break;
//            case R.id.nav_internal:
//                InternalFragment internalFragment = new InternalFragment();
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, internalFragment).addToBackStack(null).commit();
//                break;
//            case R.id.nav_card:
//                CardFragment cardFragment = new CardFragment();
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, cardFragment).addToBackStack(null).commit();
//                break;
//            case R.id.nav_about:
//                Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
//                break;
//        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        getFragmentManager().popBackStackImmediate();
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    public void onTextItemClick(View view) {
        TextView textView = (TextView) view;
        String text = textView.getText().toString();
        Toast.makeText(this, "To-Be Implemented: " + text, Toast.LENGTH_SHORT).show();
    }

    private void createAppStorage() {

    }

    public void addNewPhotos(List<File> files) {


        Toast.makeText(this,  files.size() + " Images Processed", Toast.LENGTH_SHORT).show();


    }

}