package com.example.korailtalk;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.lang.System.in;

/**
 * Created by ttaka on 2017. 6. 24..
 */

public class SeatSearch extends Activity {
    private int trainNum;
    private int nbofticket;
    private String departdatestr;
    private int trainordernum;
    private Spinner trainorderspinner;
    final static int DEFAULT_NUM = 1;
    private DBHelper dbhelper;
    private DBHelper dbhelper2;
    private LinearLayout im;
    private int firstime;
    private int totalselectnb;
    private Button btnbuyticket;
    private ArrayList seatinfo;
    private String seats;
    private static final String SEAT_SEARCH = "SEAT_SEARCH";
    private HashMap<String, Object> train_info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_search);

        Intent intent = getIntent();
        trainNum = intent.getIntExtra("trainum", 0);
        nbofticket = intent.getIntExtra("nbofticket", 0);
        departdatestr = intent.getStringExtra("departdate");
        firstime = 0;
        totalselectnb = 0;
        seatinfo = new ArrayList();

        dbhelper2 = new DBHelper(getApplicationContext(), "PNUKorailTalk.db", null, 1);

        train_info = dbhelper2.getResultAtTrainInfoTableby_TN_BD(String.valueOf(trainNum), departdatestr);
        btnbuyticket = (Button) findViewById(R.id.buyticket);

        btnbuyticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(SeatSearch.this, CheckSessionActivity.class);
                seats = seatinfo.get(0).toString();
                seats += ',';
                for (int i = 1; i < seatinfo.size(); i++) {
                    seats += seatinfo.get(i).toString();
                    seats += ',';
                }
                /*Log.i("departPoint", train_info.get("departurePoint").toString());
                Log.i("destPoint", train_info.get("destPoint").toString());
                Log.i("departdate", departdatestr);
                Log.i("trainum", String.valueOf(trainNum));
                Log.i("nbofticket", String.valueOf(nbofticket));
                Log.i("seatinfo", seats);*/

                intent2.putExtra("departPoint", train_info.get("departurePoint").toString());
                intent2.putExtra("destPoint", train_info.get("destPoint").toString());
                intent2.putExtra("departdate", departdatestr);
                intent2.putExtra("trainum", String.valueOf(trainNum));
                intent2.putExtra("nbofticket", String.valueOf(nbofticket));
                intent2.putExtra("seatinfo", seats);
                intent2.putExtra("ActivityFrom", SEAT_SEARCH);
                startActivity(intent2);
            }
        });


        trainorderspinner = (Spinner) findViewById(R.id.trainorderspinner);
        trainorderspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                trainordernum = position + 1;
                makeButton(trainordernum, firstime);
                firstime++;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }


    public Map<String, Object> createItem(int position) {
        Map<String, Object> item = new HashMap<String, Object>();
        item.put("position", position);
        return item;
    }


    public void makeButton(final int order, int firstcheck) {
        boolean paidseat = false;
        im = (LinearLayout) findViewById(R.id.seatposition);

        if (firstcheck != 0) {
            im.removeAllViews();
        }

        dbhelper = new DBHelper(getApplicationContext(), "PNUKorailTalk.db", null, 1);

        final List<HashMap<String, Object>> seat_info = dbhelper.getResultAtSeatTable(departdatestr, trainNum);
        Log.i("가능한좌석수", String.valueOf(seat_info.size()));
        List<HashMap<String, Object>> items = new LinkedList<HashMap<String, Object>>();
        for (int i = 0; i < seat_info.size(); i++) {
            String first_char = seat_info.get(i).get("availableSeat").toString().substring(0, 1);

            if (order == Integer.valueOf(first_char)) {
                HashMap<String, Object> item = new HashMap<String, Object>();
                item.put("secondstr", seat_info.get(i).get("availableSeat").toString().substring(1, 2));
                item.put("laststr", seat_info.get(i).get("availableSeat").toString().substring(2, 3));
                items.add(item);
            }
        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(2, 2, 0, 0);

        for (int i = 0; i < 4; i++) {
            LinearLayout lv = new LinearLayout(this);
            lv.setOrientation(LinearLayout.VERTICAL);
            for (int j = 0; j < 10; j++) {
                final Button btn = new Button(this);
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

                for (int k = 0; k < items.size(); k++) {
                    if (btntxt.equals(items.get(k).get("secondstr").toString()) &&
                            j == Integer.parseInt(items.get(k).get("laststr").toString())) {
                        paidseat = true;
                    }
                }
                btntxt += String.valueOf(j);
                btn.setText(btntxt);

                if (paidseat) {
                    btn.setBackgroundColor(Color.WHITE);
                    for (int m = 0; m < seatinfo.size(); m++) {
                        String str = String.valueOf(order);
                        str += btn.getText().toString();
                        if (str.equals(seatinfo.get(m))) {
                            btn.setBackgroundColor(Color.BLUE);
                        }
                    }
                    btn.setOnClickListener(new View.OnClickListener() {
                        int click = 0;
                        String tempseatinfo = String.valueOf(order);

                        @Override
                        public void onClick(View view) {
                            ColorDrawable drawable = (ColorDrawable)btn.getBackground();
                            int color = drawable.getColor();
                            if(color == Color.BLUE) click = 1;
                            else click = 0;
                            if (click == 0) {
                                if (totalselectnb < nbofticket) {
                                    btn.setBackgroundColor(Color.BLUE);
                                    tempseatinfo += btn.getText().toString();
                                    seatinfo.add(totalselectnb, tempseatinfo);
                                    totalselectnb++;
                                    click++;
                                }
                            } else {
                                btn.setBackgroundColor(Color.WHITE);
                                totalselectnb--;
                                seatinfo.remove(totalselectnb);
                                click--;
                            }
                        }
                    });
                    paidseat = false;
                } else {
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
