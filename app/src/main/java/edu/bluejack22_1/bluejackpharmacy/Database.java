package edu.bluejack22_1.bluejackpharmacy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import edu.bluejack22_1.bluejackpharmacy.model.Medicine;
import edu.bluejack22_1.bluejackpharmacy.model.Transaction;
import edu.bluejack22_1.bluejackpharmacy.model.User;

public class Database extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "bluejack_pharmacy";
    private static final int DATABASE_VERSION = 1;

    public static Database sqlite;
//    public static Database getInstance(Context context) {
//        if (sqlite == null) sqlite = new Database(context.getApplicationContext());
//        return sqlite;
//
//    }

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(User.CREATE_TABLE_USERS);
        db.execSQL(Medicine.CREATE_TABLE_MEDICINES);
        db.execSQL(Transaction.CREATE_TABLE_TRANSACTIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(String.format("DROP TABLE IF EXISTS '%s'", User.TABLE_USERS));
        db.execSQL(String.format("DROP TABLE IF EXISTS '%s'", Medicine.TABLE_MEDICINES));
        db.execSQL(String.format("DROP TABLE IF EXISTS '%s'", Transaction.TABLE_TRANSACTIONS));
        onCreate(db);
    }

    public Cursor read(String table, String[] cols, String whereCol, String[] whereVal, String orderCol){
        SQLiteDatabase db = getReadableDatabase();
        Cursor curr = db.query(table, cols, whereCol, whereVal, null, null, orderCol);
//        db.close();
        return curr;
    }

    public void insert(String table, ContentValues val){
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(table, null, val);
        db.close();
    }

    public void update(String table, ContentValues val, String whereCol, String[] whereVal) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(table, val, whereCol, whereVal);
        db.close();
    }

    public void delete(String table, String whereCol, String[] whereVal){
        SQLiteDatabase db = getReadableDatabase();
        db.delete(table, whereCol, whereVal);
        db.close();
    }
}
