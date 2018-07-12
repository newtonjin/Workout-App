package com.lostntkdgmail.workout.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

public abstract class BaseFragment extends Fragment {
    public void reload() {
    }
    public void onResume() {
        super.onResume();
        if(getContext() != null)
            reload();
        Log.d(getClass().getSimpleName(),"onResume called");
    }
    public void setUserVisibleHint(boolean isVisible) {
        if(isVisible && getContext() != null)
            reload();
        Log.d(getClass().getSimpleName(),"setUserVisibleHint("+isVisible+") called");
    }
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(getClass().getSimpleName(),"onAttach called");
    }
    public void onDetach() {
        super.onDetach();
        Log.d(getClass().getSimpleName(),"onDetach called");
    }
    public void onDestroy() {
        super.onDestroy();
        Log.d(getClass().getSimpleName(), "onDestroy called");
    }

}
