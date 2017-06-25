package com.example.korailtalk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by 이동기 on 2017-06-25.
 */

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_payment_select);

        Button btn_payment = (Button) findViewById(R.id.paymentStart);
        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //결제
                Intent intent = new Intent(
                        getApplicationContext(),
                        SendSMSAlarmActivity.class);
                startActivity(intent);
            }
        });

        Button btn_return = (Button) findViewById(R.id.paymentReturn);
        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        getApplicationContext(),
                        MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
