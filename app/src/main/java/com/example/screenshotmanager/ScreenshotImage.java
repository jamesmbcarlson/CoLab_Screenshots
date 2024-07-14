package com.example.screenshotmanager;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ScreenshotImage {
    private final File file;
    private Path path;
    private List<String> categories;
    private boolean seen;
    private String text;
    private List<String> tags;
    private Date lastViewed;

    public ScreenshotImage(File file) {
        this.file = file;
        path = Paths.get(file.getAbsolutePath());

        // set lastViewed to current date and time
        Date lastViewed = new Date();

        // display date as string:
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//        String formattedDate = dateFormat.format(lastViewed);



    }

    public Path GetPath() { return path; }

    public File GetFile() { return file; }
}
