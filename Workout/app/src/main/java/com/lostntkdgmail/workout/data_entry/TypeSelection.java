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
import android.widget.Button;
import android.widget.ListView;

import com.lostntkdgmail.workout.R;
import com.lostntkdgmail.workout.main.MainActivity;


/**
 * The Activity for selecting a Type of lift
 */

public class TypeSelection extends Fragment {
    private static final String TAG = "TypeSelection";
    private ListView typeList;
    private Button test;
    private boolean liftInitialized = false;

    public TypeSelection() {
        //required and empty constructor
    }

    public static TypeSelection newInstance() {

        TypeSelection fragment = new TypeSelection();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.type_selection, container, false);

        if(MainActivity.liftTable.getNumberOfRows() < 1)
            MainActivity.liftTable.fillWithData();
        String[] types = MainActivity.liftTable.getTypes();
        typeList = view.findViewById(R.id.typeList);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), R.layout.list_item, R.id.listEntry, types);
        typeList.setAdapter(adapter);

        typeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * Determines what happens when one of the Items is selected
             * @param adapterView The adapter view
             * @param view The ListView
             * @param position The position of the view in the adapter
             * @param id The row id of the selected item
             */
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                MainActivity.TYPE = (String)typeList.getItemAtPosition(position);
                Log.d("Debug","Selected: " + MainActivity.TYPE);

                if(!liftInitialized) {
                    ((MainActivity) getActivity()).addFragment(new LiftSelection(), "LiftSelection");
                    liftInitialized = true;
                }
                else {
                    int index = ((MainActivity) getActivity()).getPagerAdapter().getFragmentIndex("LiftSelection");
                    LiftSelection s = (LiftSelection)(((MainActivity) getActivity()).getPagerAdapter().getItem(index));
                    s.reload();
                }
                ((MainActivity)getActivity()).setViewPager("LiftSelection");
            }
        });
        return view;
    }

    /**
     * Creates the Activity and sets up the data
     * @param savedInstanceState The last saved state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Debug","Launching Activity: TypeSelection");

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