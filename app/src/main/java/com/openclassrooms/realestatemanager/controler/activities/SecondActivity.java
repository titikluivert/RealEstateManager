package com.openclassrooms.realestatemanager.controler.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    // FOR DESIGN
    private RealEstateModel realEstateModel, realEstateModelFromSecondActivity;

    private boolean deleteItem = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);

        ArrayList<UploadImage> items = new ArrayList<>();
        PhotoViewAdapter adapter = new PhotoViewAdapter(this, items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
        // coming from main activity
        realEstateModel = restoreRealEstateModel(getIntent().getStringExtra(MainActivity.EXTRA_MESSAGE));
        //coming from map activity
        RealEstateModel realEstateModelFromMap = restoreRealEstateModel(getIntent().getStringExtra(mainUtils.EXTRA_MAP_TO_SECOND));

        if (realEstateModel == null) {
            if (realEstateModelFromSecondActivity != null) {
                realEstateModel = realEstateModelFromSecondActivity;
            } else {
                realEstateModel = realEstateModelFromMap;
            }
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
        myIntent.putExtra(EXTRA_MODIFY_REAL_ESTATE,estateModelString);
        this.startActivityForResult(myIntent, REQUEST_MODIFY_REAL_ESTATE_CODE);

    }

    @OnClick(R.id.closeForm2Activity)
    public void backToMain() {
        Intent intent = new Intent(SecondActivity.this, MainActivity.class);
        startActivity(intent);
    }


    private void updateUI(RealEstateModel estateModel) {

        textRoom.setText("Number of rooms : " + estateModel.getRoomNumbers());
        textSurface.setText("Surface : " + estateModel.getSurface() + " sqm");
        textDateSale.setText("Date of sale : " + estateModel.getDateOfSale());
        textDescriptionContent.setText(estateModel.getDescription());
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

    private void openFragment(Fragment mFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container_modif, mFragment).commit();
    }

}
