package com.example.korailtalk;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

public class CheckSessionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_session);

        Intent intent = new Intent(this.getIntent());
        String menu_string=intent.getStringExtra("menu");
        TextView textView=(TextView)findViewById(R.id.menu);
        textView.setText(menu_string);
    }

}
