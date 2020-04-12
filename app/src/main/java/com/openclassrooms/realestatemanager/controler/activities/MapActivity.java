package com.openclassrooms.realestatemanager.controler.activities;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controler.fragments.MapsViewFragment;
import java.util.Objects;

public class MapActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        this.configureToolbar();
         getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,MapsViewFragment.newInstance(getIntent().getStringExtra(MainActivity.EXTRA_MAP))).commit();
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
        ab.setTitle("Real Estate on Map");
        Objects.requireNonNull(ab).setDisplayHomeAsUpEnabled(true);
    }

}