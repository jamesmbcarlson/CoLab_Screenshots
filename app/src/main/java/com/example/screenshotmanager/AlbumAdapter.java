package com.example.screenshotmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.screenshotmanager.Fragments.AlbumFragment;
import com.example.screenshotmanager.Fragments.InternalFragment;

import java.io.File;
import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumViewHolder> {
    private final Context context;
    private final List<Album> albums;
    private final OnAlbumSelectedListener listener;
    private final Fragment fragment;

    public AlbumAdapter(Context context, List<Album> albums, OnAlbumSelectedListener listener, Fragment fragment) {
        this.context = context;
        this.albums = albums;
        this.listener = listener;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AlbumViewHolder(LayoutInflater.from(context).inflate(R.layout.albums_menu_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {

        holder.tv_name.setText(albums.get(position).GetName());
        holder.tv_name.setSelected(false);

        if(albums.get(position).GetCount() > 0) {

            holder.tv_size.setText(String.valueOf(albums.get(position).GetCount()));

            holder.imgThumbnail.setLayoutParams(new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT
            ));

            holder.imgThumbnail.setImageURI(null);

            Glide.with(context)
                    .load(albums.get(position).GetImages().get(0).GetFile())
                    .error(R.drawable.ic_error)
                    .transform(new CenterCrop())
                    .into(holder.imgThumbnail);
        }
        else {
            holder.tv_size.setText(R.string.album_size_empty);
        }

        holder.tv_size.setSelected(false);

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                fragment.ToggleFileSelected(file.get(position));
//                if(fragment.GetFileIsSelected(file.get(position))){
//                    holder.imgCheckbox.setImageResource(R.drawable.ic_checkbox_checked);
//                }
//                else {
//                    holder.imgCheckbox.setImageResource(R.drawable.ic_checkbox);
//                }
//                listener.onFileClicked(albums.get(position));
            }

        });
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }
}
