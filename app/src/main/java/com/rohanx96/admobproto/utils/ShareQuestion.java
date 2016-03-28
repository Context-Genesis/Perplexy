package com.rohanx96.admobproto.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;


/**
 * Created by rish on 12/3/16.
 */

public class ShareQuestion {

    static Bitmap bitmap;             // needed for FB

    public static void shareImageWhatsapp(Activity activity) {
        Toast.makeText(activity, "Preparing for Share", Toast.LENGTH_LONG).show();
        shareImage(activity);
    }

    // TODO : (DHRUV) Fb share kar idhar.
    public static void shareImageFacebook(Activity activity) {
        takeScreenshot(activity);
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(bitmap)
                .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();

        Toast.makeText(activity, "This has not been tested!", Toast.LENGTH_LONG).show();
    }

    private static File takeScreenshot(Activity activity) {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
        String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

        // create bitmap screen capture
        View v1 = activity.getWindow().getDecorView().getRootView();
        v1.setDrawingCacheEnabled(true);
        bitmap = Bitmap.createBitmap(v1.getDrawingCache());
        v1.setDrawingCacheEnabled(false);
        File imageFile = new File(mPath);

        try {
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();
//            openScreenshot(activity, imageFile);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return imageFile;
    }

    private static void openScreenshot(Activity activity, File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        activity.startActivity(intent);
    }

    private static void shareImage(Activity activity) {
        File imageFile = takeScreenshot(activity);
        Uri uri = Uri.fromFile(imageFile);
        Intent whatsappIntent = new Intent(android.content.Intent.ACTION_SEND);
        whatsappIntent.setDataAndType(uri, "image/*");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Perplexed! Help me answer this question! " + "https://play.google.com/store/apps/details?id=" + activity.getPackageName());
        whatsappIntent.putExtra(Intent.EXTRA_STREAM, uri);
        activity.startActivity(Intent.createChooser(whatsappIntent, "Share image using"));
        try {
            activity.startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(activity, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
