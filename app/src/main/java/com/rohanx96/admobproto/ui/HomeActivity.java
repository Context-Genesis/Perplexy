package com.rohanx96.admobproto.ui;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.rohanx96.admobproto.R;
import com.rohanx96.admobproto.elements.SequenceAnswersDetails;
import com.rohanx96.admobproto.elements.SequenceQuestion;
import com.rohanx96.admobproto.utils.Constants;
import com.rohanx96.admobproto.utils.JSONUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends Activity {

    @Bind(R.id.home_start)
    Button start;

    public String TAG = HomeActivity.this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        onFirstRun();
    }

    @OnClick(R.id.home_start)
    public void startPressed() {

        /*
        * When he clicks on play, send the arraylist of questions of the type to the Questions Activity.
         */
        ArrayList<SequenceQuestion> sequenceQuestions = JSONUtils.getSequenceQuestionsFromJSONString(getApplicationContext());
        ArrayList<SequenceAnswersDetails> sequenceAnswersDetails = (ArrayList<SequenceAnswersDetails>) SequenceAnswersDetails.listAll(SequenceAnswersDetails.class);

        Log.d(TAG, "SequenceAnswersDetails");
        for (SequenceAnswersDetails sqa : sequenceAnswersDetails) {
            Log.d(TAG, sqa.toString());
        }

        Log.d(TAG, "SequenceQuestion");
        for (SequenceQuestion sq : sequenceQuestions) {
            Log.d(TAG, sq.toString());
        }


    }

    private void onFirstRun() {
        /*
        * check if app is run for the first time.
        * Initialize SequenceAnswersDetails database with default values
         */
        SharedPreferences prefs = getSharedPreferences(Constants.SHARED_PREFERENCES, MODE_PRIVATE);
        if (prefs.getBoolean(Constants.FIRST_RUN, true)) {
            SequenceAnswersDetails.initializeDatabase(getApplicationContext());
            prefs.edit().putBoolean(Constants.FIRST_RUN, false).commit();
        }
    }
}