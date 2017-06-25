package com.example.korailtalk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;

/**
 * Created by 이동기 on 2017-06-25.
 */

public class SendSMSAlarmActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_payment_waiting);

        Intent intent = getIntent();
        String phoneNumber = intent.getStringExtra("PHONE_NUMBER");
        String msg = intent.getStringExtra("MESSAGE");

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, msg, null, null);

        Intent intent1 = new Intent(this, PaymentSuccessActivity.class);
        startActivity(intent1);
    }
}
