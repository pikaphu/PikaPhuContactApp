package com.android.pikaphu.sqlitedb.global;
import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.pikaphu.sqlitedb.R;


public class TestingData extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        SQLiteDatabase db;

        db = openOrCreateDatabase(
                "TestingData.db"
                , SQLiteDatabase.CREATE_IF_NECESSARY
                , null
        );
        db.setVersion(1);
        db.setLocale(Locale.getDefault());
        //db.setLockingEnabled(true);

        Cursor cur = db.query("tbl_countries",
                null, null, null, null, null, null);

        cur.close();
    }

    public void testingGlobalData()
    {
        MyGlobals myGlog;
        myGlog = new MyGlobals(getApplicationContext());
        String username = myGlog.getSomeStuff();

        boolean inConnected = myGlog.isNetworkConnected();
    }
}