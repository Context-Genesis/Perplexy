package com.rohanx96.admobproto.utils;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.os.Looper;
import android.view.Display;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.rohanx96.admobproto.R;

import java.util.Random;

/**
 * Created by rose on 5/3/16.
 */
public class FallingDrawables {
    Context mContext;
    private ViewGroup mParentView;
    private int mNoOfDrawables = 15;
    private int mDrawablesInRow;
    private int mDrawableSize = 40;
    public static final int NO_OF_COLORS = 9;
    public static final int NO_OF_LIGHT_COLORS = 7;
    private int mWindowWidth;
    private int mWindowHeight;
    private Handler mainThread;
    private Thread workerThread;

    private boolean isRunning = false;

    public FallingDrawables(Context context, ViewGroup view) {
        super();
        mContext = context;
        mParentView = view;
        mainThread = new Handler(context.getMainLooper());
    }

    public void createAnimation() {
        setmDrawablesInRow();
        workerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                isRunning = true;
                Looper.prepare();
                int count =0 ;
                int colorChange = 0 ;
                while(true){
                    //Log.i("animation", "inside for loop");
                    if (!isRunning)
                        return;
                    createDrawablesForRow();
                    try {
                        Thread.sleep(1400);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (count%7 == 0){
                        final ValueAnimator colorAnimation = ValueAnimator.ofObject
                                (new ArgbEvaluator(), getBackgroundColor(colorChange, mContext), getBackgroundColor(colorChange + 1, mContext));
                        colorAnimation.setDuration(4000);
                        colorChange++;
                        if (colorChange == NO_OF_COLORS)
                            colorChange = 0;
                        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(final ValueAnimator animation) {
                                mainThread.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mParentView.setBackgroundColor((int) animation.getAnimatedValue());
                                    }
                                });
                            }
                        });
                        mainThread.post(new Runnable() {
                            @Override
                            public void run() {
                                colorAnimation.start();
                            }
                        });
                    }
                    count++;

                }
            }
        });
        workerThread.start();
    }

    /** Sets the maximum number of drawables that can be displayed in a row on screen */
    public void setmDrawablesInRow() {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        mWindowWidth = size.x;
        mWindowHeight = size.y;
        this.mDrawablesInRow = mWindowWidth / mDrawableSize;
    }

    public void createDrawablesForRow() {
        int noOfDrawables = generateRandomInt(2, mDrawablesInRow);
        int previousPosition = mDrawableSize/2;
        for (int i = 0; i < noOfDrawables; i++) {
            final int position;
            if(i==0){
                if (noOfDrawables < mDrawablesInRow/2)
                    position = generateRandomInt(mDrawableSize/2,300);
                else position = generateRandomInt(mDrawableSize/2,80);
            }
            else
                position = previousPosition + generateRandomInt(50,100);
            if (position>=mWindowWidth)
                break;
            addImageView(createImageView(position));
            previousPosition = position;
        }
    }

    public ImageView createImageView(int position) {
        ImageView fallingDrawable = new ImageView(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mDrawableSize, mDrawableSize);
        fallingDrawable.setLayoutParams(params);
        fallingDrawable.setX(position);
        fallingDrawable.setY(0);
        fallingDrawable.setAlpha(0.7f);
        fallingDrawable.setScaleType(ImageView.ScaleType.FIT_CENTER);
        int imageDrawable = getImageDrawable();
        if (imageDrawable != -1)
            fallingDrawable.setImageResource(imageDrawable);
        return fallingDrawable;
    }

    public void addImageView(final ImageView drawable) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                mParentView.addView(drawable);
                drawable.animate().alpha(0).rotation(760).translationY(mWindowHeight/2.5f).setDuration(20000).setInterpolator(new DecelerateInterpolator())
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                mParentView.removeView(drawable);
                            }
                        })
                        .start();
            }
        });
    }

    public int generateRandomInt(int low, int high) {
        Random random = new Random();
        return random.nextInt(high - low) + low;
    }

    public int getImageDrawable() {
        switch (generateRandomInt(0, mNoOfDrawables)) {
            case 0:
                return R.drawable.equal;
            case 1:
                return R.drawable.help;
            case 2:
                return R.drawable.close;
            case 3:
                return R.drawable.flaskoutline;
            case 4:
                return R.drawable.division;
            case 5:
                return R.drawable.plus;
            case 6:
                return R.drawable.average_math;
            case 7:
                return R.drawable.geometry;
            case 9:
                return R.drawable.idea;
            case 10:
                return R.drawable.infinity;
            case 11:
                return R.drawable.key;
            case 13:
                return R.drawable.physics;
            case 14:
                return R.drawable.puzzle;
            default:
                return -1;
        }
    }

    public static int getBackgroundColor(int count, Context mContext){
        switch (count){
            case 0:
                return  mContext.getResources().getColor(R.color.blue_d_ebony_clay);
            case 1:
                return  mContext.getResources().getColor(R.color.pink_d_material);
            case 2:
                return  mContext.getResources().getColor(R.color.black_d_material);
            case 3:
                return  mContext.getResources().getColor(R.color.purple_d_honey_flower);
            case 4:
                return  mContext.getResources().getColor(R.color.blue_d_chambray);
            case 5:
                return  mContext.getResources().getColor(R.color.purple_d_rebecca);
            case 6:
                return mContext.getResources().getColor(R.color.black_d_material_black);
            case 7:
                return mContext.getResources().getColor(R.color.teal_d_material);
            case 8:
                return mContext.getResources().getColor(R.color.black_d_blue);
            default:
              return  mContext.getResources().getColor(R.color.blue_d_chambray);
        }
    }

    public static int getLightBackgroundColor(int position, Context context){
        switch (position){
            case 0:
                return context.getResources().getColor(R.color.blue_l_steel_blue);
            case 1:
                return context.getResources().getColor(R.color.purple_l_plum);
            case 2:
                return context.getResources().getColor(R.color.red_l_chestnut);
            case 3:
                return context.getResources().getColor(R.color.blue_l_lynch);
            case 4:
                return context.getResources().getColor(R.color.blue_d_chambray);
            case 5:
                return context.getResources().getColor(R.color.green_d_sea);
            case 6:
                return context.getResources().getColor(R.color.orange_l_crusta);
            default:
                return context.getResources().getColor(R.color.blue_l_steel_blue);
        }
    }

    /** The isRunning boolean is set to false. This causes the animation to stop as it checks this value in every iteration */
    public void stopAnimation(){
        isRunning = false;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public boolean getIsRunning(){
        return isRunning;
    }
}
