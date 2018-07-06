package com.lostntkdgmail.workout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.lostntkdgmail.workout.main.MainActivity;
import com.lostntkdgmail.workout.main.PagerAdapter;

import com.lostntkdgmail.workout.database.LiftTableAccessor;


/**
 * The Activity for selecting a Type of lift
 */

public class TypeSelection extends Fragment {
    private static final String TAG = "TypeSelection";
    private LiftTableAccessor liftTable;
    private ListView typeList;
    private Button test;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.type_selection, container, false);
        liftTable = new LiftTableAccessor(this.getContext());

        if(liftTable.getNumberOfRows() < 1)
            liftTable.fillWithData();
        String[] types = liftTable.getTypes();
        typeList = view.findViewById(R.id.typeList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(),R.layout.list_item,R.id.listEntry,types);
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
                ((MainActivity)getActivity()).TYPE = (String)typeList.getItemAtPosition(position);
                Log.d("Debug","Selected: " + ((MainActivity)getActivity()).TYPE);

                Toast.makeText(getActivity(), "Going to " + ((MainActivity)getActivity()).TYPE, Toast.LENGTH_SHORT).show();
                //((MainActivity)getActivity()).addFragment(new LiftSelection(), "LiftSelection");
                //((MainActivity)getActivity()).setViewPager(1);


                LiftSelection fragment = new LiftSelection();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.add(R.id.fragment_container, fragment, "LiftSelection");
                ft.commit();

                System.out.println("---------------- IT SHOULD BE CHANGING NOW -------------------------");

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