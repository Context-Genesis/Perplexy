package com.contextgenesis.perplexy.utils;

import android.animation.Animator;
import android.os.Handler;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;

/**
 * Created by rish on 12/3/16.
 */
public class RevealAnim {


    public static void animRevealOpen(View viewRoot) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            viewRoot.clearAnimation();
//            int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
            int cx = 0;
            int cy = (viewRoot.getTop() + viewRoot.getBottom());
            int finalRadius = Math.max(viewRoot.getWidth(), viewRoot.getHeight());

            final Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, 0, finalRadius);

            viewRoot.setVisibility(View.VISIBLE);
            anim.setDuration(500);
            anim.setInterpolator(new AccelerateInterpolator());
            anim.start();
        }
    }

    public static void animRevealExit(final View viewRoot) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            viewRoot.clearAnimation();
            int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
            int cy = (viewRoot.getTop() + viewRoot.getBottom());

            int finalRadius = Math.max(viewRoot.getWidth(), viewRoot.getHeight());

            // create the animation (the final radius is zero)
            final Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, finalRadius, 0);

            viewRoot.setVisibility(View.VISIBLE);
            anim.setDuration(500);
            anim.setInterpolator(new AccelerateInterpolator());
            anim.start();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    viewRoot.setVisibility(View.GONE);
                }
            }, 500);
        }
    }
}