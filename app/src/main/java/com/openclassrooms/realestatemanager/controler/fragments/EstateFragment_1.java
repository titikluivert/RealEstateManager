package com.openclassrooms.realestatemanager.controler.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.RealEstateModel;
import com.openclassrooms.realestatemanager.model.UsersModel;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class EstateFragment_1 extends BaseFragment {


    @BindView(R.id.realEstateType)
    EditText realEstateType;

    @BindView(R.id.realEstatePrice)
    EditText realEstatePrice;

    @BindView(R.id.realEstateAddress)
    EditText realEstateAddress;

    @BindView(R.id.realEstateSurface)
    EditText realEstateSurface;

    @BindView(R.id.realEstateNumOfRooms)
    EditText realEstateNumOfRooms;

    @BindView(R.id.realEstateDescription)
    EditText realEstateDescription;

    public EstateFragment_1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_new_realestate, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btnSend)
    void nextAdd() {

        UsersModel usersModel = getUserInFireStore();
        RealEstateModel realEstateModel = new RealEstateModel(realEstateType.getText().toString(), realEstatePrice.getText().toString(),
                realEstateSurface.getText().toString(),realEstateNumOfRooms.getText().toString(),realEstateDescription.getText().toString(),
                realEstateAddress.getText().toString());

        writeNewRealEstate(usersModel.getUid(), realEstateModel);
        this.replaceFragment(new EstateFragment_2());

    }
}
