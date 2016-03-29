package com.rohanx96.admobproto.ui.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.kyleduo.switchbutton.SwitchButton;
import com.rohanx96.admobproto.R;
import com.rohanx96.admobproto.elements.GenericAnswerDetails;
import com.rohanx96.admobproto.utils.Constants;
import com.rohanx96.admobproto.utils.SoundManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rose on 10/3/16.
 */


public class SettingsFragment extends Fragment {

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Bind(R.id.switchButton)
    SwitchButton volume;

    @Bind(R.id.settings_reset)
    TextView reset;

    @Bind(R.id.settings_info)
    TextView info;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, rootView);
        Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(), "tagus.ttf");
        TextView heading = (TextView) rootView.findViewById(R.id.settings_tv_heading);
        heading.setTypeface(typeFace);
        setupSoundSwitch();
        return rootView;
    }

    public void setupSoundSwitch() {
        SoundManager.playSwipeSound(getActivity());
        pref = getContext().getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        volume.setChecked(pref.getBoolean(Constants.VOLUME, true));
        final SharedPreferences.Editor editor = pref.edit();
        volume.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editor.putBoolean(Constants.VOLUME, true).apply();
                } else {
                    editor.putBoolean(Constants.VOLUME, false).apply();
                }
            }
        });
    }

    @OnClick(R.id.settings_reset)
    public void onClick_reset() {
        SoundManager.playButtonClickSound(getActivity());
        pref = getContext().getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        new AlertDialog.Builder(getContext())
                .setMessage("All your progress will be lost. Are you sure?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        GenericAnswerDetails.initializeDatabase(getContext());
                        SharedPreferences.Editor editor = pref.edit();

                        editor.putLong(Constants.PREF_COINS, Constants.INITIAL_COINS).apply();
                        editor.putLong(Constants.PREF_COINS_SPENT, 0).apply();
                        editor.putLong(Constants.PREF_COINS_EARNED, 0).apply();

                        editor.putInt(Constants.CORRECT_COUNT, 0).apply();
                        editor.putInt(Constants.INCORRECT_COUNT, 0).apply();
                        editor.putFloat(Constants.ACCURACY, 0f).apply();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();
    }

    @OnClick(R.id.settings_info)
    public void onClick_info() {
        // TODO: MORE INFO ACTIVITY
        SoundManager.playButtonClickSound(getActivity());
        Toast.makeText(getContext(), "A new activity", Toast.LENGTH_SHORT).show();
    }
}
