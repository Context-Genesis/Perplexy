package com.contextgenesis.perplexy.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by rish on 8/3/16.
 */
public class BasePath {

    public static String getBasePath() {

        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath() + "/perplexy");

        if (!dir.exists())
            dir.mkdirs();

        return dir.getAbsolutePath();
    }

    public static String getBasePathShare() {

        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath() + "/perplexy");

        if (!dir.exists())
            dir.mkdirs();

        return dir.getAbsolutePath();
    }


}
