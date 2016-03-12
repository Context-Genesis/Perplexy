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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rohanx96.admobproto.R;
import com.rohanx96.admobproto.elements.GenericAnswerDetails;
import com.rohanx96.admobproto.elements.GenericQuestion;
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
public class QuestionTextBoxFragment extends Fragment {

    int POSITION = -1;
    int CATEGORY;
    FrameLayout questionCard;
    @Bind(R.id.qcard_textbox_question)
    TextView tvQuestion;

    @Bind(R.id.qcard_textbox_ll_row1_q)
    LinearLayout row1;

    @Bind(R.id.qcard_textbox_ll_row2_q)
    LinearLayout row2;

    @Bind(R.id.qcard_textbox_editText)
    EditText editText;

    ArrayList<Character> jumbledCharacters;
    String enteredCharacters = "";

    String answer, answerPadCharacters;

    int BLANK_CIRCLE_SIZE;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.question_textbox_card, container, false);
        ButterKnife.bind(this, rootView);
        this.questionCard = (FrameLayout) rootView.findViewById(R.id.question_card);
        Bundle args = getArguments();
        POSITION = args.getInt(Constants.BUNDLE_QUESTION_NUMBER);
        CATEGORY = args.getInt(Constants.BUNDLE_QUESTION_CATEGORY);
        GenericQuestion genericQuestion = JSONUtils.getQuestionAt(getActivity(), CATEGORY, POSITION - 1);
        tvQuestion.setText(genericQuestion.question);

        answer = genericQuestion.answer;
        lockQuestionIfRequired();
        answerPadCharacters = genericQuestion.pad_characters;
        BLANK_CIRCLE_SIZE = getBlankCircleSize();

        setUpJumbledCharacters();

        setUpBlanksAndRows();

        return rootView;
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
        ViewPager pager = (ViewPager) getActivity().findViewById(R.id.questions_activity_pager);
        pager.setCurrentItem(pager.getCurrentItem() + 1, true);
    }

    @OnClick(R.id.qcard_textbox_previous)
    public void previousQuestion() {
        ViewPager pager = (ViewPager) getActivity().findViewById(R.id.questions_activity_pager);
        pager.setCurrentItem(pager.getCurrentItem() - 1, true);
    }

    @OnClick(R.id.qcard_textbox_im_backspace)
    public void removeCharacter() {
        if (enteredCharacters.length() >= 1) {
            enteredCharacters = enteredCharacters.substring(0, enteredCharacters.length() - 1);
            editText.setText(enteredCharacters);
        }
    }

    @OnClick(R.id.qcard_textbox_im_done)
    public void checkAnswer() {
        isAnsweredCorrectly();
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
                    addThisCharacterToEditText(m);
                    setUpBlanksAndRows();
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
                    addThisCharacterToEditText(m);
                    setUpBlanksAndRows();
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
        if (answer.equals(enteredCharacters)) {
            GenericAnswerDetails.updateStatus(POSITION, CATEGORY, Constants.CORRECT);
            Toast.makeText(getActivity(), "Answered Correctly!", Toast.LENGTH_LONG).show();
            return true;
        } else {
            Toast.makeText(getActivity(), "Answered Incorrectly!", Toast.LENGTH_LONG).show();
            GenericAnswerDetails.updateStatus(POSITION, CATEGORY, Constants.INCORRECT);
            return false;
        }
    }

    private TextView generateFilledTextView(int i) {
        final TextView answerTV = new TextView(getActivity());

        answerTV.setText("" + jumbledCharacters.get(i));
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
        // TODO: Hide character when question is locked
        Log.i("question ", answer);
        Log.i("text card ", "position " + POSITION + " category " + CATEGORY + " status " + GenericAnswerDetails.getStatus(POSITION, CATEGORY));
        switch (GenericAnswerDetails.getStatus(POSITION, CATEGORY)) {
            case Constants.UNAVAILABLE:
                Log.i("textcard", "unavailable");
                //ImageView lock = (ImageView) findViewById(R.id.lock_full_image);
                //lock.setVisibility(View.VISIBLE);
                ImageView lock = new ImageView(getActivity());
                FrameLayout.LayoutParams layoutParams = new android.widget.FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                        , ViewGroup.LayoutParams.MATCH_PARENT);
                lock.setLayoutParams(layoutParams);
                lock.setImageResource(R.drawable.lock_flat);
                lock.setBackgroundColor(getResources().getColor(R.color.white));
                lock.setScaleType(ImageView.ScaleType.CENTER);
                questionCard.addView(lock, questionCard.getChildCount());
                break;
            case Constants.INCORRECT:
                //TODO: Lock image for locking options when incorrect
                ImageView options_lock = (ImageView) getActivity().findViewById(R.id.lock_options_image);
                options_lock.setVisibility(View.VISIBLE);
                break;
        }
    }

}