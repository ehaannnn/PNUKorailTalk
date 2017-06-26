package com.example.korailtalk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
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
    private String ticketNumberOutput;

    private List<HashMap<String,Object>> ticket_infos, membership_info, member_info;
    private HashMap<String, Object> ticket_info, train_info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_payment_select);
        dbhelper = new DBHelper(getApplicationContext(), "PNUKorailTalk.db",null,1);

        final Intent i = getIntent();

        newTicket = i.getStringExtra("NEW_TICKET");
        customNum = Integer.parseInt(i.getStringExtra("customID"));

        if(newTicket.equals("new")) {
            String boardingDate = i.getStringExtra("departdate");
            String departurePoint = i.getStringExtra("departPoint");
            String destPoint = i.getStringExtra("destPoint");
            String seatNum = i.getStringExtra("seatinfo");
            String trainNumber = i.getStringExtra("trainum");

            TextView textViewBoardingDate = (TextView) findViewById(R.id.bordingDate);
            TextView textViewSeatQuentity = (TextView) findViewById(R.id.seatQuentity);
            TextView textViewSeatNum = (TextView) findViewById(R.id.seatInfo);
            TextView textViewTime = (TextView) findViewById(R.id.timeInfo);

            textViewBoardingDate.setText(boardingDate.substring(0,4) + "년 " + boardingDate.substring(4,6) + "월 " + boardingDate.substring(6, 8) + "일 " + boardingDate.substring(8, 10) + "시 " + boardingDate.substring(10, 12) + "분");
            textViewSeatQuentity.setText("출발점 : " + departurePoint);
            textViewSeatNum.setText("도착점 : " + destPoint);
            textViewTime.setText("좌석번호 : " + seatNum);

        } else  {
            ticketNumber = i.getStringExtra("ticketID");
            ticketNumberOutput = ticketNumber;
            ticket_infos = dbhelper.getResultAt("TICKET_INFO",customNum);
            for(int count=0; count<ticket_infos.size(); count++) {
                if(Integer.parseInt(ticket_infos.get(count).get("ticketID").toString())==Integer.parseInt(ticketNumber)) {
                    ticket_info = ticket_infos.get(count);
                }
            }

            String trainNumber = ticket_info.get("trainNum").toString();
            String boardingDate = ticket_info.get("boardingDate").toString();
            String departurePoint = ticket_info.get("departurePoint").toString();
            String destPoint = ticket_info.get("destPoint").toString();
            String seatNum = ticket_info.get("seatNum").toString();

            TextView textViewBoardingDate = (TextView) findViewById(R.id.bordingDate);
            TextView textViewSeatQuentity = (TextView) findViewById(R.id.seatQuentity);
            TextView textViewSeatNum = (TextView) findViewById(R.id.seatInfo);
            TextView textViewTime = (TextView) findViewById(R.id.timeInfo);

            textViewBoardingDate.setText(boardingDate.substring(0,4) + "년 " + boardingDate.substring(4,6) + "월 " + boardingDate.substring(6, 8) + "일 " + boardingDate.substring(8, 10) + "시 " + boardingDate.substring(10, 12) + "분");
            textViewSeatQuentity.setText("출발점 : " + departurePoint);
            textViewSeatNum.setText("도착점 : " + destPoint);
            textViewTime.setText("좌석번호 : " + seatNum);
        }

        Button btn_payment = (Button) findViewById(R.id.paymentStart);
        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //결제
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
                    ticketNumberOutput = String.valueOf(createID);
                    createID++;
                    ticketInfo.put("customID", customNum);
                    ticketInfo.put("trainNum", Integer.parseInt(trainNumber));
                    ticketInfo.put("use", 0);
                    dbhelper.insert("TICKET_INFO", ticketInfo);
                }

                else if(newTicket.equals("old")) {//티켓 페이 변경
                    ticketNumber = i.getStringExtra("ticketID");
                    ticketNumberOutput = ticketNumber;
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

                train_info = dbhelper.getResultAtTrainInfoTableby_TN_BD(trainNumber, boardingDate);

                String[] selected_seat = seatNum.split(",");
                String availableSeatNumber = train_info.get("totalAvailableSeatNum").toString();

                int newAvailableNum = Integer.parseInt(availableSeatNumber) - selected_seat.length;

                dbhelper.UpdateTrainInfoTotalAvailableSN(trainNumber, boardingDate, String.valueOf(newAvailableNum));

                //공통 변경 부분 사용가능

                //마일리지 증가
                member_info = dbhelper.getResultAt("MEMBER", customNum);
                membership_info = dbhelper.getResultAt("MEMBERSHIP_INFO", customNum);
                int mileage = Integer.parseInt(membership_info.get(0).get("KTXMileage").toString());
                mileage += 300 * selected_seat.length;
                dbhelper.UpdateKTXMileageSub300(customNum, mileage);

                //좌석 리스트 수정(제거)

                for(int counter = 0; counter < selected_seat.length; counter++) {
                    dbhelper.DeleteSeatInfo(boardingDate, selected_seat[counter], Integer.parseInt(trainNumber));
                }


                Intent intent = new Intent(
                        getApplicationContext(),
                        SendSMSAlarmActivity.class);
                intent.putExtra("PHONE_NUMBER", member_info.get(0).get("phoneNum").toString());
                intent.putExtra("MESSAGE", ticketNumberOutput + "번 티켓이 예약되었습니다.");
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
                    seatNum = i.getStringExtra("seatinfo");
                    trainNumber = i.getStringExtra("trainum");
                    long mNow;
                    Date mDate;
                    SimpleDateFormat mFormat = new SimpleDateFormat("yyyyMMddhhmm");

                    mNow = System.currentTimeMillis() + 86400000;
                    mDate = new Date(mNow);

                    String nowTime = mFormat.format(mDate);

                    HashMap<String, Object> ticketInfo = new HashMap<String, Object>();
                    ticketInfo.put("boardingDate", boardingDate);
                    ticketInfo.put("departurePoint", departurePoint);
                    ticketInfo.put("destPoint", destPoint);
                    ticketInfo.put("paid", 0);
                    ticketInfo.put("deadLine", nowTime);
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
