package com.example.korailtalk;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.example.korailtalk.R.id.departurePoint;
import static com.example.korailtalk.R.id.trainNum;

/**
 * Created by ttaka on 2017. 6. 23..
 */

public class TrainSearch extends Activity {

    private Spinner departSpinner;
    private Spinner arrivalSpinner;
    private EditText seatNum;
    private Button btnSerch;
    private Button btnDepartDate;
    private TextView departDate;
    private String departDatestr;

    private String departPointstr;
    private String destinationPointstr;

    private DBHelper dbhelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_search);


        departSpinner = (Spinner) findViewById(R.id.departureList);
        departSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                departPointstr = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        arrivalSpinner = (Spinner) findViewById(R.id.arrivalList);
        arrivalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                destinationPointstr = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        seatNum = (EditText) findViewById(R.id.seatNum);
        btnSerch = (Button)findViewById(R.id.trainsearch);

        dbhelper = new DBHelper(getApplicationContext(), "PNUKorailTalk.db",null,1);


        final List<HashMap<String,Object>> train_info = dbhelper.getResultAt("TRAIN_INFO",131);

        btnSerch.setOnClickListener(new Button.OnClickListener(){
            boolean available = false;

            @Override
            public void onClick(View view) {
                for(int i =0; i < train_info.size(); i++){
                    if(Integer.parseInt(departDatestr) ==
                            Integer.parseInt(train_info.get(i).get("boardingDate").toString())){
                        if(departPointstr.equals(train_info.get(i).get("departurePoint").toString())
                                && destinationPointstr.equals(train_info.get(i).get("destPoint").toString())){
                            if(Integer.parseInt(train_info.get(i).get("totalAvailableSeatNum").toString())
                                    >= Integer.parseInt(seatNum.toString())){
                                available = true;
                            }
                        }
                    }
                }
                if(available){
                    available = false;
                    Log.i("점검","성공");
                    Intent intent = new Intent(TrainSearch.this , SeatSearch.class);
                    startActivity(intent);
                }
                else{
                    AlertDialog.Builder alert = new AlertDialog.Builder(TrainSearch.this);
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alert.setMessage("가능한 열차가 없습니다.");
                    alert.show();

                }

            }


        });

        departDate = (TextView) findViewById(R.id.departDate);
        btnDepartDate = (Button) findViewById(R.id.departdateButton);
        btnDepartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainSearch.this, DatePickerActivity.class);
                startActivityForResult(intent, 1);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            String boardingDate = data.getStringExtra("boardingDate");
            departDatestr = data.getStringExtra("boardingDatestr");
            departDate.setText(boardingDate);


        }

        else {
            Log.i("실행됨?", "여기는 에러");
        }
    }

}