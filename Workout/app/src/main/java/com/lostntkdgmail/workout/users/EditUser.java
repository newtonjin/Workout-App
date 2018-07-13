package com.lostntkdgmail.workout.users;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lostntkdgmail.workout.R;
import com.lostntkdgmail.workout.database.UserTableAccessor;
import com.lostntkdgmail.workout.main.BaseFragment;
import com.lostntkdgmail.workout.main.MainActivity;

/**
 * The Edit User Fragment
 */
public class EditUser extends BaseFragment {
    public static final String TITLE = "editUser";
    public static long userId;

    /**
     * Creates the fragment
     * @param inflater The inflater to inflate the layout
     * @param container The container to put the Fragment inside of
     * @param savedInstanceState The saved state
     * @return The view to display
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_user, container, false);
        Button submit = view.findViewById(R.id.editUserSubmitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit(view);
            }
        });
        Cursor cursor = MainActivity.userTable.select(userId);
        cursor.moveToFirst();
        TextInputLayout firstName = view.findViewById(R.id.editFirstNameInput);
        firstName.getEditText().setText(cursor.getString(UserTableAccessor.Columns.FIRST_NAME.ordinal()));
        TextInputLayout lastName = view.findViewById(R.id.editLastNameInput);
        lastName.getEditText().setText(cursor.getString(UserTableAccessor.Columns.LAST_NAME.ordinal()));
        return view;
    }

    /**
     * Updates the User in the database, only if there is text in the first name box
     * @param button The submit button
     */
    public void submit(View button) {
        TextInputLayout firstName = getActivity().findViewById(R.id.editFirstNameInput);
        TextInputLayout lastName = getActivity().findViewById(R.id.editLastNameInput);
        if(firstName.getEditText().getText().toString().length() > 0) {
            MainActivity.userTable.updateData(userId, firstName.getEditText().getText().toString(), lastName.getEditText().getText().toString());
            ((MainActivity)getActivity()).setViewPager(SelectUser.TITLE);
            getActivity().onBackPressed();
        }
        //TODO: Add confirmation message
    }
    @Override
    public String getTitle() {
        return TITLE;
    }
}
