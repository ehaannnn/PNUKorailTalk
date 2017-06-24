package com.example.korailtalk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by ttaka on 2017. 6. 24..
 */

public class SeatSearch extends Activity {
    private Button btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_search);

        btn = (Button)findViewById(R.id.test);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SeatSearch.this, TrainSearch.class);
                startActivity(intent);
            }
        });
    }
}
