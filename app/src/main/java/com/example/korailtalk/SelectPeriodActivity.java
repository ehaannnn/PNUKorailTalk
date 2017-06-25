package com.example.korailtalk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.GregorianCalendar;

/**
 * Created by dlehd on 2017-06-25.
 */

public class SelectPeriodActivity extends Activity implements View.OnClickListener {


    private DatePicker startDate;
    private DatePicker endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pariod_select);

        startDate = (DatePicker) findViewById(R.id.dateStart);
        endDate = (DatePicker) findViewById(R.id.dateEnd);

        final String startString;
        String endString;
        GregorianCalendar calendar = new GregorianCalendar();

        Button startbtn = (Button)findViewById(R.id.startButton);
        startbtn.setOnClickListener(this);

 /*        startDate.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
               new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String start = Integer.toString(year) + Integer.toString(monthOfYear+1) + Integer.toString(dayOfMonth);
                        System.out.println(start);
                    }
                });*/




    }


    @Override
    public void onClick(View v) {
        String startDateString = Integer.toString(startDate.getYear()) + Integer.toString(startDate.getMonth()) + Integer.toString(startDate.getDayOfMonth());
        String endDateString = Integer.toString(endDate.getYear()) + Integer.toString(endDate.getMonth()) + Integer.toString(endDate.getDayOfMonth());

        Intent intent = new Intent(this, TicketHistoryActivity.class);
        intent.putExtra("STARTDATE", startDateString);
        intent.putExtra("ENDDATE", endDateString);

        startActivity(intent);
    }
}
