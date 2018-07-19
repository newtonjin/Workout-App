package com.lostntkdgmail.workout.view;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.lostntkdgmail.workout.R;
import com.lostntkdgmail.workout.main.MainActivity;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MapAdapter extends BaseAdapter {
    private Map<String, Map<String, String>> mData;
    private Context context;
    private String types[];
    private String[][] lifts;
    private Date date;
    private boolean viewMade = false;

    public MapAdapter(Map<String, Map<String, String>> mData, Context context, String[] types, String[][] lifts, Date date){
        this.context = context;
        this.mData = mData;
        this.types = types;
        this.lifts = lifts;
        this.date = date;
        viewMade = false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View result;

        if (convertView == null) {
            result = LayoutInflater.from(context).inflate(R.layout.map_adapter, parent, false);
        } else {
            result = convertView;
        }



        //Map<String, String> item = (Map<String, String>) getItem(position);

        //TODO: if not checked by this boolean the view will be added many times, fix dis
        //TODO: Needs to display multiple sets multiple times
            if(!viewMade) {
                TextView dateDisplay = result.findViewById(R.id.dateDisplay);
                dateDisplay.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.US).format(date));

                LinearLayout myRoot = (LinearLayout) result.findViewById(R.id.my_root);
                LinearLayout inner = new LinearLayout(context);
                inner.setOrientation(LinearLayout.VERTICAL);

                for(String outerKey : mData.keySet()) {
                    Map<String, String> innerMap_ = mData.get(outerKey);
                    if(innerMap_.size() > 0) {
                        TextView currOuter_ = new TextView(context);
                        currOuter_.setText(outerKey);
                        myRoot.addView(currOuter_);

                        for(String innerKey_ : (mData.get(outerKey)).keySet()){
                            TextView currMiddle_ = new TextView(context);
                            currMiddle_.setPadding(25,0,0,0);
                            currMiddle_.setText(innerKey_);
                            myRoot.addView(currMiddle_);

                            // get ALL SETS of this type and lift
                            Cursor c = MainActivity.weightTable.select(MainActivity.USER,outerKey,innerKey_, MainActivity.weightTable.getColumnNames()[0]+" DESC", "-1");
                            ArrayList<String> result_ = new ArrayList<>();
                            while(c.moveToNext()) {
                                String arr = c.getString(6) + " repetitions of " + c.getString(5) + " LBS";
                                result_.add(arr);
                            }


                            for(String listItem : result_) {
                                TextView currInner_ = new TextView(context);
                                currInner_.setPadding(50, 0, 0, 0);
                                currInner_.setText(listItem);
                                myRoot.addView(currInner_);
                            }
                        }
                    }
                }






                //for (Map<String, String> innerMap : mData.values()) {
                //    if (innerMap.size() > 0) {
                //        for (String val : innerMap.values()) {
                //            // get the key of the value from the outer table, then inner table
                //            String outerKey = (String) getKeyFromValue(mData, innerMap);
                //            String middleKey = (String) getKeyFromValue(innerMap, val);

                //            TextView currOuter = new TextView(context);
                //            TextView currMiddle = new TextView(context);
                //            TextView currInner = new TextView(context);

                //            currMiddle.setPadding(25, 0,0,0);
                //            currInner.setPadding(50, 0, 0, 0);

                //            currOuter.setText(outerKey);
                //            currMiddle.setText(middleKey);
                //            currInner.setText(val);

                //            myRoot.addView(currOuter);
                //            myRoot.addView(currMiddle);
                //            myRoot.addView(currInner);
                //        }
                //    }
                //}
            }
        viewMade = true;
        return result;
    }

    public static Object getKeyFromValue(Map hm, Object value) {
        for (Object o : hm.keySet()) {
            if (hm.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(types[i]);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
}
