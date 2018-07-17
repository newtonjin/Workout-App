package com.lostntkdgmail.workout.view;

import android.content.Context;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ViewHistoryFragment extends BaseFragment {

    public static final String TITLE = "ViewHistory";
    private List<String[][]> listElementsArrayList;
    private View view;
    private ListView listory;
    private Context mContext;
    private MapAdapter adapter;


    public ViewHistoryFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_history, container, false);
        listory = view.findViewById(R.id.listory);

        listElementsArrayList = new ArrayList<>();
        listElementsArrayList.add(new String[][]{{"1", "2", "3"}});
//        adapter = new MapAdapter(getActivity(), android.R.layout.simple_list_item_1, listElementsArrayList);

//        listory.setAdapter(adapter);

        this.view = view;
        return view;
    }

    //TODO: get this fragment to actually display something
    //weight table query
    public void initList(Date datePicked) {
        String[] types = ((MainActivity)getActivity()).liftTable.getTypes();
        Map<String, Map<String, String>> qResults = new HashMap<>();

        for(String type : types) {
            qResults.put(type, ((MainActivity)getActivity()).weightTable.getLiftsByDate(datePicked, type));
        }

        adapter = new MapAdapter(qResults);
        listory.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        System.out.println(qResults.size());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }
}
