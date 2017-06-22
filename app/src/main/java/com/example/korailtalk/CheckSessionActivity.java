package com.example.korailtalk;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CheckSessionActivity extends Activity {

    private String menu_string, customer_id;
    private EditText edittext_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_session);

        Intent intent = new Intent(this.getIntent());
        menu_string = intent.getStringExtra("menu");
        TextView textView=(TextView)findViewById(R.id.menu);
        textView.setText(menu_string);

        Button btn_confirm = (Button) findViewById(R.id.confirm_button);
        edittext_user_id = (EditText) findViewById(R.id.user_account);

        btn_confirm.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                customer_id = edittext_user_id.getText().toString();
                if(menu_string.equals("mypage")) {
                    Intent next_intent = new Intent(CheckSessionActivity.this,MyPageActivity.class);
                    next_intent.putExtra("customID",customer_id);
                    startActivity(next_intent);
                }
                if(menu_string.equals("paiedTicketSearch")) {

                }
                if(menu_string.equals("unpaiedTicketSearch")) {

                }
                if(menu_string.equals("ticketHistory")) {

                }
                if(menu_string.equals("trainSearch")) {

                }

            }
        });
    }

}
