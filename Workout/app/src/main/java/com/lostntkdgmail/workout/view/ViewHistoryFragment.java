package com.lostntkdgmail.workout.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lostntkdgmail.workout.R;
import com.lostntkdgmail.workout.main.BaseFragment;
import com.lostntkdgmail.workout.main.MainActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewHistoryFragment extends BaseFragment {

    public static final String TITLE = "ViewHistory";
    private List<String> ListElementsArrayList;


    public ViewHistoryFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_history, container, false);
        ListView listory = view.findViewById(R.id.listory);




        ListElementsArrayList = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, ListElementsArrayList);

        listory.setAdapter(adapter);


        return view;
    }

    //weight table query
    public void initList(String datePicked) {
        String[] lifts = MainActivity.weightTable.getLiftsByDate(datePicked);
        for(String s : lifts) {
            System.out.println(s);
        }
        ListElementsArrayList = new ArrayList<>(Arrays.asList(lifts));
    }
    @Override
    public String getTitle() {
        return TITLE;
    }


}
