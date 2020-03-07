package com.openclassrooms.realestatemanager.controler.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controler.fragments.HomeFragment;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, HomeFragment.HomefragmentCallback{

    // FOR DESIGN

    //Drawer Layout
    @BindView(R.id.drawer_main_id)
    DrawerLayout drawerLayout;

    @BindView(R.id.nav_view)
    NavigationView navigationView;
    // define an ActionBarDrawerToggle
    private ActionBarDrawerToggle mToggle;

    private Menu menu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
       // this.recyclerView = findViewById(R.id.main_recycler_view);
       // this.recyclerView.setHasFixedSize(true);
       // this.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();

        mToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        //this.configureSwipeRefreshLayout();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //2 - Inflate the menu and add it to the Toolbar
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //3 - Handle actions on menu items
        /*case R.id.menu_main_add:
                this.openAddFragment(new EstateFragment_1());
                return true;*/
        if (item.getItemId() == R.id.menu_main_search) {// launchNotificationActivity();
            return true;
        }
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getMenuInflater().inflate(R.menu.menu_search, menu);
        //menu.add(0, R.id.menu_main_search, 0, "test");
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.menu_drawer_your_profile:

                //
                break;
            case R.id.menu_drawer_settings:
                //
                break;
            case R.id.menu_drawer_logout:
                //
            default:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    public void openAddFragment(Fragment mFragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, mFragment).commit();
    }


    @Override
    public void newRealEstateAdd() {
        menu.removeItem(R.id.menu_main_search);
    }
}

