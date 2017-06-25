package com.example.korailtalk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaidTicketSearch extends Activity {
    private int customID;
    private int selectedCustomID;
    private int selectedTicketID;


    private DBHelper dbhelper;
    private SessionDBHelper sessionDBhelper;
    private ListViewAdapter adapter;

    private Button mainButton;
    private Button ticketCancelButton;

    private ListView listView;
    private static final int START_DATE = 1;
    private static final int END_DATE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paied_ticket_search);
        Intent intent = getIntent();
        customID = intent.getIntExtra("customID", 0);

        dbhelper = new DBHelper(getApplicationContext(), "PNUKorailTalk.db", null, 1);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);


        ticketCancelButton = (Button) findViewById(R.id.ticketCancel);
        ticketCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(PaidTicketSearch.this, TicketCancelActivity.class);
                newIntent.putExtra("customID",selectedCustomID);
                Log.i("selectedCustomID", selectedCustomID + "");
                newIntent.putExtra("ticketID",selectedTicketID);
                startActivity(newIntent);
            }
        });

        //List<HashMap<String,Object>> train_info = dbhelper.getResultAt("TRAIN_INFO",customID);
        final List<HashMap<String, Object>> ticket_info = dbhelper.getResultAt("TICKET_INFO", customID);
        adapter = new ListViewAdapter();
        for (int i = 0; i < ticket_info.size(); ++i) {
            if (Integer.parseInt(ticket_info.get(i).get("use").toString()) == 0 && Integer.parseInt(ticket_info.get(i).get("paid").toString()) == 1) {
                adapter.addItem(createItem(ticket_info.get(i).get("boardingDate").toString(), ticket_info.get(i).get("departurePoint").toString(), ticket_info.get(i).get("destPoint").toString(),
                        ticket_info.get(i).get("seatNum").toString(), Integer.parseInt(ticket_info.get(i).get("trainNum").toString()),Integer.parseInt(ticket_info.get(i).get("ticketID").toString()), Integer.parseInt(ticket_info.get(i).get("customID").toString()),"PaidTicketSearch"));
            }
        }
        listView.setAdapter(adapter);


        /*paidTicketSearchButton = (Button) findViewById(R.id.paidTicketSearchButton);
        paidTicketSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DB에서 startDate부터 endDate사이의 티켓정보 출력
                //티켓정보 : 출발역, 도착역, 인원, 열차번호, 출발날짜

            }
        });
*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ticketCancelButton.setEnabled(true);
                ticketCancelButton = (Button) findViewById(R.id.ticketCancel);

                HashMap<String,Object> item = (HashMap<String,Object>)adapter.getItem(position);
                selectedCustomID = Integer.parseInt(item.get("customID").toString());
                selectedTicketID = Integer.parseInt(item.get("ticketID").toString());
            }
        });

        /*startDate = (TextView) findViewById(R.id.startDate);
        startDateButton = (Button) findViewById(R.id.startDateButton);
        startDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaidTicketSearch.this, DatePickerActivity.class);
                startActivityForResult(intent, START_DATE);
            }
        });

        endDate = (TextView) findViewById(R.id.endDate);
        endDateButton = (Button) findViewById(R.id.endDateButton);
        endDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaidTicketSearch.this, DatePickerActivity.class);
                startActivityForResult(intent,END_DATE);
            }
        });*/
        /*HashMap<String,Object> items = dbhelper.getResultAt("TRAIN_INFO");

        boardingDate = (TextView) findViewById(R.id.boardingDate);
        boardingDate.setText(items.get("boardingDate").toString());*/

        mainButton = (Button) findViewById(R.id.main);
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaidTicketSearch.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });





    }

    @Override
    protected void onResume() {
        super.onResume();

        final List<HashMap<String, Object>> ticket_info = dbhelper.getResultAt("TICKET_INFO", customID);
        adapter = new ListViewAdapter();
        for (int i = 0; i < ticket_info.size(); ++i) {
            if (Integer.parseInt(ticket_info.get(i).get("use").toString()) == 0 && Integer.parseInt(ticket_info.get(i).get("paid").toString()) == 1) {
                adapter.addItem(createItem(ticket_info.get(i).get("boardingDate").toString(), ticket_info.get(i).get("departurePoint").toString(), ticket_info.get(i).get("destPoint").toString(),
                        ticket_info.get(i).get("seatNum").toString(), Integer.parseInt(ticket_info.get(i).get("trainNum").toString()),Integer.parseInt(ticket_info.get(i).get("ticketID").toString()), Integer.parseInt(ticket_info.get(i).get("customID").toString()),"PaidTicketSearch"));
            }
        }
        listView.setAdapter(adapter);

    }

    public Map<String, Object> createItem(String boardingDate, String departurePoint, String destPoint, String seatNum, int trainNum,int ticketID, int customID, String from) {
        Map<String, Object> item = new HashMap<String, Object>();
        item.put("boardingDate", boardingDate);
        item.put("departurePoint", departurePoint);
        item.put("destPoint", destPoint);
        item.put("seatNum", seatNum);
        item.put("trainNum", trainNum);
        item.put("customID", customID);
        item.put("ticketID", ticketID);
        item.put("from", from);
        return item;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /*if (requestCode == START_DATE) {
            String boardingDate = data.getStringExtra("boardingDate");
            // Log.i("test",tmp.length()+"");
            //String date = tmp.charAt(0)+tmp.charAt(1)+tmp.charAt(2)+tmp.charAt(3) + "-"+tmp.charAt(4)+tmp.charAt(5)+"-"+tmp.charAt(6)+tmp.charAt(7);
            startDate.setText(boardingDate);
        } else if (requestCode == END_DATE) {
            String boardingDate = data.getStringExtra("boardingDate");
            //String date = tmp.charAt(0)+tmp.charAt(1)+tmp.charAt(2)+tmp.charAt(3) + "-"+tmp.charAt(4)+tmp.charAt(5)+"-"+tmp.charAt(6)+tmp.charAt(7);
            endDate.setText(boardingDate);
        } else {
            Log.i("실행됨?", "여기는 에러");
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_paied_ticket_search, menu);
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
