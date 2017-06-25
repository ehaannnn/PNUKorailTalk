package com.example.korailtalk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by 한결 on 2017-06-22.
 */
public class ListViewAdapter extends BaseAdapter {

    private List<Map<String, ?>> listViewItemList = new LinkedList<Map<String, ?>>();

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
            convertView = inflater.inflate(R.layout.list_items, parent, false);
        }

        TextView boardingDate = (TextView) convertView.findViewById(R.id.boardingDate);
        TextView departurePoint = (TextView) convertView.findViewById(R.id.departurePoint);
        TextView destPoint = (TextView) convertView.findViewById(R.id.destPoint);
        TextView seatNum = (TextView) convertView.findViewById(R.id.seatNum);
        TextView trainNum = (TextView) convertView.findViewById(R.id.trainNum);
        TextView numOfPeople = (TextView) convertView.findViewById(R.id.numOfPeople);
        TextView deadLine = (TextView) convertView.findViewById(R.id.deadLine);

        String tmp = listViewItemList.get(position).get("boardingDate").toString();
        String date = tmp.substring(0, 4) + "/" + tmp.substring(4, 6) + "/" + tmp.substring(6, 8) + " " + tmp.substring(8, 10) + ":" + tmp.substring(10, 12);
        boardingDate.setText(date);

        departurePoint.setText(listViewItemList.get(position).get("departurePoint").toString());

        destPoint.setText(listViewItemList.get(position).get("destPoint").toString());
        seatNum.setText(listViewItemList.get(position).get("seatNum").toString());
        trainNum.setText(listViewItemList.get(position).get("trainNum").toString());

        String[] seats = listViewItemList.get(position).get("seatNum").toString().split(",");
        numOfPeople.setText(String.valueOf(seats.length));
        if (listViewItemList.get(position).get("seatNum").toString().equalsIgnoreCase("UnPaidTicketSearch")) {
            //
            deadLine.setText(listViewItemList.get(position).get("deadLine").toString());
            ;
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
