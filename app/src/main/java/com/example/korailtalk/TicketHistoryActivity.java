package com.example.korailtalk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dlehd on 2017-06-25.
 */

public class TicketHistoryActivity extends Activity {

    private String startDate;
    private String endDate;
    private int customID;
    private DBHelper dbhelper;
    private List<HashMap<String,Object>> ticket_infos;
    private HashMap<String, Object> ticket_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ticket_history);

        Intent intent = getIntent();
        startDate = intent.getStringExtra("STARTDATE");
        endDate = intent.getStringExtra("ENDDATE");
        customID = intent.getIntExtra("customID", 0);


        ListView listView = (ListView)findViewById(R.id.history_list);

        ArrayList<HistoryItem> data = new ArrayList<>();
        //데이터 얻기
        dbhelper = new DBHelper(getApplicationContext(), "PNUKorailTalk.db",null,1);

        ticket_infos = dbhelper.getResultAt("TICKET_INFO",customID);
        for(int i=0; i<ticket_infos.size(); i++) {
            System.out.println(Integer.parseInt(ticket_infos.get(i).get("boardingDate").toString().substring(0, 8)));
            System.out.println(Integer.parseInt(endDate));
            if(Integer.parseInt(ticket_infos.get(i).get("boardingDate").toString().substring(0, 8)) <= Integer.parseInt(endDate)
                    && Integer.parseInt(ticket_infos.get(i).get("boardingDate").toString().substring(0, 8)) >= Integer.parseInt(startDate)) {
                ticket_info = ticket_infos.get(i);
                String outputString = new String();
                outputString = "티켓번호 : " + ticket_info.get("ticketID").toString();
                outputString += " 기차번호 : " + ticket_info.get("trainNum").toString();
                outputString += " 탑승일 : " + ticket_info.get("boardingDate").toString();
                outputString += " 출발역 : " + ticket_info.get("departurePoint").toString();
                outputString += " 도착역 : " + ticket_info.get("destPoint").toString();
                outputString += " 좌석번호 : " + ticket_info.get("seatNum").toString();
                String[] selected_seat = ticket_info.get("seatNum").toString().split(",");
                outputString += " 좌석수 : " + String.valueOf(selected_seat.length);
                HistoryItem input = new HistoryItem(outputString);

                data.add(input);
            }
        }

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
