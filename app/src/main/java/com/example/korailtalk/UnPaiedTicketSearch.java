package com.example.korailtalk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ttaka on 2017. 6. 24..
 */

public class UnPaiedTicketSearch extends Activity {
    private int customID;
    private DBHelper dbhelper;
    private SessionDBHelper sessionDBhelper;
    private ListViewAdapter adapter;
    private Button paidTicketSearchButton;
    private Button startDateButton;
    private Button endDateButton;
    private Button mainButton;
    private TextView startDate;
    private TextView endDate;
    private ListView listView;
    private String startDatestr;
    private String endDatestr;
    private static final int START_DATE = 1;
    private static final int END_DATE = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unpaid_ticket_search);
        Intent intent = getIntent();
        customID = intent.getIntExtra("customID",0);

        dbhelper = new DBHelper(getApplicationContext(), "PNUKorailTalk.db", null, 1);
        listView = (ListView) findViewById(R.id.unpaidlistView);
        listView.setAdapter(adapter);

        //List<HashMap<String,Object>> train_info = dbhelper.getResultAt("TRAIN_INFO",customID);
        final List<HashMap<String,Object>> ticket_info = dbhelper.getResultAt("TICKET_INFO",customID);

        paidTicketSearchButton = (Button) findViewById(R.id.unpaidTicketSearchButton);
        paidTicketSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DB에서 startDate부터 endDate사이의 티켓정보 출력
                //티켓정보 : 출발역, 도착역, 인원, 열차번호, 출발날짜
                adapter = new ListViewAdapter();
                for (int i = 0; i < ticket_info.size(); i++) {
                    if (Integer.parseInt(ticket_info.get(i).get("use").toString()) == 1) {
                        if(Integer.parseInt(ticket_info.get(i).get("boardingDate").toString())<= Integer.parseInt(startDatestr)
                                && Integer.parseInt(endDatestr) >= Integer.parseInt(ticket_info.get(i).get("boardingDate").toString())
                                ) {
                            adapter.addItem(createItem(ticket_info.get(i).get("boardingDate").toString(), ticket_info.get(i).get("departurePoint").toString(), ticket_info.get(i).get("destPoint").toString(),
                                    ticket_info.get(i).get("seatNum").toString(), Integer.parseInt(ticket_info.get(i).get("trainNum").toString())));
                        }
                    }
                }
                listView.setAdapter(adapter);
            }
        });

        startDate = (TextView) findViewById(R.id.unpaidstartDate);
        startDateButton = (Button) findViewById(R.id.unpaidstartDateButton);
        startDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UnPaiedTicketSearch.this, DatePickerActivity.class);
                startActivityForResult(intent, START_DATE);
            }
        });

        endDate = (TextView) findViewById(R.id.unpaidendDate);
        endDateButton = (Button) findViewById(R.id.unpaidendDateButton);
        endDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UnPaiedTicketSearch.this, DatePickerActivity.class);
                startActivityForResult(intent,END_DATE);
            }
        });
        /*HashMap<String,Object> items = dbhelper.getResultAt("TRAIN_INFO");

        boardingDate = (TextView) findViewById(R.id.boardingDate);
        boardingDate.setText(items.get("boardingDate").toString());*/

        mainButton = (Button) findViewById(R.id.main);
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UnPaiedTicketSearch.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        sessionDBhelper = new SessionDBHelper(getApplicationContext(), "Session.db", null, 1);
        Button sessionButton = (Button) findViewById(R.id.sessionDelete);
        sessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionDBhelper.dropTable();
            }
        });
    }

    public Map<String,Object> createItem(String boardingDate, String departurePoint, String destPoint, String seatNum, int trainNum) {
        Map<String,Object> item = new HashMap<String,Object>();
        item.put("boardingDate", boardingDate);
        item.put("departurePoint", departurePoint);
        item.put("destPoint", destPoint);
        item.put("seatNum", seatNum);
        item.put("trainNum", trainNum);
        return item;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == START_DATE) {
            String boardingDate = data.getStringExtra("boardingDate");
            // Log.i("test",tmp.length()+"");
            //String date = tmp.charAt(0)+tmp.charAt(1)+tmp.charAt(2)+tmp.charAt(3) + "-"+tmp.charAt(4)+tmp.charAt(5)+"-"+tmp.charAt(6)+tmp.charAt(7);
            startDate.setText(boardingDate);
            startDatestr = data.getStringExtra("boardingDatestr");
        }
        else if (requestCode == END_DATE){
            String boardingDate = data.getStringExtra("boardingDate");
            //String date = tmp.charAt(0)+tmp.charAt(1)+tmp.charAt(2)+tmp.charAt(3) + "-"+tmp.charAt(4)+tmp.charAt(5)+"-"+tmp.charAt(6)+tmp.charAt(7);
            endDate.setText(boardingDate);
            endDatestr = data.getStringExtra("boardingDatestr");
        }
        else {
            Log.i("실행됨?", "여기는 에러");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_unpaid_ticket_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
