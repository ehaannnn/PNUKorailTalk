package com.example.korailtalk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class TicketCancelActivity extends Activity {

    private int ticketID, customID;
    private DBHelper dbhelper;
    private List<HashMap<String,Object>> ticket_infos;
    private HashMap<String, Object> ticket_info;
    private Button yesButton, noButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_cancel);

        Intent intent = this.getIntent();
        ticketID = intent.getIntExtra("ticketID", 0);
        customID = intent.getIntExtra("customID", 0);
        Log.i("test",customID+"");
        TextView textView_ticketID = (TextView)findViewById(R.id.ticketID);
        TextView textView_trainNum = (TextView)findViewById(R.id.trainNum);
        TextView textView_boardingDate = (TextView)findViewById(R.id.boardingDate);
        TextView textView_departurePoint = (TextView)findViewById(R.id.departurePoint);
        TextView textView_destPoint = (TextView)findViewById(R.id.destPoint);
        TextView textView_seatNum = (TextView)findViewById(R.id.seatNum);
        TextView textView_isPaid = (TextView)findViewById(R.id.isPaid);
        TextView textView_isUsable = (TextView)findViewById(R.id.isUsable);

        dbhelper = new DBHelper(getApplicationContext(), "PNUKorailTalk.db",null,1);

        ticket_infos = dbhelper.getResultAt("TICKET_INFO",customID);
        for(int i=0; i<ticket_infos.size(); i++) {
            if(ticket_infos.get(i).get("ticketID").toString().equals(ticketID)) {
                ticket_info = ticket_infos.get(i);
            }
        }

        textView_ticketID.setText(ticket_info.get("ticketID").toString());
        textView_trainNum.setText(ticket_info.get("trainNum").toString());
        textView_boardingDate.setText(ticket_info.get("boardingDate").toString());
        textView_departurePoint.setText(ticket_info.get("departurePoint").toString());
        textView_destPoint.setText(ticket_info.get("destPoint").toString());
        textView_seatNum.setText(ticket_info.get("seatNum").toString());

        if(paidCheck((int)ticket_info.get("paid")))
            textView_isPaid.setText("Y");
        else
            textView_isPaid.setText("N");

        if(isUse((int)ticket_info.get("use"))) {
            textView_isUsable.setText("N");
        } else
            textView_isUsable.setText("Y");

        yesButton = (Button) findViewById(R.id.yesButton);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbhelper.DeleteTicketInfoTablebyticketID(ticket_info.get("ticketID").toString(), customID+"");
                Integer newTASN = Integer.parseInt(ticket_info.get("totalAvailableSeatNum").toString()) + 1;
                dbhelper.UpdateTrainInfoTotalAvailableSN(ticket_info.get("trainNum").toString(), ticket_info.get("boardingDate").toString(), newTASN.toString());

                HashMap<String, Object> item = new HashMap<String, Object>();
                item.put("boardingDate", ticket_info.get("boardingDate").toString());
                item.put("availableSeat", ticket_info.get("seatNum").toString());
                item.put("trainNum", Integer.parseInt(ticket_info.get("trainNum").toString()));
                dbhelper.insert("SEAT_INFO", item);

                Intent intent = new Intent(TicketCancelActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
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