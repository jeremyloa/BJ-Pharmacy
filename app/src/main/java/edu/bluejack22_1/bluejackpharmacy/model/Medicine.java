package edu.bluejack22_1.bluejackpharmacy.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.bluejack22_1.bluejackpharmacy.Database;
import edu.bluejack22_1.bluejackpharmacy.controller.home.Home;
import edu.bluejack22_1.bluejackpharmacy.controller.home.HomeFragment;

public class Medicine implements Serializable {
    public static ArrayList<Medicine> dbMed = new ArrayList<>();
    public static final String TABLE_MEDICINES = "medicines";
    public static final String KEY_ID = "medicineid";
    private static final String KEY_NAME = "name", KEY_MANUFACTURER = "manufacturer", KEY_PRICE = "price", KEY_IMAGE = "image", KEY_DESC = "description";
    public static final String CREATE_TABLE_MEDICINES =
            String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s INTEGER, %s TEXT, %s TEXT);",
            TABLE_MEDICINES, KEY_ID, KEY_NAME, KEY_MANUFACTURER, KEY_PRICE, KEY_IMAGE, KEY_DESC);

    private static final String API_URL = "https://mocki.io/v1/ae13b04b-13df-4023-88a5-7346d5d3c7eb";

    public static void FETCH_INSERT(Context ctx){

        RequestQueue rq = Volley.newRequestQueue(ctx);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, API_URL, response -> {
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray arr = obj.getJSONArray("medicines");
                for (int i=0; i<arr.length(); i++){
                    JSONObject resObj = arr.getJSONObject(i);
//                    Log.i("FETCH_INSERT", resObj.getString("name"));
                    INSERT_MEDICINE(new Medicine(resObj.getInt("price"), resObj.getString("name"), resObj.getString("manufacturer"), resObj.getString("image"), resObj.getString("description")));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
                Log.e("FETCH_INSERT", "ERR " + error.toString());
        });
//        Log.i("FETCH_INSERT", "JSON: "+stringRequest.toString());
        rq.add(stringRequest);
    }

    public static void INSERT_MEDICINE(Medicine med) {
        ContentValues val = new ContentValues();
        val.put(KEY_NAME, med.getName());
//        Log.i("INSERT_NAME", med.getName());

        val.put(KEY_MANUFACTURER, med.getManufacturer());
        val.put(KEY_PRICE, med.getPrice());
        val.put(KEY_IMAGE, med.getImage());
        val.put(KEY_DESC, med.getDesc());
        Database.sqlite.insert(TABLE_MEDICINES, val);
    }

    public static ArrayList<Medicine> GET_MEDICINES() {
        ArrayList<Medicine> meds = null;
        Cursor curr = Database.sqlite.read(TABLE_MEDICINES, new String[] {KEY_ID, KEY_NAME, KEY_MANUFACTURER, KEY_PRICE, KEY_IMAGE, KEY_DESC}, null, null, null);
        if (curr!=null && curr.moveToFirst() && curr.getCount()>0) {
            meds = new ArrayList<>();
            do {
                int currID = Integer.parseInt(curr.getString(0));
                String currName = curr.getString(1);
                String currManufacturer = curr.getString(2);
                int currPrice = Integer.parseInt(curr.getString(3));
                String currImage = curr.getString(4);
                String currDesc = curr.getString(5);
//                Log.i("GET_MED", currName);
                meds.add(new Medicine(currID, currName, currManufacturer, currPrice, currImage, currDesc));
            } while(curr.moveToNext());
            curr.close();
        }
        return meds;
    }

    public static Medicine GET_MEDICINE(int id) {
        Cursor curr = Database.sqlite.read(TABLE_MEDICINES, new String[] {KEY_ID, KEY_NAME, KEY_MANUFACTURER, KEY_PRICE, KEY_IMAGE, KEY_DESC}, KEY_ID + " = ?", new String[] {String.valueOf(id)}, null);
        if (curr!=null && curr.moveToFirst() && curr.getCount()>0) {
            int currID = Integer.parseInt(curr.getString(0));
            String currName = curr.getString(1);
            String currManufacturer = curr.getString(2);
            int currPrice = Integer.parseInt(curr.getString(3));
            String currImage = curr.getString(4);
            String currDesc = curr.getString(5);
            return new Medicine(currID, currName, currManufacturer, currPrice, currImage, currDesc);
        }
        return null;
    }

    private int ID;
    private int price;
    private String name;
    private String manufacturer;
    private String image;
    private String desc;

    public Medicine(int ID, String name, String manufacturer, int price, String image, String desc) {
        this.ID = ID;
        this.price = price;
        this.name = name;
        this.manufacturer = manufacturer;
        this.image = image;
        this.desc = desc;
    }

    public Medicine(int price, String name, String manufacturer, String image, String desc) {
        this.price = price;
        this.name = name;
        this.manufacturer = manufacturer;
        this.image = image;
        this.desc = desc;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


}
