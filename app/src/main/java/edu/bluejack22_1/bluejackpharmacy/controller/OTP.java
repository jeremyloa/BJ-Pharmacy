package edu.bluejack22_1.bluejackpharmacy.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.telephony.SmsManager;

import java.util.Random;

import edu.bluejack22_1.bluejackpharmacy.controller.home.Home;
import edu.bluejack22_1.bluejackpharmacy.R;
import edu.bluejack22_1.bluejackpharmacy.model.User;

public class OTP extends AppCompatActivity {
    private static final int SEND_SMS_PERM_REQ_CODE = 1;

    private Button btnVerif;
    private EditText etOTP;
    private String keyOTP;
    private String sentOTP;
    private SmsManager sms;
    private User temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        send();
        btnVerif = findViewById(R.id.toVerifBtn);
        btnVerif.setOnClickListener(view -> {
            set();
            Toast tst = err();
            if (tst!=null)
                tst.show();
            else {
                tst = succ();
                tst.show();
                  startActivity(new Intent(OTP.this, Home.class));
                  finish();
            }
        });
    }

    private void send(){
        sentOTP = randomOTP();
        sms = SmsManager.getDefault();
        PendingIntent sentIntent = null, deliveryIntent = null;
        User temp = (User) getIntent().getSerializableExtra("user");
        sms.sendTextMessage(temp.getPhone(), null, String.format("Your OTP Code is: %s", sentOTP), sentIntent, deliveryIntent);
//        if (checkPerm(Manifest.permission.SEND_SMS)) {
//        }
//        else {
//            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.SEND_SMS}, SEND_SMS_PERM_REQ_CODE);
////            send();
//        }
    }

    private void set(){
        etOTP = findViewById(R.id.otpCode);
        keyOTP = etOTP.getText().toString();
    }

    private Toast err(){
        if (keyOTP.isEmpty())
            return Toast.makeText(this, "All fields should be filled", Toast.LENGTH_SHORT);
        else if (!keyOTP.equals(sentOTP))
            return Toast.makeText(this, "OTP code is invalid", Toast.LENGTH_SHORT);
        else {
            temp = (User) getIntent().getExtras().getSerializable("user");
            User.UPDATE_USER_STATUS(temp.getID(), "true");
            User.curr = User.GET_USER(temp.getEmail(), temp.getPassword());
        }
        return null;
    }

    private Toast succ(){
        if (User.curr.getIsVerified()) return Toast.makeText(this, "User is now verified.", Toast.LENGTH_SHORT);
        return null;
    }

    private String randomOTP(){
        Random rand = new Random();
        String code = "";
        for (int i=0; i<6; i++){
           char temp = (char) (rand.nextInt('9'-'0') + '0');
           code += temp;
        }
        Log.i("OTP", code);
        return code;
    }

    private boolean checkPerm(String perms) {
        int check = ContextCompat.checkSelfPermission(this, perms);
        return (check == PackageManager.PERMISSION_GRANTED);
    }
}