package com.lostntkdgmail.workout;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lostntkdgmail.workout.main.MainActivity;

import java.util.zip.Inflater;
import com.lostntkdgmail.workout.database.LiftTableAccessor;


/**
 * The activity for Selecting a lift
 */

public class LiftSelection extends Fragment {
    private static final String TAG = "LiftSelection";
    private LiftTableAccessor liftTable;
    private ListView liftList;
    private TextView text;

    public LiftSelection() {
        // Required and empty constructor
    }

    public static LiftSelection newInstance(int index) {
        LiftSelection fragment = new LiftSelection();

        // Supply index input as argument
        Bundle args = new Bundle();
        args.putInt("index", index);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.lift_selection, container, false);
        liftTable = new LiftTableAccessor(this.getContext());
        view = setUpListView(view);
        text = view.findViewById(R.id.selectLiftText);
        text.setText(((MainActivity)getActivity()).TYPE);
        liftTable = new LiftTableAccessor(this.getContext());
        return view;
    }

    /**
     * Creates the activity and sets up the data
     * @param savedInstanceState The last savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Debug","Launching Activity LiftSelection");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    /**
     * Cleans up the Activity and closes the database accessors
     */


    /**
     * Sets up the list view which shows all of the different types
     */
    public View setUpListView(View view) {
        String[] lifts;
            lifts = liftTable.getLifts(((MainActivity)getActivity()).TYPE);
        liftList = view.findViewById(R.id.liftList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), R.layout.list_item, R.id.listEntry, lifts);
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
                ((MainActivity)getActivity()).addFragment(new WeightSelection(), "WeightSelection");
                ((MainActivity)getActivity()).setViewPager(2);

            }
        });
        return view;
    }
}