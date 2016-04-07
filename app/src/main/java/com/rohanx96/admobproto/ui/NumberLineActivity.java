package com.rohanx96.admobproto.ui;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.rohanx96.admobproto.R;
import com.rohanx96.admobproto.adapters.NumberLineAdapter;
import com.rohanx96.admobproto.elements.GenericAnswerDetails;
import com.rohanx96.admobproto.utils.Constants;
import com.rohanx96.admobproto.utils.FallingDrawables;
import com.rohanx96.admobproto.utils.SoundManager;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NumberLineActivity extends AppCompatActivity {
    private View mContainer;
    private int mTimeCount = 0;
    private boolean isAnimationRunning = false;

    int CATEGORY;

    @Bind(R.id.activity_number_line_title)
    TextView tvTitle;

    @Bind(R.id.activity_coin_text)
    TextView coin_display;

    /**
     * @Bind(R.id.activity_number_line_bubble_ll) LinearLayout bubbleLL;
     * @Bind(R.id.activity_number_line_bubble_im) ImageView bubble;
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_line);
        ButterKnife.bind(this);
        CATEGORY = getIntent().getIntExtra(Constants.BUNDLE_QUESTION_CATEGORY, -1);
        tvTitle.setText("Select Level");
        mContainer = findViewById(R.id.activity_number_line_container);
        SharedPreferences prefs = getSharedPreferences(Constants.SHARED_PREFERENCES, MODE_PRIVATE);
        coin_display.setText(prefs.getLong(Constants.PREF_COINS, 0) + "");

        //bubbleLL.setVisibility(View.GONE);
        ListView listView = (ListView) findViewById(R.id.activity_number_line_listview);
        NumberLineAdapter adapter = new NumberLineAdapter(this, new ArrayList<GenericAnswerDetails>());
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        /* Make the activity fullscreen */

        SharedPreferences prefs = getSharedPreferences(Constants.SHARED_PREFERENCES, MODE_PRIVATE);
        coin_display.setText(prefs.getLong(Constants.PREF_COINS, 0) + "");

        mContainer.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        /* Adapter list needs to be initialised here because we need to refresh list after returning to activity */
        ArrayList<GenericAnswerDetails> answerDetails = GenericAnswerDetails.listAll(CATEGORY);
        ListView listView = (ListView) findViewById(R.id.activity_number_line_listview);
        ((NumberLineAdapter) listView.getAdapter()).setAnswerDetails(answerDetails);
        ((NumberLineAdapter) listView.getAdapter()).notifyDataSetChanged();

        /** Change color of background after 7 seconds */
        if (!isAnimationRunning) {
            Thread animationThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    isAnimationRunning = true;
                    final Handler handler = new Handler(Looper.getMainLooper());
                    while (isAnimationRunning) {
                        // sleep the thread to stop the while loop for 5 seconds
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        final ValueAnimator colorAnimator = ValueAnimator.ofObject(new ArgbEvaluator(),
                                FallingDrawables.getBackgroundColor(mTimeCount, getApplicationContext()),
                                FallingDrawables.getBackgroundColor(mTimeCount + 1, getApplicationContext()));
                        colorAnimator.setDuration(3000);
                        mTimeCount++;
                        if (mTimeCount == FallingDrawables.NO_OF_COLORS)
                            mTimeCount = 0;
                        colorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(final ValueAnimator animation) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mContainer.setBackgroundColor((int) animation.getAnimatedValue());
                                    }
                                });
                            }
                        });
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                colorAnimator.start();
                            }
                        });
                    }
                }
            });
            animationThread.start();
        }
    }

    public void setAnimationRunning(boolean animationRunning) {
        this.isAnimationRunning = animationRunning;
    }

    @OnClick(R.id.activity_number_line_back)
    public void goBack() {
        SoundManager.playButtonClickSound(getApplicationContext());
        onBackPressed();
    }

   /* @OnClick(R.id.activity_number_line_bubble_im)
    public void characterDialogPressed() {
        this.closeCharacterDialog();
    }

    @Override
    public void toggleCharacterOpen() {
        if (bubbleLL.getVisibility() == View.VISIBLE) {
            bubbleLL.setVisibility(View.GONE);
        } else if (bubbleLL.getVisibility() == View.GONE) {
            bubbleLL.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void openCharacterDialog(int TYPE) {

        bubbleLL.setVisibility(View.VISIBLE);
        TextView titleTv = (TextView) findViewById(R.id.char_unlock_dialog_tv);
        switch (TYPE) {
            case Constants.UNAVAILABLE:
                titleTv.setText("This question is unavailable");
                break;
            case Constants.INCORRECT:
                titleTv.setText("This question is locked. Would you like to unlock it?");
                break;
        }
    }

    @Override
    public void closeCharacterDialog() {
        bubbleLL.setVisibility(View.GONE);
    }*/

    /*public void updateCoinsDisplay(){
        TextView coinsText = (TextView) findViewById(R.id.activity_coin_text);
        coinsText.setText(PreferenceManager.getDefaultSharedPreferences(this).getInt(Constants.PREF_COINS,0));
    }*/

}