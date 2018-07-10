package com.lostntkdgmail.workout.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.lostntkdgmail.workout.R;
import com.lostntkdgmail.workout.data_entry.LiftSelection;
import com.lostntkdgmail.workout.data_entry.TypeSelection;

import com.lostntkdgmail.workout.data_entry.WeightSelection;
import com.lostntkdgmail.workout.database.LiftTableAccessor;
import com.lostntkdgmail.workout.database.UserTableAccessor;
import com.lostntkdgmail.workout.database.WeightTableAccessor;
import com.lostntkdgmail.workout.users.SelectUser;
import com.lostntkdgmail.workout.view.CalendarFragment;


public class MainActivity extends FragmentActivity {

    private static final String TAG = "MainActivity";
    private PagerAdapter pagerAdapter;
    public static String TYPE, LIFT, USER;
    public static int currentPos = 0;
    private NonSwipeViewPager viewPager;
    public static LiftTableAccessor liftTable;
    public static UserTableAccessor userTable;
    public static WeightTableAccessor weightTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        liftTable = new LiftTableAccessor(this);
        userTable = new UserTableAccessor(this);
        weightTable = new WeightTableAccessor(this);

        TYPE = liftTable.getTypes()[0];
        LIFT = liftTable.getLifts(TYPE)[0];
        USER = userTable.getNames()[0];
        setContentView(R.layout.activity_main);

        pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        viewPager = findViewById(R.id.container);

        setUpViewPager();

        setViewPager(TypeSelection.TITLE);

        //Setting up navigation
        BottomNavigationView navBar = findViewById(R.id.bottom_navigation);
        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()) {
                    case R.id.recordWeightsNav:
                        currentPos++;
                        setViewPager(TypeSelection.TITLE);
                        break;
                    case R.id.switchUserNav:
                        setViewPager(SelectUser.TITLE);
                        break;
                    case R.id.pastEntriesNav:
                        setViewPager(CalendarFragment.TITLE);
                        break;
                }
                return false;
            }
        });
    }

    private void setUpViewPager() {
        pagerAdapter.addFragment(new CalendarFragment(), CalendarFragment.TITLE);
        pagerAdapter.addFragment(new TypeSelection(), TypeSelection.TITLE);
        pagerAdapter.addFragment(new LiftSelection(), LiftSelection.TITLE);
        pagerAdapter.addFragment(new WeightSelection(), WeightSelection.TITLE);
        pagerAdapter.addFragment(new SelectUser(), SelectUser.TITLE);
        viewPager.setAdapter(pagerAdapter);
    }

    public void setViewPager(int fragmentNum) {
        viewPager.setCurrentItem(fragmentNum);
    }
    public void setViewPager(String title) {
        int index = pagerAdapter.getFragmentIndex(title);
        setViewPager(index);
    }

    public PagerAdapter getPagerAdapter() {
        return pagerAdapter;
    }


    @Override
    public void onBackPressed() {
        int index = pagerAdapter.getFragmentIndex(SelectUser.TITLE);
        int currentIndex = viewPager.getCurrentItem();
        if (currentIndex> 0 && currentIndex != index) {
            if(currentIndex < index) {
                viewPager.setCurrentItem(--currentPos);
            }
            else
                viewPager.setCurrentItem(currentIndex - 1);
        }
        else if(index == viewPager.getCurrentItem()) {
            setViewPager(currentPos);
        }
        else {
            super.onBackPressed();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        liftTable.close();
        weightTable.close();
        userTable.close();
    }
}
