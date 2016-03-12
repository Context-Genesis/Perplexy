package com.rohanx96.admobproto.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rohanx96.admobproto.R;
import com.rohanx96.admobproto.callbacks.QuestionsCallback;
import com.rohanx96.admobproto.elements.GenericAnswerDetails;
import com.rohanx96.admobproto.elements.GenericQuestion;
import com.rohanx96.admobproto.utils.Constants;
import com.rohanx96.admobproto.utils.JSONUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rose on 7/3/16.
 */
public class QuestionMCQFragment extends Fragment {

    int POSITION = -1;
    int CATEGORY;
    FrameLayout questionCard;
    RelativeLayout cardContent;
    private QuestionsCallback mCallback;

    @Bind(R.id.qcard_mcq_question)
    TextView tvQuestion;

    @Bind(R.id.qcard_mcq_options_ll)
    LinearLayout llOptions;

    @Bind(R.id.qcard_mcq_option1)
    TextView tvOption1;
    @Bind(R.id.qcard_mcq_option2)
    TextView tvOption2;
    @Bind(R.id.qcard_mcq_option3)
    TextView tvOption3;
    @Bind(R.id.qcard_mcq_option4)
    TextView tvOption4;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.question_mcq_card, container, false);

        ButterKnife.bind(this, rootView);
        this.questionCard = (FrameLayout) rootView.findViewById(R.id.question_card);
        this.cardContent = (RelativeLayout) rootView.findViewById(R.id.question_card_content);
        this.mCallback = (QuestionsCallback) getActivity();
        Bundle args = getArguments();
        POSITION = args.getInt(Constants.BUNDLE_QUESTION_NUMBER);
        CATEGORY = args.getInt(Constants.BUNDLE_QUESTION_CATEGORY);
        lockQuestionIfRequired();
        GenericQuestion genericQuestion = JSONUtils.getQuestionAt(getActivity(), CATEGORY, POSITION - 1);

        tvQuestion.setText(genericQuestion.question);

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

    @OnClick(R.id.qcard_mcq_option1)
    public void onClickOption1(View view) {
        Toast.makeText(getActivity(), "Clicked option1", Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.qcard_mcq_option2)
    public void onClickOption2(View view) {
        Toast.makeText(getActivity(), "Clicked option2", Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.qcard_mcq_option3)
    public void onClickOption3(View view) {
        Toast.makeText(getActivity(), "Clicked option3", Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.qcard_mcq_option4)
    public void onClickOption4(View view) {
        Toast.makeText(getActivity(), "Clicked option4", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Check every time the fragment is refreshed
        lockQuestionIfRequired();
    }

    @OnClick(R.id.qcard_mcq_next)
    public void nextQuestion() {
        ViewPager pager = (ViewPager) getActivity().findViewById(R.id.questions_activity_pager);
        pager.setCurrentItem(pager.getCurrentItem() + 1, true);
    }

    @OnClick(R.id.qcard_mcq_previous)
    public void previousQuestion() {
        ViewPager pager = (ViewPager) getActivity().findViewById(R.id.questions_activity_pager);
        pager.setCurrentItem(pager.getCurrentItem() - 1, true);
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
