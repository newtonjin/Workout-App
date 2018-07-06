package com.lostntkdgmail.workout.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.lostntkdgmail.workout.LiftSelection;
import com.lostntkdgmail.workout.R;
import com.lostntkdgmail.workout.TypeSelection;
import com.lostntkdgmail.workout.WeightSelection;

public class MainActivity extends FragmentActivity {

    private static final String TAG = "MainActivity";
    private PagerAdapter pagerAdapter;
    public static String TYPE;
    private NonSwipeViewPager viewPager;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        viewPager = findViewById(R.id.container);

        setUpViewPager(viewPager);

    }

    public void addFragment(Fragment fm, String title) {
        pagerAdapter.addFragment(fm, title);
        viewPager.setAdapter(pagerAdapter);
    }


    private void setUpViewPager(ViewPager vp) {
        pagerAdapter.addFragment(new TypeSelection(), "TypeSelection");
        viewPager.setAdapter(pagerAdapter);
    }

    public void setViewPager(int fragmentNum) {
        viewPager.setCurrentItem(fragmentNum);
    }

    //WAKE ME UP INSIDE (cant wake up)
    @Override
    public void onBackPressed() {
        String currentFragment = ((PagerAdapter)viewPager.getAdapter()).getItemTitle(viewPager.getCurrentItem());

        if(currentFragment.equals("TypeSelection")){
            super.onBackPressed();
        } else if(currentFragment.equals("LiftSelection")){
            setViewPager(pagerAdapter.HOME);
            pagerAdapter.removeFragment(pagerAdapter.LIFT);
            viewPager.setAdapter(pagerAdapter);
        } else if (currentFragment.equals("WeightSelection")) {
            setViewPager(pagerAdapter.LIFT);
            pagerAdapter.removeFragment(pagerAdapter.WEIGHT);
            viewPager.setAdapter(pagerAdapter);
        }

    }
}
