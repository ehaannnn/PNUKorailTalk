package com.example.korailtalk;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class PaiedTicketSearch extends Activity {
    private DBHelper dbhelper;
    private TextView boardingDate;
    private ListViewAdapter adapter;
    private Button paidTicketSearchButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paied_ticket_search);

        dbhelper = new DBHelper(getApplicationContext(), "PNUKorailTalk.db",null,1);

        paidTicketSearchButton = (Button) findViewById(R.id.paidTicketSearchButton);

        /*HashMap<String,Object> items = dbhelper.getResultAt("TRAIN_INFO");

        boardingDate = (TextView) findViewById(R.id.boardingDate);
        boardingDate.setText(items.get("boardingDate").toString());*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_paied_ticket_search, menu);
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
