package com.rohanx96.admobproto.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.rohanx96.admobproto.R;
import com.rohanx96.admobproto.callbacks.QuestionsCallback;
import com.rohanx96.admobproto.elements.GenericAnswerDetails;
import com.rohanx96.admobproto.ui.fragments.QuestionMCQFragment;
import com.rohanx96.admobproto.ui.fragments.QuestionTextBoxFragment;
import com.rohanx96.admobproto.ui.fragments.QuestionWordFragment;
import com.rohanx96.admobproto.utils.CharacterUtils;
import com.rohanx96.admobproto.utils.Constants;
import com.rohanx96.admobproto.utils.FallingDrawables;
import com.rohanx96.admobproto.utils.JSONUtils;
import com.rohanx96.admobproto.utils.ShareQuestion;
import com.rohanx96.admobproto.utils.SoundManager;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rose on 6/3/16.
 */

public class QuestionsActivity extends AppCompatActivity implements QuestionsCallback {

    private ScreenSlidePagerAdapter pagerAdapter;
    private final int NO_OF_COLORS = 7;
    private ImageView character;
    private int mCurrentPage;
    private boolean isCharacterDialogOpen = false;
    private boolean isLocked = false;

    SharedPreferences pref;

    int CATEGORY = -1;

    @Bind(R.id.questions_activity_correct_indicator)
    ImageView correct_indicator;

    @Bind(R.id.questions_activity_level)
    TextView tvLevel;

    @Bind(R.id.questions_activity_container)
    ViewGroup mContainer;

    @Bind(R.id.questions_activity_pager)
    ViewPager pager;

    @Bind(R.id.questions_activity_coin_text)
    TextView coins_display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        ButterKnife.bind(this);
        FacebookSdk.sdkInitialize(getApplicationContext());

        /* The page position is one less than question number. Note question number is passed to activity instead of position */
        mCurrentPage = getIntent().getIntExtra(Constants.BUNDLE_QUESTION_NUMBER, 0) - 1;
        CATEGORY = getIntent().getIntExtra(Constants.BUNDLE_QUESTION_CATEGORY, -1);

