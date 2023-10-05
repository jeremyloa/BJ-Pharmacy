package edu.bluejack22_1.bluejackpharmacy.controller;

import static com.google.android.material.internal.ContextUtils.getActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.bluejack22_1.bluejackpharmacy.Database;
import edu.bluejack22_1.bluejackpharmacy.controller.home.Home;
import edu.bluejack22_1.bluejackpharmacy.R;
import edu.bluejack22_1.bluejackpharmacy.model.Medicine;
import edu.bluejack22_1.bluejackpharmacy.model.User;

public class Login extends AppCompatActivity {

    private Button btnRegister, btnLogin;
    private EditText etemail, etpass;
    private String email, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Database.sqlite = new Database(this);
        getMedDB();
        btnLogin = findViewById(R.id.toLoginBtn);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set();
                Toast tst = err();
                if (tst!=null)
                    tst.show();
                else {
                    tst = succ();
                    if (tst!=null)  {
                        tst.show();
                         Intent intent = new Intent(Login.this, OTP.class);
                        intent.putExtra("user", User.GET_USER(email, pass));
                        startActivity(intent);
                    } else {
                        startActivity(new Intent(Login.this, Home.class));
                    }
                    finish();
                }
            }
        });
        btnRegister = findViewById(R.id.toRegPageBtn);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });
    }

    private void set(){
        etemail = findViewById(R.id.loginMail);
        email = etemail.getText().toString();
        etpass = findViewById(R.id.loginPass);
        pass = etpass.getText().toString();
    }

    private Toast err(){
        if (email.isEmpty() || pass.isEmpty())
            return Toast.makeText(this, "All fields should be filled", Toast.LENGTH_SHORT);
        else {
            User user = User.GET_USER(email, pass);
            if (user==null)
                return Toast.makeText(this, "User is not found", Toast.LENGTH_SHORT);
            else if (!user.getPassword().equals(pass))
                return Toast.makeText(this, "Credential is invalid", Toast.LENGTH_SHORT);
            else User.curr = user;
        }
        return null;
    }

    private Toast succ(){
        if (!User.curr.getIsVerified()) return Toast.makeText(this, "User needs to verify OTP.", Toast.LENGTH_SHORT);
        return null;
    }

    public void getMedDB(){
        if (Medicine.dbMed!=null) Medicine.dbMed.clear();
        Medicine.FETCH_INSERT(Login.this);
        Log.i("STATE", "Login getMedDB");
        Medicine.dbMed = Medicine.GET_MEDICINES();
        if (Medicine.dbMed == null) {
            Log.i("STATE", "dbMed null");
        } else {
            Log.i("STATE", "dbMed not null");
        }
    }
}