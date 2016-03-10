package com.rohanx96.admobproto.ui.fragments;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rohanx96.admobproto.R;
import com.rohanx96.admobproto.elements.WordQuestion;
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

    @Bind(R.id.qcard_word_question)
    TextView tvQuestion;

    @Bind(R.id.q_card_word_ll_answerrow_q)
    LinearLayout answerRow;

    @Bind(R.id.q_card_word_ll_row1_q)
    LinearLayout row1;

    @Bind(R.id.q_card_word_ll_row2_q)
    LinearLayout row2;

    ArrayList<Character> jumbledCharacters;

    String answer, answerOptions;

    int BLANK_CIRCLE_SIZE;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.question_word_card, container, false);
        ButterKnife.bind(this, rootView);

        Bundle args = getArguments();
        POSITION = args.getInt(Constants.BUNDLE_QUESTION_POSITION);

        WordQuestion wordQuestion = JSONUtils.getRiddleQuestionAt(getActivity(), POSITION);
        tvQuestion.setText(wordQuestion.question);

        answer = wordQuestion.answer;
        answerOptions = wordQuestion.answerOptions;

        BLANK_CIRCLE_SIZE = getBlankCircleSize();

        setUpJumbledCharacters();

        setUpBlanksAndRows();

        return rootView;
    }

    private void setUpJumbledCharacters() {
        jumbledCharacters = new ArrayList<>();

        for (int i = 0; i < answerOptions.length(); i++) {
            jumbledCharacters.add(answerOptions.charAt(i));
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

        for (int i = 0; i < answerOptions.length() / 2; i++) {
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

        for (int i = answerOptions.length() / 2; i < answerOptions.length(); i++) {
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
        for (int i = answer.length(); i < answer.length() + answerOptions.length(); i++) {
            if (jumbledCharacters.get(i) == '-') {
                Collections.swap(jumbledCharacters, indexToExchange, i);
            }
        }
    }

    public boolean isAnsweredCorrectly() {
        for (int i = 0; i < answer.length(); i++) {
            if (jumbledCharacters.get(i) != answer.charAt(i)) {
                return false;
            }
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

        answerRow.getWidth();
        if (answerRow.getWidth() != 0) {
            Log.d("TAG", "Size of the blank is ar " + answerRow.getWidth() / 8);
            return answerRow.getWidth() / 8;
        } else {
            Log.d("TAG", "Size of the blank is sw " + width / 10);
            return width / 10;
        }
    }
}