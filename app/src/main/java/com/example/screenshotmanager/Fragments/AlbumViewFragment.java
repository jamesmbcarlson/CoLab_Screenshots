package com.example.screenshotmanager.Fragments;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.screenshotmanager.Album;
import com.example.screenshotmanager.FileAdapter;
import com.example.screenshotmanager.FileOpener;
import com.example.screenshotmanager.MainActivity;
import com.example.screenshotmanager.OnFileSelectedListener;
import com.example.screenshotmanager.R;
import com.example.screenshotmanager.ScreenshotImage;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class AlbumViewFragment extends InternalFragment implements OnFileSelectedListener {

    private RecyclerView recyclerView;
    private FileAdapter fileAdapter;
    private static List<File> fileList;
    private List<File> selectedFiles;
    private MainActivity mainActivity;


    private View view;
    private static Album album;

    public static AlbumViewFragment newInstance(Album a) {
        album = a;
        fileList = new ArrayList<>();
        AlbumViewFragment fragment = new AlbumViewFragment();
        Bundle args = new Bundle();
//        args.putSerializable();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_album_view, container, false);

        TextView pageTitle = view.findViewById(R.id.album_view_title);
        pageTitle.setText(album.GetName());

        mainActivity = (MainActivity) getActivity();

        displayFiles();

        return view;

    }

    private void displayFiles() {
        recyclerView = view.findViewById(R.id.recycler_album_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        for (ScreenshotImage image: album.GetImages()) {
            fileList.add(image.GetFile());
        }

        fileAdapter = new FileAdapter(getContext(), fileList, this, this);
        recyclerView.setAdapter(fileAdapter);
    }

    @Override
    public void onFileClicked(File file) {

    }

    @Override
    public void onFileLongClicked(File file, int position) {

    }

}
