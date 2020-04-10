package com.openclassrooms.realestatemanager.controler.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.openclassrooms.realestatemanager.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Login extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @BindView(R.id.username)
    TextInputEditText userName;

    @BindView(R.id.phoneNumber)
    TextInputEditText phoneNumber;

    @BindView(R.id.Email)
    TextInputEditText email;

    @BindView(R.id.password)
    TextInputEditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_realestate);
        ButterKnife.bind(this);
        init();

    }
//    sharedPreferences.edit().putBoolean("loginUser", false).apply();
//    sharedPreferences.edit().putBoolean("loginAdmin", false).apply();

    @OnClick(R.id.signInBtn)
    public void signInClick() {
        if (checkFieldsEmpty()) {

            if (userName.getText().toString().equals("user")) {
                sharedPreferences.edit().putBoolean("loginUser", true).apply();
                startActivity(new Intent(Login.this, MainActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            } else {
                sharedPreferences.edit().putBoolean("loginAdmin", true).apply();
                startActivity(new Intent(Login.this, MainActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sharedPreferences != null && sharedPreferences.getBoolean("loginUser", false)) {
            startActivity(new Intent(Login.this, MainActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
        if (sharedPreferences != null && sharedPreferences.getBoolean("loginAdmin", false)) {
            startActivity(new Intent(Login.this, MainActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }

    }

    private void init() {
       // userName = findViewById(R.id.username);
       // password = findViewById(R.id.password);
        //signIn = findViewById(R.id.signInBtn);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }

    private boolean checkFieldsEmpty() {
        if (userName.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter valid username", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (email.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter valid email", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (phoneNumber.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter valid phone number", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter valid password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
