package com.android.pikaphu.sqlitedb.contactapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pikaphu on 7/6/2558.
 */
public class ContactDataSource {
    // Database field
    public static final String TAG = "SQLite Database";
    private SQLiteDatabase database;
    private ContactDBHelper dbHelper;
    private String[] allColumns = {
            ContactDBHelper.COLUMN_ID,
            ContactDBHelper.COLUMN_FIRSTNAME,
            ContactDBHelper.COLUMN_SURNAME,
            ContactDBHelper.COLUMN_BIRTHDATE,
            ContactDBHelper.COLUMN_ADDRESS,
            ContactDBHelper.COLUMN_TITLE,
            ContactDBHelper.COLUMN_PHONE
    };

    public ContactDataSource(Context context) {
        dbHelper = new ContactDBHelper(context);
    }

    // open db connection
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    // close db connection
    public  void close() {
        dbHelper.close();
    }

    //region "+ Database Manipulation "
    public Contact insertContact(Contact contact) {
        ContentValues values = new ContentValues();
        //values.put(ContactDBHelper.COLUMN_ID, contact.getId());
        values.put(ContactDBHelper.COLUMN_FIRSTNAME, contact.getFirstname());
        values.put(ContactDBHelper.COLUMN_SURNAME, contact.getSurname());
        values.put(ContactDBHelper.COLUMN_BIRTHDATE, contact.getBirthdate());
        values.put(ContactDBHelper.COLUMN_ADDRESS, contact.getAddress());
        values.put(ContactDBHelper.COLUMN_TITLE, contact.getTitle());
        values.put(ContactDBHelper.COLUMN_PHONE, contact.getPhone());

        long insertId = database.insert(ContactDBHelper.TABLE_CONTACTS, null, values);

        Cursor cursor = database.query(ContactDBHelper.TABLE_CONTACTS,
                allColumns,
                ContactDBHelper.COLUMN_ID + " = " + insertId,
                null, null, null, null
        );
        cursor.moveToFirst();
        return cursorToContact(cursor);
    }

    public void deleteContact(Contact contact) {
        long id = contact.getId();
        Log.d(TAG, "Contact deleted id:" + id);
        database.delete(ContactDBHelper.TABLE_CONTACTS,
                        ContactDBHelper.COLUMN_ID + " = " + id,
                        null
                        );
    }

    public void updateContact(Contact contact) {
        ContentValues values = new ContentValues();
        //values.put(ContactDBHelper.COLUMN_ID, contact.getId());
        values.put(ContactDBHelper.COLUMN_FIRSTNAME, contact.getFirstname());
        values.put(ContactDBHelper.COLUMN_SURNAME, contact.getSurname());
        values.put(ContactDBHelper.COLUMN_BIRTHDATE, contact.getBirthdate());
        values.put(ContactDBHelper.COLUMN_ADDRESS, contact.getAddress());
        values.put(ContactDBHelper.COLUMN_TITLE, contact.getTitle());
        values.put(ContactDBHelper.COLUMN_PHONE, contact.getPhone());

        database.update(ContactDBHelper.TABLE_CONTACTS,
                values,
                ContactDBHelper.COLUMN_ID + "=" + contact.getId(),
                null
        );
    }
    //endregion

    // select all Contact data into List object.
    public List<Contact> getAllContact() {
        List<Contact> contactlist = new ArrayList<Contact>();
        Cursor cursor = database.query( ContactDBHelper.TABLE_CONTACTS,
                                        allColumns,
                                        null,null,null,null,null, null
                                        );
        cursor.moveToFirst();
        while( !cursor.isAfterLast() ) {
            Contact contact = cursorToContact(cursor);
            contactlist.add(contact);
            cursor.moveToNext();
        }
        cursor.close();
        return  contactlist;
    }

    //  get a contact from db cursor.
    private Contact cursorToContact(Cursor cursor) {
        // retrieve data from current database cursor and set as return dataset
        Contact contact = new Contact();
        contact.setId(cursor.getLong(0));
        contact.setFirstname(cursor.getString(1));
        contact.setSurname(cursor.getString(2));
        contact.setTitle(cursor.getString(3));
        contact.setBirthdate(cursor.getString(4));
        contact.setAddress(cursor.getString(5));
        contact.setPhone(cursor.getString(6));

        return contact;
    }



}
