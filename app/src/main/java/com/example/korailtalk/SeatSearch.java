package com.example.korailtalk;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
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
    private LinearLayout im;
    private int firstime;
    private int totalselectnb;
    private Button btnbuyticket;
    private ArrayList seatinfo;
    private String seats;
    private static final String SEAT_SEARCH = "SEAT_SEARCH";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_search);

        Intent intent = getIntent();
        trainNum = intent.getIntExtra("trainum",0);
        nbofticket = intent.getIntExtra("nbofticket",0);
        departDate = intent.getIntExtra("departdate",0);
        firstime = 0;
        totalselectnb = 0;

        btnbuyticket = (Button)findViewById(R.id.buyticket);

        btnbuyticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(SeatSearch.this, CheckSessionActivity.class);
                for(int i =0; i < seatinfo.size(); i++){
                    seats += seatinfo.get(i);
                }
                intent2.putExtra("departdate",departDate);
                intent2.putExtra("trainum", trainNum);
                intent2.putExtra("nbofticket", nbofticket);
                intent2.putExtra("seatinfo", seats);
                intent2.putExtra("ActivityFrom", SEAT_SEARCH);
                startActivity(intent2);
            }
        });

        trainorderspinner = (Spinner)findViewById(R.id.trainorderspinner);
        trainorderspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                trainordernum = position+1;
                makeButton(trainordernum, firstime);
                firstime++;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    public void makeButton(int order, int firstcheck){
        boolean paidseat = false;
        im = (LinearLayout)findViewById(R.id.seatposition);

        if(firstcheck != 0) {
            im.removeAllViews();
        }

        dbhelper = new DBHelper(getApplicationContext(),"PNUKorailTalk.db",null,1);

        final List<HashMap<String,Object>> seat_info = dbhelper.getResultAtSeatTable(departDate,trainNum);

        List<HashMap<String, Object>> items = new LinkedList<HashMap<String, Object>>();
        for(int i = 0; i < seat_info.size(); i++){
            String first_char = seat_info.get(i).get("availableSeat").toString().substring(0,1);

            if(order == Integer.valueOf(first_char)){
                Log.i("두번쨰객차의 좌석 열",seat_info.get(i).get("availableSeat").toString().substring(1,2));
                Log.i("세번쨰객차의 좌석 열",seat_info.get(i).get("availableSeat").toString().substring(2,3));
                HashMap<String, Object> item = new HashMap<String, Object>();
                item.put("secondstr",seat_info.get(i).get("availableSeat").toString().substring(1,2));
                item.put("laststr",seat_info.get(i).get("availableSeat").toString().substring(2,3));
                items.add(item);
            }
        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT ,ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(2,2,0,0);
            params.height = 80;
            params.width = 150;

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
                    for(int k =0; k < items.size(); k++){
                        if(btntxt.equals(items.get(k).get("secondstr").toString()) &&
                                j == Integer.parseInt(items.get(k).get("laststr").toString())){
                            paidseat = true;
                        }
                    }
                    btntxt += String.valueOf(j);
                    btn.setText(btntxt);
                    if(paidseat) {
                        btn.setBackgroundColor(Color.WHITE);
                        btn.setOnClickListener(new View.OnClickListener() {
                            int click = 0;

                            @Override
                            public void onClick(View view) {

                                if(click == 0) {
                                    if(totalselectnb < nbofticket) {
                                        btn.setBackgroundColor(Color.BLUE);
                                        seatinfo.add(totalselectnb,btn.getText().toString());
                                        totalselectnb++;
                                        click++;
                                    }
                                }
                                else{
                                    btn.setBackgroundColor(Color.WHITE);
                                    totalselectnb--;
                                    seatinfo.remove(totalselectnb);
                                    click--;
                                }
                            }
                        });
                        paidseat = false;
                    }
                    else{
                        btn.setBackgroundColor(Color.DKGRAY);
                    }
                    btn.setLayoutParams(params);
                    lv.addView(btn);
                }
                im.addView(lv);
                im.setGravity(Gravity.CENTER);

            }

    }
}
