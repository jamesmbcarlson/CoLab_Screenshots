package com.example.screenshotmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.screenshotmanager.Fragments.InternalFragment;

import java.io.File;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<FileViewHolder> {
    private final Context context;
    private final List<File> file;


    public ImageAdapter(Context context, List<File> file) {
        this.context = context;
        this.file = file;

    }

    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FileViewHolder(LayoutInflater.from(context).inflate(R.layout.img_lg_container, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewHolder holder, int position) {

        Glide.with(context)
                .load(file.get(position))
                .placeholder(R.drawable.ic_img)
                .error(R.drawable.ic_files_white)
                .transform(new CenterCrop())
                .into(holder.imgFile);



//        holder.container.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                fragment.ToggleFileSelected(file.get(position));
//                if(fragment.GetFileIsSelected(file.get(position))){
//                    holder.imgCheckbox.setImageResource(R.drawable.ic_checkbox_checked);
//                    holder.imgCheckbox.setVisibility(View.VISIBLE);
//                }
//                else {
//                    holder.imgCheckbox.setImageResource(R.drawable.ic_checkbox);
//                    holder.imgCheckbox.setVisibility(View.INVISIBLE);
//                }
//                listener.onFileClicked(file.get(position));
//            }
//
//        });
//
//        holder.container.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                listener.onFileLongClicked(file.get(position), position);
//                return true;
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return file.size();
    }
}
