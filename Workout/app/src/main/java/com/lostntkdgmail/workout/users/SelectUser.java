package com.lostntkdgmail.workout.users;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lostntkdgmail.workout.R;
import com.lostntkdgmail.workout.database.UserTableAccessor;

public class SelectUser extends Fragment {
    private static final String TAG = "SelectUser";
    private UserTableAccessor userTable;
    private ListView userList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.select_user, container, false);
        userTable = new UserTableAccessor(getContext());
        if(userTable.select(null, null).getCount() == 0) {
            userTable.insert("Default","User");
        }
        userList = view.findViewById(R.id.userList);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),R.layout.list_item, R.id.listEntry, getUsers());
        userList.setAdapter(adapter);
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //TODO: Select user
            }
        });
        FloatingActionButton newUser = view.findViewById(R.id.newUserButton);
        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: open new user fragment
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
        userTable.close();
        super.onDestroy();
    }
    public String[] getUsers() {
        Cursor queryResult = userTable.select(null,null);
        String[] result = new String[queryResult.getCount()];
        for(int i = 0; i < result.length; i++) {
            queryResult.moveToNext();
            result[i] = queryResult.getString(1) + " " + queryResult.getString(2);
        }
        return result;
    }
}
