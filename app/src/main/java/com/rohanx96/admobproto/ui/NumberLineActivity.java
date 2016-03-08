package com.rohanx96.admobproto.ui;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.rohanx96.admobproto.R;
import com.rohanx96.admobproto.adapters.NumberLineAdapter;
import com.rohanx96.admobproto.elements.SequenceAnswersDetails;
import com.rohanx96.admobproto.utils.FallingDrawables;

import java.util.ArrayList;

public class NumberLineActivity extends AppCompatActivity {
    private View mContainer;
    private int mTimeCount = 0;
    private boolean shouldRunAnimation = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_line);

        ArrayList<SequenceAnswersDetails> sequenceAnswersDetails = (ArrayList<SequenceAnswersDetails>) SequenceAnswersDetails.listAll(SequenceAnswersDetails.class);

        mContainer = findViewById(R.id.activity_number_line_container);
        NumberLineAdapter numberLineAdapter = new NumberLineAdapter(getApplicationContext(), sequenceAnswersDetails);
        ListView listView = (ListView) findViewById(R.id.activity_number_line_listview);
        listView.setAdapter(numberLineAdapter);
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
        /** Change color of background after 7 seconds */
        Thread animationThread  = new Thread(new Runnable() {
            @Override
            public void run() {
                final Handler handler = new Handler(Looper.getMainLooper());
                while (shouldRunAnimation){
                    // sleep the thread to stop the while loop for 7 seconds
                    try {
                        Thread.sleep(7000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    final ValueAnimator colorAnimator = ValueAnimator.ofObject(new ArgbEvaluator(),
                            FallingDrawables.getBackgoundColor(mTimeCount,getApplicationContext()),
                            FallingDrawables.getBackgoundColor(mTimeCount + 1,getApplicationContext()));
                    colorAnimator.setDuration(4000);
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