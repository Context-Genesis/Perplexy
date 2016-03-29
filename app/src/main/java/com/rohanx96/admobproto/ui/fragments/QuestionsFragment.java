package com.rohanx96.admobproto.ui.fragments;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.rohanx96.admobproto.R;
import com.rohanx96.admobproto.callbacks.QuestionsCallback;
import com.rohanx96.admobproto.elements.GenericAnswerDetails;
import com.rohanx96.admobproto.utils.Constants;
import com.rohanx96.admobproto.utils.SoundManager;

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

    public void lockQuestionIfRequired(int POSITION, int CATEGORY, final boolean isUIVisibleToUser, final QuestionsCallback mCallback) {
        //Log.i("question ", answer);
        Log.i("text card ", "position " + POSITION + " category " + CATEGORY + " status " + GenericAnswerDetails.getStatus(POSITION, CATEGORY));
        switch (GenericAnswerDetails.getStatus(POSITION, CATEGORY)) {
            case Constants.UNAVAILABLE:
                Log.i("textcard", "unavailable");
                //mCallback.setIsQuestionLocked(true);
                //ImageView lock = (ImageView) findViewById(R.id.lock_full_image);
                //lock.setVisibility(View.VISIBLE);
                ImageView lock = new ImageView(getActivity());
                FrameLayout.LayoutParams layoutParams = new android.widget.FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                        , ViewGroup.LayoutParams.MATCH_PARENT);
                lock.setLayoutParams(layoutParams);
                lock.setId(R.id.lockImageId);
                lock.setImageResource(R.drawable.lock_flat);
                lock.setBackgroundColor(getResources().getColor(R.color.white));
                lock.setScaleType(ImageView.ScaleType.CENTER);
                lock.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isUIVisibleToUser) {
                            View characterDialog = getActivity().findViewById(R.id.questions_activity_character_dialog_unlock);
                            //expand the character dialog only if it is not previously visible
                            if (characterDialog.getVisibility() == View.GONE) {
                                mCallback.showCharacterUnlockDialog();
                                mCallback.setupCharacterUnlockDialog();
                            }
                            SoundManager.playButtonClickSound(getActivity());
                        }
                    }
                });
                cardContent.addView(lock, cardContent.getChildCount());
                break;
            case Constants.INCORRECT:
                //mCallback.setIsQuestionLocked(true);
                ImageView options_lock = new ImageView(getActivity());
                RelativeLayout.LayoutParams layoutParams1 = new android.widget.RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                        , ViewGroup.LayoutParams.MATCH_PARENT);
                layoutParams1.addRule(RelativeLayout.BELOW, R.id.textAreaScroller);
                options_lock.setLayoutParams(layoutParams1);
                options_lock.setId(R.id.lockImageId + POSITION);
                options_lock.setImageResource(R.drawable.lock_flat);
                options_lock.setBackgroundColor(getResources().getColor(R.color.white));
                options_lock.setScaleType(ImageView.ScaleType.CENTER);
                options_lock.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isUIVisibleToUser) {
                            View characterDialog = getActivity().findViewById(R.id.questions_activity_character_dialog_unlock);
                            //expand the character dialog only if it is not previously visible
                            if (characterDialog.getVisibility() == View.GONE) {
                                mCallback.showCharacterUnlockDialog();
                                mCallback.setupCharacterUnlockDialog();
                            }
                            SoundManager.playButtonClickSound(getActivity());
                        }
                    }
                });
                cardContent.addView(options_lock, cardContent.getChildCount());
                break;
            default:
                Log.i("unlock", " now");
                unlockQuestion(cardContent);
        }
    }
}
