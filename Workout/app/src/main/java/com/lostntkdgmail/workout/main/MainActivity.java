package com.lostntkdgmail.workout.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.lostntkdgmail.workout.R;
import com.lostntkdgmail.workout.data_entry.TypeSelection;
import com.lostntkdgmail.workout.users.SelectUser;


public class MainActivity extends AppBaseActivity {

    private static final String TAG = "MainActivity";
    private PagerAdapter pagerAdapter;
    private NonSwipeViewPager viewPager;
    public static String type, lift;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        viewPager = (NonSwipeViewPager) findViewById(R.id.container);

        // This is where the control of which fragment appears first occurs.
        setUpViewPager(viewPager);

        viewPager.setCurrentItem(0);

        //Setting up navigation


    }

    public void addFragment(Fragment fm, String title) {
        pagerAdapter.addFragment(fm, title);
        pagerAdapter.notifyDataSetChanged();
    }

    private void setUpViewPager(ViewPager vp) {
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new TypeSelection(), "TypeSelection");
        viewPager.setAdapter(pagerAdapter);
    }

    public void setViewPager(int fragmentNum) {
        viewPager.setCurrentItem(fragmentNum);
    }

    //Not working properly atm
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
        switch(item.getItemId()) {
            case R.id.recordWeightsNav:
                viewPager.setCurrentItem(0);
                break;
            case R.id.switchUserNav:
                Intent intent = new Intent(getBaseContext(),UserSelectionActivity.class);
                intent.putExtra("TYPE",type);
                startActivity(intent);
                break;
            case R.id.pastEntriesNav:
                break;
        }
        return false;
    }
}
