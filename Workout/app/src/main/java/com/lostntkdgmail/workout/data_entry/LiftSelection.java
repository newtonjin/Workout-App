package com.lostntkdgmail.workout.data_entry;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lostntkdgmail.workout.R;
import com.lostntkdgmail.workout.main.BaseFragment;
import com.lostntkdgmail.workout.main.MainActivity;

import java.util.Objects;


/**
 * The Fragment for Selecting a lift
 */
public class LiftSelection extends BaseFragment {
    public static final String TITLE = "LiftSelection";
    private ListView liftList;
    private TextView text;
    private static String[] lifts;
    private static String lastType;

    /**
     * Creates the fragment
     * @param inflater The inflater to inflate the layout
     * @param container The container to put the Fragment inside of
     * @param savedInstanceState The saved state
     * @return The view to display
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.lift_selection, container, false);
        view = setUpListView(view);
        text = view.findViewById(R.id.selectLiftText);
        text.setText(MainActivity.TYPE);
        if(lastType == null)
            lastType = MainActivity.TYPE;
        return view;
    }
    /**
     * Sets up the list view which shows all of the different types
     * @param view The View that was inflated in onCreateView
     */
    public View setUpListView(View view) {
        if(lifts == null || !MainActivity.TYPE.equals(lastType)) {
            lifts = MainActivity.liftTable.getLifts(MainActivity.TYPE);
            lastType = MainActivity.TYPE;
        }
        liftList = view.findViewById(R.id.liftList);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(this.getContext()), R.layout.list_item, R.id.listEntry, lifts);
        liftList.setAdapter(adapter);

        liftList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * Determines what happens when one of the Items is selected
             * @param adapterView The adapter view
             * @param view The ListView
             * @param position The position of the view in the adapter
             * @param id The row id of the selected item
             */
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String lift = (String)liftList.getItemAtPosition(position);
                Log.d("Debug","Selected: " + lift);

                MainActivity.LIFT = lift;
                int index = ((MainActivity) Objects.requireNonNull(getActivity())).getPagerAdapter().getFragmentIndex(WeightSelection.TITLE);
                WeightSelection s = (WeightSelection)(((MainActivity) getActivity()).getPagerAdapter().getItem(index));
                MainActivity.currentPos++;
                ((MainActivity)getActivity()).setViewPager(WeightSelection.TITLE);

            }
        });
        return view;
    }
    /**
     * Reloads the Fragment. Specifically updates the list of lifts to reflect the current type
     */
    public void reload() {
        if(lifts == null || !MainActivity.TYPE.equals(lastType)) {
            lifts = MainActivity.liftTable.getLifts(MainActivity.TYPE);
            lastType = MainActivity.TYPE;
            ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(this.getContext()), R.layout.list_item, R.id.listEntry, lifts);
            liftList.setAdapter(adapter);
            text.setText(MainActivity.TYPE);
        }
    }

    public String[] getLifts() {
        return lifts;
    }
}