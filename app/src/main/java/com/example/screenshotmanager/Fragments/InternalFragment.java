package com.example.screenshotmanager.Fragments;

import android.Manifest;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import java.util.ArrayList;
import java.util.Collections;
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

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_internal, container, false);

        tv_path_holder = view.findViewById(R.id.tv_path_holder);
        img_back = view.findViewById(R.id.img_back);



        String internal_storage = System.getenv("EXTERNAL_STORAGE");
        String pictures_default = internal_storage + "/Pictures";
        storage = new File(pictures_default);

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
                Manifest.permission.READ_MEDIA_IMAGES
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
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        fileList = new ArrayList<>();
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
    public void onFileLongClicked(File file) {
        final Dialog optionDialog = new Dialog(getContext());
        optionDialog.setContentView(R.layout.option_dialog);
        optionDialog.setTitle("Select Options");
        ListView options = (ListView) optionDialog.findViewById(R.id.List);
        CustomAdapter customAdapter = new CustomAdapter();
        options.setAdapter(customAdapter);
        optionDialog.show();

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
