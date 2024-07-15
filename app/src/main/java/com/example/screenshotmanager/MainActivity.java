package com.example.screenshotmanager;

import static com.example.screenshotmanager.R.*;

import com.example.screenshotmanager.Fragments.AlbumFragment;
import com.example.screenshotmanager.Fragments.SettingsFragment;
import com.example.screenshotmanager.R;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

//    private DrawerLayout drawerLayout;
    private List<Album> albumCollection = new ArrayList<>();
    private List<File> filesToAdd = new ArrayList<>();

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
//        drawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView navigationView = findViewById(R.id.bottomNavigationView);
        navigationView.setOnNavigationItemSelectedListener(this);

//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.closer_drawer);
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(id.fragment_container, new HomeFragment()).commit();
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
//        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

//    @Override
//    public void onBackPressed() {
//        getFragmentManager().popBackStackImmediate();
//        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
//            drawerLayout.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//
//    }

    public void onTextItemClick(View view) {
        TextView textView = (TextView) view;
        String text = textView.getText().toString();
        Toast.makeText(this, "To-Be Implemented: " + text, Toast.LENGTH_SHORT).show();
    }

    private void createAppStorage() {

    }

    public void AddNewAlbum() {
        final Dialog newAlbumDialog = new Dialog(this);
        newAlbumDialog.setContentView(layout.dialog_album_new);
        newAlbumDialog.show();

        EditText newAlbumTitle = newAlbumDialog.findViewById(id.btn_new_album_text);
        TextView continueButton = newAlbumDialog.findViewById(id.btn_new_album_continue);
        TextView cancelButton = newAlbumDialog.findViewById(id.btn_new_album_cancel);

        newAlbumTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (newAlbumTitle.getText().toString().isEmpty()) {
                    continueButton.setTextColor(getResources().getColor(color.app_gray));
                    continueButton.setBackground(getResources().getDrawable(drawable.menu_button_primary_rounded_disabled));
                }
                else {
                    continueButton.setTextColor(getResources().getColor(color.app_black));
                    continueButton.setBackground(getResources().getDrawable(drawable.menu_button_primary_rounded));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // DEBUG
                if (!newAlbumTitle.getText().toString().isEmpty()) {
                    for (Album album: albumCollection) {
                        if (album.GetName().equals(newAlbumTitle.getText().toString())) {
                            Toast.makeText(getApplicationContext(), "Album Name Already in Use", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    Toast.makeText(getApplicationContext(), filesToAdd.size() + " Images Added to Album: " + newAlbumTitle.getText().toString(), Toast.LENGTH_SHORT).show();
                    Album targetAlbum = new Album(newAlbumTitle.getText().toString());
                    albumCollection.add(targetAlbum);

                    targetAlbum.AddToAlbum(filesToAdd);
                    setFilesToAdd(new ArrayList<>());
                    newAlbumDialog.dismiss();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Album Title Cannot be Blank", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newAlbumDialog.cancel();
            }
        });
    }

    public void addNewPhotos(List<File> files) {



        final Dialog newAlbumDialog = new Dialog(this);
        newAlbumDialog.setContentView(layout.dialog_album_new);
        newAlbumDialog.show();

        TextView continueButton = newAlbumDialog.findViewById(id.btn_new_album_continue);
        EditText newAlbumTitle = newAlbumDialog.findViewById(id.btn_new_album_text);

        newAlbumTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (newAlbumTitle.getText().toString().isEmpty()) {
                    continueButton.setTextColor(getResources().getColor(color.app_gray));
                    continueButton.setBackground(getResources().getDrawable(drawable.menu_button_primary_rounded_disabled));
                }
                else {
                    continueButton.setTextColor(getResources().getColor(color.app_black));
                    continueButton.setBackground(getResources().getDrawable(drawable.menu_button_primary_rounded));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });



        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // DEBUG
                if (!newAlbumTitle.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), files.size() + " Images Processed", Toast.LENGTH_SHORT).show();
                    Album targetAlbum = new Album("Test");
                    albumCollection.add(targetAlbum);

                    targetAlbum.AddToAlbum(files);
                }
            }
        });




