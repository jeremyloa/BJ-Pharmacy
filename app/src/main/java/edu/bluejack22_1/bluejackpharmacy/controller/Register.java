package edu.bluejack22_1.bluejackpharmacy.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.bluejack22_1.bluejackpharmacy.Database;
import edu.bluejack22_1.bluejackpharmacy.R;
import edu.bluejack22_1.bluejackpharmacy.model.User;

public class Register extends AppCompatActivity {

    private Button btnRegister, btnLogin;
    private EditText etname, etemail, etpass, etconf, etphone;
    private String name, email, pass, conf, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.SEND_SMS}, 1);
        Database.sqlite = new Database(this);
        btnRegister = findViewById(R.id.toRegBtn);
        btnRegister.setOnClickListener(view -> {
            set();
            Toast tst = err();
            if (tst!=null) tst.show();
            else {
                User.INSERT_USER(new User(name, email, pass, phone));
                tst = succ();
                tst.show();
                Intent intent = new Intent(Register.this, OTP.class);
                intent.putExtra("user", User.GET_USER(email, pass));
                startActivity(intent);
                finish();
            }
        });
        btnLogin = findViewById(R.id.toLoginPageBtn);
        btnLogin.setOnClickListener(view -> startActivity(new Intent(Register.this, Login.class)));
    }

    private void set(){
        etname = findViewById(R.id.regisName);
        name = etname.getText().toString();
        etemail = findViewById(R.id.regisMail);
        email = etemail.getText().toString();
        etpass = findViewById(R.id.regisPass);
        pass = etpass.getText().toString();
        etconf = findViewById(R.id.regisConfPass);
        conf = etconf.getText().toString();
        etphone = findViewById(R.id.regisPhone);
        phone = etphone.getText().toString();
    }

    private Toast err(){
        if (name.isEmpty() || email.isEmpty() || pass.isEmpty() || conf.isEmpty() || phone.isEmpty())
            return Toast.makeText(this, "All fields should be filled", Toast.LENGTH_SHORT);
        else if (name.length()<5)
            return Toast.makeText(this, "Name length should be at least 5 characters", Toast.LENGTH_SHORT);
        else if (!email.endsWith(".com"))
            return Toast.makeText(this, "Email should ends with '.com'", Toast.LENGTH_SHORT);
        else if (!alphanum(pass))
            return Toast.makeText(this, "Password should have letters and numbers", Toast.LENGTH_SHORT);
        else if (!pass.equals(conf))
            return Toast.makeText(this, "Password and confirmed password should be the same", Toast.LENGTH_SHORT);
        return null;
    }

    private Toast succ(){
        return Toast.makeText(this, "User successfully registered.", Toast.LENGTH_SHORT);
    }
    private boolean alphanum(String str){
        boolean isChar = false, isNum = false;
        for (int i=0; i<str.length(); i++) {
            if (str.charAt(i) >= 'a' && str.charAt(i) <= 'z' ) isChar = true;
            if (str.charAt(i) >= 'A' && str.charAt(i) <= 'Z' ) isChar = true;
            if (str.charAt(i) >= '0' && str.charAt(i) <= '9' ) isNum = true;
            if (isChar && isNum) break;
        }
        return isChar && isNum;
    }
}