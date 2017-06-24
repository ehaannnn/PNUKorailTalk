package com.example.korailtalk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.HashMap;

public class CheckSessionActivity extends Activity {

    private SessionDBHelper sessionDBhelper;
    private DBHelper dbhelper;
    private String activityFrom;

    private static final String PAID_TICKET_BUTTON = "PAID_TICKET_BUTTON";
    private static final String TICKET_HISTORY_BUTTON = "TICKET_HISTORY_BUTTON";
    private static final String UNPAID_TICKET_BUTTON = "UNPAID_TICKET_BUTTON";
    private static final String TRAIN_SEARCH = "TRAIN_SEARCH";
    private static final String MY_PAGE = "MY_PAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_session);

        sessionDBhelper = new SessionDBHelper(getApplicationContext(), "Session.db", null, 1);
        dbhelper = new DBHelper(getApplicationContext(), "PNUKorailTalk.db", null, 1);
        Intent intent = getIntent();
        activityFrom = intent.getStringExtra("ActivityFrom");

        if (activityFrom.equalsIgnoreCase("PAID_TICKET_BUTTON")) {
            HashMap<String, String> session = sessionDBhelper.getSession();
            if (session.size() != 0) {  //session작동
                HashMap<String, Object> item = dbhelper.getResultAtMemberTable(session.get("ID"), session.get("password"));
                Intent newIntent = new Intent(CheckSessionActivity.this, PaiedTicketSearch.class);
                newIntent.putExtra("customID", Integer.parseInt(item.get("customID").toString()));
                startActivity(newIntent);
            } else {
                Intent newIntent = new Intent(CheckSessionActivity.this, LoginActivity.class);
                newIntent.putExtra("ActivityFrom", PAID_TICKET_BUTTON);
                startActivity(newIntent);
            }
        } else if (activityFrom.equalsIgnoreCase("TICKET_HISTORY_BUTTON")) {
            HashMap<String, String> session = sessionDBhelper.getSession();
            if (session.size() != 0) {  //session작동
                HashMap<String, Object> item = dbhelper.getResultAtMemberTable(session.get("ID"), session.get("password"));
                Intent newIntent = new Intent(CheckSessionActivity.this, TicketHistory.class);
                newIntent.putExtra("customID", Integer.parseInt(item.get("customID").toString()));
                startActivity(newIntent);
            } else {
                Intent newIntent = new Intent(CheckSessionActivity.this, LoginActivity.class);
                newIntent.putExtra("ActivityFrom", TICKET_HISTORY_BUTTON);
                startActivity(newIntent);
            }

        } else if (activityFrom.equalsIgnoreCase("unpaidTicketSearch")) {

        } else if (activityFrom.equalsIgnoreCase("trainSearch")) {

        } else if (activityFrom.equalsIgnoreCase("MY_PAGE")) {
            HashMap<String, String> session = sessionDBhelper.getSession();
            if (session.size() != 0) {  //session작동
                HashMap<String, Object> item = dbhelper.getResultAtMemberTable(session.get("ID"), session.get("password"));
                Intent newIntent = new Intent(CheckSessionActivity.this, MyPageActivity.class);
                newIntent.putExtra("customID", Integer.parseInt(item.get("customID").toString()));
                startActivity(newIntent);
            } else {
                Intent newIntent = new Intent(CheckSessionActivity.this, LoginActivity.class);
                newIntent.putExtra("ActivityFrom", MY_PAGE);
                startActivity(newIntent);
            }
        }



    }

}
