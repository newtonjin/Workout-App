package com.lostntkdgmail.workout.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.lostntkdgmail.workout.data_entry.LiftSelection;
import com.lostntkdgmail.workout.data_entry.TypeSelection;
import com.lostntkdgmail.workout.data_entry.WeightSelection;
import com.lostntkdgmail.workout.users.EditUser;
import com.lostntkdgmail.workout.users.NewUser;
import com.lostntkdgmail.workout.users.SelectUser;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends FragmentPagerAdapter {

    private final List<String> fragmentTagList = new ArrayList<>(); //List used to keep up with the current fragments
    public static final List<Fragment> fragmentList = new ArrayList<>();
    private FragmentManager manager;

    public PagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        this.manager = fragmentManager;
    }

    public void addFragment(Fragment fragment, String title) {
           manager.beginTransaction().add(fragment,title).attach(fragment).commit();
           fragmentTagList.add(title);
           fragmentList.add(fragment);
           notifyDataSetChanged();
    }
    public Fragment getItem(int pos) {
        String currentFrame = fragmentTagList.get(pos);
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
                throw new RuntimeException("Item does not exist");
        }
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment createdFragment = (Fragment)super.instantiateItem(container,position);
        fragmentList.set(position, createdFragment);
        return createdFragment;
    }
    @Override
    public int getCount() {
        return fragmentTagList.size();
    }

    public int getFragmentIndex(String title) {
        return fragmentTagList.indexOf(title);
    }
}
