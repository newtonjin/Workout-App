package com.lostntkdgmail.workout.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

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
        if(title.equals("TypeSelection") || title.equals("SelectUser")) {
            mFragmentList.add(fm);
            mFragmentTitleList.add(title);
        } else {


            int iOfUser = mFragmentTitleList.indexOf("SelectUser");
            String tag = "SelectUser";

            // Put the select user fragment at the end
            mFragmentList.add(mFragmentList.get(iOfUser));
            mFragmentTitleList.add(tag);

            // Remove it's previous position
            mFragmentList.remove(iOfUser);
            mFragmentTitleList.remove(iOfUser);

            // Add the new fragment in it's place
            mFragmentList.add(iOfUser, fm);
            mFragmentTitleList.add(iOfUser, title);


            notifyDataSetChanged();

            //from last commit in case this doesn't work
           //mFragmentList.add(fm);
           //mFragmentTitleList.add(title);
           //notifyDataSetChanged();
        }
    }

    public void removeFragment(int index) {
        mFragmentList.add(mFragmentList.get(mFragmentTitleList.indexOf("SelectUser") - 1));
        mFragmentList.remove(mFragmentTitleList.lastIndexOf("SelectUser"));
        //mFragmentList.remove(index);
        //mFragmentTitleList.remove(index);
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
