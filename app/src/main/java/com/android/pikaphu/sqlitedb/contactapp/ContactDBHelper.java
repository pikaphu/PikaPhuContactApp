package com.android.pikaphu.sqlitedb.contactapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by Pikaphu on 3/6/2558.
 */
/**
 * ContactDBHelper, [SimpleSQLiteHelper]
 */
public class ContactDBHelper extends SQLiteOpenHelper {
    public static final String TABLE_CONTACTS = "contact"; // name of
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FIRSTNAME = "firstname";
    public static final String COLUMN_SURNAME = "surname";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_BIRTHDATE = "birthdate";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_PHONE = "phone";

    public static final String DATABASE_NAME = "contacts.db";
    public static final int DATABASE_VERSION = 1;

    // sql for create database
    private  static final String DATABASE_CREATE = "create table " + TABLE_CONTACTS +
            "(" +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_FIRSTNAME + " text," +
            COLUMN_SURNAME + " text," +
            COLUMN_TITLE + " text," +
            COLUMN_BIRTHDATE + " text," +
            COLUMN_ADDRESS + " text," +
            COLUMN_PHONE + " text" +
            ");";

    // ctor
    public ContactDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
        Log.w(  ContactDBHelper.class.getName(),
                "upgrade database from version " + oldversion + " to " + newversion + ", old data will be destroyed");
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_CONTACTS);
        onCreate(db);
    }


}
