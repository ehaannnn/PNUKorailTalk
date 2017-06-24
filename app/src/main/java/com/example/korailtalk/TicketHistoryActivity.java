package com.example.korailtalk;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by dlehd on 2017-06-25.
 */

public class TicketHistoryActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ticket_history);

        ListView listView = (ListView)findViewById(R.id.history_list);

        ArrayList<HistoryItem> data = new ArrayList<>();

        //data 얻기

        HistoryItem test1 = new HistoryItem("Test Text");

        data.add(test1);

        HistoryListviewAdapter adapter = new HistoryListviewAdapter(this, R.layout.activity_ticket_history_item, data);
        listView.setAdapter(adapter);

    }
}
