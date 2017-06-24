package com.example.korailtalk;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by 한결 on 2017-06-24.
 */
public class SessionDBHelper extends SQLiteOpenHelper {

    public SessionDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists SESSION");
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS SESSION (ID TEXT, password TEXT, time Text);");
    }

    public void insert(String ID, String password) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues newValues = new ContentValues();
        Date date = new Date(System.currentTimeMillis());

        newValues.put("ID", ID);
        newValues.put("password", password);
        newValues.put("time", date.toString());

        db.insert("SESSION", null, newValues);
    }

    public void dropTable() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("drop table if exists SESSION");
        onCreate(db);
    }

    public HashMap<String, String> getSession() {
        HashMap<String, String> session = new HashMap<String, String>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM SESSION", null);
        while (cursor.moveToNext()) {
            session.put("ID",cursor.getString(cursor.getColumnIndex("ID")));
            session.put("password",cursor.getString(cursor.getColumnIndex("password")));
        }
        return session;
    }

}
