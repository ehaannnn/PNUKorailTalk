package com.example.korailtalk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by dlehd on 2017-06-25.
 */

public class HistoryListviewAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<HistoryItem> data;
    private int layout;

    public HistoryListviewAdapter(Context context, int layout, ArrayList<HistoryItem> data) {
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public String getItem(int position) {
        return data.get(position).getOutput();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
        }

        HistoryItem hItem = data.get(position);

        TextView output = (TextView)convertView.findViewById(R.id.history_item);
        output.setText(hItem.getOutput());

        return convertView;
    }
}
