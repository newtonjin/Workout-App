package com.lostntkdgmail.workout.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lostntkdgmail.workout.TypeSelection;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public final int HOME = 0;
    public final int LIFT = 1;
    public final int WEIGHT = 2;

    public PagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        mFragmentList.add(new TypeSelection());
    }

    public void addFragment(Fragment fm, String title) {
        mFragmentList.add(fm);
        mFragmentTitleList.add(title);

    }

    public Fragment getItem(int pos) {
        return mFragmentList.get(pos);
    }


    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
