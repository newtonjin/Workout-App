package com.lostntkdgmail.workout.data_entry;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lostntkdgmail.workout.R;
import com.lostntkdgmail.workout.main.BaseFragment;
import com.lostntkdgmail.workout.main.MainActivity;

import java.util.Objects;


/**
 * The Fragment for selecting a Type of lift
 */
public class TypeSelection extends BaseFragment {
    public static final String TITLE = "TypeSelection";
    private ListView typeList;
    public static boolean hasChanged = true; //Set this to true if a new type is added
    private static String[] types;

    /**
     * Creates the fragment
     * @param inflater The inflater to inflate the layout
     * @param container The container to put the Fragment inside of
     * @param savedInstanceState The saved state
     * @return The view to display
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.type_selection, container, false);
        if(hasChanged) {
            types = MainActivity.liftTable.getTypes();
            hasChanged = false;
        }
        typeList = view.findViewById(R.id.typeList);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(this.getContext()), R.layout.list_item, R.id.listEntry, types);
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
                int index = ((MainActivity) Objects.requireNonNull(getActivity())).getPagerAdapter().getFragmentIndex(LiftSelection.TITLE);
                LiftSelection s = (LiftSelection)(((MainActivity) getActivity()).getPagerAdapter().getItem(index));
                MainActivity.currentPos++;
                ((MainActivity)getActivity()).setViewPager(LiftSelection.TITLE);
            }
        });
        return view;
    }

    /**
     * Reloads the Fragment. Specifically updates the list of types if it has been indicated that there is a change
     */
    @Override
    public void reload() {
        if(hasChanged) {
            types = MainActivity.liftTable.getTypes();
            hasChanged = false;
            ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(this.getContext()), R.layout.list_item, R.id.listEntry, types);
            typeList.setAdapter(adapter);
        }
    }

}