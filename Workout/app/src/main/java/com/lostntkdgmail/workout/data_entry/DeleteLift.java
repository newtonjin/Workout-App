package com.lostntkdgmail.workout.data_entry;

import android.content.Context;
import android.os.Bundle;
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

public class DeleteLift extends BaseFragment {
    public static final String TITLE = "DeleteLift";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.delete_lift, container, false);
        Button deleteButton = view.findViewById(R.id.liftDeleteButton);
        final TextInputLayout inputBox = view.findViewById(R.id.liftNameInput);
        final EditText input = inputBox.getEditText();

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!inputBox.getEditText().getText().toString().equals("")){
                    Toast.makeText(getContext(),inputBox.getEditText().getText().toString() + " has been deleted!", Toast.LENGTH_SHORT).show();
                    ((MainActivity)getActivity()).deleteLift(input.getText().toString());
                }
            }
        });

        return view;
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
    public String getTitle() {
        return null;
    }
}
