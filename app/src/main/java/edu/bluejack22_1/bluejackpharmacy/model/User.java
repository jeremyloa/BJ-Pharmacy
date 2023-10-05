package edu.bluejack22_1.bluejackpharmacy.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.io.Serializable;

import edu.bluejack22_1.bluejackpharmacy.Database;

public class User implements Serializable {
    public static User curr;
    public static final String TABLE_USERS = "users";
    public static final String KEY_ID = "userid";
    private static final String KEY_NAME = "name", KEY_EMAIL = "email", KEY_PASS = "password", KEY_PHONE = "phone", KEY_ISVERIFIED = "isverified";
    public static final String CREATE_TABLE_USERS =
            String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT);",
            TABLE_USERS, KEY_ID, KEY_NAME, KEY_EMAIL, KEY_PASS, KEY_PHONE, KEY_ISVERIFIED);

    public static void INSERT_USER(User user) {
        ContentValues val = new ContentValues();
        val.put(KEY_NAME, user.getName());
        val.put(KEY_EMAIL, user.getEmail());
        val.put(KEY_PASS, user.getPassword());
        val.put(KEY_PHONE, user.getPhone());
        val.put(KEY_ISVERIFIED, user.getIsVerified());
        Database.sqlite.insert(TABLE_USERS, val);
    }

    public static User GET_USER(String email, String password) {
        Cursor curr = Database.sqlite.read(TABLE_USERS, new String[] {KEY_ID, KEY_NAME, KEY_EMAIL, KEY_PASS, KEY_PHONE, KEY_ISVERIFIED}, KEY_EMAIL + " = ?", new String[] {email}, null);
        if (curr!=null && curr.moveToFirst() && curr.getCount()>0) {
            int currID = Integer.parseInt(curr.getString(0));
            String currName = curr.getString(1);
            String currEmail = curr.getString(2);
            String currPass = curr.getString(3);
            String currPhone = curr.getString(4);
            boolean currVerf = Boolean.parseBoolean(curr.getString(5));
            return new User(currID, currName, currEmail, currPass, currPhone, currVerf);
        }
        return null;
    }

    public static User GET_USER(int id) {
        Cursor curr = Database.sqlite.read(TABLE_USERS, new String[] {KEY_ID, KEY_NAME, KEY_EMAIL, KEY_PASS, KEY_PHONE, KEY_ISVERIFIED}, KEY_ID + " = ?", new String[] {String.valueOf(id)}, null);
        if (curr!=null && curr.moveToFirst() && curr.getCount()>0) {
            int currID = Integer.parseInt(curr.getString(0));
            String currName = curr.getString(1);
            String currEmail = curr.getString(2);
            String currPass = curr.getString(3);
            String currPhone = curr.getString(4);
            boolean currVerf = Boolean.parseBoolean(curr.getString(5));
            return new User(currID, currName, currEmail, currPass, currPhone, currVerf);
        }
        return null;
    }

    public static void UPDATE_USER_STATUS(int id, String verified) {
        ContentValues val = new ContentValues();
        val.put(KEY_ISVERIFIED, verified);
        Database.sqlite.update(TABLE_USERS, val, KEY_ID + " = ?", new String[] {String.valueOf(id)});
        User.curr = GET_USER(id);
    }

    private int ID;
    private String name;
    private String email;
    private String password;
    private String phone;
    private boolean isVerified;

    public User(int ID, String name, String email, String password, String phone, boolean isVerified){
        this.ID = ID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.isVerified = isVerified;
    }

    public User(String name, String email, String password, String phone){
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.isVerified = false;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean getIsVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }
}
