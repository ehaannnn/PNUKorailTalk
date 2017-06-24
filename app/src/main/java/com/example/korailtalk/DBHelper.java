/*DBHelper*/

package com.example.korailtalk;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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
        db.execSQL("CREATE TABLE IF NOT EXISTS TICKET_INFO(  boardingDate TEXT, destPoint TEXT, paid INTEGER, departurePoint TEXT,seatNum TEXT, ticketID INTEGER, customID INTEGER, trainNum INTEGER, use INTEGER);");
        db.execSQL("CREATE TABLE IF NOT EXISTS MEMBER( customID INTEGER, ID TEXT, phoneNum TEXT, password TEXT);");
        db.execSQL("CREATE TABLE IF NOT EXISTS NON_MEMBER(  customID INTEGER,phoneNum TEXT, password TEXT);");
        db.execSQL("CREATE TABLE IF NOT EXISTS TRAIN_INFO(  boardingDate TEXT, departurePoint TEXT, destPoint TEXT, totalAvailableSeatNum INTEGER, trainNum INTEGER);");
        db.execSQL("CREATE TABLE IF NOT EXISTS SEAT_INFO( boardingDate TEXT, availableSeat TEXT, trainNum INTEGER);");
        db.execSQL("CREATE TABLE IF NOT EXISTS MEMBERSHIP_INFO(  CouponNum INTEGER, KTXMileage INTEGER, ID TEXT, customID TEXT);");
    }

    public void insert(String table, HashMap<String, Object> items) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues newValues = new ContentValues();
        if (table.equalsIgnoreCase("TICKET_INFO")) {
            newValues.put("boardingDate", items.get("boardingDate").toString());
            newValues.put("destPoint", items.get("destPoint").toString());
            newValues.put("paid", Integer.parseInt(items.get("paid").toString()));
            newValues.put("departurePoint", items.get("departurePoint").toString());
            newValues.put("seatNum", items.get("seatNum").toString());
            newValues.put("ticketID", Integer.parseInt(items.get("ticketID").toString()));
            newValues.put("customID", Integer.parseInt(items.get("customID").toString()));
            newValues.put("trainNum", Integer.parseInt(items.get("trainNum").toString()));
            newValues.put("use", Integer.parseInt(items.get("use").toString()));
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
            newValues.put("boardingDate", items.get("boardingDate").toString());
            newValues.put("availableSeat", items.get("availableSeat").toString());
            newValues.put("trainNum", Integer.parseInt(items.get("trainNum").toString()));
        } else if (table.equalsIgnoreCase("MEMBERSHIP_INFO")) {
            newValues.put("CouponNum", Integer.parseInt(items.get("CouponNum").toString()));
            newValues.put("KTXMileage", Integer.parseInt(items.get("KTXMileage").toString()));
            newValues.put("ID", Integer.parseInt(items.get("ID").toString()));
            newValues.put("customID", Integer.parseInt(items.get("customID").toString()));
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

    public HashMap<String, Object> getResultAtMemberTable(String ID, String PW) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor;
        HashMap<String, Object> item = new HashMap<String, Object>();

        cursor = db.rawQuery("SELECT * FROM MEMBER WHERE ID='" + ID + "' and password='" + PW + "'", null);

        while (cursor.moveToNext()) {
            item.put("customID", cursor.getString(cursor.getColumnIndex("customID")));
            item.put("ID", cursor.getString(cursor.getColumnIndex("ID")));
            item.put("phoneNum", cursor.getString(cursor.getColumnIndex("phoneNum")));
        }

        return item;
    }

    public void DeleteTicketInfoTablebyticketID(String ticketID, String customID) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor;

        cursor = db.rawQuery("DELETE FROM TICKET_INFO WHERE ticketID='" + ticketID + "' and customID='" + customID + "'", null);
    }

    public void UpdateTrainInfoTotalAvailableSN(String trainNum, String boardingDate, String totalAvailableSeatNum) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor;

        cursor = db.rawQuery("UPDATE TRAIN_INFO SET totalAvailableSeatNum = '" + totalAvailableSeatNum + "' WHERE trainNum='" + trainNum + "' and boardingDate='" + boardingDate + "'", null);
    }

    public List<HashMap<String, Object>> getResultAt(String table, int customID) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor;
        List<HashMap<String, Object>> items = new LinkedList<HashMap<String, Object>>();
        if (table.equalsIgnoreCase("TICKET_INFO")) {
            cursor = db.rawQuery("SELECT * FROM TICKET_INFO WHERE customID=" + customID, null);

            while (cursor.moveToNext()) {
                HashMap<String, Object> item = new HashMap<String, Object>();
                item.put("boardingDate", cursor.getString(cursor.getColumnIndex("boardingDate")));
                item.put("departurePoint", cursor.getString(cursor.getColumnIndex("departurePoint")));
                item.put("destPoint", cursor.getString(cursor.getColumnIndex("destPoint")));
                item.put("seatNum", cursor.getString(cursor.getColumnIndex("seatNum")));
                item.put("ticketID", cursor.getString(cursor.getColumnIndex("ticketID")));
                item.put("customID", cursor.getString(cursor.getColumnIndex("customID")));
                item.put("trainNum", cursor.getString(cursor.getColumnIndex("trainNum")));
                item.put("use", cursor.getString(cursor.getColumnIndex("use")));
                items.add(item);
            }

        } else if (table.equalsIgnoreCase("MEMBER")) {
            cursor = db.rawQuery("SELECT * FROM MEMBER WHERE customID=" + customID, null);

            while (cursor.moveToNext()) {
                HashMap<String, Object> item = new HashMap<String, Object>();

                item.put("customID", cursor.getString(cursor.getColumnIndex("customID")));
                item.put("ID", cursor.getString(cursor.getColumnIndex("ID")));
                item.put("phoneNum", cursor.getString(cursor.getColumnIndex("phoneNum")));
                items.add(item);
            }
        } else if (table.equalsIgnoreCase("NON_MEMBER")) {
            cursor = db.rawQuery("SELECT * FROM NON_MEMBER WHERE customID=" + customID, null);

            while (cursor.moveToNext()) {
                HashMap<String, Object> item = new HashMap<String, Object>();

                item.put("customID", cursor.getString(cursor.getColumnIndex("customID")));
                item.put("phoneNum", cursor.getString(cursor.getColumnIndex("phoneNum")));
                items.add(item);
            }
        } else if (table.equalsIgnoreCase("TRAIN_INFO")) {
            cursor = db.rawQuery("SELECT * FROM TRAIN_INFO WHERE customID=" + customID, null);

            while (cursor.moveToNext()) {
                HashMap<String, Object> item = new HashMap<String, Object>();

                item.put("boardingDate", cursor.getString(cursor.getColumnIndex("boardingDate")));
                item.put("departurePoint", cursor.getString(cursor.getColumnIndex("departurePoint")));
                item.put("destPoint", cursor.getString(cursor.getColumnIndex("destPoint")));
                item.put("totalAvailableSeatNum", cursor.getString(cursor.getColumnIndex("totalAvailableSeatNum")));
                item.put("trainNum", cursor.getString(cursor.getColumnIndex("trainNum")));
                items.add(item);
            }
        } else if (table.equalsIgnoreCase("SEAT_INFO")) {

            cursor = db.rawQuery("SELECT * FROM MEMBERSHIP_INFO WHERE customID=" + customID, null);

            while (cursor.moveToNext()) {
                HashMap<String, Object> item = new HashMap<String, Object>();
                item.put("boardingDate", cursor.getString(cursor.getColumnIndex("boardingDate")));
                item.put("availableSeat", cursor.getString(cursor.getColumnIndex("availableSeat")));
                item.put("trainNum", cursor.getString(cursor.getColumnIndex("trainNum")));
                items.add(item);
            }

        } else if (table.equalsIgnoreCase("MEMBERSHIP_INFO")) {
            cursor = db.rawQuery("SELECT * FROM MEMBERSHIP_INFO WHERE customID=" + customID, null);

            while (cursor.moveToNext()) {
                HashMap<String, Object> item = new HashMap<String, Object>();
                item.put("CouponNum", cursor.getString(cursor.getColumnIndex("CouponNum")));
                item.put("KTXMileage", cursor.getString(cursor.getColumnIndex("KTXMileage")));
                item.put("ID", cursor.getString(cursor.getColumnIndex("ID")));
                item.put("customID", cursor.getString(cursor.getColumnIndex("customID")));
                items.add(item);
            }
        }



        return items;
    }
}
