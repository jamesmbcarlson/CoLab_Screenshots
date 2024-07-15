package com.example.screenshotmanager;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Album {
    private String name;
    private Date lastModified;
    private List<ScreenshotImage> images;

    public Album(String newName) {
        name = newName;
        lastModified = new Date();
        images = new ArrayList<>();
    }

    public void AddToAlbum(List<File> files) {
        for (File f: files) {
            images.add(new ScreenshotImage(f));
        }
        lastModified = new Date();

        // TO-DO: save changes to file
    }

    public String GetName(){
        return name;
    }

    public List<ScreenshotImage> GetImages() { return images; };

    public int GetCount() {
        return images.size();
    }
}
