package com.rohanx96.admobproto;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class NumberLineActivity extends AppCompatActivity {
    private View mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_line);

        ArrayList<Integer> ints = new ArrayList<>();

        for(int i=0;i<25;i++){
            ints.add(i);
        }
        mContainer = findViewById(R.id.number_line_container);
        NumberLineAdapter numberLineAdapter = new NumberLineAdapter(getApplication(), ints);
        ListView listView = (ListView) findViewById(R.id.listView);
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
