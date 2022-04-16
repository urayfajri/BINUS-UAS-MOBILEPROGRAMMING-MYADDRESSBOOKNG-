package com.example.myaddressbook_2301908343.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "BookAddress";
    public static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE employee(" +
                "id TEXT," +
                "fullName TEXT," +
                "firstName TEXT," +
                "lastName TEXT," +
                "city TEXT," +
                "country TEXT," +
                "phone TEXT," +
                "cell TEXT," +
                "memberSince TEXT," +
                "email TEXT," +
                "picture TEXT," +
                "latitude TEXT," +
                "longitude TEXT" +
                ")");

        sqLiteDatabase.execSQL("CREATE TABLE addressBook(" +
                "id TEXT," +
                "fullName TEXT," +
                "firstName TEXT," +
                "lastName TEXT," +
                "city TEXT," +
                "country TEXT," +
                "phone TEXT," +
                "cell TEXT," +
                "email TEXT," +
                "picture TEXT" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists employee");
        sqLiteDatabase.execSQL("drop table if exists addressBook");

    }

    public boolean insertImployee(String id, String firstName, String lastName, String city, String country, String phone, String cell, String memberSince, String email, String picture, String latitude, String longitude) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("fullName", firstName + " " + lastName);
        contentValues.put("firstName", firstName);
        contentValues.put("lastName",lastName);
        contentValues.put("city", city);
        contentValues.put("country", country);
        contentValues.put("phone",phone);
        contentValues.put("cell",cell);
        contentValues.put("memberSince",memberSince);
        contentValues.put("email",email);
        contentValues.put("picture",picture);
        contentValues.put("latitude",latitude);
        contentValues.put("longitude",longitude);
        long result = db.insert("employee", null, contentValues);

        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getAllEmployees() {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM employee", null);
        return cursor;
    }

    public Cursor getSearchEmployee(String input) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM employee where fullName=? or firstName=? or lastName=? or city=? or country=? or phone=? or cell=? or memberSince=? or email=?",
                new String[]{input,input,input,input,input,input,input, input,input});

        return cursor;
    }

    public boolean insertAddressBook(String id, String firstName, String lastName, String city, String country, String phone, String cell, String email, String picture) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("fullName", firstName + " " + lastName);
        contentValues.put("firstName", firstName);
        contentValues.put("lastName",lastName);
        contentValues.put("city", city);
        contentValues.put("country", country);
        contentValues.put("phone",phone);
        contentValues.put("cell",cell);
        contentValues.put("email",email);
        contentValues.put("picture",picture);
        long result = db.insert("addressBook", null, contentValues);

        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getAllAddressBooks() {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM addressBook", null);
        return cursor;
    }

    public Cursor getAddressBook(String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM addressBook where id=?", new String[]{id});
        return cursor;
    }
}
