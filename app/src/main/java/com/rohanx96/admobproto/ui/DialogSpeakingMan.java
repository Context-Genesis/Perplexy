package com.rohanx96.admobproto.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rohanx96.admobproto.R;

/**
 * Created by bhutanidhruv16 on 30-Sep-15.
 */
public class DialogSpeakingMan extends Dialog {

    TextView showhint;
    LinearLayout hint, confirmhint;
    TextView nohint, yeshint, showhiddenhint;

    TextView showsolution;
    LinearLayout solution, confirmsolution;
    TextView nosolution, yessolution, showhiddensolution;

    TextView skipquestion;
    LinearLayout skip, confirmskip;
    TextView noskip, yesskip, showhiddenskip;

    public DialogSpeakingMan(Context context) {
        super(context);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.character_click_dialog);
        this.setCancelable(true);

        showhint = (TextView) findViewById(R.id.showhint);
        hint = (LinearLayout) findViewById(R.id.ll_hint);
        confirmhint = (LinearLayout) findViewById(R.id.ll_confirmhint);
        nohint = (TextView) findViewById(R.id.nohint);
        yeshint = (TextView) findViewById(R.id.yeshint);
        showhiddenhint = (TextView) findViewById(R.id.showhiddenhint);

        showhint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showhint.setVisibility(View.GONE);
                Animation in = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in);
                confirmhint.startAnimation(in);
                confirmhint.setVisibility(View.VISIBLE);
            }
        });

        yeshint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmhint.setVisibility(View.GONE);
                showhint.setVisibility(View.VISIBLE);

                showhiddenhint.setVisibility(View.INVISIBLE);
                Animation in = AnimationUtils.loadAnimation(getContext(), R.anim.scale_y_downards);
                showhiddenhint.startAnimation(in);
                showhiddenhint.setVisibility(View.VISIBLE);
                showhiddenhint.startAnimation(in);
            }
        });

        nohint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmhint.setVisibility(View.GONE);
                Animation in = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in);
                showhint.startAnimation(in);
                showhint.setVisibility(View.VISIBLE);
            }
        });


        showsolution = (TextView) findViewById(R.id.showsolution);
        solution = (LinearLayout) findViewById(R.id.ll_solution);
        confirmsolution = (LinearLayout) findViewById(R.id.ll_confirmsolution);
        nosolution = (TextView) findViewById(R.id.nosolution);
        yessolution = (TextView) findViewById(R.id.yessolution);
        showhiddensolution = (TextView) findViewById(R.id.showhiddensolution);

        showsolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showsolution.setVisibility(View.GONE);
                Animation in = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in);
                confirmsolution.startAnimation(in);
                confirmsolution.setVisibility(View.VISIBLE);
            }
        });

        yessolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmsolution.setVisibility(View.GONE);
                showsolution.setVisibility(View.VISIBLE);

                showhiddensolution.setVisibility(View.INVISIBLE);
                Animation in = AnimationUtils.loadAnimation(getContext(), R.anim.scale_y_downards);
                showhiddensolution.startAnimation(in);
                showhiddensolution.setVisibility(View.VISIBLE);
                showhiddensolution.startAnimation(in);
            }
        });

        nosolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmsolution.setVisibility(View.GONE);
                Animation in = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in);
                showsolution.startAnimation(in);
                showsolution.setVisibility(View.VISIBLE);
            }
        });

        skipquestion = (TextView) findViewById(R.id.skipquestion);
        skip = (LinearLayout) findViewById(R.id.ll_skip);
        confirmskip = (LinearLayout) findViewById(R.id.ll_confirmskip);
        noskip = (TextView) findViewById(R.id.noskip);
        yesskip = (TextView) findViewById(R.id.yesskip);
        showhiddenskip = (TextView) findViewById(R.id.showhiddenskip);

        skipquestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skipquestion.setVisibility(View.GONE);
                Animation in = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in);
                confirmskip.startAnimation(in);
                confirmskip.setVisibility(View.VISIBLE);
            }
        });

        yesskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmskip.setVisibility(View.GONE);
                skipquestion.setVisibility(View.VISIBLE);

                showhiddenskip.setVisibility(View.INVISIBLE);
                Animation in = AnimationUtils.loadAnimation(getContext(), R.anim.scale_y_downards);
                showhiddenskip.startAnimation(in);
                showhiddenskip.setVisibility(View.VISIBLE);
                showhiddenskip.startAnimation(in);
            }
        });

        noskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmskip.setVisibility(View.GONE);
                Animation in = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in);
                skipquestion.startAnimation(in);
                skipquestion.setVisibility(View.VISIBLE);
            }
        });
    }
}
