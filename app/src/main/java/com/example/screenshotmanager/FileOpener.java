package com.example.screenshotmanager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;

public class FileOpener {
    public static void openFile(Context context, File file) throws IOException {
        File selectedFile = file;
        Uri uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);

        Intent intent = new Intent(Intent.ACTION_VIEW);

        if(uri.toString().contains(".jpg") || uri.toString().contains(".jpeg") || uri.toString().contains(".png")) {
            intent.setDataAndType(uri, "image/*");
        }
        else {
            intent.setDataAndType(uri, "*/*");
        }

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        context.startActivity(intent);
    }
}
