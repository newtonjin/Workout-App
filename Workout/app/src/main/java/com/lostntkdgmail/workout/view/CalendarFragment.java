package com.lostntkdgmail.workout.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lostntkdgmail.workout.R;
import com.lostntkdgmail.workout.database.LiftTableAccessor;
import com.lostntkdgmail.workout.main.BaseFragment;
import com.lostntkdgmail.workout.main.MainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarFragment extends BaseFragment {

    public static final String TITLE = "ViewEntries";

    private ImageView previousButton, nextButton;
    private TextView currentDate;
    private GridView calendarGridView;
    private View view;
    private Calendar cal = Calendar.getInstance(Locale.ENGLISH);
    private GridAdapter mAdapter;
    private LiftTableAccessor mQuery;
    private static final int MAX_CALENDAR_COLUMN = 42;
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);

    public CalendarFragment() {
        //ok
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.calendar_view, container, false);

        previousButton = view.findViewById(R.id.previous_month);
        nextButton = view.findViewById(R.id.next_month);
        currentDate = view.findViewById(R.id.display_current_date);
        calendarGridView = view.findViewById(R.id.calendar_grid);

        setUpCalendarAdapter();
        setPreviousButtonClickEvent();
        setNextButtonClickEvent();
        setGridCellClickEvents();


        return view;
    }

    private void setPreviousButtonClickEvent(){
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.add(Calendar.MONTH, -1);
                setUpCalendarAdapter();
            }
        });
    }
    private void setNextButtonClickEvent(){
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.add(Calendar.MONTH, 1);
                setUpCalendarAdapter();
            }
        });
    }
    private void setGridCellClickEvents(){
        calendarGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "Clicked " + parent.getItemAtPosition(position), Toast.LENGTH_LONG).show();

                //calls to update the view history and set as the current fragment
                System.out.println(formatter.format((Date) parent.getItemAtPosition(position)));
                ((MainActivity)getActivity()).updateViewHistory(formatter.format((Date) parent.getItemAtPosition(position)));
                ((MainActivity)getActivity()).setViewPager(ViewHistoryFragment.TITLE);
            }
        });
    }
    private void setUpCalendarAdapter(){
        List<Date> dayValueInCells = new ArrayList<>();
        mQuery = new LiftTableAccessor(getContext());
        List<EventObjects> mEvents = mQuery.getAllLifts();
        Calendar mCal = (Calendar)cal.clone();
        mCal.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfTheMonth = mCal.get(Calendar.DAY_OF_WEEK) - 1;
        mCal.add(Calendar.DAY_OF_MONTH, -firstDayOfTheMonth);
        while(dayValueInCells.size() < MAX_CALENDAR_COLUMN){
            dayValueInCells.add(mCal.getTime());
            mCal.add(Calendar.DAY_OF_MONTH, 1);
        }
        Log.d(TITLE, "Number of date " + dayValueInCells.size());
        String sDate = formatter.format(cal.getTime());
        currentDate.setText(sDate);
        mAdapter = new GridAdapter(getContext(), dayValueInCells, cal, mEvents);
        calendarGridView.setAdapter(mAdapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return super.onCreateAnimation(transit, enter, nextAnim);
    }


}
