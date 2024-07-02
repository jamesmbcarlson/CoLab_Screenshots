package com.example.screenshotmanager.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.screenshotmanager.FileAdapter;
import com.example.screenshotmanager.FileOpener;
import com.example.screenshotmanager.R;
import com.example.screenshotmanager.OnFileSelectedListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class InternalFragment extends Fragment implements OnFileSelectedListener {

    private RecyclerView recyclerView;
    private FileAdapter fileAdapter;
    private List<File> fileList;
    private ImageView img_back;
    private TextView tv_path_holder;
    File storage;
    String data;
    String[] items = {"Details", "Rename", "Delete", "Share"};
    String storage_root = System.getenv("EXTERNAL_STORAGE");

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_internal, container, false);

        tv_path_holder = view.findViewById(R.id.tv_path_holder);
        img_back = view.findViewById(R.id.img_back);

        // Set initial starting point for images to view
        String starting_path = storage_root;
        if(Files.exists(Paths.get(storage_root + "/Pictures"))) {
            if(Files.exists(Paths.get(storage_root + "/Pictures/Screenshots"))) {
                starting_path = storage_root  + "/Pictures/Screenshots";
            }
            else {
                starting_path = storage_root + "/Pictures";
            }
        }
        storage = new File(starting_path);

        try {
            data = getArguments().getString("path");
            File file = new File(data);
            storage = file;
        } catch(Exception e) {
            e.printStackTrace();
        }

        tv_path_holder.setText(storage.getAbsolutePath());
        runtimePermission();

        return view;

    }

    private void runtimePermission() {
        Dexter.withContext(getContext()).withPermissions(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE
        ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                displayFiles();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    public ArrayList<File> findFiles(File file)
    {
        ArrayList<File> arrayList = new ArrayList<>();
        File[] files = file.listFiles();

        Collections.addAll(arrayList, files);
//        for (File single_file : files){
//            if (single_file.getName().toLowerCase().endsWith(".jpeg") ||
//                    single_file.getName().toLowerCase().endsWith(".jpg") ||
//                    single_file.getName().toLowerCase().endsWith(".png"))
//            {
//                arrayList.add(single_file);
//            }
//        }
        return arrayList;
    }

    private void displayFiles() {
        recyclerView = view.findViewById(R.id.recycler_internal);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        fileList = new ArrayList<>();
        
        // Add folder to navigate up
        if(!storage.toString().equals(storage_root)) {
            File currentDirectory = storage;
            fileList.add(storage.getParentFile());
        }
        
        fileList.addAll(findFiles(storage));
        fileAdapter = new FileAdapter(getContext(), fileList, this);
        recyclerView.setAdapter(fileAdapter);
    }

    @Override
    public void onFileClicked(File file) {
        if(file.isDirectory()){
            Bundle bundle = new Bundle();
            bundle.putString("path", file.getAbsolutePath());
            InternalFragment internalFragment = new InternalFragment();
            internalFragment.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.fragment_container, internalFragment).addToBackStack(null).commit();

        }
        else {
            try {
                FileOpener.openFile(getContext(), file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void onFileLongClicked(File file, int position) {
        final Dialog optionDialog = new Dialog(getContext());
        optionDialog.setContentView(R.layout.option_dialog);
        optionDialog.setTitle("Select Options");
        ListView options = (ListView) optionDialog.findViewById(R.id.List);
        CustomAdapter customAdapter = new CustomAdapter();
        options.setAdapter(customAdapter);
        optionDialog.show();

        options.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getItemAtPosition(i).toString();

                switch(selectedItem){
                    case "Details":
                        AlertDialog.Builder detailDialog = new AlertDialog.Builder(getContext());
                        detailDialog.setTitle("Details:");
                        final TextView details = new TextView(getContext());
                        detailDialog.setView(details);
                        Date lastModified = new Date(file.lastModified());
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        String formattedDate = formatter.format(lastModified);

                        details.setText("Filename: " + file.getName() + "\n" +
                                "Size: " + Formatter.formatShortFileSize(getContext(), file.length()) + "\n" +
                                "Path: " + file.getAbsolutePath() + "\n" +
                                "Last Modified: " + formattedDate);
                        
                        detailDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                optionDialog.cancel();
                            }
                        });
                        
                        AlertDialog alert_dialog_details = detailDialog.create();
                        alert_dialog_details.show();
                        break;

                    case "Rename":
                        AlertDialog.Builder renameDialog = new AlertDialog.Builder(getContext());
                        renameDialog.setTitle("Rename File:");
                        final EditText name = new EditText(getContext());
                        renameDialog.setView(name);

                        renameDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String new_name = name.getEditableText().toString();
                                String extension = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf("."));
                                File current = new File(file.getAbsolutePath());
                                File destination = new File(file.getAbsolutePath().replace(file.getName(), new_name) + extension);
                                if (current.renameTo(destination)){
                                    fileList.set(position, destination);
                                    fileAdapter.notifyItemChanged(position);
                                    Toast.makeText(getContext(), "File has been renamed.", Toast.LENGTH_LONG).show();
                                }
                                else {
                                    Toast.makeText(getContext(), "ERROR: Could not rename file.", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                        renameDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                optionDialog.cancel();
                            }
                        });
                        AlertDialog alert_dialog_rename = renameDialog.create();
                        alert_dialog_rename.show();

                        break;

                    case "Delete":
                        AlertDialog.Builder delete_dialog = new AlertDialog.Builder(getContext());
                        delete_dialog.setTitle("Are you sure you want to delete " + file.getName() + "?");
                        delete_dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                file.delete();
                                fileList.remove(position);
                                fileAdapter.notifyDataSetChanged();
                                Toast.makeText(getContext(), "File Deleted", Toast.LENGTH_SHORT).show();
                            }
                        });

                        delete_dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                optionDialog.cancel();
                            }
                        });

                        AlertDialog alert_dialog_delete = delete_dialog.create();
                        alert_dialog_delete.show();
                        break;

                    case "Share":
                        String filename = file.getName();
                        Intent share = new Intent();
                        share.setAction(Intent.ACTION_SEND);
                        share.setType("image/*");
                        share.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(getContext(), getContext().getPackageName() + ".provider", file));
                        startActivity(Intent.createChooser(share, "Share " + filename));
                }
            }
        });

    }

    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public Object getItem(int i) {
            return items[i];
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View myView = getLayoutInflater().inflate(R.layout.option_layout, null);
            TextView txtOptions = myView.findViewById(R.id.txt_option);
            ImageView imgOptions = myView.findViewById(R.id.imgOption);
            txtOptions.setText(items[i]);

            if(items[i].equals("Details")){
                imgOptions.setImageResource(R.drawable.ic_details);
            }
            else if(items[i].equals("Rename")){
                imgOptions.setImageResource(R.drawable.ic_rename);
            }
            else if(items[i].equals("Delete")){
                imgOptions.setImageResource(R.drawable.ic_delete);
            }
            else if(items[i].equals("Share")){
                imgOptions.setImageResource(R.drawable.ic_share);
            }

            return myView;
        }
    }
}
