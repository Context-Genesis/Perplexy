package com.rohanx96.admobproto.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rohanx96.admobproto.R;

/**
 * Created by bhutanidhruv16 on 06-Mar-16.
 */
public class StatisticsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_statisticspage, container, false);

        return rootView;
    }
}