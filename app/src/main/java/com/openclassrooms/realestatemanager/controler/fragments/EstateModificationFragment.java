package com.openclassrooms.realestatemanager.controler.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controler.activities.SecondActivity;
import com.openclassrooms.realestatemanager.model.RealEstateModel;
import com.openclassrooms.realestatemanager.model.RealEstateModelPref;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class EstateModificationFragment extends BaseFragment {


    private RealEstateModel realEstateModel, realEstateModelUpdated;


    private static final int PICK_IMAGES = 1;
    List<String> photos = new ArrayList<>();
    private static boolean photoIsUploaded = false;

    private RealEstateModelPref userData;

    @BindView(R.id.realEstateTypeEdit)
    EditText realEstateTypeEdit;

    @BindView(R.id.realEstatePriceEdit)
    EditText realEstatePriceEdit;

    @BindView(R.id.realEstateAddressEdit)
    EditText realEstateAddressEdit;

    @BindView(R.id.realEstateSurfaceEdit)
    EditText realEstateSurfaceEdit;

    @BindView(R.id.realEstateNumOfRoomsEdit)
    EditText realEstateNumOfRoomsEdit;

    @BindView(R.id.realEstateDescriptionEdit)
    EditText realEstateDescriptionEdit;

    @BindView(R.id.dateOfSaleEdit)
    EditText dateOfSaleEdit;

    @BindView(R.id.statusEdit)
    EditText statusEdit;

    @BindView(R.id.realEstate_dateEntranceEdit)
    EditText realEstate_dateEntranceEdit;


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;

    public EstateModificationFragment() {
        // Required empty public constructor
    }

    public static EstateModificationFragment newInstance(String param1) {
        EstateModificationFragment fragment = new EstateModificationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            realEstateModel = new Gson().fromJson(mParam1, new TypeToken<RealEstateModel>() {
            }.getType());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_realestate_modif, container, false);
        ButterKnife.bind(this, view);
        this.updateUI(realEstateModel);

        return view;
    }

    @OnClick(R.id.closeFormEdit)
    void closeFormEdit() {
        this.openActivity();
    }

    @OnClick(R.id.btnSendEdit)
    void modifiedUI() {
        this.getUpdatedUI(realEstateModel);
        Intent i = new Intent(getActivity(), SecondActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.uploadPhotosEdit)
    void takeImg() {

    }

    private void openActivity() {
        Intent i = new Intent(getActivity(), SecondActivity.class);
        startActivity(i);
    }

    private void updateUI(RealEstateModel estateModel) {

        realEstateTypeEdit.setText(estateModel.getType() == null ? "" : estateModel.getType());
        realEstateSurfaceEdit.setText(estateModel.getSurface() == null ? "" : estateModel.getType());
        realEstatePriceEdit.setText(estateModel.getPrice() == null ? "" : estateModel.getType());
        realEstateAddressEdit.setText(estateModel.getAddress() == null ? "" : estateModel.getType());
        realEstateNumOfRoomsEdit.setText(estateModel.getRoomNumbers() == null ? "" : estateModel.getType());
        realEstateDescriptionEdit.setText(estateModel.getDescription() == null ? "" : estateModel.getType());
        dateOfSaleEdit.setText(estateModel.getDateOfSale() == null ? "" : estateModel.getType());
        realEstate_dateEntranceEdit.setText(estateModel.getDateOfEntrance() == null ? "" : estateModel.getType());
        statusEdit.setText(estateModel.getStatus() == null ? "" : estateModel.getType());
    }

    private void getUpdatedUI(RealEstateModel estateModel) {

        realEstateModelUpdated = new RealEstateModel(
                realEstateTypeEdit.getText().toString(),
                realEstatePriceEdit.getText().toString(),
                realEstateSurfaceEdit.getText().toString(),
                realEstateNumOfRoomsEdit.getText().toString(),
                realEstateDescriptionEdit.getText().toString(),
                realEstateAddressEdit.getText().toString(),
                estateModel.getPhotos(),
                dateOfSaleEdit.getText().toString(),
                realEstate_dateEntranceEdit.getText().toString(),
                statusEdit.getText().toString(),
                1
        );

    }

}

