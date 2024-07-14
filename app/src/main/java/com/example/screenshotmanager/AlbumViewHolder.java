package com.example.screenshotmanager;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class AlbumViewHolder extends RecyclerView.ViewHolder {
    public TextView tv_name, tv_size;
    public CardView container;
    public ImageView imgThumbnail;
    public AlbumViewHolder(@NonNull View itemView) {
        super(itemView);

        tv_name = itemView.findViewById(R.id.albums_menu_album_name);
        tv_size = itemView.findViewById(R.id.albums_menu_album_size);
        container = itemView.findViewById(R.id.album_container);
        imgThumbnail = itemView.findViewById(R.id.album_menu_album_preview);

    }
}
