package com.lostntkdgmail.workout.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.lostntkdgmail.workout.R;
import com.lostntkdgmail.workout.data_entry.TypeSelection;

import com.lostntkdgmail.workout.users.SelectUser;


public class MainActivity extends FragmentActivity {

    private static final String TAG = "MainActivity";
    private PagerAdapter pagerAdapter;
    public static String TYPE, LIFT;
    private NonSwipeViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        viewPager = findViewById(R.id.container);

        setUpViewPager(viewPager);

        viewPager.setCurrentItem(0);

        //Setting up navigation
        BottomNavigationView navBar = findViewById(R.id.bottom_navigation);
        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()) {
                    case R.id.recordWeightsNav:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.switchUserNav:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.pastEntriesNav:
                        break;
                }
                return false;
            }
        });

    }

    public void addFragment(Fragment fm, String title) {
        pagerAdapter.addFragment(fm, title);
        viewPager.setAdapter(pagerAdapter);
    }


    private void setUpViewPager(ViewPager vp) {
        pagerAdapter.addFragment(new TypeSelection(), "TypeSelection");
        pagerAdapter.addFragment(new SelectUser(), "SelectUser");
        viewPager.setAdapter(pagerAdapter);
    }

    public void setViewPager(int fragmentNum) {
        viewPager.setCurrentItem(fragmentNum);
    }

    @Override
    public void onBackPressed() {
//<<<<<<< HEAD
       //String currentFragment = ((PagerAdapter)viewPager.getAdapter()).getItemTitle(viewPager.getCurrentItem());

       //if(currentFragment.equals("TypeSelection")){
//=======
            System.out.println(viewPager.getCurrentItem());
            if (viewPager.getCurrentItem() > 0) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            } else {
                super.onBackPressed();
            }

//>>>>>>> Tyler-dev
        //    super.onBackPressed();
        //} else if(currentFragment.equals("LiftSelection")){
        //    setViewPager(pagerAdapter.HOME);
        //    pagerAdapter.removeFragment(pagerAdapter.LIFT);
        //    viewPager.setAdapter(pagerAdapter);
        //} else if (currentFragment.equals("WeightSelection")) {
        //    setViewPager(pagerAdapter.LIFT);
        //    pagerAdapter.removeFragment(pagerAdapter.WEIGHT);
        //    viewPager.setAdapter(pagerAdapter);
        //}

    }
}
