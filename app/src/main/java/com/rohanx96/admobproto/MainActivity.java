package com.rohanx96.admobproto;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;

import com.viewpagerindicator.CirclePageIndicator;

public class MainActivity extends FragmentActivity {

    private static final int NUM_PAGES = 2;

    private ViewPager mPager;
    private FrameLayout mContainer;

    private PagerAdapter mPagerAdapter;

    CirclePageIndicator circlePageIndicator;
    FallingDrawables fallingDrawables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContainer = (FrameLayout) findViewById(R.id.main_activity_container);
        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        circlePageIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        mPager.setAdapter(mPagerAdapter);
        circlePageIndicator.setViewPager(mPager);
        mContainer.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        fallingDrawables = new FallingDrawables(this,mContainer);
        fallingDrawables.createAnimation();
        fallingDrawables.setmDrawablesInRow();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0)
                return new FrontPageFragment();

            return new StatisticsFragment();
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}