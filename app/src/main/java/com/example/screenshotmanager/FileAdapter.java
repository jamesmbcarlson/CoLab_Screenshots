package com.example.screenshotmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.screenshotmanager.OnFileSelectedListener;

import java.io.File;
import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileViewHolder> {
    private final Context context;
    private final List<File> file;
    private final OnFileSelectedListener listener;

    public FileAdapter(Context context, List<File> file, OnFileSelectedListener listener) {
        this.context = context;
        this.file = file;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FileViewHolder(LayoutInflater.from(context).inflate(R.layout.file_container, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewHolder holder, int position) {
        int items = 0;
        if(file.get(position).isDirectory()){
            File[] files = file.get(position).listFiles();
            for(File single_file : files) {
                if(!single_file.isHidden()){
                    items += 1;
                }
            }
//            holder.tv_size.setText(items + " Files");
        }
//        else {
//            holder.tv_size.setText(Formatter.formatShortFileSize(context, file.get(position).length()));
//        }

        if(file.get(position).isDirectory()) {
//            if(position == 0 && file.get(position).getPath().contains(file.get(position).getName())){
//                holder.imgFile.setImageResource((R.drawable.ic_folder_up));
//            }
//            else {
            holder.imgFile.setImageResource((R.drawable.ic_folder_white));
//            }
            holder.tv_name.setText(file.get(position).getName());
            holder.tv_name.setSelected(true);
        }
        else if (file.get(position).getName().toLowerCase().endsWith(".jpeg") ||
                file.get(position).getName().toLowerCase().endsWith(".jpg") ||
                file.get(position).getName().toLowerCase().endsWith(".png"))
        {
            holder.tv_name.setText("");
            holder.tv_name.setSelected(false);

            Glide.with(context)
                    .load(file.get(position))
                    .placeholder(R.drawable.ic_img)
                    .error(R.drawable.ic_files_white)
                    .transform(new CenterCrop())
                    .into(holder.imgFile);
        }
        else {
            holder.tv_name.setText(file.get(position).getName());
            holder.tv_name.setSelected(true);

            holder.imgFile.setImageResource((R.drawable.ic_files_white));
        }

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onFileClicked(file.get(position));
            }
        });

        holder.container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onFileLongClicked(file.get(position), position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return file.size();
    }
}
