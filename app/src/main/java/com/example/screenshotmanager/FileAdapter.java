package com.example.myapplication;

import android.content.Context;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileViewHolder> {
    private Context context;
    private List<File> file;

    public FileAdapter(List<File> file, Context context) {
        this.file = file;
        this.context = context;
    }

    public FileAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FileViewHolder(LayoutInflater.from(context).inflate(R.layout.file_container, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewHolder holder, int position) {
        holder.tv_name.setText(file.get(position).getName());
        holder.tv_name.setSelected(true);
        int items = 0;
        if(file.get(position).isDirectory()){
            File[] files = file.get(position).listFiles();
            for(File single_file : files) {
                if(!single_file.isHidden()){
                    items += 1;
                }
            }
            holder.tv_size.setText(String.valueOf(items) + " Files");
        }
        else {
            holder.tv_size.setText(Formatter.formatShortFileSize(context, file.get(position).length()));
        }

        if (file.get(position).getName().toLowerCase().endsWith(".jpeg") ||
                file.get(position).getName().toLowerCase().endsWith(".jpg") ||
                file.get(position).getName().toLowerCase().endsWith(".png"))
        {
            holder.imgFile.setImageResource(R.drawable.ic_img);
        }
        else {
            holder.imgFile.setImageResource((R.drawable.ic_folder_white));
        }
    }

    @Override
    public int getItemCount() {
        return file.size();
    }
}
