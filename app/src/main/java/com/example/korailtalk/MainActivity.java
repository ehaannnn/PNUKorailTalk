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

    private static boolean dbinit_flag = false;


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
                intent4.putExtra("ActivityFrom",UNPAID_TICKET_BUTTON);
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
                Intent intent = new Intent(MainActivity.this, CheckSessionActivity.class);
                intent.putExtra("ActivityFrom", TICKET_HISTORY_BUTTON);
                startActivity(intent);
            }
        });

        //동기 끝

        /* 한결  시작*/
        checkPaiedTicket_button = (Button) findViewById(R.id.checkPaiedTicket_button);

        dbinit();

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

    private void dbinit() {
        if(dbinit_flag == false) {
            dbhelper = new DBHelper(getApplicationContext(), "PNUKorailTalk.db", null, 1);

            dbhelper.dropTable();

            // 우진 수정 시작
            HashMap<String, Object> member1 = new HashMap<String, Object>();
            member1.put("ID", "admin");
            member1.put("customID", "1");
            member1.put("phoneNum", "010-1234-5678");
            member1.put("password", "admin");
            dbhelper.insert("MEMBER", member1);

            HashMap<String, Object> member2 = new HashMap<String, Object>();
            member2.put("ID", "holinder4s");
            member2.put("customID", "31337");
            member2.put("phoneNum", "010-7185-7663");
            member2.put("password", "helloworld");
            dbhelper.insert("MEMBER", member2);

            HashMap<String, Object> member3 = new HashMap<String, Object>();
            member3.put("ID", "holinder4s");
            member3.put("customID", "31337");
            member3.put("phoneNum", "010-7185-7663");
            member3.put("password", "helloworld");
            dbhelper.insert("MEMBER", member3);

            HashMap<String, Object> nonmember1 = new HashMap<String, Object>();
            nonmember1.put("customID", "-1234");
            nonmember1.put("phoneNum", "010-1111-1111");
            nonmember1.put("password", "test1234");
            dbhelper.insert("NON_MEMBER", nonmember1);

            HashMap<String, Object> nonmember2 = new HashMap<String, Object>();
            nonmember2.put("customID", "-2222");
            nonmember2.put("phoneNum", "010-2222-2222");
            nonmember2.put("password", "test2222");
            dbhelper.insert("NON_MEMBER", nonmember2);

            HashMap<String, Object> membershipinfo1 = new HashMap<String, Object>();
            membershipinfo1.put("CouponNum", "9999");
            membershipinfo1.put("KTXMileage", "99999999");
            membershipinfo1.put("ID", "admin");
            membershipinfo1.put("customID", "1");
            dbhelper.insert("MEMBERSHIP_INFO", membershipinfo1);

            HashMap<String, Object> membershipinfo2 = new HashMap<String, Object>();
            membershipinfo2.put("CouponNum", "3");
            membershipinfo2.put("KTXMileage", "23500");
            membershipinfo2.put("ID", "holinder4s");
            membershipinfo2.put("customID", "31337");
            dbhelper.insert("MEMBERSHIP_INFO", membershipinfo2);

            HashMap<String, Object> ticketinfo1 = new HashMap<String, Object>();
            ticketinfo1.put("boardingDate", "201706261300");
            //Log.i("test",items1.get("boardingDate")+"");
            ticketinfo1.put("departurePoint", "부산");
            ticketinfo1.put("destPoint", "서울");
            ticketinfo1.put("paid", 1);
            ticketinfo1.put("seatNum", "3A3,3A4");
            ticketinfo1.put("ticketID", 1337);
            ticketinfo1.put("customID", 31337);
            ticketinfo1.put("trainNum", 123);
            ticketinfo1.put("use", 0);
            dbhelper.insert("TICKET_INFO", ticketinfo1);

            HashMap<String, Object> ticketinfo2 = new HashMap<String, Object>();
            ticketinfo2.put("boardingDate", "201706251300");
            ticketinfo2.put("departurePoint", "부산");
            ticketinfo2.put("destPoint", "서울");
            ticketinfo2.put("paid", 0);
            ticketinfo2.put("deadLine", "201706241300");
            ticketinfo2.put("seatNum", "3A5");
            ticketinfo2.put("ticketID", 1338);
            ticketinfo2.put("customID", 31337);
            ticketinfo2.put("trainNum", 123);
            ticketinfo2.put("use", 0);
            dbhelper.insert("TICKET_INFO", ticketinfo2);

            HashMap<String, Object> ticketinfo3 = new HashMap<String, Object>();
            ticketinfo3.put("boardingDate", "20176231300");
            ticketinfo3.put("departurePoint", "부산");
            ticketinfo3.put("destPoint", "서울");
            ticketinfo3.put("paid", 1);
            ticketinfo3.put("seatNum", "1A5");
            ticketinfo3.put("ticketID", 1336);
            ticketinfo3.put("customID", 31337);
            ticketinfo3.put("trainNum", 123);
            ticketinfo3.put("use", 1);
            dbhelper.insert("TICKET_INFO", ticketinfo3);

            HashMap<String, Object> traininfo1 = new HashMap<String, Object>();
            traininfo1.put("boardingDate", "201706261300");
            traininfo1.put("departurePoint", "부산");
            traininfo1.put("destPoint", "서울");
            traininfo1.put("totalAvailableSeatNum", 117);
            traininfo1.put("trainNum", 123);
            dbhelper.insert("TRAIN_INFO", traininfo1);

            HashMap<String, Object> traininfo2 = new HashMap<String, Object>();
            traininfo2.put("boardingDate", "201706231300");
            traininfo2.put("departurePoint", "부산");
            traininfo2.put("destPoint", "서울");
            traininfo2.put("totalAvailableSeatNum", 119);
            traininfo2.put("trainNum", 123);
            dbhelper.insert("TRAIN_INFO", traininfo2);

            HashMap<String, Object> traininfo3 = new HashMap<String, Object>();
            traininfo3.put("boardingDate", "201706251500");
            traininfo3.put("departurePoint", "부산");
            traininfo3.put("destPoint", "서울");
            traininfo3.put("totalAvailableSeatNum", 3);
            traininfo3.put("trainNum", 133);
            dbhelper.insert("TRAIN_INFO", traininfo3);

            for(int order=0; order<3; order++) {
                for(int alpha=0; alpha<4; alpha++) {
                    for (int num = 0; num < 10; num++) {
                        HashMap<String, Object> seatinfo1 = new HashMap<String, Object>();
                        seatinfo1.put("boardingDate", "201706261300");
                        String availableSeat = String.valueOf(order+1);
                        if(alpha == 0) availableSeat += "A";
                        else if(alpha == 1) availableSeat += "B";
                        else if(alpha == 2) availableSeat += "C";
                        else if(alpha == 3) availableSeat += "D";
                        availableSeat += String.valueOf(num);
                        if(availableSeat.equals("3A3") || availableSeat.equals("3A4") || availableSeat.equals("3A5")) break;
                        seatinfo1.put("availableSeat", availableSeat);
                        seatinfo1.put("trainNum", 123);
                        dbhelper.insert("SEAT_INFO", seatinfo1);
                    }
                }
            }

            for(int order=0; order<3; order++) {
                for(int alpha=0; alpha<4; alpha++) {
                    for (int num = 0; num < 10; num++) {
                        HashMap<String, Object> seatinfo2 = new HashMap<String, Object>();
                        seatinfo2.put("boardingDate", "201706231300");
                        String availableSeat = String.valueOf(order+1);
                        if(alpha == 0) availableSeat += "A";
                        else if(alpha == 1) availableSeat += "B";
                        else if(alpha == 2) availableSeat += "C";
                        else if(alpha == 3) availableSeat += "D";
                        availableSeat += String.valueOf(num);
                        if(availableSeat.equals("1A5")) break;
                        seatinfo2.put("availableSeat", availableSeat);
                        seatinfo2.put("trainNum", 123);
                        dbhelper.insert("SEAT_INFO", seatinfo2);
                    }
                }
            }

            for(int order=0; order<3; order++) {
                for(int alpha=0; alpha<4; alpha++) {
                    for (int num = 0; num < 10; num++) {
                        HashMap<String, Object> seatinfo3 = new HashMap<String, Object>();
                        seatinfo3.put("boardingDate", "201706251500");
                        String availableSeat = String.valueOf(order+1);
                        if(alpha == 0) availableSeat += "A";
                        else if(alpha == 1) availableSeat += "B";
                        else if(alpha == 2) availableSeat += "C";
                        else if(alpha == 3) availableSeat += "D";
                        availableSeat += String.valueOf(num);
                        if(availableSeat.equals("1A2") || availableSeat.equals("1A3") || availableSeat.equals("1A4")) {
                            seatinfo3.put("availableSeat", availableSeat);
                            seatinfo3.put("trainNum", 123);
                            dbhelper.insert("SEAT_INFO", seatinfo3);
                        }
                    }
                }
            }
            // 우진 수정 끝

            dbinit_flag = true;
        }
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
