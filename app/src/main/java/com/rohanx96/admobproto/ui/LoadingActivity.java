package com.rohanx96.admobproto.ui;

import android.app.Activity;
import android.os.Bundle;

import com.rohanx96.admobproto.R;

public class LoadingActivity extends Activity {

    public static Activity thisActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        thisActivity = this;
    }
}