package com.lostntkdgmail.workout.data_entry;

/* Created by Tom Pedraza and Tyler Atkinson
 * Workout-App
 * https://github.com/tha7556/Workout-App
 */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
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


/**
 * The Fragment for Selecting a lift
 */
public class LiftSelection extends BaseFragment {
    public static final String TITLE = "LiftSelection";
    private ListView liftList;
    private TextView text;
    public static String[] lifts;
    private static String lastType;
    public static boolean newInitialized = false, EInitialized = false;
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
        text.setText(MainActivity.TYPE);

        if(lastType == null)
            lastType = MainActivity.TYPE;

        FloatingActionButton newLift = view.findViewById(R.id.newLiftButton);


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

        return view;
    }
    /**
     * Sets up the list view which shows all of the different types
     * @param view The View that was inflated in onCreateView
     */
    public View setUpListView(View view) {
        if(lifts == null) {
            lifts = ((MainActivity)getActivity()).liftTable.getLifts(((MainActivity)getActivity()).TYPE);
        }
        liftList = view.findViewById(R.id.liftList);
        this.adapter = new ArrayAdapter<>(getActivity(), R.layout.lifts_list_item, R.id.liftListEntry, lifts);
        liftList.setAdapter(this.adapter);
        liftList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String lift = (String) liftList.getItemAtPosition(position);
                Log.d("Debug", "Selected: " + lift);

                ((MainActivity)getActivity()).LIFT = lift;
                String test = ((MainActivity)getActivity()).LIFT;
                ((MainActivity)getActivity()).updateWeightTitle();
                ((MainActivity)getActivity()).currentPos++;
                ((MainActivity) getActivity()).setViewPager(WeightSelection.TITLE);

            }
        });
        FloatingActionButton newLift = view.findViewById(R.id.newLiftButton);
        newLift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!newInitialized) {
                    ((MainActivity) getActivity()).addFragment(new NewLift(), NewLift.TITLE);
                    newInitialized = true;
                }
                ((MainActivity) getActivity()).setViewPager(NewLift.TITLE);
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
            this.adapter = new ArrayAdapter<>(getActivity(), R.layout.lifts_list_item, R.id.liftListEntry, lifts);
            liftList.setAdapter(adapter);
            text.setText(MainActivity.TYPE);
        }
    }

    public void updateList() {
        lifts = ((MainActivity)getActivity()).liftTable.getLifts(MainActivity.TYPE);
        this.adapter.notifyDataSetChanged();
    }

    @Override
    public String getTitle(){
        return TITLE;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}