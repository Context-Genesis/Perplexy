package com.rohanx96.admobproto.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rohanx96.admobproto.R;
import com.rohanx96.admobproto.ui.NumberLineActivity;

public class FrontPageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_frontpage, container, false);
        ImageView playButton = (ImageView) rootView.findViewById(R.id.home_play);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent questionsActivity = new Intent(getActivity(), NumberLineActivity.class);
                startActivity(questionsActivity);
            }
        });
        return rootView;
    }
}