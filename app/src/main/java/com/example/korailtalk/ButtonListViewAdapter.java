package com.example.korailtalk;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by ttaka on 2017. 6. 25..
 */

public class ButtonListViewAdapter extends BaseAdapter {
    private List<Map<String, ?>> listViewItemList = new LinkedList<Map<String, ?>>();
    private SeatSearch seatSearch;
    private int numOfTickets;
    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.button_items, parent, false);



            Button btn1 = (Button) convertView.findViewById(R.id.btn1);
            Button btn2 = (Button) convertView.findViewById(R.id.btn2);
            Button btn3 = (Button) convertView.findViewById(R.id.btn3);
            Button btn4 = (Button) convertView.findViewById(R.id.btn4);

            btn1.setText("A" + listViewItemList.get(position).get("position").toString());
            btn2.setText("B" + listViewItemList.get(position).get("position").toString());
            btn3.setText("C" + listViewItemList.get(position).get("position").toString());
            btn4.setText("D" + listViewItemList.get(position).get("position").toString());

            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ( seatSearch.numOfTickets>=0) {
                        view.setBackgroundColor(Color.BLUE);
                        seatSearch.numOfTickets -= 1;
                    }

                }
            });
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ( seatSearch.numOfTickets>=0) {
                        view.setBackgroundColor(Color.BLUE);
                        seatSearch.numOfTickets -= 1;
                    }

                }
            });

            btn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ( seatSearch.numOfTickets>=0) {
                        view.setBackgroundColor(Color.BLUE);
                        seatSearch.numOfTickets -= 1;
                    }

                }
            });


            btn4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (seatSearch.numOfTickets>=0) {
                        view.setBackgroundColor(Color.BLUE);
                        seatSearch.numOfTickets -= 1;
                    }

                }
            });
            return convertView;
        }


        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(Map<String, ?> item) {
        listViewItemList.add(item);
    }

}
