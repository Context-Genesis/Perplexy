package com.rohanx96.admobproto.ui.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        TextView heading = (TextView) rootView.findViewById(R.id.statistics_tv_heading);
        Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(), "tagus.ttf");
        heading.setTypeface(typeFace);
        return rootView;
    }
}