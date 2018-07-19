package com.lostntkdgmail.workout.users;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.lostntkdgmail.workout.R;
import com.lostntkdgmail.workout.main.BaseFragment;
import com.lostntkdgmail.workout.main.MainActivity;

/**
 * The New User Fragment
 */
public class NewUser extends BaseFragment {
    public static final String TITLE = "newUser";
    /**
     * Creates the fragment
     * @param inflater The inflater to inflate the layout
     * @param container The container to put the Fragment inside of
     * @param savedInstanceState The saved state
     * @return The view to display
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_user, container, false);
        Button submit = view.findViewById(R.id.userSubmitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit(view);
            }
        });
        return view;
    }

    /**
     * Submits the new user to the database, only if there is text in the first name box
     * @param button The submit button
     */
    public void submit(View button) {
        TextInputLayout firstName = getActivity().findViewById(R.id.firstNameInput);
        TextInputLayout lastName = getActivity().findViewById(R.id.lastNameInput);
        if(firstName.getEditText().getText().toString().length() > 0) {
            MainActivity.USER = MainActivity.userTable.insert(firstName.getEditText().getText().toString(), lastName.getEditText().getText().toString());
            ((MainActivity)getActivity()).setViewPager(SelectUser.TITLE);
        }
        else
            Toast.makeText(getContext(),"First name cannot be blank!",Toast.LENGTH_SHORT).show();
    }
    @Override
    public String getTitle() {
        return TITLE;
    }
}
