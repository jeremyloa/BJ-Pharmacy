package edu.bluejack22_1.bluejackpharmacy.controller.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;

import edu.bluejack22_1.bluejackpharmacy.R;
import edu.bluejack22_1.bluejackpharmacy.controller.Login;
import edu.bluejack22_1.bluejackpharmacy.controller.home.HomeFragment;
import edu.bluejack22_1.bluejackpharmacy.controller.home.TransactionFragment;
import edu.bluejack22_1.bluejackpharmacy.databinding.ActivityHomeBinding;
import edu.bluejack22_1.bluejackpharmacy.model.Medicine;
import edu.bluejack22_1.bluejackpharmacy.model.User;

public class Home extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView navigation;
    private ImageButton homLogout;
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        loadFragment(new HomeFragment());
        homLogout = findViewById(R.id.homLogout);
        homLogout.setOnClickListener(view -> {
            User.curr = null;
            startActivity(new Intent(Home.this, Login.class));
            finish();
        });
        navigation = findViewById(R.id.homNav);
        navigation.setOnNavigationItemSelectedListener(this);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.homFrag, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_home:
                fragment = new HomeFragment();
                break;

            case R.id.navigation_trans:
                fragment = new TransactionFragment();
                break;
        }
        return loadFragment(fragment);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }


}