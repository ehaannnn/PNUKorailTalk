package com.example.korailtalk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ttaka on 2017. 6. 24..
 */

public class AvailableTrainLists extends Activity {

    private DBHelper dbhelper;
    private ListView trainlistview;
    private ListViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_available_lists);

        final ArrayList<TrainArray> ticket_info = (ArrayList<TrainArray>) getIntent().getSerializableExtra("trainlist");


        dbhelper = new DBHelper(getApplicationContext(), "PNUKorailTalk.db", null, 1);
        trainlistview = (ListView)findViewById(R.id.trainlist);
        trainlistview.setAdapter(adapter);

        adapter = new ListViewAdapter();
        for (int i = 0; i < ticket_info.size(); i++) {
                adapter.addItem(createItem(ticket_info.get(i).getDepartdate(),ticket_info.get(i).getDeparturePoint(),
                        ticket_info.get(i).getDestPoint(), ticket_info.get(i).getTotalAvailableSeatNum(),
                        ticket_info.get(i).getTrainnum() ) );
        }
        trainlistview.setAdapter(adapter);

        trainlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(AvailableTrainLists.this, SeatSearch.class);
                intent.putExtra("trainum",ticket_info.get(i).getTrainnum());
                intent.putExtra("nbofticket",ticket_info.get(i).getNbofticket());
                intent.putExtra("departdate",ticket_info.get(i).getDepartdate());
                startActivity(intent);
            }
        });

    }

    public Map<String,Object> createItem(int boardingDate, String departurePoint, String destPoint, int seatNum, int trainNum) {
        Map<String,Object> item = new HashMap<String,Object>();
        item.put("boardingDate", boardingDate);
        item.put("departurePoint", departurePoint);
        item.put("destPoint", destPoint);
        item.put("seatNum", seatNum);
        item.put("trainNum", trainNum);
        return item;
    }
}
