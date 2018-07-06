package com.lostntkdgmail.workout.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.lostntkdgmail.workout.R;
import com.lostntkdgmail.workout.data_entry.TypeSelection;
import com.lostntkdgmail.workout.users.NewUser;
import com.lostntkdgmail.workout.users.SelectUser;

public class userSelectionActivity extends FragmentActivity {

    private static final String TAG = "UserSelection";
    private PagerAdapter pagerAdapter;
    private NonSwipeViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_selection);

        pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        viewPager = findViewById(R.id.container);

        setUpViewPager(viewPager);

        viewPager.setCurrentItem(0);

        //Setting up navigation
        BottomNavigationView navBar = findViewById(R.id.bottom_navigation);
        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
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

    private void setUpViewPager(ViewPager vp) {
        pagerAdapter.addFragment(new SelectUser(), "TypeSelection");
        pagerAdapter.addFragment(new NewUser(), "SelectUser");
        viewPager.setAdapter(pagerAdapter);
    }

    public void setViewPager(int fragmentNum) {
        viewPager.setCurrentItem(fragmentNum);
    }

    @Override
    public void onBackPressed() {
        System.out.println(viewPager.getCurrentItem());
        if (viewPager.getCurrentItem() > 0) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        } else {
            super.onBackPressed();
        }

    }
}
