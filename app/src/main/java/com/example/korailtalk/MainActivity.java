package com.example.korailtalk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;

public class MainActivity extends Activity {
    private DBHelper dbhelper;
    private SessionDBHelper sessionDBhelper;
    public Button btn_trainsearch;
    private Button checkPaiedTicket_button;

    private static final String PAID_TICKET_BUTTON = "PAID_TICKET_BUTTON";
    private static final String TICKET_HISTORY_BUTTON = "TICKET_HISTORY_BUTTON";
    private static final String UNPAID_TICKET_BUTTON = "UNPAID_TICKET_BUTTON";
    private static final String TRAIN_SEARCH = "TRAIN_SEARCH";
    private static final String MY_PAGE = "MY_PAGE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /* 태원 시작 */
        btn_trainsearch = (Button) findViewById(R.id.trainSearch_button);
        btn_trainsearch.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(MainActivity.this, TrainSearch.class);
                startActivity(intent3);
            }
        });
        /* 태원 끝 */

        /* 우진  시작*/
        Button btn_mypage = (Button) findViewById(R.id.myPage_button);

        btn_mypage.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this, CheckSessionActivity.class);
                intent2.putExtra("ActivityFrom", MY_PAGE);
                startActivity(intent2);
            }
        });
        /* 우진  끝*/

        // 동기 시작
        Button btn_history = (Button) findViewById(R.id.ticketHistory_button);

        btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, CheckSessionActivity.class);
                Intent intent = new Intent(MainActivity.this, TicketHistoryActivity.class);
                intent.putExtra("ActivityFrom", TICKET_HISTORY_BUTTON);
                startActivity(intent);
            }
        });

        //동기 끝

        /* 한결  시작*/
        checkPaiedTicket_button = (Button) findViewById(R.id.checkPaiedTicket_button);

        dbhelper = new DBHelper(getApplicationContext(), "PNUKorailTalk.db", null, 1);

        dbhelper.dropTable();

        HashMap<String, Object> member = new HashMap<String, Object>();
        member.put("ID", "1234");
        member.put("password", "1234");
        member.put("customID", "1");
        member.put("phoneNum", "010-1234-1234");

        dbhelper.insert("MEMBER", member);


        HashMap<String, Object> items = new HashMap<String, Object>();
        items.put("boardingDate", "2017622");
        items.put("departurePoint", "부산");
        items.put("destPoint", "서울");
        items.put("totalAvailableSeatNum", 100);
        items.put("trainNum", 131);
        dbhelper.insert("TRAIN_INFO", items);

        HashMap<String, Object> items1 = new HashMap<String, Object>();
        items1.put("boardingDate", "2017622");
        //Log.i("test",items1.get("boardingDate")+"");
        items1.put("departurePoint", "부산");
        items1.put("destPoint", "서울");
        items1.put("paid", 1);
        items1.put("seatNum", "A23");
        items1.put("ticketID", 1312);
        items1.put("customID", 1);
        items1.put("trainNum", 131);
        items1.put("use", 0);
        dbhelper.insert("TICKET_INFO", items1);

        HashMap<String, Object> items2 = new HashMap<String, Object>();
        items2.put("boardingDate", "2017622");
        items2.put("departurePoint", "부산");
        items2.put("destPoint", "서울");
        items2.put("paid", 1);
        items2.put("seatNum", "A25");
        items2.put("ticketID", 1312);
        items2.put("customID", 1);
        items2.put("trainNum", 131);
        items2.put("use", 1);
        dbhelper.insert("TICKET_INFO", items2);

        // 우진 수정 시작
        HashMap<String, Object> item1 = new HashMap<String, Object>();
        item1.put("ID", "admin");
        item1.put("customID", "1");
        item1.put("phoneNum", "010-1234-5678");
        item1.put("password", "admin");
        dbhelper.insert("MEMBER", item1);

        HashMap<String, Object> item2 = new HashMap<String, Object>();
        item2.put("ID", "holinder4s");
        item2.put("customID", "31337");
        item2.put("phoneNum", "010-7185-7663");
        item2.put("password", "helloworld");
        dbhelper.insert("MEMBER", item2);

        HashMap<String, Object> item3 = new HashMap<String, Object>();
        item3.put("customID", "-1234");
        item3.put("phoneNum", "010-1111-1111");
        item3.put("password", "test1234");
        dbhelper.insert("NON_MEMBER", item3);

        HashMap<String, Object> item4 = new HashMap<String, Object>();
        item4.put("customID", "-2222");
        item4.put("phoneNum", "010-2222-2222");
        item4.put("password", "test2222");
        dbhelper.insert("NON_MEMBER", item4);

        HashMap<String, Object> item5 = new HashMap<String, Object>();
        item5.put("CouponNum", "9999");
        item5.put("KTXMileage", "99999999");
        item5.put("ID", "1234");
        item5.put("customID", "1");
        dbhelper.insert("MEMBERSHIP_INFO", item5);

        HashMap<String, Object> item6 = new HashMap<String, Object>();
        item6.put("CouponNum", "3");
        item6.put("KTXMileage", "23500");
        item6.put("ID", "1234");
        item6.put("customID", "31337");
        dbhelper.insert("MEMBERSHIP_INFO", item6);

        HashMap<String, Object> item7 = new HashMap<String, Object>();
        item7.put("boardingDate", "2017622");
        //Log.i("test",items1.get("boardingDate")+"");
        item7.put("departurePoint", "부산");
        item7.put("destPoint", "서울");
        item7.put("paid", 1);
        item7.put("seatNum", "A23");
        item7.put("ticketID", 1312);
        item7.put("customID", 31337);
        item7.put("trainNum", 131);
        item7.put("use", 0);
        dbhelper.insert("TICKET_INFO", item7);

        HashMap<String, Object> item8 = new HashMap<String, Object>();
        item8.put("boardingDate", "2017622");
        item8.put("departurePoint", "부산");
        item8.put("destPoint", "서울");
        item8.put("paid", 1);
        item8.put("seatNum", "A25");
        item8.put("ticketID", 1312);
        item8.put("customID", 31337);
        item8.put("trainNum", 131);
        item8.put("use", 1);
        dbhelper.insert("TICKET_INFO", item8);
        // 우진 수정 끝

        checkPaiedTicket_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CheckSessionActivity.class);
                intent.putExtra("ActivityFrom", PAID_TICKET_BUTTON);
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
