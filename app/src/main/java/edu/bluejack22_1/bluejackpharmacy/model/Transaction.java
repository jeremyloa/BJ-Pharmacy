package edu.bluejack22_1.bluejackpharmacy.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import edu.bluejack22_1.bluejackpharmacy.Database;

public class Transaction implements Serializable {
    public static ArrayList<Transaction> dbTrans = new ArrayList<>();
    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final String TABLE_TRANSACTIONS = "transactions";
    public static final String KEY_ID = "transactionid";
    private static final String KEY_DATE = "transactiondate", KEY_QTY = "quantity";
    public static final String CREATE_TABLE_TRANSACTIONS =
            String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s INTEGER, %s INTEGER, %s INTEGER);",
            TABLE_TRANSACTIONS, KEY_ID, KEY_DATE, User.KEY_ID, Medicine.KEY_ID, KEY_QTY);

    public static void INSERT_TRANSACTION(Transaction trans) {
        ContentValues val = new ContentValues();
        val.put(KEY_DATE, trans.getDate());
        val.put(User.KEY_ID, trans.getUserid());
        val.put(Medicine.KEY_ID, trans.getMedicineid());
        val.put(KEY_QTY, trans.getQuantity());
        Log.i("INSERT_TRANS","Date: " + trans.getDate());
        Log.i("INSERT_TRANS","User: " + trans.getUserid());
        Log.i("INSERT_TRANS", "Med: " + trans.getMedicineid());
        Log.i("INSERT_TRANS","Qty: " + trans.getQuantity());
        try {
            Database.sqlite.insert(TABLE_TRANSACTIONS, val);
        } catch (SQLException e) {
            Log.e("INSERT_TRANS", e.toString());
        }
    }

    public static ArrayList<Transaction> GET_TRANSACTIONS_USERID(int userid) {
        ArrayList<Transaction> trans = new ArrayList<>();
        Cursor curr = Database.sqlite.read(TABLE_TRANSACTIONS, new String[] {KEY_ID, KEY_DATE, User.KEY_ID, Medicine.KEY_ID, KEY_QTY}, User.KEY_ID + " = ?", new String[] {String.valueOf(userid)}, null);
        if (curr!=null && curr.moveToFirst() && curr.getCount()>0) {
            trans = new ArrayList<>();
            do {
                int currID = Integer.parseInt(curr.getString(0));
                String currDate = curr.getString(1);
                int currUserID = Integer.parseInt(curr.getString(2));
                int currMedID = Integer.parseInt(curr.getString(3));
                int currQty = Integer.parseInt(curr.getString(4));
                trans.add(new Transaction(currID, currDate, currUserID, currMedID, currQty));
            } while(curr.moveToNext());
//            curr.close();
        }
        return trans;
    }

    public static Transaction GET_TRANS(int id) {
        Cursor curr = Database.sqlite.read(TABLE_TRANSACTIONS, new String[] {KEY_ID, KEY_DATE, User.KEY_ID, Medicine.KEY_ID, KEY_QTY}, KEY_ID + " = ?", new String[] {String.valueOf(id)}, null);
        if (curr!=null && curr.moveToFirst() && curr.getCount()>0) {
            int currID = Integer.parseInt(curr.getString(0));
            String currDate = curr.getString(1);
            int currUserID = Integer.parseInt(curr.getString(2));
            int currMedID = Integer.parseInt(curr.getString(3));
            int currQty = Integer.parseInt(curr.getString(4));
            return new Transaction(currID, currDate, currUserID, currMedID, currQty);
        }
        return null;
    }

    public static void UPDATE_TRANS_QTY(int id, int qty) {
        ContentValues val = new ContentValues();
        val.put(KEY_QTY, qty);
        Database.sqlite.update(TABLE_TRANSACTIONS, val, KEY_ID + " = ?", new String[] {String.valueOf(id)});
    }

    public static void DELETE_TRANS(int id){
        Database.sqlite.delete(TABLE_TRANSACTIONS, KEY_ID + " = ?", new String[] {String.valueOf(id)});
    }

    private int ID;
    private int userid;
    private int medicineid;
    private int quantity;
    private String date;
    public Transaction(int ID, String date, int userid, int medicineid, int quantity ) {
        this.setID(ID);
        this.setUserid(userid);
        this.setMedicineid(medicineid);
        this.setQuantity(quantity);
        this.setDate(date);
    }

    public Transaction(int userid, int medicineid, int quantity) {
        this.setUserid(userid);
        this.setMedicineid(medicineid);
        this.setQuantity(quantity);
        this.setDate(df.format(new Date()));
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getMedicineid() {
        return medicineid;
    }

    public void setMedicineid(int medicineid) {
        this.medicineid = medicineid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
