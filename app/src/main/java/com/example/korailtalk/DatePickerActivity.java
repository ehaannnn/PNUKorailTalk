package com.example.korailtalk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

public class DatePickerActivity extends Activity {
    private DatePicker datePicker;
    private Button checkConfirm;
    private String boardingDate;
    private String boardingDateNoformat;
    private static final int RESULT_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);


        checkConfirm = (Button) findViewById(R.id.checkConfirm);
        checkConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("boardingDate", boardingDate);
                intent.putExtra("boardingDatestr", boardingDateNoformat);

                setResult(RESULT_CODE, intent);
                finish();
            }
        });



        datePicker = (DatePicker) findViewById(R.id.datepicker);

        datePicker.init(datePicker.getYear(),
                datePicker.getMonth(),
                datePicker.getDayOfMonth(),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        boardingDate = String.format("%d-%d-%d", year, monthOfYear + 1, dayOfMonth);//6월 선택했는데 5월로 나와 +1로 태원수정
                        boardingDateNoformat = String.format("%d%d%d",year,monthOfYear + 1, dayOfMonth);
                    }
                });

        boardingDate = String.format("%d-%d-%d",datePicker.getYear(),datePicker.getMonth(), datePicker.getDayOfMonth());
        boardingDateNoformat = String.format("%d%d%d",datePicker.getYear(),datePicker.getMonth(), datePicker.getDayOfMonth());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_date_picker, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
