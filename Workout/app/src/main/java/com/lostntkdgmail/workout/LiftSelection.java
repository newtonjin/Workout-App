package com.lostntkdgmail.workout;


import android.app.Activity;
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

/**
 * The activity for Selecting a lift
 */
public class LiftSelection extends Fragment {
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
        text = view.findViewById(R.id.tvlift);
        text.setText(getActivity().getIntent().getStringExtra("TYPE"));
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
        Log.d("Debug","onDestroy() called for LiftSelection");
        liftTable.close();
        super.onAttach(context);
    }
    /**
     * Cleans up the Activity and closes the database accessors
     */


    /**
     * Sets up the list view which shows all of the different types
     */
    public View setUpListView(View view) {
        String[] lifts = liftTable.getLifts(getActivity().getIntent().getStringExtra("TYPE"));
        liftList = view.findViewById(R.id.listvlift);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(),R.layout.list_item,R.id.listText,lifts);
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
                Log.d("Debug","Selected: "+lift);
                ((MainActivity)getActivity()).setViewPager(2);

            }
        });
        return view;
    }
}