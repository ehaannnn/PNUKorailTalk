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
        items.put("boardingDate","2017-06-22");
        items.put("departurePoint","부산");
        items.put("destPoint","서울");
        items.put("totalAvailableSeatNum",100);
        items.put("trainNum", 131);
        dbhelper.insert("TRAIN_INFO", items);

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
