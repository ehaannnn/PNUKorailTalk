package com.example.korailtalk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class LoginActivity extends Activity {
    private EditText ID;
    private EditText password;
    private Button signup;
    private Button sessionButton;
    private Button signin;
    private String activityFrom;
    private DBHelper dbhelper;
    private SessionDBHelper sessionDBhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent intent = getIntent();

        activityFrom = intent.getStringExtra("ActivityFrom");
        ID = (EditText)findViewById(R.id.IDInput);
        password = (EditText)findViewById(R.id.passwordInput);

        sessionDBhelper = new SessionDBHelper(getApplicationContext(), "Session.db",null,1);

        sessionButton = (Button) findViewById(R.id.sessionDelete);
        sessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionDBhelper.dropTable();
            }
        });
        /*
        HashMap<String,String> session = sessionDBhelper.getSession();
        if (session.size() != 0) {  //session작동
            ID.setText(session.get("ID"));
            password.setText(session.get("password"));

            String inputID = ID.getText().toString();
            String inputPassword = password.getText().toString();
            HashMap<String, Object> item = dbhelper.getResultAtMemberTable(inputID,inputPassword);

            if (item.size() != 0) {
                goActivityAt(item);
            } else {    //유저 없음
                Toast.makeText(getApplicationContext(), "아이디 혹은 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT);
            }
        }
        else {  //session 없음

        }*/


        dbhelper = new DBHelper(getApplicationContext(), "PNUKorailTalk.db",null,1);

        signup = (Button) findViewById(R.id.signupButton);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        signin = (Button) findViewById(R.id.loginButton);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //디비에서 유저 확인

                String inputID = ID.getText().toString();
                Log.i("test",inputID);
                String inputPassword = password.getText().toString();

                if (inputID.equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "아이디를 입력해주세요.", Toast.LENGTH_SHORT);
                }
                else if (inputPassword.equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(),"비밀번호를 입력해주세요.",Toast.LENGTH_SHORT);
                }
                else {  //로그인 성공
                    sessionDBhelper.insert(inputID,inputPassword);
                    HashMap<String, Object> item = dbhelper.getResultAtMemberTable(inputID,inputPassword);
                    if (item.size() != 0) {
                        goActivityAt(item);
                    } else {    //유저 없음
                        Toast.makeText(getApplicationContext(), "아이디 혹은 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT);
                    }


                }
            }
        });
    }

    private void goActivityAt(HashMap<String, Object> item) {
        if (activityFrom.equalsIgnoreCase("PAID_TICKET_BUTTON")) {
            Intent intent = new Intent(LoginActivity.this, PaiedTicketSearch.class);
            intent.putExtra("customID", Integer.parseInt(item.get("customID").toString()));
            startActivity(intent);
            finish();
        } else if (activityFrom.equalsIgnoreCase("ticketHistory")) {

        } else if (activityFrom.equalsIgnoreCase("unpaidTicketSearch")) {

        } else if (activityFrom.equalsIgnoreCase("trainSearch")) {

        } else if (activityFrom.equalsIgnoreCase("mypage")) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
