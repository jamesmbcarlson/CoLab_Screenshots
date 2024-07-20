package com.example.screenshotmanager.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.screenshotmanager.Album;
import com.example.screenshotmanager.AlbumAdapter;
import com.example.screenshotmanager.FileAdapter;
import com.example.screenshotmanager.ImageAdapter;
import com.example.screenshotmanager.MainActivity;
import com.example.screenshotmanager.OnAlbumSelectedListener;
import com.example.screenshotmanager.OnFileSelectedListener;
import com.example.screenshotmanager.R;
import com.example.screenshotmanager.ScreenshotImage;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class HomeFragment extends Fragment implements OnAlbumSelectedListener {
    View view;
    private RecyclerView recentView;
    private RecyclerView rememberView;
    private List<Album> albumsList;
    private AlbumAdapter albumAdapter;
    private MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        mainActivity = (MainActivity) getActivity();

        TextView btnSeeAll = view.findViewById(R.id.btn_see_all_albums);
        btnSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlbumFragment albumsFragment = new AlbumFragment();
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, albumsFragment).addToBackStack(null).commit();
            }
        });

        displayAlbums();
        displayRemember();

        return view;

    }

    private void displayRemember() {
        rememberView = view.findViewById(R.id.home_remember);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);
        rememberView.setLayoutManager(layoutManager);

        if (!albumsList.isEmpty()) {
            List<ScreenshotImage> imageList = new ArrayList<>(albumsList.get(0).GetImages());
            List<File> fileList = new ArrayList<File>();


            for (ScreenshotImage image : imageList) {
                fileList.add(image.GetFile());
            }
            ImageAdapter imageAdapter;
            imageAdapter = new ImageAdapter(getContext(), fileList);
            rememberView.setAdapter(imageAdapter);
        }
    }

    private void displayAlbums() {
        recentView = view.findViewById(R.id.home_recent);
        recentView.setHasFixedSize(true);
        recentView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        albumsList = mainActivity.getAlbumCollection();
        List<Album> displayList = new ArrayList<>(albumsList.subList(0, albumsList.size()));
        Collections.reverse(displayList);
        albumAdapter = new AlbumAdapter(getContext(), displayList, this, this);
        recentView.setAdapter(albumAdapter);
    }

    @Override
    public void OnAlbumClicked(Album album) {
        AlbumViewFragment albumViewFragment = AlbumViewFragment.newInstance(album);

        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, albumViewFragment)
                .addToBackStack(null)
                .commit();
    }
}
