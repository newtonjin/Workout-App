package com.lostntkdgmail.workout.data_entry;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lostntkdgmail.workout.R;
import com.lostntkdgmail.workout.data_entry.LiftSelection;
import com.lostntkdgmail.workout.database.LiftTableAccessor;
import com.lostntkdgmail.workout.database.UserTableAccessor;
import com.lostntkdgmail.workout.main.BaseFragment;
import com.lostntkdgmail.workout.main.MainActivity;

/**
 * The Edit User Fragment
 */
public class EditLift extends BaseFragment {
    public static final String TITLE = "editLift";
    public static String lift;

    /**
     * Creates the fragment
     * @param inflater The inflater to inflate the layout
     * @param container The container to put the Fragment inside of
     * @param savedInstanceState The saved state
     * @return The view to display
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_lift, container, false);
        Button submit = view.findViewById(R.id.editLiftSubmitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit(view);
            }
        });
        Cursor cursor = ((MainActivity)getActivity()).liftTable.select(((MainActivity)getActivity()).TYPE, lift);
        cursor.moveToFirst();
        TextInputLayout newLift = view.findViewById(R.id.editLiftInput);
        newLift.getEditText().setText(cursor.getString(LiftTableAccessor.Columns.LIFT.ordinal()));
        return view;
    }

    /**
     * Updates the User in the database, only if there is text in the first name box
     * @param button The submit button
     */
    public void submit(View button) {
        TextInputLayout newLift = getActivity().findViewById(R.id.editLiftInput);
        if(newLift.getEditText().getText().toString().length() > 0) {
            ((MainActivity)getActivity()).liftTable.updateData(((MainActivity)getActivity()).USER, ((MainActivity)getActivity()).TYPE, "");
            ((MainActivity)getActivity()).refreshLiftSelection();
            ((MainActivity)getActivity()).setViewPager(LiftSelection.TITLE);
        }
        Toast.makeText(getContext(),"Successfully modified: "+newLift.getEditText().getText().toString(),Toast.LENGTH_SHORT).show();
    }


    @Override
    public String getTitle() {
        return TITLE;
    }
}
