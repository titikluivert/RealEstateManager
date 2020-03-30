package com.openclassrooms.realestatemanager.controler.activities;


import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controler.fragments.EstateFragment;
import com.openclassrooms.realestatemanager.model.RealEstateModel;
import com.openclassrooms.realestatemanager.model.RealEstateModelPref;
import com.openclassrooms.realestatemanager.utils.mainUtils;
import com.openclassrooms.realestatemanager.view.RealEstateAdapter;
import com.openclassrooms.realestatemanager.viewmodel.RealEstateViewModel;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.openclassrooms.realestatemanager.api.App.CHANNEL_1_ID;
import static com.openclassrooms.realestatemanager.utils.mainUtils.REAL_ESTATE_SALE;
import static com.openclassrooms.realestatemanager.utils.mainUtils.getTodayDate;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, EstateFragment.onSomeEventListener {

    public static final String EXTRA_MESSAGE = "com.openclassrooms.realestatemanager.controler.activities.MESSAGE";
    // FOR DESIGN
    private RealEstateViewModel realEstateViewModel;

    //Drawer Layout
    @BindView(R.id.drawer_main_id)
    DrawerLayout drawerLayout;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    // define an ActionBarDrawerToggle
    private ActionBarDrawerToggle mToggle;

    @BindView(R.id.recyclerViewHome)
    RecyclerView recyclerView;

    private NotificationManagerCompat notificationManager;

    //private String dataToSave;
    //private RealEstateModelPref dataToSave1;

    private Menu menu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        notificationManager = NotificationManagerCompat.from(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        // adapter
        final RealEstateAdapter adapter = new RealEstateAdapter();
        recyclerView.setAdapter(adapter);

        //navigation view
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // view model
        realEstateViewModel = ViewModelProviders.of(this).get(RealEstateViewModel.class);
        realEstateViewModel.getAllNotes().observe(this, adapter::setNotes);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                if (direction == ItemTouchHelper.LEFT) {
                    realEstateViewModel.delete(adapter.getRealEstateAt(viewHolder.getAdapterPosition()));
                    Toast.makeText(MainActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();
                }
                if (direction == ItemTouchHelper.RIGHT) {
                    REAL_ESTATE_SALE = true;
                    RealEstateModel updateRealEstate = adapter.getRealEstateAt(viewHolder.getAdapterPosition());
                    updateRealEstate.setStatus("Sale");
                    updateRealEstate.setDateOfSale(mainUtils.getConvertDate(mainUtils.getTodayDate()));
                    realEstateViewModel.update(updateRealEstate);
                    Toast.makeText(MainActivity.this, "Sale date and Status were updated ", Toast.LENGTH_SHORT).show();
                }

            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(realEstateModel -> {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            intent.putExtra(EXTRA_MESSAGE, new Gson().toJson(realEstateModel));
            startActivity(intent);
        });


        mToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

    }

    @OnClick(R.id.addNewRealEstate_fab)
    public void addNewRealEstate() {
        menu.removeItem(R.id.menu_main_search);
        this.openFragment(new EstateFragment());
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
        if (item.getItemId() == R.id.menu_main_search) {
            this.launchSearchActivity();
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

            case R.id.menu_drawer_share:
//                shareFile();
                break;

            case R.id.menu_drawer_logout:

            default:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    public void updateUI(String data0, RealEstateModelPref data1) {
        List<String> photo = mainUtils.Converter.restoreList(data0);
        RealEstateModel note = new RealEstateModel(data1.getType(), data1.getPrice(), data1.getSurface(), data1.getRoomNumbers(), data1.getDescription(), data1.getAddress(),
                photo, "for sale", mainUtils.getConvertDate(getTodayDate()), null, 1);
        realEstateViewModel.insert(note);
        notification ();
    }

    @Override
    public void someEvent(String data0, RealEstateModelPref data1) {
        updateUI(data0, data1);
        //this.dataToSave = data0;
        //this.dataToSave1 = data1;
    }

    private void notification () {
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_notifications_active_black_24dp)
                .setContentTitle("Add new Real Estate")
                .setContentText("RealEstate was successfully added")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setAutoCancel(true)
                .build();

        notificationManager.notify(1, notification);

    }

    private void openFragment(Fragment mFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, mFragment).commit();
    }

    private void launchSearchActivity() {
         Intent myIntent = new Intent(MainActivity.this, SearchActivity.class);
         this.startActivityForResult(myIntent, 1);
    }
}

