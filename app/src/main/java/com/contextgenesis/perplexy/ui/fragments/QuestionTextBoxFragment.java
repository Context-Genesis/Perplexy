package com.contextgenesis.perplexy.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.contextgenesis.perplexy.R;
import com.contextgenesis.perplexy.callbacks.QuestionsCallback;
import com.contextgenesis.perplexy.elements.GenericAnswerDetails;
import com.contextgenesis.perplexy.elements.GenericQuestion;
import com.contextgenesis.perplexy.ui.QuestionsActivity;
import com.contextgenesis.perplexy.utils.Coins;
import com.contextgenesis.perplexy.utils.Constants;
import com.contextgenesis.perplexy.utils.DrawingView;
import com.contextgenesis.perplexy.utils.JSONUtils;
import com.contextgenesis.perplexy.utils.SoundManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rish on 9/3/16.
 */
public class QuestionTextBoxFragment extends Fragment {

    int POSITION = -1;
    int CATEGORY;
    private QuestionsCallback mCallback;
    GenericQuestion genericQuestion;
    SharedPreferences pref;

    @Bind(R.id.qcard_textbox_previous)
    ImageButton prevQuestion;

    @Bind(R.id.qcard_textbox_next)
    ImageButton nextQuestion;

    @Bind(R.id.qcard_textbox_question)
    TextView tvQuestion;

    @Bind(R.id.qcard_textbox_ll_row1_q)
    LinearLayout row1;

    @Bind(R.id.qcard_textbox_ll_row2_q)
    LinearLayout row2;

    @Bind(R.id.qcard_textbox_editText)
    EditText editText;

    @Bind(R.id.textbox_canvas_pull)
    Button canvas;

    @Bind(R.id.textAreaScroller)
    ScrollView scroll;

    ArrayList<Character> jumbledCharacters;
    String enteredCharacters = "";

    String answer, answerPadCharacters;

