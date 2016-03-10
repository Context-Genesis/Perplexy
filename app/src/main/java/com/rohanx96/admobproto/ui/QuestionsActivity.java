package com.rohanx96.admobproto.ui;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rohanx96.admobproto.R;
import com.rohanx96.admobproto.ui.fragments.QuestionMCQFragment;
import com.rohanx96.admobproto.ui.fragments.QuestionTextBoxFragment;
import com.rohanx96.admobproto.ui.fragments.QuestionWordFragment;
import com.rohanx96.admobproto.utils.Constants;
import com.rohanx96.admobproto.utils.FallingDrawables;
import com.rohanx96.admobproto.utils.JSONUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rose on 6/3/16.
 */
public class QuestionsActivity extends AppCompatActivity {

    private ScreenSlidePagerAdapter pagerAdapter;
    private final int NO_OF_COLORS = 7;
    private ImageView character;
    private int mCurrentPage;
    private boolean isCharacterDialogOpen = false;

    int CATEGORY = -1;

    @Bind(R.id.questions_activity_level)
    TextView tvLevel;

    @Bind(R.id.questions_activity_container)
    ViewGroup mContainer;

    @Bind(R.id.questions_activity_pager)
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        ButterKnife.bind(this);

        mCurrentPage = getIntent().getIntExtra(Constants.BUNDLE_QUESTION_POSITION, 0) - 1;
        CATEGORY = getIntent().getIntExtra(Constants.BUNDLE_QUESTION_CATEGORY, -1);

        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setPageMargin(convertDip2Pixels(this, 16));
        pager.setPageTransformer(true, new DepthPageTransformer());

