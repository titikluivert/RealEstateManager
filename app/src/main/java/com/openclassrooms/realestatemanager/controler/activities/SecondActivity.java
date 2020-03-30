package com.openclassrooms.realestatemanager.controler.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.GeoPoint;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controler.fragments.EstateModificationFragment;
import com.openclassrooms.realestatemanager.model.RealEstateModel;
import com.openclassrooms.realestatemanager.model.UploadImage;
import com.openclassrooms.realestatemanager.view.PhotoViewAdapter;
import com.openclassrooms.realestatemanager.viewmodel.RealEstateViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.openclassrooms.realestatemanager.utils.mainUtils.getLocationFromAddress;


public class SecondActivity extends BaseActivity {

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
    private RealEstateViewModel realEstateViewModel;
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

        // view model
        realEstateViewModel = ViewModelProviders.of(this).get(RealEstateViewModel.class);

        realEstateModel = restoreRealEstateModel(getIntent().getStringExtra(MainActivity.EXTRA_MESSAGE));

        for (int i = 0; i < realEstateModel.getPhotos().size(); i++) {
            items.add(new UploadImage("Image " + i, realEstateModel.getPhotos().get(i)));
            adapter.notifyDataSetChanged();
        }

        this.updateUI(realEstateModel);
        Glide.with(this).load("https://maps.googleapis.com/maps/api/staticmap?center=Brooklyn+Bridge,New+York,NY&zoom=15&size=200x200&maptype=terrain&markers=color:blue%7Clabel:S%7C40.702147,-74.015794&key=AIzaSyCXBKZ5tT07uT8XdXsUuAMsVkV-Uxs70E8").into(mapImg);

        adapter.setOnItemLongClickListener(photos -> {

            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("Do you want to delete this image ?");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Yes",
                    (dialog, id) -> {
                        deleteItem = true;
                        dialog.cancel();
                    });
            builder1.setNegativeButton(
                    "No",
                    (dialog, id) -> dialog.cancel());

            AlertDialog alert11 = builder1.create();
            alert11.show();

        });

       /* if (deleteItem) {
            adapter.removeAt();

            ItemClickSupport.removeFrom(recyclerView, R.layout.photos_items);
            RealEstateModel updateRealEstate = //adapter.getRealEstateAt(viewHolder.getAdapterPosition());
                    updateRealEstate.setStatus("Sale");
            updateRealEstate.setDateOfSale(mainUtils.getTodayDate());
            realEstateViewModel.update(updateRealEstate);
            deleteItem = false;


        }*/


    }


    @OnClick(R.id.ModifyRealEstate_fab)
    public void ModifyRealEstate() {
        String estateModelString = new Gson().toJson(realEstateModel);
        this.openFragment(EstateModificationFragment.newInstance(estateModelString));

    }


    private RealEstateModel restoreRealEstateModel(String s) {
        return new Gson().fromJson(s, new TypeToken<RealEstateModel>() {
        }.getType());
    }

    private void configureToolbar() {
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        Objects.requireNonNull(ab).setDisplayHomeAsUpEnabled(true);
    }

    private void updateUI(RealEstateModel estateModel) {

        textRoom.setText(String.format("Number of rooms : %s", estateModel.getRoomNumbers()));
        textSurface.setText(String.format("Surface : %s sqm", estateModel.getSurface()));
        textDateSale.setText(String.format("Date de vente : %s", estateModel.getDateOfSale()));
        textDescriptionContent.setText(estateModel.getDescription());
    }

    public String getGoogleMapThumbnail(Context ctx, String myAddress) throws IOException {
        GeoPoint location = getLocationFromAddress(ctx, myAddress);
        assert location != null;
        String URL = "http://maps.google.com/maps/api/staticmap?center=" + location.getLatitude() + "," + location.getLongitude() + "&zoom=15&size=200x200&sensor=false";
        String zoom = "13";
        String size = "600x300";
        String maptype = "roadmap";
        String markers = "=color:blue%7Clabel:S%7C40.702147,-74.015794";
        String key = "google_maps_key";
        String myUrl = URL;

        return URL;

    }

    private void openFragment(Fragment mFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container_modif, mFragment).commit();
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
