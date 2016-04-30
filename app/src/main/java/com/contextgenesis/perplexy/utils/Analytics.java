package com.contextgenesis.perplexy.utils;

import android.app.Activity;

import com.contextgenesis.perplexy.PerplexyApplication;
import com.google.android.gms.analytics.HitBuilders;

/**
 * Created by rose on 25/4/16.
 */

public class Analytics {
    public static final String CATEGORY_UI = "UI";
    public static final String CATEGORY_ADS = "Ad";
    public static final String CATEGORY_COINS = "Coins";
    public static final String CATEGORY_QUESTION = "Questions";
    public static final String ACTION_NO_COINS = "Not enough Coins";
    public static final String ACTION_SHOW_HINT = "Show Hint";
    public static final String ACTION_SHOW_SOLUTION = "Show solution";
    public static final String ACTION_UNLOCK_QUESTION = "Unlock Question";
    public static final String ACTION_WATCH_AD = "Click on Watch Ad";
    public static final String ACTION_PLAY_CATEGORY = "Play category";

    public static void sendWatchAd(Activity mActivity,long coins){
        PerplexyApplication application = (PerplexyApplication) mActivity.getApplication();
        application.getDefaultTracker().send(new HitBuilders.EventBuilder()
                .setCategory(Analytics.CATEGORY_ADS).setAction(Analytics.ACTION_WATCH_AD).setValue(coins).build());
    }

    public static void sendShowHint(Activity mActivity, int category, int questionNo){
        PerplexyApplication application = (PerplexyApplication) mActivity.getApplication();
        application.getDefaultTracker().send(new HitBuilders.EventBuilder()
                .setCategory(Analytics.CATEGORY_QUESTION).setAction(Analytics.ACTION_SHOW_HINT)
                .setLabel("Category:" + category)
                .setValue(questionNo).build());
    }

    public static void sendShowSolution(Activity mActivity, int category, int questionNo){
        PerplexyApplication application = (PerplexyApplication) mActivity.getApplication();
        application.getDefaultTracker().send(new HitBuilders.EventBuilder()
                .setCategory(Analytics.CATEGORY_QUESTION).setAction(Analytics.ACTION_SHOW_SOLUTION)
                .setLabel("Category:" + category)
                .setValue(questionNo).build());
    }

    public static void sendUnlockQuestion(Activity mActivity, int category, int questionNo){
        PerplexyApplication application = (PerplexyApplication) mActivity.getApplication();
        application.getDefaultTracker().send(new HitBuilders.EventBuilder()
                .setCategory(Analytics.CATEGORY_QUESTION).setAction(Analytics.ACTION_UNLOCK_QUESTION)
                .setLabel("Category:" + category)
                .setValue(questionNo).build());
    }

    public static void sendNoCoins(Activity mActivity, long coins){
        PerplexyApplication application = (PerplexyApplication) mActivity.getApplication();
        application.getDefaultTracker().send(new HitBuilders.EventBuilder()
                .setCategory(Analytics.CATEGORY_COINS).setAction(Analytics.ACTION_NO_COINS).setValue(coins).build());
    }
}
