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
    private Map<String, Map<String, ArrayList<String>>> mData;
    private Context context;
    private String types[];
    private String[][] lifts;
    private Date date;
    private boolean viewMade = false;

    public MapAdapter(Map<String, Map<String, ArrayList<String>>> mData, Context context, String[] types, String[][] lifts, Date date){
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

        //TODO: if not checked by this boolean the view will be added many times, fix dis
            if(!viewMade) {
                TextView dateDisplay = result.findViewById(R.id.dateDisplay);
                dateDisplay.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.US).format(date));

                LinearLayout myRoot =  result.findViewById(R.id.my_root);
                LinearLayout inner = new LinearLayout(context);
                inner.setOrientation(LinearLayout.VERTICAL);

                // iterating the types
                for(String outerKey : mData.keySet()) {
                    Map<String, ArrayList<String>> innerMap = mData.get(outerKey);
                    if(innerMap.size() > 0) {
                        TextView currOuter = new TextView(context);
                        currOuter.setText(outerKey);
                        myRoot.addView(currOuter);

                        // iterating the map<lift, List<Weights & reps>>
                        for(String innerKey : (mData.get(outerKey)).keySet()){
                            TextView currMiddle = new TextView(context);
                            currMiddle.setPadding(25,0,0,0);
                            currMiddle.setText(innerKey);
                            myRoot.addView(currMiddle);

                            // iterating the weights and reps
                            for(String listItem : mData.get(outerKey).get(innerKey)) {
                                TextView currInner = new TextView(context);
                                currInner.setPadding(50, 0, 0, 0);
                                currInner.setText(listItem);
                                myRoot.addView(currInner);
                            }
                        }
                    }
                }
            }

        viewMade = true;
        return result;
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
