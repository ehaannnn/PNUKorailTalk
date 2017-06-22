package com.example.korailtalk;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by 한결 on 2017-06-22.
 */
public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists TICKET_INFO");
        db.execSQL("drop table if exists MEMBER");
        db.execSQL("drop table if exists NON_MEMBER");
        db.execSQL("drop table if exists TRAIN_INFO");
        db.execSQL("drop table if exists SEAT_INFO");
        db.execSQL("drop table if exists MEMBERSHIP_INFO");
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS TICKET_INFO( _id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, destPoint TEXT, paid INTEGER, departurePoint TEXT,seatNum TEXT, ticketID INTEGER, customID INTEGER, trainNum INTEGER);");
        db.execSQL("CREATE TABLE IF NOT EXISTS MEMBER( _id INTEGER PRIMARY KEY AUTOINCREMENT, customID INTEGER, ID TEXT, phoneNum TEXT, password TEXT);");
        db.execSQL("CREATE TABLE IF NOT EXISTS NON_MEMBER( _id INTEGER PRIMARY KEY AUTOINCREMENT, customID INTEGER,phoneNum TEXT, password TEXT);");
        db.execSQL("CREATE TABLE IF NOT EXISTS TRAIN_INFO( _id INTEGER PRIMARY KEY AUTOINCREMENT, boardingDate TEXT, departurePoint TEXT, destPoint TEXT, totalAvailableSeatNum INTEGER, trainNum INTEGER);");
        db.execSQL("CREATE TABLE IF NOT EXISTS SEAT_INFO( _id INTEGER PRIMARY KEY AUTOINCREMENT, availableSeat TEXT, trainNum INTEGER);");
        db.execSQL("CREATE TABLE IF NOT EXISTS MEMBERSHIP_INFO( _id INTEGER PRIMARY KEY AUTOINCREMENT, CouponNum INTEGER, KTXMileage INTEGER);");
    }

    public void insert(String table, HashMap<String, Object> items) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues newValues = new ContentValues();
        if (table.equalsIgnoreCase("TICKET_INFO")) {
            newValues.put("date", items.get("date").toString());
            newValues.put("destPoint", items.get("destPoint").toString());
            newValues.put("paid", Integer.parseInt(items.get("paid").toString()));
            newValues.put("departurePoint", items.get("departurePoint").toString());
            newValues.put("seatNum", items.get("seatNum").toString());
            newValues.put("ticketID", Integer.parseInt(items.get("ticketID").toString()));
            newValues.put("customID", Integer.parseInt(items.get("customID").toString()));
            newValues.put("trainNum", Integer.parseInt(items.get("trainNum").toString()));
        } else if (table.equalsIgnoreCase("MEMBER")) {
            newValues.put("customID", Integer.parseInt(items.get("customID").toString()));
            newValues.put("ID", items.get("ID").toString());
            newValues.put("phoneNum", items.get("phoneNum").toString());
            newValues.put("password", items.get("password").toString());
        } else if (table.equalsIgnoreCase("NON_MEMBER")) {
            newValues.put("customID", Integer.parseInt(items.get("customID").toString()));
            newValues.put("phoneNum", items.get("phoneNum").toString());
            newValues.put("password", items.get("password").toString());
            Log.i("log", "NON_MEMBER table");
        } else if (table.equalsIgnoreCase("TRAIN_INFO")) {
            newValues.put("boardingDate", items.get("boardingDate").toString());
            newValues.put("departurePoint", items.get("departurePoint").toString());
            newValues.put("destPoint", items.get("destPoint").toString());
            Log.i("log", items.get("destPoint").toString());
            newValues.put("totalAvailableSeatNum", Integer.parseInt(items.get("totalAvailableSeatNum").toString()));
            newValues.put("trainNum", Integer.parseInt(items.get("trainNum").toString()));
        } else if (table.equalsIgnoreCase("SEAT_INFO")) {
            newValues.put("availableSeat", items.get("availableSeat").toString());
            newValues.put("trainNum", Integer.parseInt(items.get("trainNum").toString()));
        } else if (table.equalsIgnoreCase("MEMBERSHIP_INFO")) {
            newValues.put("CouponNum", Integer.parseInt(items.get("CouponNum").toString()));
            newValues.put("KTXMileage", Integer.parseInt(items.get("KTXMileage").toString()));
        }
        db.insert(table, null, newValues);
    }

    public void delete() {

    }
    public void dropTable() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("drop table if exists TICKET_INFO");
        db.execSQL("drop table if exists MEMBER");
        db.execSQL("drop table if exists NON_MEMBER");
        db.execSQL("drop table if exists TRAIN_INFO");
        db.execSQL("drop table if exists SEAT_INFO");
        db.execSQL("drop table if exists MEMBERSHIP_INFO");
        onCreate(db);
    }

    public HashMap<String,Object> getResultAt(String table) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor;
        HashMap<String,Object> items = new HashMap<String,Object>();
        if (table.equalsIgnoreCase("TICKET_INFO")) {

        } else if (table.equalsIgnoreCase("MEMBER")) {

        } else if (table.equalsIgnoreCase("NON_MEMBER")) {

        } else if (table.equalsIgnoreCase("TRAIN_INFO")) {
            cursor = db.rawQuery("SELECT * FROM TRAIN_INFO",null);
            while (cursor.moveToNext()) {
                items.put("boardingDate", cursor.getString(cursor.getColumnIndex("boardingDate")));
                Log.i("열차 정보 가져오기",items.get("boardingDate").toString());
            }
        } else if (table.equalsIgnoreCase("SEAT_INFO")) {

        } else if (table.equalsIgnoreCase("MEMBERSHIP_INFO")) {

        }

        return items;
    }
}
