package com.rohanx96.admobproto.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.rohanx96.admobproto.R;
import com.rohanx96.admobproto.callbacks.QuestionsCallback;
import com.rohanx96.admobproto.elements.GenericAnswerDetails;
import com.rohanx96.admobproto.elements.GenericQuestion;
import com.rohanx96.admobproto.ui.QuestionsActivity;
import com.rohanx96.admobproto.utils.Coins;
import com.rohanx96.admobproto.utils.Constants;
import com.rohanx96.admobproto.utils.DrawingView;
import com.rohanx96.admobproto.utils.JSONUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rose on 7/3/16.
 */
public class QuestionMCQFragment extends QuestionsFragment {

    int POSITION = -1;
    int CATEGORY;
    private QuestionsCallback mCallback;
    GenericQuestion genericQuestion;
    SharedPreferences pref;

    @Bind(R.id.qcard_mcq_question)
    TextView tvQuestion;

    @Bind(R.id.qcard_mcq_options_ll)
    RelativeLayout llOptions;

    @Bind(R.id.qcard_mcq_previous)
    ImageButton prevQuestion;

    @Bind(R.id.qcard_mcq_next)
    ImageButton nextQuestion;

    @Bind(R.id.qcard_mcq_option1)
    TextView tvOption1;
    @Bind(R.id.qcard_mcq_option2)
    TextView tvOption2;
    @Bind(R.id.qcard_mcq_option3)
    TextView tvOption3;
    @Bind(R.id.qcard_mcq_option4)
    TextView tvOption4;

    @Bind(R.id.canvas_pull)
    Button canvas_pull;

    private boolean isUIVisibleToUser = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.question_mcq_card, container, false);

        ButterKnife.bind(this, rootView);

        this.cardContent = (RelativeLayout) rootView.findViewById(R.id.question_card_content);
        setCardContent(cardContent);

        this.mCallback = (QuestionsCallback) getActivity();
        Bundle args = getArguments();
        POSITION = args.getInt(Constants.BUNDLE_QUESTION_NUMBER);
        CATEGORY = args.getInt(Constants.BUNDLE_QUESTION_CATEGORY);
        lockQuestionIfRequired();
        genericQuestion = JSONUtils.getQuestionAt(getActivity(), CATEGORY, POSITION - 1);
        pref = getContext().getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);

        tvQuestion.setText(genericQuestion.question);

        if (genericQuestion.question_number == 1) {
            this.prevQuestion.setVisibility(View.GONE);
        }

        // TODO: replace 14 by count of question in that particular category
        if (genericQuestion.question_number == 14) {
            this.nextQuestion.setVisibility(View.GONE);
        }

        try {
            switch (genericQuestion.options.length()) {
                case 2:
                    tvOption1.setText(genericQuestion.options.getJSONObject(0).getString("choice"));
                    tvOption2.setText(genericQuestion.options.getJSONObject(1).getString("choice"));

                    tvOption3.setVisibility(View.GONE);
                    tvOption4.setVisibility(View.GONE);
                    break;
                case 4:
                    tvOption1.setText(genericQuestion.options.getJSONObject(0).getString("choice"));
                    tvOption2.setText(genericQuestion.options.getJSONObject(1).getString("choice"));
                    tvOption3.setText(genericQuestion.options.getJSONObject(2).getString("choice"));
                    tvOption4.setText(genericQuestion.options.getJSONObject(3).getString("choice"));
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rootView;
    }


    @OnClick(R.id.canvas_pull)
    public void canvasClick() {
        if (isUIVisibleToUser) {
            DrawingView.setUpCanvas(getContext(), QuestionsActivity.convertDip2Pixels(getContext(), tvQuestion.getHeight() + 80));
        }
    }

    @OnClick(R.id.qcard_mcq_option1)
    public void onClickOption1(View view) {
        if (isUIVisibleToUser) {
            isRight("1");
        }
    }

    @OnClick(R.id.qcard_mcq_option2)
    public void onClickOption2(View view) {
        if (isUIVisibleToUser) {
            isRight("2");
        }
    }

    @OnClick(R.id.qcard_mcq_option3)
    public void onClickOption3(View view) {
        if (isUIVisibleToUser) {
            isRight("3");
        }
    }

    @OnClick(R.id.qcard_mcq_option4)
    public void onClickOption4(View view) {
        if (isUIVisibleToUser) {
            isRight("4");
        }
    }

    void isRight(String check) {
        if (genericQuestion.answer.equals(check)) {
            GenericAnswerDetails details = GenericAnswerDetails.getAnswerDetail(genericQuestion.question_number, CATEGORY);
            // Coins and question should be unlocked when status is available. For correct status relevant coins and question have already
            // been unlocked. For incorrect and unavailable user should not be able to answer.
            if (details.status == Constants.AVAILABLE) {
                Coins.correct_answer(getContext());
                details.status = Constants.CORRECT;
                details.save();

                TextView display_coins = (TextView) getActivity().findViewById(R.id.questions_activity_coin_text);
                display_coins.setText(pref.getLong(Constants.PREF_COINS, 0) + "");
                mCallback.unlockNextQuestion(CATEGORY);
                mCallback.refreshAdapter();
            }
            //Toast.makeText(getActivity(), "Clicked option " + check + " CORRECT", Toast.LENGTH_SHORT).show();

        } else {
            GenericAnswerDetails details = GenericAnswerDetails.getAnswerDetail(genericQuestion.question_number, CATEGORY);
            if (details.status == Constants.AVAILABLE) {
                Coins.wrong_answer(getContext());
                details.status = Constants.INCORRECT;
                details.save();

                TextView display_coins = (TextView) getActivity().findViewById(R.id.questions_activity_coin_text);
                display_coins.setText(pref.getLong(Constants.PREF_COINS, 0) + "");
                // Sets status of question locked in questionsActivity. Used to change the layout of character
                mCallback.setIsQuestionLocked(true);
                lockQuestionIfRequired();
            }
            Toast.makeText(getActivity(), "Clicked option " + check + " INCORRECT", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Check every time the fragment is refreshed
        lockQuestionIfRequired();
    }

    @OnClick(R.id.qcard_mcq_next)
    public void nextQuestion() {
        if (isUIVisibleToUser) {
            ViewPager pager = (ViewPager) getActivity().findViewById(R.id.questions_activity_pager);
            pager.setCurrentItem(pager.getCurrentItem() + 1, true);
        }
    }

    @OnClick(R.id.qcard_mcq_previous)
    public void previousQuestion() {
        if (isUIVisibleToUser) {
            ViewPager pager = (ViewPager) getActivity().findViewById(R.id.questions_activity_pager);
            pager.setCurrentItem(pager.getCurrentItem() - 1, true);
        }
    }

    public static QuestionMCQFragment newInstance(Bundle args) {

        QuestionMCQFragment fragment = new QuestionMCQFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void lockQuestionIfRequired() {
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
                        if (isUIVisibleToUser) {
                            View characterDialog = getActivity().findViewById(R.id.questions_activity_character_dialog_unlock);
                            //expand the character dialog only if it is not previously visible
                            if (characterDialog.getVisibility() == View.GONE) {
                                mCallback.showCharacterUnlockDialog();
                                mCallback.setupCharacterUnlockDialog();
                            }
                        }
                    }
                });
                cardContent.addView(options_lock, cardContent.getChildCount());
                break;
            default:
                Log.i("unlock", " now");
                unlockQuestion(cardContent);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isUIVisibleToUser = isVisibleToUser;
    }
}