//        optionDialog.setTitle("Select Options");
//        ListView options = (ListView) optionDialog.findViewById(R.id.List);
//        CustomAdapter customAdapter = new CustomAdapter();
//        options.setAdapter(customAdapter);


//
//        options.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String selectedItem = adapterView.getItemAtPosition(i).toString();
//
//                switch(selectedItem){
//                    case "Details":
//                        AlertDialog.Builder detailDialog = new AlertDialog.Builder(getContext());
//                        detailDialog.setTitle("Details:");
//                        final TextView details = new TextView(getContext());
//                        detailDialog.setView(details);
//                        Date lastModified = new Date(file.lastModified());
//                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//                        String formattedDate = formatter.format(lastModified);
//
//                        details.setText("Filename: " + file.getName() + "\n" +
//                                "Size: " + Formatter.formatShortFileSize(getContext(), file.length()) + "\n" +
//                                "Path: " + file.getAbsolutePath() + "\n" +
//                                "Last Modified: " + formattedDate);
//
//                        detailDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                optionDialog.cancel();
//                            }
//                        });
//
//                        AlertDialog alert_dialog_details = detailDialog.create();
//                        alert_dialog_details.show();
//                        break;
//
//                    case "Rename":
//                        AlertDialog.Builder renameDialog = new AlertDialog.Builder(getContext());
//                        renameDialog.setTitle("Rename File:");
//                        final EditText name = new EditText(getContext());
//                        renameDialog.setView(name);
//
//                        renameDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                String new_name = name.getEditableText().toString();
//                                String extension = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf("."));
//                                File current = new File(file.getAbsolutePath());
//                                File destination = new File(file.getAbsolutePath().replace(file.getName(), new_name) + extension);
//                                if (current.renameTo(destination)){
//                                    fileList.set(position, destination);
//                                    fileAdapter.notifyItemChanged(position);
//                                    Toast.makeText(getContext(), "File has been renamed.", Toast.LENGTH_LONG).show();
//                                }
//                                else {
//                                    Toast.makeText(getContext(), "ERROR: Could not rename file.", Toast.LENGTH_LONG).show();
//                                }
//                            }
//                        });
//
//                        renameDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                optionDialog.cancel();
//                            }
//                        });
//                        AlertDialog alert_dialog_rename = renameDialog.create();
//                        alert_dialog_rename.show();
//
//                        break;
//
//                    case "Delete":
//                        AlertDialog.Builder delete_dialog = new AlertDialog.Builder(getContext());
//                        delete_dialog.setTitle("Are you sure you want to delete " + file.getName() + "?");
//                        delete_dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                file.delete();
//                                fileList.remove(position);
//                                fileAdapter.notifyDataSetChanged();
//                                Toast.makeText(getContext(), "File Deleted", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//
//                        delete_dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                optionDialog.cancel();
//                            }
//                        });
//
//                        AlertDialog alert_dialog_delete = delete_dialog.create();
//                        alert_dialog_delete.show();
//                        break;
//
//                    case "Share":
//                        String filename = file.getName();
//                        Intent share = new Intent();
//                        share.setAction(Intent.ACTION_SEND);
//                        share.setType("image/*");
//                        share.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(getContext(), getContext().getPackageName() + ".provider", file));
//                        startActivity(Intent.createChooser(share, "Share " + filename));
//                }
//            }
//        });
    }

    public List<Album> getAlbumCollection() {
        return albumCollection;
    }

    public List<File> getFilesToAdd() {
        return filesToAdd;
    }

    public void setFilesToAdd(List<File> filesToAdd) {
        this.filesToAdd = filesToAdd;
    }
}