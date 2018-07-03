package com.lostntkdgmail.workout.main;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.lostntkdgmail.workout.R;
import com.lostntkdgmail.workout.TypeSelection;

public class MainActivity extends FragmentActivity {

    private static final String TAG = "MainActivity";
    private PagerAdapter pagerAdapter;
    private ViewPager viewPager;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setting up the View Pager
        viewPager = (ViewPager) findViewById(R.id.container);
        fragmentManager = getSupportFragmentManager();
        pagerAdapter = new PagerAdapter(fragmentManager);

        // This is where the control of which fragment appears first occurs.
        viewPager.setAdapter(pagerAdapter);
        // Displays the Dashboard first.
        viewPager.setCurrentItem(pagerAdapter.HOME);

    }

    private void setViewPagersAdapter(ViewPager vp) {
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TypeSelection(), "TypeSelection");
        viewPager.setAdapter(adapter);
    }

    public void setViewPager(int fragmentNum) {
        viewPager.setCurrentItem(fragmentNum);
    }

}
