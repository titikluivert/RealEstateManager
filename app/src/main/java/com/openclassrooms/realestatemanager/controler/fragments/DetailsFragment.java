package com.openclassrooms.realestatemanager.controler.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controler.activities.ModificationActivity;
import com.openclassrooms.realestatemanager.model.RealEstateModel;
import com.openclassrooms.realestatemanager.model.UploadImage;
import com.openclassrooms.realestatemanager.utils.Utils;
import com.openclassrooms.realestatemanager.view.PhotoViewAdapter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;
import static com.openclassrooms.realestatemanager.utils.Utils.getLocationFromAddress;


public class DetailsFragment extends Fragment {

    static final int REQUEST_MODIFY_REAL_ESTATE_CODE = 15;
    public static final String EXTRA_MODIFY_REAL_ESTATE = "com.openclassrooms.realestatemanager.controler.activities.EXTRA_MODIFY_REAL_ESTATE";

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "param";

    public interface OnDataRealEstatePass {
        void onDataRealEstatePass(Intent data);

        void onDataRealEstateSecond(RealEstateModel data);

    }

    OnDataRealEstatePass dataPasser;

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

    @BindView(R.id.ModifyRealEstate_fab)
    FloatingActionButton modifyRealEstate_fab;

    // FOR DESIGN
    private RealEstateModel realEstateModel;

    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment newInstance(String param1) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mViewSecond = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this, mViewSecond);
        if (getArguments() != null) {
            this.realEstateModel = restoreRealEstateModelTablet(getArguments().getString(ARG_PARAM));

            assert realEstateModel != null;
            List<UploadImage> items = realEstateModel.getPhotos();
            PhotoViewAdapter adapter = new PhotoViewAdapter(getContext(), items);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setAdapter(adapter);
            // coming from main activity

            adapter.notifyDataSetChanged();

            try {
                Glide.with(this).load(getGoogleMapUrl(getActivity(), realEstateModel.getAddress())).into(mapImg);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.displayDetails(realEstateModel);

            adapter.setOnItemLongClickListener(photos -> {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
                builder1.setMessage("Do you want to delete this image ?");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Yes",
                        (dialog, id) -> {
                            items.remove(photos);
                            realEstateModel.setPhotos(items);
                            passDataRealEstate(realEstateModel);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
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
                            passDataRealEstate(realEstateModel);
                            adapter.notifyDataSetChanged();

                            dialog.cancel();
                        });

                AlertDialog alert11 = builder.create();
                alert11.show();

            });

            modifyRealEstate_fab.setOnClickListener(view -> ModifyRealEstate());

        }

        return mViewSecond;
    }


    private void ModifyRealEstate() {
        String estateModelString = new Gson().toJson(this.realEstateModel);
        Intent myIntent = new Intent(getActivity(), ModificationActivity.class);
        myIntent.putExtra(EXTRA_MODIFY_REAL_ESTATE, estateModelString);
        this.startActivityForResult(myIntent, REQUEST_MODIFY_REAL_ESTATE_CODE);

    }

    private void displayDetails(RealEstateModel estateModel) {
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

    private String getGoogleMapUrl(Context ctx, String myAddress) throws IOException {
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

    private RealEstateModel restoreRealEstateModelTablet(String s) {
        return new Gson().fromJson(s, new TypeToken<RealEstateModel>() {
        }.getType());
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_MODIFY_REAL_ESTATE_CODE) {
            if (resultCode == RESULT_OK) {
                passData(data);
            }
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataPasser = (OnDataRealEstatePass) context;
    }


    public void passData(Intent data) {
        dataPasser.onDataRealEstatePass(data);
    }

    public void passDataRealEstate(RealEstateModel data) {
        dataPasser.onDataRealEstateSecond(data);
    }

}
