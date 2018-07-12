package com.lostntkdgmail.workout.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * A Base Fragment for all of the Fragments in this project
 */
public abstract class BaseFragment extends Fragment {
    /**
     * Reloads the Fragment. Usually does nothing, some Fragments implement this.
     */
    public void reload() {
    }

    /**
     * Determines what happens when the Fragment is resumed. Currently just reloads the Fragment
     */
    @Override
    public void onResume() {
        super.onResume();
        if(getContext() != null)
            reload();
        Log.d(getClass().getSimpleName(),"onResume called");
    }

    /**
     * Determines what happens when the Fragment is visible or not, currently just reloads the Fragment
     * @param isVisible True if the Fragment is visible
     */
    @Override
    public void setUserVisibleHint(boolean isVisible) {
        if(isVisible && getContext() != null)
            reload();
        Log.d(getClass().getSimpleName(),"setUserVisibleHint("+isVisible+") called");
    }

    /**
     * Determines what happens when the Fragment is attached to the Activity
     * @param context The current context
     */
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(getClass().getSimpleName(),"onAttach called");
    }

    /**
     * Determines what happens when the Fragment is detached from the Activity
     */
    public void onDetach() {
        super.onDetach();
        Log.d(getClass().getSimpleName(),"onDetach called");
    }

    /**
     * Determines what happens when the Fragment is destroyed
     */
    public void onDestroy() {
        super.onDestroy();
        Log.d(getClass().getSimpleName(), "onDestroy called");
    }

}
