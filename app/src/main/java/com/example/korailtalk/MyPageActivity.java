package com.example.korailtalk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class MyPageActivity extends Activity {

    private DBHelper dbhelper;
    private String customer_id;
    private List<HashMap<String,Object>> membership_info, member_info;
    private String ID, phoneNum, KTXmileage, couponNum;
    private int customID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        Intent intent = new Intent(this.getIntent());
        customer_id = intent.getStringExtra("customID");
        TextView textView_ID = (TextView)findViewById(R.id.ID);
        TextView textView_customID = (TextView)findViewById(R.id.customID);
        TextView textView_phoneNum = (TextView)findViewById(R.id.phoneNum);
        TextView textView_KTXmileage = (TextView)findViewById(R.id.KTXMileage);
        TextView textView_couponNum = (TextView)findViewById(R.id.couponNum);

        dbhelper = new DBHelper(getApplicationContext(), "PNUKorailTalk.db",null,1);
        if(isMember(customer_id)) {
            member_info = dbhelper.getResultAt("MEMBER",customID);
            membership_info = dbhelper.getResultAt("MEMBERSHIP_INFO",customID);

           /* member_info.put("ID", "holinder4s");
            member_info.put("customID", String.valueOf(1234));
            member_info.put("phoneNum", "010-7185-7663");

            membership_info.put("KTXMileage","23000");
            membership_info.put("couponNum", "3");

            textView_ID.setText(member_info.get("ID").toString());
            textView_customID.setText(member_info.get("customID").toString());
            textView_phoneNum.setText(member_info.get("phoneNum").toString());
            textView_KTXmileage.setText(membership_info.get("KTXMileage").toString());
            textView_couponNum.setText(membership_info.get("couponNum").toString());*/
        }
        else {
            textView_ID.setText("Korail 멤버가 아닙니다.");
            textView_customID.setText("Korail 멤버가 아닙니다.");
            textView_phoneNum.setText("Korail 멤버가 아닙니다.");
            textView_KTXmileage.setText("Korail 멤버가 아닙니다.");
            textView_couponNum.setText("Korail 멤버가 아닙니다.");
        }
    }

    private boolean isMember(String customer_id) {
        if(Integer.parseInt(customer_id) >= 6000) {
            return false;
        }
        else return true;
    }
}
