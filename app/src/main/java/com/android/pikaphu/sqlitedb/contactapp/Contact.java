package com.android.pikaphu.sqlitedb.contactapp;

/**
 * Created by Pikaphu on 7/6/2558.
 */
public class Contact {
    private long id;
    private String firstname;
    private String surname;
    private String title;
    private String birthdate;
    private String address;
    private String phone;


    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // override for ArrayAdapter in ListView
    @Override
    public String toString() {
        return title;
    }

}
