package com.openclassrooms.realestatemanager.controler.activities;

import android.app.Notification;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.api.RealEstateHelper;
import com.openclassrooms.realestatemanager.controler.fragments.DetailsFragment;
import com.openclassrooms.realestatemanager.model.RealEstateModel;
import com.openclassrooms.realestatemanager.model.RealEstateModelPref;
import com.openclassrooms.realestatemanager.utils.Utils;
import com.openclassrooms.realestatemanager.view.RealEstateAdapter;
import com.openclassrooms.realestatemanager.viewmodel.RealEstateViewModel;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.openclassrooms.realestatemanager.api.App.CHANNEL_1_ID;
import static com.openclassrooms.realestatemanager.controler.activities.ModificationActivity.EXTRA_ID_CURRENT_ESTATE;
import static com.openclassrooms.realestatemanager.controler.activities.ModificationActivity.EXTRA_RESULT_MODIFICATION;
import static com.openclassrooms.realestatemanager.controler.activities.SearchActivity.SEARCH_RESULT;
import static com.openclassrooms.realestatemanager.controler.activities.SearchActivity.regexS;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, DetailsFragment.OnDataRealEstatePass {

    public static final String EXTRA_ADD_NEW_ESTATE = "com.openclassrooms.realestatemanager.controler.activities.EXTRA_ADD_NEW_ESTATE";
    public static final String ON_BACK_PRESSED_SECOND = "com.openclassrooms.realestatemanager.controler.activities.ON_BACK_PRESSED_SECOND";
    public static final String EXTRA_MESSAGE = "com.openclassrooms.realestatemanager.controler.activities.MESSAGE";
    public static final String EXTRA_MAP = "com.openclassrooms.realestatemanager.controler.activities.MAP_DATA";
    public static final String EXTRA_MAP_IS_TABLET_MODE_ON = "com.openclassrooms.realestatemanager.controler.activities.MAP_IS_TABLET_MODE_ON";
    static final int REQUEST_SEARCH_CODE = 1;
    public static final int EDIT_REAL_ESTATE_REQUEST = 2;
    static final int REQUEST_ADD_REAL_ESTATE_CODE = 4;

    PlacesClient placesClient;

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

    RealEstateAdapter adapter;
    private Menu menu;


    // For Tablet mode landscape only
    private boolean isTabletModeLandScapeOn = false;

    @Nullable
    @BindView(R.id.linLytFirstScreen)
    LinearLayout linLytFirstScreen;

    @Nullable
    @BindView(R.id.linLytSecondScreen)
    LinearLayout linLytSecondScreen;


    @Nullable
    @BindView(R.id.fragmentRemove)
    FrameLayout fragmentRemove;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        this.configureToolbar();
        // Initialize Places.
        Places.initialize(getApplicationContext(), Utils.ApiKeyGoogleID);
        //Create a new Places client instance.
        placesClient = Places.createClient(this);

        notificationManager = NotificationManagerCompat.from(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        if (linLytFirstScreen != null) {
            assert linLytSecondScreen != null;
            isTabletModeLandScapeOn = linLytFirstScreen.getVisibility() == View.VISIBLE & linLytSecondScreen.getVisibility() == View.VISIBLE;

            assert fragmentRemove != null;
            fragmentRemove.setVisibility(View.GONE);
        }

        // adapter
        this.adapter = new RealEstateAdapter(isTabletModeLandScapeOn);
        recyclerView.setAdapter(adapter);
        RealEstateModel realEstateModelMAP = restoreRealEstateModel(getIntent().getStringExtra(Utils.EXTRA_MAP_TO_MAIN));
        if (realEstateModelMAP != null) {
            assert fragmentRemove != null;
            fragmentRemove.setVisibility(View.VISIBLE);
            int positionFromMap = getIntent().getIntExtra(Utils.EXTRA_MAP_TO_MAIN_CURRENT_POSITION_ADAPTER, -1);
            if(isTabletModeLandScapeOn && positionFromMap >= 0) {
                adapter.updateAdapter(positionFromMap);
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentB, DetailsFragment.newInstance(new Gson().toJson(realEstateModelMAP))).commit();
        }

        
        //navigation view
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // view model
        realEstateViewModel = ViewModelProviders.of(this).get(RealEstateViewModel.class);
        realEstateViewModel.getAllNotes().observe(this, adapter::setNotes);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder
                    viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                if (direction == ItemTouchHelper.LEFT) {
                    RealEstateModel updateRealEstate = adapter.getRealEstateAt(viewHolder.getAdapterPosition());
                    updateRealEstate.setStatus(true);
                    updateRealEstate.setDateOfSale(null);
                    realEstateViewModel.update(updateRealEstate);
                    Toast.makeText(MainActivity.this, "Sale date and Status were updated to available ", Toast.LENGTH_SHORT).show();
                }
                if (direction == ItemTouchHelper.RIGHT) {
                    RealEstateModel updateRealEstate = adapter.getRealEstateAt(viewHolder.getAdapterPosition());
                    updateRealEstate.setStatus(false);
                    updateRealEstate.setDateOfSale(new Date());
                    realEstateViewModel.update(updateRealEstate);
                    RealEstateHelper.updateRealEstateChildren(updateRealEstate);
                    Toast.makeText(MainActivity.this, "Sale date and Status were updated to sale", Toast.LENGTH_SHORT).show();
                }

            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(realEstateModel -> {

            if (isTabletModeLandScapeOn) {
                assert fragmentRemove != null;
                fragmentRemove.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentB, DetailsFragment.newInstance(new Gson().toJson(realEstateModel))).commit();
            } else {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra(EXTRA_MESSAGE, new Gson().toJson(realEstateModel));
                startActivityForResult(intent, EDIT_REAL_ESTATE_REQUEST);
            }
        });

        mToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

    }

    @OnClick(R.id.addNewRealEstate_fab)
    public void addNewRealEstate() {
        menu.removeItem(R.id.menu_main_search);
        Intent myIntent = new Intent(MainActivity.this, RealEstateActivity.class);
        this.startActivityForResult(myIntent, REQUEST_ADD_REAL_ESTATE_CODE);
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.menu_drawer_your_profile:

                Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(i);

                break;
            case R.id.menu_drawer_map:
                if (Utils.isInternetAvailable(this)) {
                    LiveData<List<RealEstateModel>> AllRealEstates = realEstateViewModel.getAllNotes();
                    Intent intent = new Intent(MainActivity.this, MapActivity.class);
                    intent.putExtra(EXTRA_MAP, new Gson().toJson(AllRealEstates.getValue()));
                    intent.putExtra(EXTRA_MAP_IS_TABLET_MODE_ON, isTabletModeLandScapeOn);
                    startActivity(intent);
                } else {
                    showToast("check your internet connection");
                }
                //
                break;

            case R.id.menu_drawer_logout:
                showToast("Not supported");
                //    sharedPreferences.edit().putBoolean("loginAdmin", false).apply();
                break;

            default:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SEARCH_CODE) {

            if (resultCode == RESULT_OK) {
                String resultString = data.getStringExtra(SEARCH_RESULT);
                String[] dateTemp = resultString.split(regexS);

                switch (dateTemp[0]) {

                    case "BY_SURFACE":
                        realEstateViewModel.SearchBySurfaceRepo(Float.parseFloat(dateTemp[1]), Float.parseFloat(dateTemp[2])).observe(this, this.adapter::setNotes);
                        break;
                    case "BY_PRICE":
                        realEstateViewModel.SearchByPriceRepo(Integer.parseInt(dateTemp[1]), Integer.parseInt(dateTemp[2])).observe(this, this.adapter::setNotes);
                        break;
                    case "BY_AVAILABILITY":
                        realEstateViewModel.SearchByOnlineSinceRepo(dateTemp[1]).observe(this, this.adapter::setNotes);
                        break;
                    case "BY_SALE":
                        realEstateViewModel.SearchBySaleSinceRepo(dateTemp[1]).observe(this, this.adapter::setNotes);
                        break;
                    case "BY_ADDRESS":
                        realEstateViewModel.SearchByAddressRepo(dateTemp[1]).observe(this, this.adapter::setNotes);
                        break;
                    case "BY_PHOTOS":
                        realEstateViewModel.SearchByPhotoRepo(Integer.parseInt(dateTemp[1])).observe(this, this.adapter::setNotes);
                        break;

                    default:
                        break;
                }
            }
            if (resultCode == RESULT_CANCELED) {
                showToast("error occurred, no able to filter");
            }
        }

        if (requestCode == REQUEST_ADD_REAL_ESTATE_CODE) {
            if (resultCode == RESULT_OK) {
                this.updateUI(data.getStringExtra(EXTRA_ADD_NEW_ESTATE));
            }
            if (resultCode == RESULT_CANCELED) {
                showToast("error occurred was not able to add new real estate");
            }
        }

        if (requestCode == EDIT_REAL_ESTATE_REQUEST) {
            String resultString;
            RealEstateModel estateModelModification;
            long id;
            if (resultCode == RESULT_OK) {
                resultString = data.getStringExtra(EXTRA_RESULT_MODIFICATION);
                if (resultString == null || resultString.isEmpty()) {
                    resultString = data.getStringExtra(ON_BACK_PRESSED_SECOND);
                    estateModelModification = restoreRealEstateModel(resultString);
                } else {
                    id = data.getLongExtra(EXTRA_ID_CURRENT_ESTATE, -1);
                    if (id == -1) {
                        Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    estateModelModification = restoreRealEstateModel(resultString);
                    estateModelModification.setId(id);
                }


                this.updateFromModification(estateModelModification);
            }
            if (resultCode == RESULT_CANCELED) {

                estateModelModification = Utils.getRealEstateModel(this);
                if (estateModelModification == null) {
                    showToast("error occurred was not able to update current real estate");
                } else {
                    this.updateFromModification(estateModelModification);
                    Utils.saveRealEstateModel(this, null);
                }

            }
        }

    }

    public void updateUI(String data) {

        RealEstateModelPref data1 = new Gson().fromJson(data, new TypeToken<RealEstateModelPref>() {
        }.getType());
        RealEstateModel note = new RealEstateModel(data1.getType(), Double.parseDouble(data1.getPrice()), Float.parseFloat(data1.getSurface()), Integer.parseInt(data1.getRoomNumbers()), data1.getDescription(), data1.getAddress(),
                data1.getPhotos(),data1.getPhotos().size(), true, new Date(), null, data1.getPoi(), 1);
        realEstateViewModel.insert(note);
        RealEstateHelper.writeNewRealEstate(note);
        this.notification();
    }

    public void updateFromModification(RealEstateModel data) {
        RealEstateHelper.updateRealEstateChildren(data);
        realEstateViewModel.update(data);
    }

    private void notification() {
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

    private void launchSearchActivity() {
        Intent myIntent = new Intent(MainActivity.this, SearchActivity.class);
        this.startActivityForResult(myIntent, REQUEST_SEARCH_CODE);
    }

    private void configureToolbar() {
        // Get the toolbar view inside the activity layout
        Toolbar toolbar = findViewById(R.id.toolbar);
        // Sets the Toolbar
        setSupportActionBar(toolbar);
    }

    @Override
    public void onDataRealEstatePass(Intent data) {

        long id = data.getLongExtra(EXTRA_ID_CURRENT_ESTATE, -1);
        String resultStringTablet = data.getStringExtra(EXTRA_RESULT_MODIFICATION);
        if (id == -1) {
            Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show();
            return;
        }
        RealEstateModel estateModelModificationTablet = restoreRealEstateModel(resultStringTablet);
        estateModelModificationTablet.setId(id);
        this.updateFromModification(estateModelModificationTablet);

    }

    @Override
    public void onDataRealEstateSecond(RealEstateModel data) {
        this.updateFromModification(data);
    }
}

