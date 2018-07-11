package com.lostntkdgmail.workout.main;

import android.support.v4.app.Fragment;

public abstract class BaseFragment extends Fragment {
    public void reload() {
    }
    public void onResume() {
        super.onResume();
        if(getContext() != null)
            reload();
    }
    public void setUserVisibleHint(boolean isVisible) {
        if(isVisible && getContext() != null)
            reload();
    }
}
