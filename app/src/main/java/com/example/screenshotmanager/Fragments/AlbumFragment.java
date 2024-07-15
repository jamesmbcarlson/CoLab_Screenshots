package com.example.screenshotmanager.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.screenshotmanager.Album;
import com.example.screenshotmanager.AlbumAdapter;
import com.example.screenshotmanager.FileAdapter;
import com.example.screenshotmanager.MainActivity;
import com.example.screenshotmanager.OnAlbumSelectedListener;
import com.example.screenshotmanager.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class AlbumFragment extends Fragment implements OnAlbumSelectedListener {
    View view;
    private RecyclerView recyclerView;
    private AlbumAdapter albumAdapter;
    private List<Album> albumsList;
    private MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_albums, container, false);
        mainActivity = (MainActivity) getActivity();

        ImageView btnAddAlbum = view.findViewById(R.id.btn_album_add);
        btnAddAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.AddNewAlbum();
            }
        });

        displayAlbums();

        return view;

    }

    private void displayAlbums() {
        recyclerView = view.findViewById(R.id.albums_container);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        albumsList = mainActivity.getAlbumCollection();
        albumAdapter = new AlbumAdapter(getContext(), albumsList, this, this);
        recyclerView.setAdapter(albumAdapter);
    }

    @Override
    public void OnAlbumClicked(Album album) {

    }
}
