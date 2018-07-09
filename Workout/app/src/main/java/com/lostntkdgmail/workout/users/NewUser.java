package com.lostntkdgmail.workout.users;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lostntkdgmail.workout.R;
import com.lostntkdgmail.workout.database.UserTableAccessor;
//TODO: This may become an activity
public class NewUser extends Fragment {
    private static final String TAG = "newUser";
    private UserTableAccessor userTable;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_user, container, false);
        userTable = new UserTableAccessor(getContext());

        return view;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onDestroy() {
        userTable.close();
        super.onDestroy();
    }
}
