package com.rohanx96.admobproto.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.rohanx96.admobproto.R;
import com.rohanx96.admobproto.ui.CharacterStore;
import com.rohanx96.admobproto.ui.MainActivity;
import com.rohanx96.admobproto.ui.NumberLineActivity;
import com.rohanx96.admobproto.utils.Constants;
import com.rohanx96.admobproto.utils.SoundManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FrontPageFragment extends Fragment {
    @Bind(R.id.home_tv_heading)
    TextView heading;

    @Bind(R.id.home_tv_lvl_text)
    TextView gameTypeText;

    @Bind(R.id.home_seekbar)
    SeekBar gameSeekBar;

    @Bind(R.id.home_play)
    ImageView playButton;

    @Bind(R.id.game_1)
    ImageView gameType1;
    @Bind(R.id.game_2)
    ImageView gameType2;
    @Bind(R.id.game_3)
    ImageView gameType3;

    private int selectedGameType = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_frontpage, container, false);

        ButterKnife.bind(this, rootView);

        gameSeekBar.setProgress(0);
        gameType1.performClick();
        gameTypeText.setText(getGameTypeText(0));
        resetLevelSizes(0);

        setUpSeekBar();

        Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(), "tagus.ttf");
        heading.setTypeface(typeFace);

        return rootView;
    }

    private void setUpSeekBar() {
        gameSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, final int progress, boolean fromUser) {
                final Animation slideOut = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_out_right);
                final Animation slideIn = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_in_left);

                slideOut.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        gameTypeText.setText(getGameTypeText(progress));
                        gameTypeText.startAnimation(slideIn);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                selectedGameType = progress;
                resetLevelSizes(progress);
                gameTypeText.startAnimation(slideOut);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @OnClick(R.id.game_1)
    public void onClickGame1() {
        gameSeekBar.setProgress(0);
        selectedGameType = Constants.GAME_TYPE_LOGIC;
        SoundManager.playSwipeSound(getActivity());
    }

    @OnClick(R.id.game_2)
    public void onClickGame2() {
        gameSeekBar.setProgress(1);
        selectedGameType = Constants.GAME_TYPE_RIDDLE;
        SoundManager.playSwipeSound(getActivity());
    }

    @OnClick(R.id.game_3)
    public void onClickGame3() {
        gameSeekBar.setProgress(2);
        selectedGameType = Constants.GAME_TYPE_SEQUENCES;
        SoundManager.playSwipeSound(getActivity());
    }

    @OnClick(R.id.home_play)
    public void playGame() {
        /*
        *Send which game type user chose with this intent
         */
        Intent questionsActivity = new Intent(getActivity(), NumberLineActivity.class);
        /*Implement switch case here once we set up code and questions*/
        questionsActivity.putExtra(Constants.BUNDLE_QUESTION_CATEGORY, selectedGameType);
        startActivity(questionsActivity);

        /* This will stop the falling drawables animation when the activity has been left. Improves performance */
        ((MainActivity) getActivity()).getFallingDrawables().stopAnimation();

        SoundManager.playButtonClickSound(getActivity());
    }


    private String getGameTypeText(int lvl) {
        switch (lvl) {
            case 0:
                return "Do you have the logic in you?";
            case 1:
                return "Riddle Me This";
            case 2:
                return "If a:b and c:d, then x: ?";
            case 3:
                return "4 Pictures 1 Word";
            default:
                return "Error in selecting level";
        }
    }

    private void resetLevelSizes(int lvl) {
        gameType1.requestLayout();
        gameType1.getLayoutParams().height = convertDip2Pixels(getActivity(),36);
        gameType1.getLayoutParams().width = convertDip2Pixels(getActivity(),36);
        gameType2.requestLayout();
        gameType2.getLayoutParams().height = convertDip2Pixels(getActivity(),36);
        gameType2.getLayoutParams().width = convertDip2Pixels(getActivity(),36);
        gameType3.requestLayout();
        gameType3.getLayoutParams().height = convertDip2Pixels(getActivity(),36);
        gameType3.getLayoutParams().width = convertDip2Pixels(getActivity(),36);
        switch (lvl) {
            case 0:
                gameType1.requestLayout();
                gameType1.getLayoutParams().height = convertDip2Pixels(getActivity(),44);
                gameType1.getLayoutParams().width = convertDip2Pixels(getActivity(),44);
                return;
            case 1:
                gameType2.requestLayout();
                gameType2.getLayoutParams().height = convertDip2Pixels(getActivity(),44);
                gameType2.getLayoutParams().width = convertDip2Pixels(getActivity(),44);
                return;
            case 2:
                gameType3.requestLayout();
                gameType3.getLayoutParams().height = convertDip2Pixels(getActivity(),44);
                gameType3.getLayoutParams().width = convertDip2Pixels(getActivity(),44);
                return;
            /*case 3:
                gameType4.requestLayout();
                gameType4.getLayoutParams().height = 50;
                gameType4.getLayoutParams().width = 50;
                return;*/
            default:
                return;
        }
    }

    @OnClick(R.id.home_settings_button)
    public void openSettings() {
        ((MainActivity) getActivity()).goToSettings();
        //startActivity(new Intent(getActivity(), CharacterStore.class));
    }

    @OnClick(R.id.home_statistics_button)
    public void openStats(){
        ((MainActivity)getActivity()).goToStats();
    }

    public static int convertDip2Pixels(Context context, int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
    }
}