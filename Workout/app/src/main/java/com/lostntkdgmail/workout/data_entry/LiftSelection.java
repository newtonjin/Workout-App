package com.lostntkdgmail.workout.data_entry;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lostntkdgmail.workout.R;
import com.lostntkdgmail.workout.main.MainActivity;

import java.util.Objects;


/**
 * The activity for Selecting a lift
 */

public class LiftSelection extends Fragment {
    public static final String TITLE = "LiftSelection";
    private ListView liftList;
    private TextView text;
    private boolean weightInitialized = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.lift_selection, container, false);
        view = setUpListView(view);
        text = view.findViewById(R.id.selectLiftText);
        text.setText(MainActivity.TYPE);
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
     * Sets up the list view which shows all of the different types
     */
    public View setUpListView(View view) {
        String[] lifts;
            lifts = MainActivity.liftTable.getLifts(MainActivity.TYPE);
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
                s.reload();
                MainActivity.currentPos++;
                ((MainActivity)getActivity()).setViewPager(WeightSelection.TITLE);

            }
        });
        return view;
    }
    public void reload() {
        System.out.println("Reloading");
        String[] lifts = MainActivity.liftTable.getLifts(MainActivity.TYPE);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(this.getContext()), R.layout.list_item, R.id.listEntry, lifts);
        liftList.setAdapter(adapter);
        text.setText(MainActivity.TYPE);
    }
}