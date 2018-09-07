package com.lostntkdgmail.workout.data_entry;

/* Created by Tom Pedraza and Tyler Atkinson
 * Workout-App
 * https://github.com/tha7556/Workout-App
 */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lostntkdgmail.workout.R;
import com.lostntkdgmail.workout.main.BaseFragment;
import com.lostntkdgmail.workout.main.MainActivity;

public class NewLift extends BaseFragment {
    public static final String TITLE = "NewLift";


    public NewLift() {
        //required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_lift, container, false);
        final TextInputLayout inputBox = view.findViewById(R.id.liftNameInput);
        final EditText input = inputBox.getEditText();
        Button submit = view.findViewById(R.id.liftSubmitButton);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!inputBox.getEditText().getText().toString().equals("")){
                    Toast.makeText(getContext(),inputBox.getEditText().getText().toString() + " has been added as a workout!", Toast.LENGTH_SHORT).show();
                    ((MainActivity)getActivity()).addLift(input.getText().toString());
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public String getTitle() {
        return TITLE;
    }

    @Override
    public void reload() {
        super.reload();
    }
}
