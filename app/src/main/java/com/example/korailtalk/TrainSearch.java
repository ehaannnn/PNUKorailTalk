package com.example.korailtalk;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Date;

/**
 * Created by ttaka on 2017. 6. 23..
 */

public class TrainSearch extends Activity {

    private Spinner departSpinner;
    private Spinner arrivalSpinner;
    private DatePicker departureDate;
    private EditText seatNum;
    private Button btnSerch;

    private DBHelper dbhelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        departSpinner = (Spinner) findViewById(R.id.departureList);
        arrivalSpinner = (Spinner) findViewById(R.id.arrivalList);
        departureDate = (DatePicker) findViewById(R.id.dateList);
        seatNum = (EditText) findViewById(R.id.seatNum);
        btnSerch = (Button)findViewById(R.id.trainsearch);

        dbhelper = new DBHelper(getApplicationContext(), "PNUKorailTalk.db",null,1);

        btnSerch.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(trainSearch(seatNum , departSpinner, arrivalSpinner, departureDate)){

                }

            }


        });


    }

    private boolean trainSearch(EditText seatNum, Spinner departSpinner, Spinner arrivalSpinner, Object p0) {
        return true;
    }
}