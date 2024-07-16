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
import androidx.recyclerview.widget.RecyclerView;

import com.example.screenshotmanager.Album;
import com.example.screenshotmanager.AlbumAdapter;
import com.example.screenshotmanager.MainActivity;
import com.example.screenshotmanager.OnAlbumSelectedListener;
import com.example.screenshotmanager.R;

import java.util.List;


public class HomeFragment extends Fragment implements OnAlbumSelectedListener {
    View view;
    private RecyclerView recentView;
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

        return view;

    }



    private void displayAlbums() {
        recentView = view.findViewById(R.id.home_recent);
        recentView.setHasFixedSize(true);
        recentView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        albumsList = mainActivity.getAlbumCollection();
        albumAdapter = new AlbumAdapter(getContext(), albumsList, this, this);
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