    private boolean isUIVisibleToUser = false;
    private RelativeLayout cardContent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.question_textbox_card, container, false);
        ButterKnife.bind(this, rootView);
        this.cardContent = (RelativeLayout) rootView.findViewById(R.id.question_card_content);
        this.mCallback = (QuestionsCallback) getActivity();
        Bundle args = getArguments();
        POSITION = args.getInt(Constants.BUNDLE_QUESTION_NUMBER);
        CATEGORY = args.getInt(Constants.BUNDLE_QUESTION_CATEGORY);
        genericQuestion = JSONUtils.getQuestionAt(getActivity(), CATEGORY, POSITION - 1);
        tvQuestion.setText(genericQuestion.question);
        pref = getContext().getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;

        scroll.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, height / 4));

        answer = genericQuestion.answer;
        lockQuestionIfRequired();
        answerPadCharacters = genericQuestion.pad_characters;

        if (genericQuestion.question_number == 1) {
            this.prevQuestion.setVisibility(View.GONE);
        }

        if (genericQuestion.question_number == Constants.RIDDLE_COUNT && genericQuestion.category == Constants.GAME_TYPE_RIDDLE) {
            this.nextQuestion.setVisibility(View.GONE);
        }
        if (genericQuestion.question_number == Constants.SEQUENCE_COUNT && genericQuestion.category == Constants.GAME_TYPE_SEQUENCES) {
            this.nextQuestion.setVisibility(View.GONE);
        }
        if (genericQuestion.question_number == Constants.LOGIC_QUESTION && genericQuestion.category == Constants.GAME_TYPE_LOGIC) {
            this.nextQuestion.setVisibility(View.GONE);
        }

        setUpJumbledCharacters();

        setUpBlanksAndRows();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Check every time the fragment is refreshed
        lockQuestionIfRequired();
    }

    private void setUpJumbledCharacters() {
        jumbledCharacters = new ArrayList<>();

        for (int i = 0; i < answerPadCharacters.length(); i++) {
            jumbledCharacters.add(answerPadCharacters.charAt(i));
        }
        Collections.shuffle(jumbledCharacters, new Random(System.currentTimeMillis()));
    }

    @OnClick(R.id.qcard_textbox_next)
    public void nextQuestion() {
        if (isUIVisibleToUser) {
            ViewPager pager = (ViewPager) getActivity().findViewById(R.id.questions_activity_pager);
            pager.setCurrentItem(pager.getCurrentItem() + 1, true);
            SoundManager.playSwipeSound(getActivity());
        }
    }

    @OnClick(R.id.qcard_textbox_previous)
    public void previousQuestion() {
        if (isUIVisibleToUser) {
            ViewPager pager = (ViewPager) getActivity().findViewById(R.id.questions_activity_pager);
            pager.setCurrentItem(pager.getCurrentItem() - 1, true);
            SoundManager.playSwipeSound(getActivity());
        }
    }

    @OnClick(R.id.textbox_canvas_pull)
    public void canvas_pulldown() {
        if (isUIVisibleToUser) {
            DrawingView.setUpCanvas(getContext(), QuestionsActivity.convertDip2Pixels(getContext(), tvQuestion.getHeight() + 80));
            SoundManager.playButtonClickSound(getActivity());
        }
    }

    @OnClick(R.id.qcard_textbox_im_backspace)
    public void removeCharacter() {
        if (isUIVisibleToUser) {
            if (enteredCharacters.length() >= 1) {
                enteredCharacters = enteredCharacters.substring(0, enteredCharacters.length() - 1);
                editText.setText(enteredCharacters);
                SoundManager.playBackClickSound(getActivity());
            }
        }
    }

    @OnClick(R.id.qcard_textbox_im_done)
    public void checkAnswer() {
        if (isUIVisibleToUser) {
            SoundManager.playButtonClickSound(getActivity());
            isAnsweredCorrectly();
        }
    }

    public static QuestionTextBoxFragment newInstance(Bundle args) {
        QuestionTextBoxFragment fragment = new QuestionTextBoxFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void setUpBlanksAndRows() {

        row1.removeAllViews();
        row2.removeAllViews();

        editText.setText(enteredCharacters);

        for (int i = 0; i < answerPadCharacters.length() / 2; i++) {
            final int m = i;
            final TextView answerTV = generateFilledTextView(i);

            answerTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isUIVisibleToUser) {
                        addThisCharacterToEditText(m);
                        setUpBlanksAndRows();
                        SoundManager.playPadCharacterSound(getActivity());
                    }
                    // isAnsweredCorrectly();
                }
            });

            row1.addView(answerTV);
        }

        for (int i = answerPadCharacters.length() / 2; i < answerPadCharacters.length(); i++) {
            final TextView answerTV = generateFilledTextView(i);
            final int m = i;

            answerTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isUIVisibleToUser) {
                        addThisCharacterToEditText(m);
                        setUpBlanksAndRows();
                        SoundManager.playPadCharacterSound(getActivity());
                    }
                    //isAnsweredCorrectly();
                }
            });

            row2.addView(answerTV);
        }
    }

    private void addThisCharacterToEditText(int index) {
        enteredCharacters += jumbledCharacters.get(index);
        editText.setText(enteredCharacters);
    }

    public boolean isAnsweredCorrectly() {
        GenericAnswerDetails details = GenericAnswerDetails.getAnswerDetail(genericQuestion.question_number, CATEGORY);
        if (pref.getInt(Constants.PREF_SHOW_AD, 0) >= Constants.AD_DISPLAY_LIMIT)
            mCallback.showAd(false);
        else
            pref.edit().putInt(Constants.PREF_SHOW_AD, pref.getInt(Constants.PREF_SHOW_AD, 0) + 1).apply();
        editText.setText("");
        if (answer.equals(enteredCharacters)) {
            // Coins and question should be unlocked when status is available. For correct status relevant coins and question have already
            // been unlocked. For incorrect and unavailable user should not be able to answer.
            if (details.status == Constants.AVAILABLE) {
                GenericAnswerDetails.updateStatus(POSITION, CATEGORY, Constants.CORRECT);
                Coins.correct_answer(getContext());

                ImageView correctIndicator = (ImageView) getActivity().findViewById(R.id.questions_activity_correct_indicator);
                correctIndicator.setImageResource(R.drawable.tick_green);

                TextView display_coins = (TextView) getActivity().findViewById(R.id.questions_activity_coin_text);
                display_coins.setText(pref.getLong(Constants.PREF_COINS, 0) + "");
                SoundManager.playCoinSound(getActivity());
                int next = mCallback.unlockNextQuestion(CATEGORY);
                mCallback.showCorrectAnswerFeedback(next);
                mCallback.refreshAdapter();
            } else mCallback.showCorrectAnswerFeedback(-1);
            //Toast.makeText(getActivity(), "Answered Correctly!", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            if (details.status == Constants.AVAILABLE) {
                GenericAnswerDetails.updateStatus(POSITION, CATEGORY, Constants.INCORRECT);
                Coins.wrong_answer(getContext());

                ImageView correctIndicator = (ImageView) getActivity().findViewById(R.id.questions_activity_correct_indicator);
                correctIndicator.setImageResource(R.drawable.cross);

                TextView display_coins = (TextView) getActivity().findViewById(R.id.questions_activity_coin_text);
                display_coins.setText(pref.getLong(Constants.PREF_COINS, 0) + "");
            }
            // Sets status of question locked in questionsActivity. Used to change the layout of character
            mCallback.setIsQuestionLocked(true);
            mCallback.showIncorrectAnswerFeedback();
            lockQuestionIfRequired();
            return false;
        }
    }

    private TextView generateFilledTextView(int i) {
        int screen = getScreenWidth();
        int tvMargin = 2;
        int tvWidth = (screen / (answerPadCharacters.length() / 2)) - (2 * tvMargin);

        TextView answerTV = new TextView(getActivity());

        answerTV.setText("" + jumbledCharacters.get(i));
        answerTV.setTextSize(TypedValue.COMPLEX_UNIT_PX, tvWidth / 2);
        answerTV.setTextColor(Color.WHITE);
        answerTV.setBackgroundResource(R.drawable.pad_character_background);
        answerTV.setLayoutParams(new ViewGroup.LayoutParams(tvWidth, tvWidth));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(tvWidth, tvWidth);
        layoutParams.setMargins(tvMargin, tvMargin, tvMargin, tvMargin);
        answerTV.setLayoutParams(layoutParams);
        answerTV.setGravity(Gravity.CENTER);

        return answerTV;
    }

    private int getScreenWidth() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        Resources r = getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, r.getDisplayMetrics());

        return size.x - 4 * px;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isUIVisibleToUser = isVisibleToUser;
    }

    public void lockQuestionIfRequired() {
        //Log.i("question ", answer);
        Log.i("text card ", "position " + POSITION + " category " + CATEGORY + " status " + GenericAnswerDetails.getStatus(POSITION, CATEGORY));
        switch (GenericAnswerDetails.getStatus(POSITION, CATEGORY)) {
            case Constants.UNAVAILABLE:
                Log.i("textcard", "unavailable");
                //mCallback.setIsQuestionLocked(true);
                //ImageView lock = (ImageView) findViewById(R.id.lock_full_image);
                //lock.setVisibility(View.VISIBLE);
                ImageView lock = new ImageView(getActivity());
                FrameLayout.LayoutParams layoutParams = new android.widget.FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                        , ViewGroup.LayoutParams.MATCH_PARENT);
                lock.setLayoutParams(layoutParams);
                lock.setId(R.id.lockImageId);
                lock.setImageResource(R.drawable.lock_flat);
                lock.setBackgroundColor(getResources().getColor(R.color.white));
                lock.setScaleType(ImageView.ScaleType.CENTER);
                lock.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isUIVisibleToUser) {
                            View characterDialog = getActivity().findViewById(R.id.questions_activity_character_dialog_unlock);
                            //expand the character dialog only if it is not previously visible
                            if (characterDialog.getVisibility() == View.GONE) {
                                mCallback.showCharacterUnlockDialog();
                                mCallback.setupCharacterUnlockDialog();
                            }
                            SoundManager.playButtonClickSound(getActivity());
                        }
                    }
                });
                cardContent.addView(lock, cardContent.getChildCount() - 2);
                canvas.setVisibility(View.GONE);
                break;
            case Constants.INCORRECT:
                //mCallback.setIsQuestionLocked(true);
                ImageView options_lock = new ImageView(getActivity());
                RelativeLayout.LayoutParams layoutParams1 = new android.widget.RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                        , ViewGroup.LayoutParams.MATCH_PARENT);
                layoutParams1.addRule(RelativeLayout.BELOW, R.id.textAreaScroller);
                options_lock.setLayoutParams(layoutParams1);
                options_lock.setId(R.id.lockImageId);
                options_lock.setImageResource(R.drawable.lock_flat);
                options_lock.setBackgroundColor(getResources().getColor(R.color.white));
                options_lock.setScaleType(ImageView.ScaleType.CENTER);
                options_lock.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isUIVisibleToUser) {
                            View characterDialog = getActivity().findViewById(R.id.questions_activity_character_dialog_unlock);
                            //expand the character dialog only if it is not previously visible
                            if (characterDialog.getVisibility() == View.GONE) {
                                mCallback.showCharacterUnlockDialog();
                                mCallback.setupCharacterUnlockDialog();
                            }
                            SoundManager.playButtonClickSound(getActivity());
                        }
                    }
                });
                cardContent.addView(options_lock, cardContent.getChildCount() - 2);
                canvas.setVisibility(View.GONE);
                break;
        }
    }
}