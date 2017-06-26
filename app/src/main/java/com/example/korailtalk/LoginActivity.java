package com.example.korailtalk;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
                Log.i("test", inputID);
                String inputPassword = password.getText().toString();
                if (inputID.equalsIgnoreCase("")) {
                    Log.i("test2", inputID);

                   // Toast.makeText(LoginActivity.this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();     //닫기
                        }
                    });
                    alert.setMessage("아이디를 입력해주세요.");
                    alert.show();

                }
                else if (inputPassword.equalsIgnoreCase("")) {
                   // Toast.makeText(getApplicationContext(),"비밀번호를 입력해주세요.",Toast.LENGTH_SHORT);
                    AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();     //닫기
                        }
                    });
                    alert.setMessage("비밀번호를 입력해주세요.");
                    alert.show();
                }
                else {  //로그인 성공
                    HashMap<String, Object> item = dbhelper.getResultAtMemberTable(inputID,inputPassword);
                    if (item.size() != 0) {
                        sessionDBhelper.insert(inputID,inputPassword);
                        goActivityAt(item);
                    } else {    //유저 없음
                        AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();     //닫기
                            }
                        });
                        alert.setMessage("존재하지 않는 회원입니다.");
                        alert.show();
                    }
                }
            }
        });
    }

    private void goActivityAt(HashMap<String, Object> item) {
        if (activityFrom.equalsIgnoreCase("PAID_TICKET_BUTTON")) {
            Intent intent = new Intent(LoginActivity.this, PaidTicketSearch.class);
            intent.putExtra("customID", Integer.parseInt(item.get("customID").toString()));
            startActivity(intent);
            finish();
        } else if (activityFrom.equalsIgnoreCase("TICKET_HISTORY_BUTTON")) {
            Intent intent = new Intent(LoginActivity.this, SelectPeriodActivity.class);
            intent.putExtra("customID", Integer.parseInt(item.get("customID").toString()));
            startActivity(intent);
            finish();

        } else if (activityFrom.equalsIgnoreCase("UNPAID_TICKET_BUTTON")) {
            Intent intent = new Intent(LoginActivity.this, UnPaidTicketSearch.class);
            intent.putExtra("customID", Integer.parseInt(item.get("customID").toString()));
            startActivity(intent);
            finish();

        } else if (activityFrom.equalsIgnoreCase("SEAT_SEARCH")) {
            Intent getintent = getIntent();
            String departPoint = getintent.getStringExtra("departPoint");
            String destPoint = getintent.getStringExtra("destPoint");
            String departDate = getintent.getStringExtra("departdate");
            String traiNum = getintent.getStringExtra("trainum");
            String nbofTicket = getintent.getStringExtra("nbofticket");
            String seatInfo = getintent.getStringExtra("seatinfo");

            Intent newIntent = new Intent(LoginActivity.this, PaymentActivity.class);
            newIntent.putExtra("departPoint", departPoint);
            newIntent.putExtra("destPoint", destPoint);
            newIntent.putExtra("departdate", departDate);
            newIntent.putExtra("trainum", traiNum);
            newIntent.putExtra("nbofticket", nbofTicket);
            newIntent.putExtra("seatinfo", seatInfo);
            newIntent.putExtra("customID", item.get("customID").toString());
            newIntent.putExtra("NEW_TICKET", "new");
            startActivity(newIntent);
            finish();

        } else if (activityFrom.equalsIgnoreCase("MY_PAGE")) {
            Intent intent = new Intent(LoginActivity.this, MyPageActivity.class);
            intent.putExtra("customID", item.get("customID").toString());
            startActivity(intent);
            finish();
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
