package com.lostntkdgmail.workout.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.lostntkdgmail.workout.R;
import com.lostntkdgmail.workout.database.LiftTableAccessor;
import com.lostntkdgmail.workout.main.BaseFragment;
import com.lostntkdgmail.workout.main.MainActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class ViewHistoryFragment extends BaseFragment {

    private String[] types;
    private String[][] lifts;
    public static final String TITLE = "ViewHistory";
    private View view;
    private ListView listory;
    private MapAdapter adapter;
    private Context mContext;


    public ViewHistoryFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_history, container, false);
        listory = view.findViewById(R.id.listory);
        this.view = view;
        return view;
    }

    //weight table query
    public void initList(Date datePicked) {

        Map<String, Map<String, ArrayList<String>>> qResults = new HashMap<>();
        types = ((MainActivity)getActivity()).liftTable.getTypes();
        lifts = ((MainActivity)getActivity()).liftTable.getLifts();

        for(String type : types) {
            Map<String, ArrayList<String>> innerMap = ((MainActivity)getActivity()).weightTable.getLiftsByDate(datePicked, type, ((MainActivity)getActivity()).USER);
            qResults.put(type, innerMap);
        }

        adapter = new MapAdapter(qResults, getContext(), types, ((MainActivity)getActivity()).liftTable.getLifts(), datePicked);
        listory.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
    @Override
    public String getTitle() {
        return TITLE;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }
}
