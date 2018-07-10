package com.lostntkdgmail.workout.users;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lostntkdgmail.workout.R;
import com.lostntkdgmail.workout.main.MainActivity;

import java.util.Objects;

public class SelectUser extends Fragment {
    public static final String TITLE = "SelectUser";
    private ListView userList;
    public static String[] ids;

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
                ((MainActivity)getActivity()).addFragment(new NewUser(), NewUser.TITLE);
                ((MainActivity)getActivity()).setViewPager(NewUser.TITLE);
            }
        });
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
    public String[] getUsers() {
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
    public void reload() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),R.layout.user_list_item, R.id.userListEntry, getUsers());
        userList.setAdapter(adapter);
    }
}
