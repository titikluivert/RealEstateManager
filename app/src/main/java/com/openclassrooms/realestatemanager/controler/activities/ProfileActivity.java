package com.openclassrooms.realestatemanager.controler.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.RealEstateAgent;
import com.openclassrooms.realestatemanager.utils.Utils;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends BaseActivity {

    @BindView(R.id.NameOfAgent)
    TextView NameOfAgent;

    @BindView(R.id.emailOfAgent)
    TextView emailOfAgent;

    @BindView(R.id.phoneNumberAgent)
    TextView phoneNumberAgent;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        this.configureToolbar();

        RealEstateAgent agentProfileData =  Utils.getRealEstateAgent(this);
        if (agentProfileData != null) {
            NameOfAgent.setText(agentProfileData.getName());
            emailOfAgent.setText(agentProfileData.getEmail());
            phoneNumberAgent.setText(agentProfileData.getPhoneNumber());
        }
        //textView = findViewById(R.id.logout);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void configureToolbar() {
        // Get the toolbar view inside the activity layout
        Toolbar toolbar = findViewById(R.id.toolbar);
        // Sets the Toolbar
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        assert ab != null;
        ab.setTitle("My Profile");
        Objects.requireNonNull(ab).setDisplayHomeAsUpEnabled(true);
    }
}
