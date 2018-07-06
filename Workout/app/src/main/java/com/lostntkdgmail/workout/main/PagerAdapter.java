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

    public static final int HOME = 0;
    public static final int LIFT = 1;
    public static final int WEIGHT = 2;

    public PagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public void addFragment(Fragment fm, String title) {
        if(!mFragmentTitleList.contains(title)) {
            mFragmentList.add(fm);
            mFragmentTitleList.add(title);
            notifyDataSetChanged();
        } else {
            System.out.println(title + " already exists, replacing it");
            int foundIndex;
            foundIndex = mFragmentTitleList.indexOf(title);
            mFragmentList.remove(foundIndex);
            mFragmentList.add(foundIndex, fm);
            mFragmentTitleList.remove(foundIndex);
            mFragmentTitleList.add(foundIndex, title);
        }
        }

    public void removeFragment(int index) {
        mFragmentList.remove(index);
        mFragmentTitleList.remove(index);
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
