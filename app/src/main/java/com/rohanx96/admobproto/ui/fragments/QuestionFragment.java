package com.rohanx96.admobproto.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rohanx96.admobproto.R;
import com.rohanx96.admobproto.elements.SequenceQuestion;
import com.rohanx96.admobproto.utils.Constants;
import com.rohanx96.admobproto.utils.JSONUtils;

import org.json.JSONException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rose on 7/3/16.
 */
public class QuestionFragment extends Fragment {

    int POSITION = -1;

    @Bind(R.id.qcard_option1)
    TextView tvOption1;

    @Bind(R.id.qcard_option2)
    TextView tvOption2;

    @Bind(R.id.qcard_option3)
    TextView tvOption3;

    @Bind(R.id.qcard_option4)
    TextView tvOption4;

    @Bind(R.id.qcard_question)
    TextView tvQuestion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.question_card, container, false);

        ButterKnife.bind(this, rootView);

        Bundle args = getArguments();
        POSITION = args.getInt(Constants.BUNDLE_QUESTION_POSITION);

        SequenceQuestion sequenceQuestion = JSONUtils.getSequenceQuestionAt(getActivity(), POSITION);

        tvQuestion.setText(sequenceQuestion.question);

        String option1 = "404", option2 = "404", option3 = "404", option4 = "404";
        try {
            option1 = sequenceQuestion.options.getJSONObject(0).getString("choice");
            option2 = sequenceQuestion.options.getJSONObject(1).getString("choice");
            option3 = sequenceQuestion.options.getJSONObject(2).getString("choice");
            option4 = sequenceQuestion.options.getJSONObject(3).getString("choice");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        tvOption1.setText(option1);
        tvOption2.setText(option2);
        tvOption3.setText(option3);
        tvOption4.setText(option4);

        return rootView;
    }

    @OnClick(R.id.qcard_next)
    public void nextQuestion(){
        ViewPager pager = (ViewPager) getActivity().findViewById(R.id.questions_activity_pager);
        pager.setCurrentItem(pager.getCurrentItem() + 1,true);
    }

    @OnClick(R.id.qcard_previous)
    public void previousQuestion(){
        ViewPager pager = (ViewPager) getActivity().findViewById(R.id.questions_activity_pager);
        pager.setCurrentItem(pager.getCurrentItem() - 1, true);
    }

    public static QuestionFragment newInstance(Bundle args) {

        QuestionFragment fragment = new QuestionFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
