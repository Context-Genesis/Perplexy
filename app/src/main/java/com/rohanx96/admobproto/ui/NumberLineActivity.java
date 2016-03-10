package com.rohanx96.admobproto.ui;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_line);
        ButterKnife.bind(this);
        CATEGORY = getIntent().getIntExtra(Constants.BUNDLE_QUESTION_CATEGORY, -1);
        tvTitle.setText("Select Level");
        mContainer = findViewById(R.id.activity_number_line_container);
    }

    @Override
    protected void onResume() {
        super.onResume();
        /* Make the activity fullscreen */
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
        NumberLineAdapter numberLineAdapter = new NumberLineAdapter(this, answerDetails);
        ListView listView = (ListView) findViewById(R.id.activity_number_line_listview);
        listView.setAdapter(numberLineAdapter);

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
    public void goBack(){
        onBackPressed();
    }

    /*public void updateCoinsDisplay(){
        TextView coinsText = (TextView) findViewById(R.id.activity_coin_text);
        coinsText.setText(PreferenceManager.getDefaultSharedPreferences(this).getInt(Constants.PREF_COINS,0));
    }*/

}