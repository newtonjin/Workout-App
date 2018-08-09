package com.lostntkdgmail.workout.data_entry;


import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lostntkdgmail.workout.R;
import com.lostntkdgmail.workout.main.BaseFragment;
import com.lostntkdgmail.workout.main.MainActivity;
import com.lostntkdgmail.workout.users.NewUser;

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
    public static boolean newInitialized = false, delInitialized = false;
    private ArrayAdapter<String> adapter;

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
        text.setText(((MainActivity)getActivity()).TYPE);
        if(lastType == null)
            lastType = ((MainActivity)getActivity()).TYPE;

        Button newLift = view.findViewById(R.id.newLiftButton);
        Button delLift = view.findViewById(R.id.deleteLiftButton);

        newLift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!newInitialized) {
                    ((MainActivity) getActivity()).addFragment(new NewLift(), NewLift.TITLE);
                    newInitialized = true;
                }
                ((MainActivity) getActivity()).setViewPager(NewLift.TITLE);
            }
        });

        delLift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!delInitialized) {
                    ((MainActivity) getActivity()).addFragment(new DeleteLift(), DeleteLift.TITLE);
                    delInitialized = true;
                }
                ((MainActivity) getActivity()).setViewPager(DeleteLift.TITLE);
            }
        });
        return view;
    }
    /**
     * Sets up the list view which shows all of the different types
     * @param view The View that was inflated in onCreateView
     */
    public View setUpListView(View view) {
        if(lifts == null || !MainActivity.TYPE.equals(lastType)) {
            lifts = ((MainActivity)getActivity()).liftTable.getLifts(MainActivity.TYPE);
            lastType = MainActivity.TYPE;
        }
        liftList = view.findViewById(R.id.liftList);
        adapter = new ArrayAdapter<>(Objects.requireNonNull(this.getContext()), R.layout.list_item, R.id.listEntry, lifts);
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

                ((MainActivity)getActivity()).LIFT = lift;
                int index = ((MainActivity) Objects.requireNonNull(getActivity())).getPagerAdapter().getItemPosition(WeightSelection.TITLE);
                ((MainActivity)getActivity()).currentPos++;
                ((MainActivity)getActivity()).setViewPager(WeightSelection.TITLE);

            }
        });
        return view;
    }

    /**
     * Reloads the Fragment. Specifically updates the list of lifts to reflect the current type
     */
    public void reload() {
        //if(lifts == null || !MainActivity.TYPE.equals(lastType)) {
        if(getContext() != null) {
            lifts = MainActivity.liftTable.getLifts(MainActivity.TYPE);
            lastType = MainActivity.TYPE;
            ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(this.getContext()), R.layout.list_item, R.id.listEntry, lifts);
            liftList.setAdapter(adapter);
            text.setText(MainActivity.TYPE);
        }
    }

    public void updateList() {
        lifts = ((MainActivity)getActivity()).liftTable.getLifts(MainActivity.TYPE);
        if(adapter == null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(this.getContext()), R.layout.list_item, R.id.listEntry, lifts);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public String getTitle(){
        return TITLE;
    }
}