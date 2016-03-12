package com.rohanx96.admobproto.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by rish on 12/3/16.
 */
public class ShareQuestion {

    // TODO : (DHRUV) Check why this isnt working.
    public static void shareImageWhatsapp(Activity activity) {
        Toast.makeText(activity, "Preparing for Share", Toast.LENGTH_LONG).show();
        shareImage(activity);
    }

    // TODO : (DHRUV) Fb share kar idhar.
    public static void shareImageFacebook(Activity activity) {
        Toast.makeText(activity, "Requires FB SDK. Bhutani dhoondke kar.", Toast.LENGTH_LONG).show();
    }

    private static String takeScreenshot(Activity activity) {

        View v = activity.getWindow().getDecorView().getRootView();
        v.setDrawingCacheEnabled(true);
        Bitmap b = v.getDrawingCache();
        String extr = BasePath.getBasePathShare();
        File basePath = new File(extr);
        if (!basePath.exists()) {
            basePath.mkdir();
            basePath.mkdirs();
        }
        File myPath = new File(extr, "sharedImage" + ".jpg");
        myPath.mkdirs();
        myPath.mkdir();
        System.out.println(myPath);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
            b.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            MediaStore.Images.Media.insertImage(activity.getContentResolver(), b,
                    "Screen", "screen");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myPath.getPath();
    }

    private static Intent shareImage(Activity activity) {
        Intent share = new Intent(Intent.ACTION_SEND);

        String path = takeScreenshot(activity);

        File imageFileToShare = new File(path);

        Uri uri = Uri.fromFile(imageFileToShare);
        share.putExtra(Intent.EXTRA_STREAM, uri);
        share.setType("image/jpeg");
        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        share.putExtra(Intent.EXTRA_TEXT, "Perplexed! Help me answer this question! " + "https://play.google.com/store/apps/details?id=" + activity.getPackageName());
        activity.startActivity(Intent.createChooser(share, "Share Image!"));
        return share;
    }
}
