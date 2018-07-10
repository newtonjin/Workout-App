package com.lostntkdgmail.workout.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lostntkdgmail.workout.R;
import com.lostntkdgmail.workout.database.LiftTableAccessor;

public class ViewHistoryFragment extends Fragment {

    private LiftTableAccessor mQuery;

    public ViewHistoryFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_history, container, false);
        ListView listory = view.findViewById(R.id.listory);



        return view;
    }
}
