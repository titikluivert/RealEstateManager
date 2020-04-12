package com.openclassrooms.realestatemanager.controler.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.RealEstateModel;
import com.openclassrooms.realestatemanager.model.UploadImage;
import com.openclassrooms.realestatemanager.utils.mainUtils;
import com.openclassrooms.realestatemanager.view.PhotoViewAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.openclassrooms.realestatemanager.controler.activities.ModificationActivity.EXTRA_ID_CURRENT_ESTATE;
import static com.openclassrooms.realestatemanager.utils.mainUtils.getLocationFromAddress;


public class SecondActivity extends BaseActivity {

    static final int REQUEST_MODIFY_REAL_ESTATE_CODE = 15;
    public static final String EXTRA_MODIFY_REAL_ESTATE = "com.openclassrooms.realestatemanager.controler.activities.EXTRA_MODIFY_REAL_ESTATE";

    @BindView(R.id.textroom)
    TextView textRoom;

    @BindView(R.id.textsurface)
    TextView textSurface;

    @BindView(R.id.textDateSale)
    TextView textDateSale;

    @BindView(R.id.textDescriptionContent)
    TextView textDescriptionContent;

    @BindView(R.id.map)
    ImageView mapImg;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.address_txt)
    TextView address_txt;

    // FOR DESIGN
    private RealEstateModel realEstateModel;

    private boolean deleteItem = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);
        this.configureToolbar();

        ArrayList<UploadImage> items = new ArrayList<>();
        PhotoViewAdapter adapter = new PhotoViewAdapter(this, items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
        // coming from main activity
        realEstateModel = restoreRealEstateModel(getIntent().getStringExtra(MainActivity.EXTRA_MESSAGE));

        if (realEstateModel == null) {
            // //coming from map activity --> this activity was start from map activity
            realEstateModel = restoreRealEstateModel(getIntent().getStringExtra(mainUtils.EXTRA_MAP_TO_SECOND));
        }
        for (int i = 0; i < realEstateModel.getPhotos().size(); i++) {
            items.add(new UploadImage("Image " + i, realEstateModel.getPhotos().get(i)));
            adapter.notifyDataSetChanged();
        }
        try {
            Glide.with(this).load(getGoogleMapUrl(this, realEstateModel.getAddress())).into(mapImg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.updateUI(realEstateModel);

        adapter.setOnItemLongClickListener(photos -> {

            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("Do you want to delete this image ?");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Yes",
                    (dialog, id) -> {
                        // adapter.getItemCount();
                        //    items.remove();
                        deleteItem = true;
                        dialog.cancel();
                    });
            builder1.setNegativeButton(
                    "No",
                    (dialog, id) -> dialog.cancel());

            AlertDialog alert11 = builder1.create();
            alert11.show();

        });

    }

    @OnClick(R.id.ModifyRealEstate_fab)
    public void ModifyRealEstate() {
        String estateModelString = new Gson().toJson(realEstateModel);
        Intent myIntent = new Intent(SecondActivity.this, ModificationActivity.class);
        myIntent.putExtra(EXTRA_MODIFY_REAL_ESTATE, estateModelString);
        //iDCurrentEstate = getIntent().getIntExtra(EXTRA_ID, -1);
        this.startActivityForResult(myIntent, REQUEST_MODIFY_REAL_ESTATE_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_MODIFY_REAL_ESTATE_CODE) {

            if (resultCode == RESULT_OK) {
                setResult(RESULT_OK, data);
                finish();
            }
            if (resultCode == RESULT_CANCELED) {
                showToast("error occurred, Verified input data fields");
            }
        }
    }

    private void updateUI(RealEstateModel estateModel) {
        String [] realEstateAddress = estateModel.getAddress().split(",");
        String dateOfSaleStr;
        if (estateModel.getDateOfSale() == null) {
            dateOfSaleStr = "";
        } else {
            dateOfSaleStr = mainUtils.getConvertDate(mainUtils.DateConverters.dateToTimestamp(estateModel.getDateOfSale()));
        }

        textRoom.setText("Number of rooms : " + estateModel.getRoomNumbers());
        textSurface.setText("Surface : " + estateModel.getSurface() + " sqm");
        textDateSale.setText("Date of sale : " + dateOfSaleStr);
        textDescriptionContent.setText(estateModel.getDescription());
        address_txt.setText(realEstateAddress[0].trim()+"\n"+realEstateAddress[1].trim());

    }

    public String getGoogleMapUrl(Context ctx, String myAddress) throws IOException {
        double[] location = getLocationFromAddress(ctx, myAddress);
        assert location != null;
        String URL = "https://maps.google.com/maps/api/staticmap?center=" + location[0] + "," + location[1];
        String zoom = "&zoom=16";
        String size = "&size=200x200";
        String mapType = "&maptype=terrain";
        String markers = "&markers=size:tiny%7Ccolor:red%7C" + location[0] + "," + location[1];
        String key = "&key=AIzaSyCXBKZ5tT07uT8XdXsUuAMsVkV-Uxs70E8";
        return (URL + zoom + size + mapType + markers + key);
    }

    private void configureToolbar() {
        // Get the toolbar view inside the activity layout
        Toolbar toolbar = findViewById(R.id.toolbar);
        // Sets the Toolbar
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        assert ab != null;
        ab.setTitle("Real Estate Details");
        Objects.requireNonNull(ab).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
