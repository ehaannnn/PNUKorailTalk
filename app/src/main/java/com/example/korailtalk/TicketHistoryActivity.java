package com.example.korailtalk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by dlehd on 2017-06-25.
 */

public class TicketHistoryActivity extends Activity {

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


        Button b = (Button)findViewById(R.id.history_to_main);
        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(),
                        MainActivity.class);
                startActivity(intent);

            }
        });
    }
}
