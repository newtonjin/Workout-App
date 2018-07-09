package com.lostntkdgmail.workout.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public PagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public void addFragment(Fragment fm, String title) {
           mFragmentList.add(fm);
           mFragmentTitleList.add(title);
           notifyDataSetChanged();
    }

    public Fragment getItem(int pos) {
        return mFragmentList.get(pos);
    }

    public String getItemTitle(int pos) {
        return mFragmentTitleList.get(pos);
    }

    public boolean containsFragment(String title) {
        return mFragmentTitleList.contains(title);
    }

    public int getFragmentIndex(String title) {
        return mFragmentTitleList.indexOf(title);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
