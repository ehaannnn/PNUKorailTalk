package com.example.korailtalk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;

public class MainActivity extends Activity {
    private DBHelper dbhelper;
    public Button checkPaiedTicket_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* 우진  시작*/
        Button btn_mypage = (Button) findViewById(R.id.myPage_button);

        btn_mypage.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this,CheckSessionActivity.class);
                intent2.putExtra("menu","mypage");
                startActivity(intent2);
            }
        });
        /* 우진  끝*/

        /* 한결  시작*/
        checkPaiedTicket_button = (Button) findViewById(R.id.checkPaiedTicket_button);

        dbhelper = new DBHelper(getApplicationContext(), "PNUKorailTalk.db",null,1);

        dbhelper.dropTable();

        HashMap<String,Object> items = new HashMap<String,Object>();
        items.put("boardingDate","2017622");
        items.put("departurePoint","부산");
        items.put("destPoint","서울");
        items.put("totalAvailableSeatNum",100);
        items.put("trainNum", 131);
        dbhelper.insert("TRAIN_INFO", items);

        HashMap<String,Object> items1 = new HashMap<String,Object>();
        items1.put("boardingDate","2017622");
        //Log.i("test",items1.get("boardingDate")+"");
        items1.put("departurePoint","부산");
        items1.put("destPoint", "서울");
        items1.put("paid", 1);
        items1.put("seatNum", "A23");
        items1.put("ticketID", 1312);
        items1.put("customID", 1);
        items1.put("trainNum", 131);
        items1.put("use", 0);
        dbhelper.insert("TICKET_INFO", items1);

        HashMap<String,Object> items2 = new HashMap<String,Object>();
        items2.put("boardingDate","2017622");
        items2.put("departurePoint","부산");
        items2.put("destPoint","서울");
        items2.put("paid", 1);
        items2.put("seatNum","A25");
        items2.put("ticketID", 1312);
        items2.put("customID", 1);
        items2.put("trainNum", 131);
        items2.put("use",1);
        dbhelper.insert("TICKET_INFO", items2);

        checkPaiedTicket_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PaiedTicketSearch.class);
                startActivity(intent);
            }
        });
        /* 한결  끝*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbhelper.dropTable();
    }
}
