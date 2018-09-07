package com.lostntkdgmail.workout.view;

/*
* Created by Tom Pedraza
* Workout-App
* https://github.com/tha7556/Workout-App
*/

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.lostntkdgmail.workout.R;
import com.lostntkdgmail.workout.main.BaseFragment;
import com.lostntkdgmail.workout.main.MainActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ViewHistoryFragment extends BaseFragment {

    private String[] types;
    private String[][] lifts;
    public static final String TITLE = "ViewHistory";
    private View view;
    private ListView listory;
    private MapAdapter adapter;
    private Context mContext;
    private Date datePicked;
    private static final DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);


    public ViewHistoryFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_history, container, false);
        listory = view.findViewById(R.id.listory);
        Button deleteButton = view.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(getActivity());
                }
                builder.setTitle("Delete entries")
                        .setMessage("Are you sure you want to delete all entries for \"" + dateFormatter.format(datePicked) + "\"?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                ((MainActivity)getActivity()).weightTable.deleteLiftByDate(datePicked);
                                ((MainActivity)getActivity()).setViewPager(CalendarFragment.TITLE);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });






        this.view = view;
        return view;
    }

    //weight table query
    public void initList(Date datePicked) {
        this.datePicked = datePicked;

        // types that map to lifts that map to db entries
        Map<String, Map<String, ArrayList<String>>> qResults = new HashMap<>();
        types = ((MainActivity)getActivity()).liftTable.getTypes();
        lifts = ((MainActivity)getActivity()).liftTable.getLifts();

        //building the maps entries
        for(String type : types) {
            //puts entries (separated by dates) the array list
            Map<String, ArrayList<String>> innerMap = ((MainActivity)getActivity()).weightTable.getLiftsByDate(this.datePicked, type, ((MainActivity)getActivity()).USER);
            qResults.put(type, innerMap);
        }

        //refresh map adapter
        adapter = new MapAdapter(qResults, getContext(), types, ((MainActivity)getActivity()).liftTable.getLifts(), this.datePicked);
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
