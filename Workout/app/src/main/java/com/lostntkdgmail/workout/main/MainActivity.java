package com.lostntkdgmail.workout.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.lostntkdgmail.workout.LiftSelection;
import com.lostntkdgmail.workout.R;
import com.lostntkdgmail.workout.TypeSelection;
import com.lostntkdgmail.workout.WeightSelection;

public class MainActivity extends FragmentActivity {

    private static final String TAG = "MainActivity";
    private PagerAdapter pagerAdapter;
    public static String TYPE;
    private NonSwipeLeftViewPager viewPager;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        viewPager = findViewById(R.id.container);

        setUpViewPager(viewPager);

        addFragment(new TypeSelection(), "TypeSelection");

    }

    public void addFragment(Fragment fm, String title) {
        if(pagerAdapter.containsFragment(title)) {
            int i = pagerAdapter.getFragmentIndex(title);
            getSupportFragmentManager().beginTransaction().replace(i, fm);
            pagerAdapter.removeFragment(i);
            pagerAdapter.addFragment(fm, title);
        }
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
        System.out.println("----------------------------COMING FROM FRAGMENT-------------------------------------------");
        System.out.println(((PagerAdapter)viewPager.getAdapter()).getItem(viewPager.getCurrentItem()));
        System.out.println(((PagerAdapter)viewPager.getAdapter()).getItem(viewPager.getCurrentItem()).getTag());

        viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);

        System.out.println("----------------------------GOING TO FRAGMENT-------------------------------------------");
        System.out.println(((PagerAdapter)viewPager.getAdapter()).getItem(viewPager.getCurrentItem()));
        System.out.println(((PagerAdapter)viewPager.getAdapter()).getItem(viewPager.getCurrentItem()).getTag());
    }
}
