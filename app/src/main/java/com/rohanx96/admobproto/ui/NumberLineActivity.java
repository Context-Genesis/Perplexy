package com.rohanx96.admobproto.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.rohanx96.admobproto.R;
import com.rohanx96.admobproto.adapters.NumberLineAdapter;
import com.rohanx96.admobproto.elements.SequenceAnswersDetails;

import java.util.ArrayList;

public class NumberLineActivity extends AppCompatActivity {
    private View mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_line);

        ArrayList<SequenceAnswersDetails> sequenceAnswersDetails = (ArrayList<SequenceAnswersDetails>) SequenceAnswersDetails.listAll(SequenceAnswersDetails.class);

        mContainer = findViewById(R.id.activity_number_line_container);
        NumberLineAdapter numberLineAdapter = new NumberLineAdapter(getApplication(), sequenceAnswersDetails);
        ListView listView = (ListView) findViewById(R.id.activity_number_line_listview);
        listView.setAdapter(numberLineAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        /* Make the activity fullscreen */
        mContainer.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }
}