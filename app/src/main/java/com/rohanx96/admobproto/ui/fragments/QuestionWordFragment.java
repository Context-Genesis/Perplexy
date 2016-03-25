package com.rohanx96.admobproto.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rohanx96.admobproto.R;
import com.rohanx96.admobproto.callbacks.QuestionsCallback;
import com.rohanx96.admobproto.elements.GenericAnswerDetails;
import com.rohanx96.admobproto.elements.GenericQuestion;
import com.rohanx96.admobproto.utils.Coins;
import com.rohanx96.admobproto.utils.Constants;
import com.rohanx96.admobproto.utils.JSONUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rish on 9/3/16.
 */

public class QuestionWordFragment extends Fragment {

    int POSITION = -1;
    int CATEGORY;
    FrameLayout questionCard;
    RelativeLayout cardContent;
    private QuestionsCallback mCallback;
    GenericQuestion genericQuestion;
    SharedPreferences pref;

    @Bind(R.id.qcard_word_previous)
    ImageButton prevQuestion;

    @Bind(R.id.qcard_word_next)
    ImageButton nextQuestion;

    @Bind(R.id.qcard_word_question)
    TextView tvQuestion;

    @Bind(R.id.q_card_word_ll_answerrow_q)
    LinearLayout answerRow;

    @Bind(R.id.q_card_word_ll_row1_q)
    LinearLayout row1;

    @Bind(R.id.q_card_word_ll_row2_q)
    LinearLayout row2;

    ArrayList<Character> jumbledCharacters;

    String answer, answerPadCharacters;

