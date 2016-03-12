package com.rohanx96.admobproto.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
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
    int CATEGORY ;
    FrameLayout questionCard;
    RelativeLayout cardContent;
    private QuestionsCallback mCallback;

    @Bind(R.id.qcard_mcq_question)
    TextView tvQuestion;

    @Bind(R.id.qcard_mcq_options_ll)
    LinearLayout llOptions;

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
        GenericQuestion genericQuestion = JSONUtils.getQuestionAt(getActivity(), CATEGORY, POSITION-1);

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

    private TextView generateBlanksTextView(String option) {
        // TODO : options be set in xml and not dynamically Rishab
        // TODO : implement listeners for buttons accordingly Rishab
        final TextView answerTV = new TextView(getActivity());

        answerTV.setText(option);
        answerTV.setTextSize(20);
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

    public void lockQuestionIfRequired(){
        // TODO: (Done) Hide character when question is locked
        //Log.i("question ", answer);
        Log.i("text card ", "position " + POSITION + " category " + CATEGORY + " status " + GenericAnswerDetails.getStatus(POSITION,CATEGORY));
        switch (GenericAnswerDetails.getStatus(POSITION,CATEGORY)){
            case Constants.UNAVAILABLE:
                Log.i("textcard","unavailable");
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
                //TODO: (done) Lock image for locking options when incorrect
                //mCallback.setIsQuestionLocked(true);
                ImageView options_lock = new ImageView(getActivity());
                RelativeLayout.LayoutParams layoutParams1 = new android.widget.RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                        , ViewGroup.LayoutParams.MATCH_PARENT);
                layoutParams1.addRule(RelativeLayout.BELOW,R.id.textAreaScroller);
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
