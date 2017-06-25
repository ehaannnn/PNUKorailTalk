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
    private Button checkUnPaidTicket_button;

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

        checkUnPaidTicket_button = (Button) findViewById(R.id.checkUnpaiedTicket_button);
        checkUnPaidTicket_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(MainActivity.this, CheckSessionActivity.class);
                intent4.putExtra("ActivityFrom","unpaidTicketSearch");
                startActivity(intent4);
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

        HashMap<String, Object> item0 = new HashMap<String, Object>();
        item0.put("boardingDate", "2017623");
        item0.put("departurePoint", "부산");
        item0.put("destPoint", "서울");
        item0.put("totalAvailableSeatNum", 100);
        item0.put("trainNum", 133);
        dbhelper.insert("TRAIN_INFO", item0);

        HashMap<String, Object> items1 = new HashMap<String, Object>();
        items1.put("boardingDate", "2017622");
        //Log.i("test",items1.get("boardingDate")+"");
        items1.put("departurePoint", "부산");
        items1.put("destPoint", "서울");
        items1.put("paid", 1);
        items1.put("seatNum", "2A5");
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
        items2.put("seatNum", "1A8");
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
        item7.put("boardingDate", "2017625");
        //Log.i("test",items1.get("boardingDate")+"");
        item7.put("departurePoint", "부산");
        item7.put("destPoint", "인천");
        item7.put("paid", 1);
        item7.put("seatNum", "A23");
        item7.put("ticketID", 1337);
        item7.put("customID", 31337);
        item7.put("trainNum", 123);
        item7.put("use", 0);
        dbhelper.insert("TICKET_INFO", item7);

        HashMap<String, Object> item8 = new HashMap<String, Object>();
        item8.put("boardingDate", "2017625");
        item8.put("departurePoint", "부산");
        item8.put("destPoint", "인천");
        item8.put("paid", 1);
        item8.put("seatNum", "A25");
        item8.put("ticketID", 1337);
        item8.put("customID", 31337);
        item8.put("trainNum", 123);
        item8.put("use", 1);
        dbhelper.insert("TICKET_INFO", item8);

        HashMap<String, Object> item9 = new HashMap<String, Object>();
        item9.put("boardingDate", "2017625");
        item9.put("departurePoint", "부산");
        item9.put("destPoint", "서울");
        item9.put("totalAvailableSeatNum", 120);
        item9.put("trainNum", 123);
        dbhelper.insert("TRAIN_INFO", item9);

        HashMap<String, Object> item10 = new HashMap<String, Object>();
        item10.put("boardingDate", "2017625");
        item10.put("availableSeat", "1A9");
        item10.put("trainNum", 123);
        dbhelper.insert("SEAT_INFO", item10);

        HashMap<String, Object> item11 = new HashMap<String, Object>();
        item11.put("boardingDate", "2017625");
        item11.put("availableSeat", "1C3");
        item11.put("trainNum", 123);
        dbhelper.insert("SEAT_INFO", item11);

        HashMap<String, Object> item12 = new HashMap<String, Object>();
        item12.put("boardingDate", "2017625");
        item12.put("availableSeat", "2A9");
        item12.put("trainNum", 123);
        dbhelper.insert("SEAT_INFO", item12);

        HashMap<String, Object> item13 = new HashMap<String, Object>();
        item13.put("boardingDate", "2017625");
        item13.put("availableSeat", "2A3");
        item13.put("trainNum", 123);
        dbhelper.insert("SEAT_INFO", item13);

        HashMap<String, Object> item14 = new HashMap<String, Object>();
        item14.put("boardingDate", "2017625");
        item14.put("availableSeat", "3D3");
        item14.put("trainNum", 123);
        dbhelper.insert("SEAT_INFO", item14);

        HashMap<String, Object> item15 = new HashMap<String, Object>();
        item15.put("boardingDate", "2017625");
        item15.put("availableSeat", "3B3");
        item15.put("trainNum", 123);
        dbhelper.insert("SEAT_INFO", item15);

        HashMap<String, Object> item16 = new HashMap<String, Object>();
        item16.put("boardingDate", "2017625");
        item16.put("availableSeat", "1B4");
        item16.put("trainNum", 123);
        dbhelper.insert("SEAT_INFO", item16);
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
