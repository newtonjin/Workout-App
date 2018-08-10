package com.lostntkdgmail.workout.main;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.lostntkdgmail.workout.data_entry.EditLift;
import com.lostntkdgmail.workout.data_entry.LiftSelection;
import com.lostntkdgmail.workout.data_entry.NewLift;
import com.lostntkdgmail.workout.data_entry.TypeSelection;
import com.lostntkdgmail.workout.data_entry.WeightSelection;
import com.lostntkdgmail.workout.users.EditUser;
import com.lostntkdgmail.workout.users.NewUser;
import com.lostntkdgmail.workout.users.SelectUser;
import com.lostntkdgmail.workout.view.CalendarFragment;
import com.lostntkdgmail.workout.view.ViewHistoryFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * The PagerAdapter used to manage the Fragments
 */
public class PagerAdapter extends FragmentPagerAdapter {

    private final List<String> fragmentTagList = new ArrayList<>(); //List used to keep up with the current fragments
    public static final List<Fragment> fragmentList = new ArrayList<>();
    private FragmentManager manager;

    /**
     * Creates the PagerAdapter
     * @param fragmentManager The FragmentManager from the Main Activity
     */
    public PagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        this.manager = fragmentManager;
    }

    /**
     * Adds a Fragment to the adapter
     * @param fragment The Fragment to add
     * @param title The title of the Fragment
     */
    public void addFragment(Fragment fragment, String title) {
           manager.beginTransaction().add(fragment,title).attach(fragment).commit();
           fragmentTagList.add(title);
           fragmentList.add(fragment);
           notifyDataSetChanged();
    }

    /**
     * Gets a new instance of the item at the specified position
     * @param pos The position of the Fragment
     * @return The Fragment at the position
     */
    @Override
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
            case(ViewHistoryFragment.TITLE):
                return new ViewHistoryFragment();
            case(CalendarFragment.TITLE):
                return new CalendarFragment();
            case(NewLift.TITLE):
                return new NewLift();
            case(EditLift.TITLE):
                return new EditLift();
            default:
                throw new RuntimeException("Item does not exist: " + currentFrame);
        }
    }

    /**
     * Instantiates the Fragment at the specified position
     * @param container The container of the fragments
     * @param position The position of the Fragment
     * @return The Fragment at the position
     */
    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment createdFragment = (Fragment)super.instantiateItem(container,position);
        fragmentList.set(position, createdFragment);
        return createdFragment;
    }

    /**
     * Gets the number of Fragments
     * @return The number of Fragments
     */
    @Override
    public int getCount() {
        return fragmentTagList.size();
    }

    /**
     * Gets the index of the Fragment with the specified title
     * @param title The title of the Fragment
     * @return The index of the Fragment
     */
    public int getFragmentIndex(String title) {
        return fragmentTagList.indexOf(title);
    }

    public Fragment getFragmentByTitle(String title) {
        return fragmentList.get(fragmentTagList.indexOf(title));
    }

    /**
     * Gets the title of the page
     * @param position The position of the page
     * @return The title of the page
     */
    @Override
    public CharSequence getPageTitle(int position) {
        System.out.println(position);
        if(position > -1 && position < getCount()) {
            return fragmentTagList.get(position);
        }
        return null;
    }
}
