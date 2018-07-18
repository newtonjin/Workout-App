package com.lostntkdgmail.workout.view;

import android.content.Context;
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

                for (Map<String, String> innerMap : mData.values()) {
                    if (innerMap.size() > 0) {
                        for (String val : innerMap.values()) {
                            TextView currOuter = new TextView(context);
                            TextView currMiddle = new TextView(context);
                            TextView currInner = new TextView(context);

                            currMiddle.setPadding(25, 0,0,0);
                            currInner.setPadding(50, 0, 0, 0);

                            currOuter.setText((String) getKeyFromValue(mData, innerMap));
                            currMiddle.setText((String) getKeyFromValue(innerMap, val));
                            currInner.setText(val);

                            myRoot.addView(currOuter);
                            myRoot.addView(currMiddle);
                            myRoot.addView(currInner);
                        }
                    }
                }
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
