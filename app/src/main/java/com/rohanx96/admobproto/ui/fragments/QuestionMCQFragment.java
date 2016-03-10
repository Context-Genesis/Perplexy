package com.rohanx96.admobproto.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rohanx96.admobproto.R;
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
    String CATEGORY = "";

    @Bind(R.id.qcard_mcq_question)
    TextView tvQuestion;

    @Bind(R.id.qcard_mcq_options_ll)
    LinearLayout llOptions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.question_mcq_card, container, false);

        ButterKnife.bind(this, rootView);

        Bundle args = getArguments();
        POSITION = args.getInt(Constants.BUNDLE_QUESTION_POSITION);
        CATEGORY = args.getString(Constants.BUNDLE_QUESTION_CATEGORY);

        GenericQuestion genericQuestion = JSONUtils.getQuestionAt(getActivity(), CATEGORY, POSITION);

        tvQuestion.setText(genericQuestion.question);

        try {
            for (int i = 0; i < genericQuestion.options.length(); i++) {
                String option = genericQuestion.options.getJSONObject(i).getString("choice");
                TextView textView = generateBlanksTextView(option);

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "Not yet implemented!", Toast.LENGTH_LONG).show();
                    }
                });

                llOptions.addView(textView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rootView;
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

    private TextView generateBlanksTextView(String option) {
        final TextView answerTV = new TextView(getActivity());

        answerTV.setText(option);
        answerTV.setTextSize(25);
        answerTV.setTextColor(Color.BLACK);
        answerTV.setBackgroundResource(R.drawable.button_background);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(5, 5, 5, 5);
        answerTV.setLayoutParams(layoutParams);
        answerTV.setGravity(Gravity.CENTER);

        return answerTV;
    }

    public static QuestionMCQFragment newInstance(Bundle args) {
        QuestionMCQFragment fragment = new QuestionMCQFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