        mContainer.setBackgroundColor(FallingDrawables.getLightBackgroundColor(mCurrentPage, getApplicationContext()));
        pager.setCurrentItem(mCurrentPage);
        tvLevel.setText("Level " + (mCurrentPage + 1));

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //See note above for why this is needed
                ValueAnimator colorAnimator;
                colorAnimator = ValueAnimator.ofObject(new ArgbEvaluator(),
                        FallingDrawables.getLightBackgroundColor(mCurrentPage % NO_OF_COLORS, QuestionsActivity.this),
                        FallingDrawables.getLightBackgroundColor(position % NO_OF_COLORS, QuestionsActivity.this));
                colorAnimator.setDuration(500).addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mContainer.setBackgroundColor((int) animation.getAnimatedValue());
                    }
                });
                colorAnimator.start();
                mCurrentPage = position;
                tvLevel.setText("Level " + (mCurrentPage + 1));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //Unused
            }
        });
        View back = findViewById(R.id.questions_activity_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setupCharacter();
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
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.BUNDLE_QUESTION_POSITION, position);
            bundle.putInt(Constants.BUNDLE_QUESTION_CATEGORY, CATEGORY);

            if (JSONUtils.getQuestionAt(getApplicationContext(), CATEGORY, position).layout_type == 0) {
                return QuestionMCQFragment.newInstance(bundle);
            } else if (JSONUtils.getQuestionAt(getApplicationContext(), CATEGORY, position).layout_type == 1) {
                return QuestionWordFragment.newInstance(bundle);
            } else if (JSONUtils.getQuestionAt(getApplicationContext(), CATEGORY, position).layout_type == 2) {
                return QuestionTextBoxFragment.newInstance(bundle);
            }
            return QuestionWordFragment.newInstance(bundle);
        }

        @Override
        public int getCount() {
            return JSONUtils.getTotalQuestions(getApplicationContext(), CATEGORY);
        }
    }

    public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private float MIN_SCALE = 0.85f;
        private float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }

    public class DepthPageTransformer implements ViewPager.PageTransformer {
        private float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1);
                view.setTranslationX(0);
                view.setScaleX(1);
                view.setScaleY(1);
                character.animate().translationY(pageWidth * (-1 * position)).setDuration(0).start();

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);
                character.animate().translationY(pageWidth * position).setDuration(0).start();

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }

    public void setupCharacter() {
        character = (ImageView) findViewById(R.id.questions_activity_bubble);
        setupCharacterDialog();
        character.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        v.animate().scaleX(1.3f).scaleY(1.3f).setDuration(100).start();
                        break;
                    case MotionEvent.ACTION_UP:
                        character.clearAnimation();
                        character.animate().scaleX(1.0f).scaleY(1.0f).setDuration(300).start();
                        View characterDialog;
                        if (!isCharacterDialogOpen) {
                            characterDialog = findViewById(R.id.questions_activity_character_dialog);
                            ScaleAnimation scaleAnimation = new ScaleAnimation(0f, 1f, 0f, 1f, character.getX(), character.getY());
                            scaleAnimation.setDuration(500);
                            scaleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
                            characterDialog.setVisibility(View.VISIBLE);
                            characterDialog.startAnimation(scaleAnimation);
                            toggleIsCharacterDialogOpen();
                        } else {
                            characterDialog = findViewById(R.id.questions_activity_character_dialog);
                            ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 0f, 1f, 0f, character.getX(), character.getY());
                            scaleAnimation.setDuration(500);
                            scaleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
                            characterDialog.setVisibility(View.GONE);
                            characterDialog.startAnimation(scaleAnimation);
                            toggleIsCharacterDialogOpen();
                        }
                }
                return true;
            }
        });
    }

    public static int convertDip2Pixels(Context context, int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());

    }

    /**
     * This sets up the click listeners for various options in character dialog. Implementation copied from DialogSpeakingMan
     * by Dhruv
     */
    public void setupCharacterDialog() {
        final TextView showhint;
        final LinearLayout hint, confirmhint;
        final TextView nohint, yeshint, showhiddenhint;

        final TextView showsolution;
        final LinearLayout solution, confirmsolution;
        final TextView nosolution, yessolution, showhiddensolution;

        final TextView skipquestion;
        final LinearLayout skip, confirmskip;
        final TextView noskip, yesskip, showhiddenskip;

        showhint = (TextView) findViewById(R.id.showhint);
        hint = (LinearLayout) findViewById(R.id.ll_hint);
        confirmhint = (LinearLayout) findViewById(R.id.ll_confirmhint);
        nohint = (TextView) findViewById(R.id.nohint);
        yeshint = (TextView) findViewById(R.id.yeshint);
        showhiddenhint = (TextView) findViewById(R.id.showhiddenhint);

        showhint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showhint.setVisibility(View.GONE);
                Animation in = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
                confirmhint.startAnimation(in);
                confirmhint.setVisibility(View.VISIBLE);
            }
        });

        yeshint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmhint.setVisibility(View.GONE);
                showhint.setVisibility(View.VISIBLE);

                showhiddenhint.setVisibility(View.INVISIBLE);
                Animation in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_y_downards);
                showhiddenhint.startAnimation(in);
                showhiddenhint.setVisibility(View.VISIBLE);
                showhiddenhint.startAnimation(in);
            }
        });

        nohint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmhint.setVisibility(View.GONE);
                Animation in = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
                showhint.startAnimation(in);
                showhint.setVisibility(View.VISIBLE);
            }
        });


        showsolution = (TextView) findViewById(R.id.showsolution);
        solution = (LinearLayout) findViewById(R.id.ll_solution);
        confirmsolution = (LinearLayout) findViewById(R.id.ll_confirmsolution);
        nosolution = (TextView) findViewById(R.id.nosolution);
        yessolution = (TextView) findViewById(R.id.yessolution);
        showhiddensolution = (TextView) findViewById(R.id.showhiddensolution);

        showsolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showsolution.setVisibility(View.GONE);
                Animation in = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
                confirmsolution.startAnimation(in);
                confirmsolution.setVisibility(View.VISIBLE);
            }
        });

        yessolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmsolution.setVisibility(View.GONE);
                showsolution.setVisibility(View.VISIBLE);

                showhiddensolution.setVisibility(View.INVISIBLE);
                Animation in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_y_downards);
                showhiddensolution.startAnimation(in);
                showhiddensolution.setVisibility(View.VISIBLE);
                showhiddensolution.startAnimation(in);
            }
        });

        nosolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmsolution.setVisibility(View.GONE);
                Animation in = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
                showsolution.startAnimation(in);
                showsolution.setVisibility(View.VISIBLE);
            }
        });

        skipquestion = (TextView) findViewById(R.id.skipquestion);
        skip = (LinearLayout) findViewById(R.id.ll_skip);
        confirmskip = (LinearLayout) findViewById(R.id.ll_confirmskip);
        noskip = (TextView) findViewById(R.id.noskip);
        yesskip = (TextView) findViewById(R.id.yesskip);
        showhiddenskip = (TextView) findViewById(R.id.showhiddenskip);

        skipquestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skipquestion.setVisibility(View.GONE);
                Animation in = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
                confirmskip.startAnimation(in);
                confirmskip.setVisibility(View.VISIBLE);
            }
        });

        yesskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmskip.setVisibility(View.GONE);
                skipquestion.setVisibility(View.VISIBLE);

                showhiddenskip.setVisibility(View.INVISIBLE);
                Animation in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_y_downards);
                showhiddenskip.startAnimation(in);
                showhiddenskip.setVisibility(View.VISIBLE);
                showhiddenskip.startAnimation(in);
            }
        });

        noskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmskip.setVisibility(View.GONE);
                Animation in = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
                skipquestion.startAnimation(in);
                skipquestion.setVisibility(View.VISIBLE);
            }
        });
    }

    public void toggleIsCharacterDialogOpen() {
        isCharacterDialogOpen = !isCharacterDialogOpen;
    }
}
