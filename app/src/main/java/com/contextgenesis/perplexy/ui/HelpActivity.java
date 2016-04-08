package com.contextgenesis.perplexy.ui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.contextgenesis.perplexy.R;
import com.contextgenesis.perplexy.utils.SoundManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HelpActivity extends Activity {

    @Bind(R.id.help_fragment_frame)
    RelativeLayout relativeLayout;

    @Bind(R.id.help_text)
    TextView charText;

    @Bind(R.id.help_im0)
    ImageView image0;

    @Bind(R.id.help_next)
    ImageView next;

    @Bind(R.id.help_prev)
    ImageView prev;

    @Bind(R.id.help_main)
    RelativeLayout main;

    String charTextArray[];

    int COUNTER = 0;

    private Animation slideIn, slideOut;
    private Animation fadeIn, fadeOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        ButterKnife.bind(this);
        slideOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_bottom);
        slideIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_top);
        fadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
        fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

        charTextArray = getResources().getStringArray(R.array.helpText);
        prev.setVisibility(View.INVISIBLE);
        animateImage(COUNTER);
        charText.setMovementMethod(new ScrollingMovementMethod());
    }

    @OnClick(R.id.help_prev)
    public void onPrev() {
        SoundManager.playSwipeSound(getApplicationContext());
        animateImage(--COUNTER);
        setButtonVisibility();
        setBackground();
    }

    @OnClick(R.id.help_next)
    public void onNext() {
        SoundManager.playSwipeSound(getApplicationContext());
        if (++COUNTER >= 11) {
            finish();
            return;
        }
        animateImage(COUNTER);
        setButtonVisibility();
        setBackground();
    }

    private void setButtonVisibility() {
        if (COUNTER <= 0) {
            prev.setVisibility(View.INVISIBLE);
        } else {
            prev.setVisibility(View.VISIBLE);
        }
        if (COUNTER >= 10) {
            next.setImageResource(R.drawable.ok);
        } else {
            next.setImageResource(R.drawable.right);
        }
    }

    public void setCharText(int COUNTER) {
        try {
            charText.setText("" + charTextArray[COUNTER]);
        }
        catch (ArrayIndexOutOfBoundsException e){
            Log.e("Tutorial",e.getMessage());
        }

    }

    public void setImage(int COUNTER) {
        SharedPreferences prefs = getSharedPreferences(CharacterStore.CHAR_SHARED_PREFS, Context.MODE_PRIVATE);
        String resourceString = prefs.getString(CharacterStore.STRING_EXPRESSION_HAPPY_CLOSED, "tutorial" + COUNTER);
        int resourceID = getResources().getIdentifier(resourceString, "drawable", getPackageName());
        image0.setImageResource(resourceID);
        animateText();
    }

    private void animateText() {
        slideOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setCharText(COUNTER);
                charText.startAnimation(slideIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        charText.startAnimation(slideOut);
    }

    private void setBackground() {
        switch (COUNTER) {
            case 0:
                main.setBackgroundColor(getResources().getColor(R.color.blue_d_grey));
                break;
            case 1:
                main.setBackgroundColor(getResources().getColor(R.color.black_d_material_black));
                break;
            case 2:
                main.setBackgroundColor(getResources().getColor(R.color.black_d_material));
                break;
            case 3:
                main.setBackgroundColor(getResources().getColor(R.color.purple_l_plum));
                break;
            case 4:
                main.setBackgroundColor(getResources().getColor(R.color.purple_l_plum));
                break;
            case 5:
                main.setBackgroundColor(getResources().getColor(R.color.blue_l_steel_blue));
                break;
            case 6:
                main.setBackgroundColor(getResources().getColor(R.color.blue_l_steel_blue));
                break;
            case 7:
                main.setBackgroundColor(getResources().getColor(R.color.red_l_chestnut));
                break;
            case 8:
                main.setBackgroundColor(getResources().getColor(R.color.red_l_chestnut));
                break;
            case 9:
                main.setBackgroundColor(getResources().getColor(R.color.purple_l_plum));
                break;
            case 10:
                main.setBackgroundColor(getResources().getColor(R.color.blue_d_grey));
                break;
        }
    }

    private void animateImage(final int COUNTER) {
//        fadeOut.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                setImage(COUNTER);
//                image0.startAnimation(fadeIn);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//        image0.startAnimation(fadeOut);
        setImage(COUNTER);
    }
}