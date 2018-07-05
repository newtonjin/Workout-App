package com.lostntkdgmail.workout.main;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.lostntkdgmail.workout.LiftSelection;
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

        pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        viewPager = (ViewPager) findViewById(R.id.container);

        // This is where the control of which fragment appears first occurs.
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(pagerAdapter.HOME);
        setUpViewPager(viewPager);

        System.out.println("Test 9");


    }

    private void setUpViewPager(ViewPager vp) {
        System.out.println("Test 8");
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TypeSelection(), "TypeSelection");
        adapter.addFragment(new LiftSelection(), "LiftSelection");
        viewPager.setAdapter(adapter);
    }

    public void setViewPager(int fragmentNum) {
        System.out.println("Test 10");
        viewPager.setCurrentItem(fragmentNum);
    }

}
