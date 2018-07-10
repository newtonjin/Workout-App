package com.lostntkdgmail.workout.users;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.lostntkdgmail.workout.R;
import com.lostntkdgmail.workout.database.UserTableAccessor;
import com.lostntkdgmail.workout.main.MainActivity;

public class EditUser extends Fragment {
    public static final String TITLE = "editUser";
    public static long userId;

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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    public void submit(View button) {
        TextInputLayout firstName = getActivity().findViewById(R.id.editFirstNameInput);
        TextInputLayout lastName = getActivity().findViewById(R.id.editLastNameInput);
        if(firstName.getEditText().getText().toString().length() > 0) {
            MainActivity.userTable.updateData(userId, firstName.getEditText().getText().toString(), lastName.getEditText().getText().toString());
            int index = ((MainActivity)getActivity()).getPagerAdapter().getFragmentIndex(SelectUser.TITLE);
            ((SelectUser)((MainActivity)getActivity()).getPagerAdapter().getItem(index)).reload();
            ((MainActivity)getActivity()).setViewPager(SelectUser.TITLE);
            //getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        }
        //TODO: Add confirmation message
    }
}
