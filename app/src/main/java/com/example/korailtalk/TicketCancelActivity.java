package com.example.korailtalk;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class TicketCancelActivity extends Activity {

    final Context context = this;
    private int ticketID, customID;
    private DBHelper dbhelper;
    private List<HashMap<String,Object>> ticket_infos, membership_info;
    private HashMap<String, Object> ticket_info, train_info;
    private Button yesButton, noButton;
    private String[] selected_seat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_cancel);

        Intent intent = this.getIntent();
        ticketID = intent.getIntExtra("ticketID", 0);
        customID = intent.getIntExtra("customID", 0);
        Log.i("test",customID+" ," +ticketID);
        TextView textView_ticketID = (TextView)findViewById(R.id.ticketID);
        TextView textView_trainNum = (TextView)findViewById(R.id.trainNum);
        TextView textView_boardingDate = (TextView)findViewById(R.id.boardingDate);
        TextView textView_departurePoint = (TextView)findViewById(R.id.departurePoint);
        TextView textView_destPoint = (TextView)findViewById(R.id.destPoint);
        TextView textView_seatNum = (TextView)findViewById(R.id.seatNum);
        TextView textView_passengerNum = (TextView)findViewById(R.id.passengerNum);
        TextView textView_isPaid = (TextView)findViewById(R.id.isPaid);
        TextView textView_isUsable = (TextView)findViewById(R.id.isUsable);

        dbhelper = new DBHelper(getApplicationContext(), "PNUKorailTalk.db",null,1);

        ticket_infos = dbhelper.getResultAt("TICKET_INFO",customID);
        for(int i=0; i<ticket_infos.size(); i++) {
            if(Integer.parseInt(ticket_infos.get(i).get("ticketID").toString())==ticketID) {
                ticket_info = ticket_infos.get(i);
            }
        }

        textView_ticketID.setText(ticket_info.get("ticketID").toString());
        textView_trainNum.setText(ticket_info.get("trainNum").toString());
        textView_boardingDate.setText(ticket_info.get("boardingDate").toString());
        textView_departurePoint.setText(ticket_info.get("departurePoint").toString());
        textView_destPoint.setText(ticket_info.get("destPoint").toString());
        textView_seatNum.setText(ticket_info.get("seatNum").toString());

        selected_seat = ticket_info.get("seatNum").toString().split(",");
        textView_passengerNum.setText(String.valueOf(selected_seat.length));

        if(paidCheck(Integer.parseInt(ticket_info.get("paid").toString())))
            textView_isPaid.setText("Y");
        else
            textView_isPaid.setText("N");

        if(isUse(Integer.parseInt(ticket_info.get("use").toString()))) {
            textView_isUsable.setText("Y");
        } else
            textView_isUsable.setText("N");

        yesButton = (Button) findViewById(R.id.yesButton);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String time = sdfNow.format(new Date(System.currentTimeMillis()));
                String nowtime = time.substring(0,4) + time.substring(5,7) + time.substring(8,10) + time.substring(11,13) + time.substring(14,16);

                BigInteger nowtime_big = new BigInteger(nowtime);
                BigInteger boadingtime_big = new BigInteger(ticket_info.get("boardingDate").toString());
                if(nowtime_big.compareTo(boadingtime_big) == -1) {
                    train_info = dbhelper.getResultAtTrainInfoTableby_TN_BD(ticket_info.get("trainNum").toString(), ticket_info.get("boardingDate").toString());
                    membership_info = dbhelper.getResultAt("MEMBERSHIP_INFO", customID);
                    dbhelper.DeleteTicketInfoTablebyticketID(ticketID, customID);
                    Integer newTASN = Integer.parseInt(train_info.get("totalAvailableSeatNum").toString()) + selected_seat.length;
                    Integer newKTXMileage = Integer.parseInt(membership_info.get(0).get("KTXMileage").toString()) - 300*selected_seat.length;
                    dbhelper.UpdateTrainInfoTotalAvailableSN(ticket_info.get("trainNum").toString(), ticket_info.get("boardingDate").toString(), newTASN.toString());
                    dbhelper.UpdateKTXMileageSub300(customID, newKTXMileage);

                    for(int i=0; i<selected_seat.length; i++) {
                        HashMap<String, Object> item = new HashMap<String, Object>();
                        item.put("boardingDate", ticket_info.get("boardingDate").toString());
                        item.put("availableSeat", selected_seat[i]);
                        item.put("trainNum", Integer.parseInt(ticket_info.get("trainNum").toString()));
                        dbhelper.insert("SEAT_INFO", item);
                    }

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle("승차권 취소 결과");
                    alertDialogBuilder
                            .setMessage("승차권을 취소하였습니다 :)")
                            .setCancelable(false)
                            .setPositiveButton("취소 확인",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            finish();
                                        }
                                    }
                            );
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
                else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle("승차권 취소 에러");
                    alertDialogBuilder
                            .setMessage("탑승날짜가 지난 승차권입니다 :(")
                            .setCancelable(false)
                            .setPositiveButton("확인",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            finish();
                                        }
                                    }
                            );
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
        });

        noButton = (Button) findViewById(R.id.noButton);
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TicketCancelActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // 승차권이 사용된 승차권인지 확인
    public boolean isUse(int use) {
        if(use == 1) return true;
        else return false;
    }
    // 승차권이 결제되었는지 확인
    public boolean paidCheck(int paid) {
        if(paid == 1) return true;
        else return false;
    }
}