    int BLANK_CIRCLE_SIZE;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.question_word_card, container, false);
        ButterKnife.bind(this, rootView);
        this.questionCard = (FrameLayout) rootView.findViewById(R.id.question_card);
        this.cardContent = (RelativeLayout) rootView.findViewById(R.id.question_card_content);
        this.mCallback = (QuestionsCallback) getActivity();
        Bundle args = getArguments();
        POSITION = args.getInt(Constants.BUNDLE_QUESTION_NUMBER);
        CATEGORY = args.getInt(Constants.BUNDLE_QUESTION_CATEGORY);
        pref = getContext().getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);

        genericQuestion = JSONUtils.getQuestionAt(getActivity(), CATEGORY, POSITION - 1);
        tvQuestion.setText(genericQuestion.question);

        answer = genericQuestion.answer;
        answerPadCharacters = genericQuestion.pad_characters;

        BLANK_CIRCLE_SIZE = getBlankCircleSize();


        if (genericQuestion.question_number == 1) {
            this.prevQuestion.setVisibility(View.GONE);
        }

        // TODO: replace 14 by count of question in that particular category
        if (genericQuestion.question_number == 14) {
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

        for (int i = 0; i < answer.length(); i++) {
            jumbledCharacters.add(0, '-');
        }
    }

    @OnClick(R.id.qcard_word_next)
    public void nextQuestion() {
        ViewPager pager = (ViewPager) getActivity().findViewById(R.id.questions_activity_pager);
        pager.setCurrentItem(pager.getCurrentItem() + 1, true);
    }

    @OnClick(R.id.qcard_word_previous)
    public void previousQuestion() {
        ViewPager pager = (ViewPager) getActivity().findViewById(R.id.questions_activity_pager);
        pager.setCurrentItem(pager.getCurrentItem() - 1, true);
    }

    public static QuestionWordFragment newInstance(Bundle args) {

        QuestionWordFragment fragment = new QuestionWordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void setUpBlanksAndRows() {

        row1.removeAllViews();
        row2.removeAllViews();
        answerRow.removeAllViews();

        for (int i = 0; i < answer.length(); i++) {
            final int m = i;
            final TextView answerTV = generateBlanksTextView(i);

            answerTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    putThisCharacterBackToOptionsRow(m);
                    setUpBlanksAndRows();
                }
            });

            answerRow.addView(answerTV);
        }

        for (int i = 0; i < answerPadCharacters.length() / 2; i++) {
            final int m = i;
            final TextView answerTV = generateFilledTextView(i);

            answerTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addThisCharacterToAnswerRow(answer.length() + m);
                    setUpBlanksAndRows();
                    isAnsweredCorrectly();
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
                    addThisCharacterToAnswerRow(answer.length() + m);
                    setUpBlanksAndRows();
                    isAnsweredCorrectly();
                }
            });

            row2.addView(answerTV);
        }
    }

    private void addThisCharacterToAnswerRow(int indexToExchange) {
        for (int i = 0; i < answer.length(); i++) {
            if (jumbledCharacters.get(i) == '-') {
                Collections.swap(jumbledCharacters, indexToExchange, i);
            }
        }
    }

    private void putThisCharacterBackToOptionsRow(int indexToExchange) {
        for (int i = answer.length(); i < answer.length() + answerPadCharacters.length(); i++) {
            if (jumbledCharacters.get(i) == '-') {
                Collections.swap(jumbledCharacters, indexToExchange, i);
            }
        }
    }

    public boolean isAnsweredCorrectly() {
        GenericAnswerDetails details = GenericAnswerDetails.getAnswerDetail(genericQuestion.question_number, CATEGORY);
        for (int i = 0; i < answer.length(); i++) {
            if (jumbledCharacters.get(i) != answer.charAt(i)) {
                return false;
            }
        }
        // Coins and question should be unlocked when status is available. For correct status relevant coins and question have already
        // been unlocked. For incorrect and unavailable user should not be able to answer.
        if (details.status == Constants.AVAILABLE) {
            GenericAnswerDetails.updateStatus(POSITION, CATEGORY, Constants.CORRECT);
            Coins.correct_answer(getContext());

            TextView display_coins = (TextView) getActivity().findViewById(R.id.questions_activity_coin_text);
            display_coins.setText(pref.getLong(Constants.PREF_COINS, 0) + "");
            mCallback.unlockNextQuestion(CATEGORY);
        }
        Toast.makeText(getActivity(), "Answered Correctly!", Toast.LENGTH_LONG).show();
        return true;
    }

    private TextView generateBlanksTextView(int i) {
        final TextView answerTV = new TextView(getActivity());

        answerTV.setText("" + jumbledCharacters.get(i));
        answerTV.setId(100 + i);
        answerTV.setTextSize(25);
        answerTV.setTextColor(Color.BLACK);
        answerTV.setBackgroundResource(R.drawable.circle_border);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(BLANK_CIRCLE_SIZE, BLANK_CIRCLE_SIZE);
        layoutParams.setMargins(2, 2, 2, 2);
        answerTV.setLayoutParams(layoutParams);
        answerTV.setGravity(Gravity.CENTER);

        return answerTV;
    }

    private TextView generateFilledTextView(int i) {
        final TextView answerTV = new TextView(getActivity());

        answerTV.setText("" + jumbledCharacters.get(answer.length() + i));
        answerTV.setId(100 + i);
        answerTV.setTextSize(25);
        answerTV.setTextColor(Color.BLACK);
        answerTV.setBackgroundResource(R.drawable.circle_filled);
//        GradientDrawable gradientDrawable = (GradientDrawable) answerTV.getBackground();
//        gradientDrawable.setColor(Color.BLUE);
//        answerTV.setBackground(gradientDrawable);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(BLANK_CIRCLE_SIZE, BLANK_CIRCLE_SIZE);
        layoutParams.setMargins(2, 2, 2, 2);
        answerTV.setLayoutParams(layoutParams);
        answerTV.setGravity(Gravity.CENTER);

        return answerTV;
    }

    private int getBlankCircleSize() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        row1.getWidth();
        if (row1.getWidth() != 0) {
            Log.d("TAG", "Size of the blank is ar " + row1.getWidth() / 8);
            return row1.getWidth() / 8;
        } else {
            Log.d("TAG", "Size of the blank is sw " + width / 10);
            return width / 10;
        }
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
                        View characterDialog = getActivity().findViewById(R.id.questions_activity_character_dialog_unlock);
                        //expand the character dialog only if it is not previously visible
                        if (characterDialog.getVisibility() == View.GONE) {
                            mCallback.showCharacterUnlockDialog();
                        }
                    }
                });
                cardContent.addView(lock, cardContent.getChildCount());
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
                        View characterDialog = getActivity().findViewById(R.id.questions_activity_character_dialog_unlock);
                        //expand the character dialog only if it is not previously visible
                        if (characterDialog.getVisibility() == View.GONE) {
                            mCallback.showCharacterUnlockDialog();
                        }
                    }
                });
                cardContent.addView(options_lock, cardContent.getChildCount());
                break;
        }
    }
}