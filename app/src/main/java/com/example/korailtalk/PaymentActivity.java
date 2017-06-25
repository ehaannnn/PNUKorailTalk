package com.example.korailtalk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;
import java.util.List;

/**
 * Created by 이동기 on 2017-06-25.
 */

public class PaymentActivity extends AppCompatActivity {

    private String ticketNumber;
    private String trainNumber;
    private String boardingDate;
    private String departurePoint;
    private String destPoint;
    private String seatNum;
    private String newTicket;
    private DBHelper dbhelper;
    private int customNum;
    static int createID = 10000;

    private List<HashMap<String,Object>> ticket_infos, membership_info;
    private HashMap<String, Object> ticket_info, train_info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_payment_select);
        dbhelper = new DBHelper(getApplicationContext(), "PNUKorailTalk.db",null,1);

        final Intent i = getIntent();

        newTicket = i.getStringExtra("NEW_TICKET");
        customNum = Integer.parseInt(i.getStringExtra("customID"));

        Button btn_payment = (Button) findViewById(R.id.paymentStart);
        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //결제
                System.out.println("AaAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"+newTicket);
                if(newTicket.equals("new")) {//새로 티켓을 생성.

                    boardingDate = i.getStringExtra("departdate");
                    departurePoint = i.getStringExtra("departPoint");
                    destPoint = i.getStringExtra("destPoint");
                    seatNum = i.getStringExtra("seatinfo");
                    trainNumber = i.getStringExtra("trainum");

                    HashMap<String, Object> ticketInfo = new HashMap<String, Object>();
                    ticketInfo.put("boardingDate", boardingDate);
                    ticketInfo.put("departurePoint", departurePoint);
                    ticketInfo.put("destPoint", destPoint);
                    ticketInfo.put("paid", 1);
                    ticketInfo.put("deadLine", null);
                    ticketInfo.put("seatNum", seatNum);
                    ticketInfo.put("ticketID", createID);
                    createID++;
                    ticketInfo.put("customID", customNum);
                    ticketInfo.put("trainNum", Integer.parseInt(trainNumber));
                    ticketInfo.put("use", 0);
                    dbhelper.insert("TICKET_INFO", ticketInfo);
                }

                else if(newTicket.equals("old")) {//티켓 페이 변경
                    ticketNumber = i.getStringExtra("ticketID");
                    ticket_infos = dbhelper.getResultAt("TICKET_INFO",customNum);
                    for(int i=0; i<ticket_infos.size(); i++) {
                        if(Integer.parseInt(ticket_infos.get(i).get("ticketID").toString())==Integer.parseInt(ticketNumber)) {
                            ticket_info = ticket_infos.get(i);
                        }
                    }

                    trainNumber = ticket_info.get("trainNum").toString();
                    boardingDate = ticket_info.get("boardingDate").toString();
                    departurePoint = ticket_info.get("departurePoint").toString();
                    destPoint = ticket_info.get("destPoint").toString();
                    seatNum = ticket_info.get("seatNum").toString();
                    dbhelper.UpdateTicketInfoPaidZeroToOne(ticketNumber);
                }

                //available seat 수정

                System.out.println("AAAAAAAAAAAAVVVVVVVVVVVVVVV" + trainNumber + boardingDate);
                train_info = dbhelper.getResultAtTrainInfoTableby_TN_BD(trainNumber, boardingDate);

                String[] selected_seat = seatNum.split(",");
                String availableSeatNumber = train_info.get("totalAvailableSeatNum").toString();

                int newAvailableNum = Integer.parseInt(availableSeatNumber) - selected_seat.length;

                dbhelper.UpdateTrainInfoTotalAvailableSN(trainNumber, boardingDate, String.valueOf(newAvailableNum));

                //공통 변경 부분 사용가능

                //마일리지 증가
                membership_info = dbhelper.getResultAt("MEMBERSHIP_INFO", customNum);
                int mileage = Integer.parseInt(membership_info.get(0).get("KTXMileage").toString());
                mileage += 300 * selected_seat.length;
                dbhelper.UpdateKTXMileageSub300(customNum, mileage);

                //좌석 리스트 수정




                Intent intent = new Intent(
                        getApplicationContext(),
                        SendSMSAlarmActivity.class);
                startActivity(intent);
            }
        });

        Button btn_return = (Button) findViewById(R.id.paymentReturn);
        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(newTicket.equals("new")) {//paid 가 false인 티켓을 생성.

                    boardingDate = i.getStringExtra("departdate");
                    departurePoint = i.getStringExtra("departPoint");
                    destPoint = i.getStringExtra("destPoint");
                    seatNum = i.getStringExtra("seatInfo");
                    trainNumber = i.getStringExtra("trainum");

                    HashMap<String, Object> ticketInfo = new HashMap<String, Object>();
                    ticketInfo.put("boardingDate", boardingDate);
                    ticketInfo.put("departurePoint", departurePoint);
                    ticketInfo.put("destPoint", destPoint);
                    ticketInfo.put("paid", 0);
                    ticketInfo.put("deadLine", null);
                    ticketInfo.put("seatNum", seatNum);
                    ticketInfo.put("ticketID", createID);
                    createID++;
                    ticketInfo.put("customID", customNum);
                    ticketInfo.put("trainNum", Integer.parseInt(trainNumber));
                    ticketInfo.put("use", 0);
                    dbhelper.insert("TICKET_INFO", ticketInfo);

                    Intent intent = new Intent(
                            getApplicationContext(),
                            MainActivity.class);
                    startActivity(intent);
                }

                else if(newTicket.equals("old")) {//바로 예약승차권으로 감
                    finish();
                }

            }
        });
    }
}
