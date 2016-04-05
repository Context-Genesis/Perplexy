package com.rohanx96.admobproto.ui.fragments;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.kyleduo.switchbutton.SwitchButton;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.rohanx96.admobproto.R;
import com.rohanx96.admobproto.elements.GenericAnswerDetails;
import com.rohanx96.admobproto.ui.AboutActivity;
import com.rohanx96.admobproto.ui.MainActivity;
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

        final DialogPlus dialog = DialogPlus.newDialog(getContext())
                .setGravity(Gravity.BOTTOM)
                .setOverlayBackgroundResource(Color.TRANSPARENT)
                .setContentHolder(new ViewHolder(R.layout.reset_confirmation))
                .setContentWidth(ViewGroup.LayoutParams.MATCH_PARENT)
                .setPadding(16, 16, 16, 16)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .create();
        dialog.show();

        View dialogView = dialog.getHolderView();

        TextView yes = (TextView) dialogView.findViewById(R.id.yes_reset);
        TextView no = (TextView) dialogView.findViewById(R.id.no_reset);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenericAnswerDetails.initializeDatabase(getContext());
                SharedPreferences.Editor editor = pref.edit();

                editor.putLong(Constants.PREF_COINS, Constants.INITIAL_COINS).apply();
                editor.putLong(Constants.PREF_COINS_SPENT, 0).apply();
                editor.putLong(Constants.PREF_COINS_EARNED, 0).apply();

                editor.putInt(Constants.CORRECT_COUNT, 0).apply();
                editor.putInt(Constants.INCORRECT_COUNT, 0).apply();
                editor.putFloat(Constants.ACCURACY, 0f).apply();
                dialog.dismiss();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @OnClick(R.id.settings_rate_us)
    public void rateUs(){
        Uri uri = Uri.parse("market://details?id=" + getActivity().getPackageName());
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getActivity(), "Unable to find market app", Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.settings_info)
    public void onClick_info() {
        SoundManager.playButtonClickSound(getActivity());
        startActivity(new Intent(getActivity(), AboutActivity.class));
    }
}
