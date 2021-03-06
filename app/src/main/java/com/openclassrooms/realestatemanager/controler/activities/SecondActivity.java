package com.openclassrooms.realestatemanager.controler.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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
import com.openclassrooms.realestatemanager.utils.Utils;
import com.openclassrooms.realestatemanager.view.PhotoViewAdapter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.openclassrooms.realestatemanager.controler.activities.MainActivity.ON_BACK_PRESSED_SECOND;
import static com.openclassrooms.realestatemanager.utils.Utils.getLocationFromAddress;


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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);
        this.configureToolbar();
        realEstateModel = restoreRealEstateModel(getIntent().getStringExtra(MainActivity.EXTRA_MESSAGE));
        if (realEstateModel == null) {
            // //coming from map activity --> this activity was start from map activity
            realEstateModel = restoreRealEstateModel(getIntent().getStringExtra(Utils.EXTRA_MAP_TO_SECOND));
        }

        List<UploadImage> items = realEstateModel.getPhotos();
        PhotoViewAdapter adapter = new PhotoViewAdapter(this, items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
        // coming from main activity

        adapter.notifyDataSetChanged();

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
                        items.remove(photos);
                        realEstateModel.setPhotos(items);
                        adapter.notifyDataSetChanged();
                        dialog.cancel();
                    });
            builder1.setNegativeButton(
                    "No",
                    (dialog, id) -> dialog.cancel());

            AlertDialog alert11 = builder1.create();
            alert11.show();

        });

        adapter.setOnItemClickListener(photos -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            // set the custom layout
            View customLayout = getLayoutInflater().inflate(R.layout.image_name_layout, null);
            builder.setView(customLayout);

            ImageView imageView = customLayout.findViewById(R.id.imageView_photo);
            EditText textEdit = customLayout.findViewById(R.id.editImageName);
            TextView textV = customLayout.findViewById(R.id.currentImageName);

            textV.setText(photos.getName());
            imageView.setImageBitmap(Utils.loadImageBitmap(photos.getImageUrl()));


            builder.setPositiveButton(
                    "CLOSE",
                    (dialog, id) -> {

                        photos.setName(textEdit.getText().toString().isEmpty() ? textV.getText().toString() : textEdit.getText().toString());
                        realEstateModel.setPhotos(items);
                        adapter.notifyDataSetChanged();
                        dialog.cancel();
                    });

            AlertDialog alert11 = builder.create();
            alert11.show();

        });

    }


    @Override
    public void onPause() {
        Utils.saveRealEstateModel(this, realEstateModel);
        super.onPause();
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
        String[] realEstateAddress = estateModel.getAddress().split(",");
        String dateOfSaleStr;
        if (estateModel.getDateOfSale() == null) {
            dateOfSaleStr = "";
        } else {
            dateOfSaleStr = Utils.getConvertDate(Utils.DateConverters.dateToTimestamp(estateModel.getDateOfSale()));
        }

        textRoom.setText("Number of rooms : " + estateModel.getRoomNumbers());
        textSurface.setText("Surface : " + estateModel.getSurface() + " sqm");
        textDateSale.setText("Date of sale : " + dateOfSaleStr);
        textDescriptionContent.setText(estateModel.getDescription());
        address_txt.setText(realEstateAddress[0].trim() + "\n" + realEstateAddress[1].trim());

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
            this.onBackPressedFcn();
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onBackPressedFcn() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(ON_BACK_PRESSED_SECOND, new Gson().toJson(realEstateModel));
        setResult(RESULT_OK, resultIntent);
    }


}
