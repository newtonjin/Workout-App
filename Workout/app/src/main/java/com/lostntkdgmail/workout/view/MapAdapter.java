package com.lostntkdgmail.workout.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lostntkdgmail.workout.R;

import java.util.Map;

public class MapAdapter extends BaseAdapter {
    private Map<String, Map<String, String>> mData;
    //TODO: make this shit work

    public MapAdapter(Map<String, Map<String, String>> mData){
        this.mData = mData;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View result;

        if (convertView == null) {
            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_cell_layout, parent, false);
        } else {
            result = convertView;
        }

        Map.Entry<String, String> item = (Map.Entry<String, String>) getItem(position);

        // TODO replace findViewById by ViewHolder
        ((TextView) result.findViewById(android.R.id.text1)).setText(item.getKey());
        ((TextView) result.findViewById(android.R.id.text2)).setText(item.getValue());

        return result;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
}
