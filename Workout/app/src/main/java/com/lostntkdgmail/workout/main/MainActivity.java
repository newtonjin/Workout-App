package com.lostntkdgmail.workout.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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


public class MainActivity extends AppBaseActivity {

    private static final String TAG = "MainActivity";
    private PagerAdapter pagerAdapter;
    private NonSwipeViewPager viewPager;
    public static String type, lift;
    public static String TYPE, LIFT, USER;
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

        viewPager = (NonSwipeViewPager) findViewById(R.id.container);

        setUpViewPager(viewPager);

        viewPager.setCurrentItem(0);

    }

    public void addFragment(Fragment fm, String title) {
        pagerAdapter.addFragment(fm, title);
        pagerAdapter.notifyDataSetChanged();
    }

    private void setUpViewPager(ViewPager vp) {
        pagerAdapter.addFragment(new TypeSelection(), TypeSelection.TITLE);
        pagerAdapter.addFragment(new LiftSelection(), LiftSelection.TITLE);
        pagerAdapter.addFragment(new WeightSelection(), WeightSelection.TITLE);
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

    public NonSwipeViewPager getViewPager() {
        return viewPager;
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() > 0) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.recordWeightsNav:
                viewPager.setCurrentItem(0);
                break;
            case R.id.switchUserNav:
                Intent intent = new Intent(getBaseContext(), UserSelectionActivity.class);
                intent.putExtra("TYPE", type);
                startActivity(intent);
                break;
            case R.id.pastEntriesNav:
                break;
        }
        return false;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        liftTable.close();
        weightTable.close();
        userTable.close();
    }
}
