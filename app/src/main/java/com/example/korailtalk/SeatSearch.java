package com.example.korailtalk;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ttaka on 2017. 6. 24..
 */

public class SeatSearch extends Activity {
    private int trainNum;
    private int nbofticket;
    private int departDate;
    private int trainordernum;
    private Spinner trainorderspinner;
    final static int DEFAULT_NUM = 1;
    private DBHelper dbhelper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_search);

        Intent intent = getIntent();
        trainNum = intent.getIntExtra("trainum",0);
        nbofticket = intent.getIntExtra("nbofticket",0);
        departDate = intent.getIntExtra("departdate",0);

        trainorderspinner = (Spinner)findViewById(R.id.trainorderspinner);
        trainorderspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                trainordernum = position+1;
                makeButton(trainordernum);

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                makeButton(DEFAULT_NUM);
            }
        });


    }

    public void makeButton(int order){
        final LinearLayout im = (LinearLayout)findViewById(R.id.seatposition);
        dbhelper = new DBHelper(getApplicationContext(),"PNUKorailTalk.db",null,1);

        final List<HashMap<String,Object>> seat_info = dbhelper.getResultAtSeatTable(departDate,trainNum);

        HashMap<String, Object> item = new HashMap<String, Object>();
        List<HashMap<String, Object>> items = new LinkedList<HashMap<String, Object>>();

        for(int i = 0; i < seat_info.size(); i++){
            String first_char = seat_info.get(i).get("availableSeat").toString().substring(1,1);
            if(order == Integer.valueOf(first_char)){
                item.put("secondstr",seat_info.get(i).get("availableSeat").toString().substring(2,2));
                item.put("laststr",seat_info.get(i).get("availableSeat").toString().substring(3,3));
                items.add(item);
            }
        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

            for (int i = 0; i < 4; i++) {
                LinearLayout lv = new LinearLayout(this);
                lv.setOrientation(LinearLayout.VERTICAL);
                for (int j = 0; j < 10; j++) {
                    final Button btn = new Button(this);
                    btn.setId(i * 10 + j);
                    String btntxt;
                    if (i == 0) {
                        btntxt = "A";
                    } else if (i == 1) {
                        btntxt = "B";
                    } else if (i == 2) {
                        btntxt = "C";
                    } else {
                        btntxt = "D";
                    }
                    btntxt += String.valueOf(j);
                    btn.setText(btntxt);
                    btn.setBackgroundColor(Color.WHITE);
                    btn.setLayoutParams(params);

                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });
                    lv.addView(btn);
                }
                im.addView(lv);
            }
            for(int i =0; i < items.size(); i++){
                
            }
    }
}
