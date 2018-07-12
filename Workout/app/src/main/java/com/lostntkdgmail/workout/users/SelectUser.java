package com.lostntkdgmail.workout.users;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
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
 * The Select User Fragment
 */
public class SelectUser extends BaseFragment {
    public static final String TITLE = "SelectUser";
    private ListView userList;
    public static boolean newInitialized = false, editInitialized = false;
    public static String[] ids;

    /**
     * Creates the fragment
     * @param inflater The inflater to inflate the layout
     * @param container The container to put the Fragment inside of
     * @param savedInstanceState The saved state
     * @return The view to display
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.select_user, container, false);
        userList = view.findViewById(R.id.userList);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),R.layout.user_list_item, R.id.userListEntry, getUsers());
        userList.setAdapter(adapter);
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                MainActivity.USER = Long.parseLong(ids[(int)id]);
                getActivity().onBackPressed();
            }
        });
        FloatingActionButton newUser = view.findViewById(R.id.newUserButton);
        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!newInitialized) {
                    ((MainActivity) getActivity()).addFragment(new NewUser(), NewUser.TITLE);
                    newInitialized = true;
                }
                ((MainActivity) getActivity()).setViewPager(NewUser.TITLE);
            }
        });
        return view;
    }

    /**
     * Gets all of the users from the database to display
     * @return An array of all of the Users' names
     */
    public static String[] getUsers() {
        Cursor queryResult = MainActivity.userTable.select(null,null);
        String[] result = new String[queryResult.getCount()];
        ids = new String[result.length];
        for(int i = 0; i < result.length; i++) {
            queryResult.moveToNext();
            result[i] = queryResult.getString(1) + " " + queryResult.getString(2);
            ids[i] = queryResult.getString(0);
        }
        return result;
    }

    /**
     * Reloads the Fragment, specifically refreshes the list of users
     */
    @Override
    public void reload() {
        if(getContext() != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), R.layout.user_list_item, R.id.userListEntry, getUsers());
            userList.setAdapter(adapter);
        }
    }
}
