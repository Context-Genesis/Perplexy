package com.rohanx96.admobproto.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.rohanx96.admobproto.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends Activity {

    @Bind(R.id.about_butterknife)
    TextView butterknife;
    @Bind(R.id.about_dialog)
    TextView dialog;
    @Bind(R.id.about_freepik)
    TextView freepik;
    @Bind(R.id.about_icons)
    TextView icons8;
    @Bind(R.id.about_sugar)
    TextView sugar;
    @Bind(R.id.about_switch)
    TextView switchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

    }

    private void openWeb(String link) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(browserIntent);
    }

    @OnClick(R.id.about_butterknife)
    public void setButterknife() {
        openWeb("jakewharton.github.io/butterknife/");
    }

    @OnClick(R.id.about_dialog)
    public void setDialog() {
        openWeb("https://github.com/orhanobut/dialogplus");
    }

    @OnClick(R.id.about_freepik)
    public void setFreepik() {
        openWeb("http://freepik.com/");
    }

    @OnClick(R.id.about_icons)
    public void setIcons8() {
        openWeb("https://icons8.com/");
    }

    @OnClick(R.id.about_sugar)
    public void setSugar() {
        openWeb("https://github.com/satyan/sugar/");
    }

    @OnClick(R.id.about_switch)
    public void setSwitchBtn() {
        openWeb("https://github.com/kyleduo/SwitchButton");
    }

    @OnClick(R.id.devandroid1)
    public void setAndDev1() {
        openWeb("https://play.google.com/store/apps/details?id=com.Dhruv.MemoryMaze");
    }

    @OnClick(R.id.devgplus1)
    public void setgplus1() {
        openWeb("https://plus.google.com/u/0/115993146711231228746/about");
    }

    @OnClick(R.id.devandroid2)
    public void setAndDev2() {
        openWeb("https://play.google.com/store/apps/details?id=rish.crearo.lifehacks");
    }

    @OnClick(R.id.devgplus2)
    public void setgplus2() {
        openWeb("https://plus.google.com/u/0/114245670419614057385");
    }

    @OnClick(R.id.devandroid3)
    public void setAndDev3() {
        openWeb("https://play.google.com/store/apps/details?id=com.rose.quickwallet");
    }

    @OnClick(R.id.devgplus3)
    public void setgplus3() {
        openWeb("https://plus.google.com/u/0/117317494206385274738/about");
    }

    @OnClick(R.id.devandroid4)
    public void setAndroid4(){
        openWeb("https://plus.google.com/112784156774948582788/about");
    }
    @OnClick(R.id.devgplus4)
    public void setgplus4() {
        openWeb("https://plus.google.com/112784156774948582788/about");
    }
}