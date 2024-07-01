package com.example.myapplication;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class FileViewHolder extends RecyclerView.ViewHolder {
    public TextView tv_name, tv_size;
    public CardView container;
    public ImageView imgFile;
    public FileViewHolder(@NonNull View itemView) {
        super(itemView);

        tv_name = itemView.findViewById(R.id.tv_file_name);
        tv_size = itemView.findViewById(R.id.tv_file_size);
        container = itemView.findViewById(R.id.container);
        imgFile = itemView.findViewById(R.id.img_fileType);
    }
}
