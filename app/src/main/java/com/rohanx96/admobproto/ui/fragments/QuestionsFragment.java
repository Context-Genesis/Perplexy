package com.rohanx96.admobproto.ui.fragments;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.rohanx96.admobproto.R;

/**
 * Created by rose on 26/3/16.
 */

/**
 * This class is the parent class for all types of question fragment. Any method and variable common to question type fragment
 * should be defined here
 */
public class QuestionsFragment extends Fragment {
    ViewGroup cardContent;

    public void setCardContent(ViewGroup cardContent) {
        this.cardContent = cardContent;
    }

    public void unlockQuestion() {
        Log.i("unlocking", " removing image");
        if (cardContent != null) {
            Log.i("unlocking", " removing image2");
            if (cardContent.getChildAt(cardContent.getChildCount() - 1).getId() == R.id.lockImageId) {
                Log.i("unlocking", " removing image3");
                cardContent.removeViewAt(cardContent.getChildCount() - 1);
            }
        }
    }

    public void unlockQuestion(ViewGroup cardContent) {
        Log.i("unlocking", " removing image");
        if (cardContent != null) {
            Log.i("unlocking", " removing image2");
            if (cardContent.getChildAt(cardContent.getChildCount() - 1).getId() == R.id.lockImageId) {
                Log.i("unlocking", " removing image3");
                cardContent.removeViewAt(cardContent.getChildCount() - 1);
            }
        }
    }
}
