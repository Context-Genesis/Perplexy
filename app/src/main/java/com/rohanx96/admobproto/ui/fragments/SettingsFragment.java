package com.rohanx96.admobproto.ui.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.rohanx96.admobproto.R;
import com.rohanx96.admobproto.elements.GenericAnswerDetails;
import com.rohanx96.admobproto.utils.Constants;

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
    Switch volume;

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
        return rootView;
    }

    @OnClick(R.id.switchButton)
    public void onClick_volume() {
        pref = getContext().getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
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
        pref = getContext().getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        GenericAnswerDetails.initializeDatabase(getContext());
                        SharedPreferences.Editor editor = pref.edit();

                        editor.putLong(Constants.PREF_COINS, Constants.INITIAL_COINS).apply();
                        editor.putLong(Constants.PREF_COINS_SPENT, 0).apply();
                        editor.putLong(Constants.PREF_COINS_EARNED, 0).apply();

                        editor.putInt(Constants.CORRECT_COUNT, 0).apply();
                        editor.putInt(Constants.INCORRECT_COUNT, 0).apply();
                        editor.putFloat(Constants.ACCURACY, 0f).apply();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("All your progress will be lost.Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    @OnClick(R.id.settings_info)
    public void onClick_info() {
        // TODO: MORE INFO ACTIVITY
        Toast.makeText(getContext(),"A new activity",Toast.LENGTH_SHORT).show();
    }
}
