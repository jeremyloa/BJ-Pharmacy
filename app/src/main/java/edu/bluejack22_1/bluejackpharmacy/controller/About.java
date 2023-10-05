package edu.bluejack22_1.bluejackpharmacy.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import edu.bluejack22_1.bluejackpharmacy.MapsFragment;
import edu.bluejack22_1.bluejackpharmacy.R;

public class About extends AppCompatActivity {

    MapsFragment mapsFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        mapsFragment = new MapsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.mapFragment, mapsFragment).commit();

    }
}