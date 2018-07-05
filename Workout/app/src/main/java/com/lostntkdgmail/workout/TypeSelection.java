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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.lostntkdgmail.workout.main.MainActivity;


/**
 * The Activity for selecting a Type of lift
 */
public class TypeSelection extends Fragment {
    private LiftTableAccessor liftTable;
    private ListView typeList;
    private static final String TAG = "TypeSelection";

    private Button test;

    public TypeSelection() {
        //required and empty constructor
        System.out.println("TEst 3?!");
    }

    public static TypeSelection newInstance() {
        System.out.println("Test 4");
        TypeSelection fragment = new TypeSelection();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("Test 5");
        View view = inflater.inflate(R.layout.type_selection, container, false);
        System.out.println("Test 6");
        liftTable = new LiftTableAccessor(this.getContext());


        if(liftTable.getNumberOfRows() < 1)
            liftTable.fillWithData();
        String[] types = liftTable.getTypes();
        typeList = view.findViewById(R.id.listv);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(),R.layout.list_item,R.id.listText,types);
        typeList.setAdapter(adapter);

        System.out.println("Test 1");
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
                System.out.println("test");
                String type = (String)typeList.getItemAtPosition(position);
                Log.d("Debug","Selected: "+type);

                Toast.makeText(getActivity(), "Going to " + type, Toast.LENGTH_SHORT);
                ((MainActivity)getActivity()).setViewPager(1);

            }
        });
        System.out.println("Test 2");
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
        System.out.println("Test 7");
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }




}