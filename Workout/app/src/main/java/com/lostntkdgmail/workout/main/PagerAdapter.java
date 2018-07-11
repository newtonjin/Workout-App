package com.lostntkdgmail.workout.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.support.v4.app.FragmentPagerAdapter;

import com.lostntkdgmail.workout.data_entry.LiftSelection;
import com.lostntkdgmail.workout.data_entry.TypeSelection;
import com.lostntkdgmail.workout.data_entry.WeightSelection;
import com.lostntkdgmail.workout.users.EditUser;
import com.lostntkdgmail.workout.users.NewUser;
import com.lostntkdgmail.workout.users.SelectUser;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends FragmentPagerAdapter {

    private final List<String> mFragmentTitleList = new ArrayList<>();
    private FragmentManager manager;

    public PagerAdapter(FragmentManager fragmentManager, int containerId) {
        super(fragmentManager);
        this.manager = fragmentManager;
    }

    public void addFragment(Fragment fm, String title) {
           manager.beginTransaction().disallowAddToBackStack().add(fm,title).attach(fm).commit();
           mFragmentTitleList.add(title);
           notifyDataSetChanged();
    }
    public Fragment getItem(int pos) {
        String currentFrame = mFragmentTitleList.get(pos);
        switch (currentFrame) {
            case(LiftSelection.TITLE):
                return new LiftSelection();
            case(TypeSelection.TITLE):
                return new TypeSelection();
            case(WeightSelection.TITLE):
                return new WeightSelection();
            case(EditUser.TITLE):
                return new EditUser();
            case(NewUser.TITLE):
                return new NewUser();
            case(SelectUser.TITLE):
                return new SelectUser();
            default:
                return null;
        }
    }
    public Fragment getItem(String title) {
        return manager.getFragments().get(mFragmentTitleList.indexOf(title));
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
        return mFragmentTitleList.size();
    }
}