        View back = findViewById(R.id.questions_activity_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundManager.playButtonClickSound(getApplicationContext());
                onBackPressed();
            }
        });

        if (GenericAnswerDetails.getStatus(mCurrentPage + 1, CATEGORY) == Constants.CORRECT) {
            correct_indicator.setImageResource(R.drawable.tick_green);
        } else if (GenericAnswerDetails.getStatus(mCurrentPage + 1, CATEGORY) == Constants.INCORRECT) {
            correct_indicator.setImageResource(R.drawable.cross);
        } else {
            correct_indicator.setImageResource(0);
        }

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
        setUpViewPager();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pager.clearOnPageChangeListeners();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.i("QuestionsActivity", " Handling permission request result");
        switch (requestCode) {
            case ShareQuestion.REQUEST_WRITE_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Preparing for Share", Toast.LENGTH_LONG).show();
                    ShareQuestion.shareImageWhatsapp(this);
                } else {
                    Snackbar.make(mContainer, "Cannot share. Please grant the write to external storage permission", Snackbar.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void setUpViewPager() {
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setPageMargin(convertDip2Pixels(this, 16));
        pager.setPageTransformer(true, new DepthPageTransformer());

        mContainer.setBackgroundColor(FallingDrawables.getLightBackgroundColor(mCurrentPage, getApplicationContext()));
        pager.setCurrentItem(mCurrentPage);
        tvLevel.setText("Level " + (mCurrentPage + 1));

        pref = getBaseContext().getSharedPreferences(Constants.SHARED_PREFERENCES, MODE_PRIVATE);
        coins_display.setText(pref.getLong(Constants.PREF_COINS, 0) + " ");

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
                hideCorrectAnswerFeedback();
                hideIncorrectAnswerFeedback();
                if (isCharacterDialogOpen) {
                    hideCharacterDialog();
                    hideCharacterUnlockDialog();
                }
                isLocked = (GenericAnswerDetails.getStatus(mCurrentPage + 1, CATEGORY) == Constants.INCORRECT)
                        || (GenericAnswerDetails.getStatus(mCurrentPage + 1, CATEGORY) == Constants.UNAVAILABLE);
                Log.i("lock status ", " position " + mCurrentPage + 1 + " " + isLocked);
                if (isLocked)
                    CharacterUtils.setCharacterDrawable(character, CharacterUtils.EXPRESSION_SAD_CLOSED);

                if (GenericAnswerDetails.getStatus(mCurrentPage + 1, CATEGORY) == Constants.INCORRECT) {
                    correct_indicator.setImageResource(R.drawable.cross);
                } else if (GenericAnswerDetails.getStatus(mCurrentPage + 1, CATEGORY) == Constants.CORRECT) {
                    correct_indicator.setImageResource(R.drawable.tick_green);
                } else
                    correct_indicator.setImageResource(0);

                // Remove the lock image on the fragment if question was previously locked but is now unlocked
                // finding view by id and then removing it does not work even if unique IDs are assigne to lock image view
                // Another way to do this is getCurrentFragment and call method of fragment that removes the view from container
                /*if (!isLocked) {
                    ((QuestionsFragment)pagerAdapter.getItem(mCurrentPage)).unlockQuestion();
                }*/
                // The above method also does not work because the view cardContent used in unlockQuestion method is always null.
                // So currently this issue is resolved by calling notifyDataSetChanged every time user correctly answers a question
                // or unlocks a new question
                // TODO: Another possible solution could be to display the lock image in the activity instead of fragments
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //Unused
            }
        });
    }

    @Override
    public void toggleIsCharacterOpen() {
        isCharacterDialogOpen = !isCharacterDialogOpen;
    }

    @Override
    public void setIsQuestionLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    @Override
    public int unlockNextQuestion(int category) {
        return GenericAnswerDetails.unlockNextQuestion(category);
    }

    @Override
    public void refreshAdapter() {
        pagerAdapter.notifyDataSetChanged();
    }

    @Override
    public float getCharacterX() {
        return character.getX();
    }

    @Override
    public float getCharacterY() {
        return character.getY();
    }

    @Override
    public void setupCharacterDialog() {
        CharacterHelper characterHelper = new CharacterHelper(this);
        characterHelper.setupCharacterDialog(CATEGORY, mCurrentPage, getApplicationContext());
    }

    @Override
    public void showCharacterDialog() {
        final View characterDialog = findViewById(R.id.questions_activity_character_dialog);
        startCharacterDialogShowAnimation(characterDialog);
        CharacterUtils.setCharacterDrawable(character, CharacterUtils.EXPRESSION_HAPPY_OPEN);
        toggleIsCharacterOpen();
    }

    @Override
    public void hideCharacterDialog() {
        final View characterDialog = findViewById(R.id.questions_activity_character_dialog);
        // This will prevent running of animation when hiding not visible dialog.
        // This helps because we can now call this method even if the view is not visible
        if (characterDialog.getVisibility() == View.VISIBLE) {
            startCharacterDialogHideAnimation(characterDialog);
            CharacterUtils.setCharacterDrawable(character, CharacterUtils.EXPRESSION_HAPPY_CLOSED);
            toggleIsCharacterOpen();
        }
    }

    @Override
    public void showCharacterUnlockDialog() {
        final View characterDialog = findViewById(R.id.questions_activity_character_dialog_unlock);
        startCharacterDialogShowAnimation(characterDialog);
        CharacterUtils.setCharacterDrawable(character, CharacterUtils.EXPRESSION_BLUSH);
        toggleIsCharacterOpen();
    }

    @Override
    public void setupCharacterUnlockDialog() {
        CharacterHelper characterHelper = new CharacterHelper(this);
        characterHelper.setupCharacterUnlockDialog(CATEGORY, mCurrentPage, getApplicationContext());

    }

    @Override
    public void hideCharacterUnlockDialog() {
        final View characterDialog = findViewById(R.id.questions_activity_character_dialog_unlock);
        // This will prevent running of animation when hiding not visible dialog.
        // This helps because we can now call this method even if the view is not visible
        if (characterDialog.getVisibility() == View.VISIBLE) {
            startCharacterDialogHideAnimation(characterDialog);
            CharacterUtils.setCharacterDrawable(character, CharacterUtils.EXPRESSION_SAD_CLOSED);
            toggleIsCharacterOpen();
        }
    }

    @Override
    public void setupCorrectAnswerFeedback(int nextQuestionUnlocked) {
        CharacterHelper helper = new CharacterHelper(this);
        helper.setupCorrectAnswerFeedback(CATEGORY, mCurrentPage, nextQuestionUnlocked, getApplicationContext());
    }

    @Override
    public void showCorrectAnswerFeedback(int nextQuestion) {
        final View characterDialog = findViewById(R.id.questions_activity_character_feedback_correct);
        startCharacterDialogShowAnimation(characterDialog);
        CharacterUtils.setCharacterDrawable(character, CharacterUtils.EXPRESSION_HAPPY_OPEN);
        setupCorrectAnswerFeedback(nextQuestion);
        toggleIsCharacterOpen();
    }

    @Override
    public void hideCorrectAnswerFeedback() {
        final View characterDialog = findViewById(R.id.questions_activity_character_feedback_correct);
        // This will prevent running of animation when hiding not visible dialog.
        // This helps because we can now call this method even if the view is not visible
        if (characterDialog.getVisibility() == View.VISIBLE) {
            startCharacterDialogHideAnimation(characterDialog);
            toggleIsCharacterOpen();
        }
        CharacterUtils.setCharacterDrawable(character, CharacterUtils.EXPRESSION_HAPPY_CLOSED);
    }

    @Override
    public void setupIncorrectAnswerFeedback() {
        CharacterHelper helper = new CharacterHelper(this);
        helper.setupIncorrectAnswerFeedback(CATEGORY, mCurrentPage, getApplicationContext());
    }

    @Override
    public void showIncorrectAnswerFeedback() {
        final View characterDialog = findViewById(R.id.questions_activity_character_feedback_incorrect);
        startCharacterDialogShowAnimation(characterDialog);
        CharacterUtils.setCharacterDrawable(character, CharacterUtils.EXPRESSION_SHOCKED);
        setupIncorrectAnswerFeedback();
        toggleIsCharacterOpen();
    }

    @Override
    public void hideIncorrectAnswerFeedback() {
        final View characterDialog = findViewById(R.id.questions_activity_character_feedback_incorrect);
        // This will prevent running of animation when hiding not visible dialog.
        // This helps because we can now call this method even if the view is not visible
        if (characterDialog.getVisibility() == View.VISIBLE) {
            startCharacterDialogHideAnimation(characterDialog);
            toggleIsCharacterOpen();
            // Question is locked so we need to set the character as sad
            CharacterUtils.setCharacterDrawable(character, CharacterUtils.EXPRESSION_SAD_CLOSED);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.BUNDLE_QUESTION_NUMBER, position + 1); // The question number is position + 1
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

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
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
        if (Build.VERSION.SDK_INT >= 21)
            character.setTransitionName("character");
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
                        isLocked = (GenericAnswerDetails.getStatus(mCurrentPage + 1, CATEGORY) == Constants.INCORRECT)
                                || (GenericAnswerDetails.getStatus(mCurrentPage + 1, CATEGORY) == Constants.UNAVAILABLE);
                        if (!isCharacterDialogOpen) {
                            if (isLocked && GenericAnswerDetails.getStatus(mCurrentPage + 1, CATEGORY) != Constants.INCORRECT) {
                                showCharacterUnlockDialog();
                                setupCharacterUnlockDialog();
                            } else if (isLocked && GenericAnswerDetails.getStatus(mCurrentPage + 1, CATEGORY) == Constants.INCORRECT) {
                                showIncorrectAnswerFeedback();
                                setupIncorrectAnswerFeedback();
                            } else {
                                showCharacterDialog();
                                //Dialog is reinitialised based on question every time character is clicked
                                setupCharacterDialog();
                            }
                        } else {
                            if (isLocked) {
                                hideCharacterUnlockDialog();
                                hideIncorrectAnswerFeedback();
                            } else {
                                hideCharacterDialog();
                                hideCorrectAnswerFeedback();
                            }
                        }
                }
                return true;
            }
        });
    }

    public void gotoQuestion(int questionNumber) {
        if (questionNumber == -1)
            pager.setCurrentItem(mCurrentPage + 1, true);
        else pager.setCurrentItem(questionNumber - 1, true);
    }

    public static int convertDip2Pixels(Context context, int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
    }

    public void startCharacterDialogShowAnimation(final View characterDialog) {
        /* Animation for post Lollipop devices*/
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            characterDialog.post(new Runnable() {
                @Override
                public void run() {
                    int cx = characterDialog.getWidth() / 2;
                    int cy = characterDialog.getHeight();
                    int radius = characterDialog.getHeight();

                    Animator animator = ViewAnimationUtils.createCircularReveal(characterDialog, cx, cy, 0, radius);
                    animator.start();
                    characterDialog.setVisibility(View.VISIBLE);
                }
            });
        } else {
            ScaleAnimation scaleAnimation = new ScaleAnimation(0f, 1f, 0f, 1f, character.getX(), character.getY());
            scaleAnimation.setDuration(500);
            scaleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
            characterDialog.setVisibility(View.VISIBLE);
            characterDialog.startAnimation(scaleAnimation);
        }
    }

    public void startCharacterDialogHideAnimation(final View characterDialog) {
        /* Animation for post Lollipop devices */
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            characterDialog.post(new Runnable() {
                @Override
                public void run() {
                    int cx = characterDialog.getWidth() / 2;
                    int cy = characterDialog.getHeight();
                    int radius = characterDialog.getHeight();

                    Animator animator = ViewAnimationUtils.createCircularReveal(characterDialog, cx, cy, radius, 0);
                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            characterDialog.setVisibility(View.GONE);
                        }
                    });
                    animator.start();
                }
            });
        } else {
            characterDialog.setVisibility(View.GONE);
            ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 0f, 1f, 0f, character.getX(), character.getY());
            scaleAnimation.setDuration(500);
            scaleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
            characterDialog.startAnimation(scaleAnimation);
        }
    }
}