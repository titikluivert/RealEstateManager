package com.openclassrooms.realestatemanager.controler.fragments;

import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controler.activities.MainActivity;
import com.openclassrooms.realestatemanager.model.RealEstateModelPref;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;


public class EstateFragment extends BaseFragment {


    public interface onSomeEventListener {
        public void someEvent(String data0, RealEstateModelPref data1);
    }

    onSomeEventListener someEventListener;


    private static final int PICK_IMAGES = 1;
    List<String> photos = new ArrayList<>();
    private static boolean photoIsUploaded = false;

    private RealEstateModelPref userData;

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

    @BindView(R.id.uploadPhotosfeedbck)
    TextView uploadPhotos;

    public EstateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_realestate, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.closeForm)
    void closeForm() {
        this.openActivity();
    }

    @OnClick(R.id.btnSend)
    void nextAdd() {

        userData = new RealEstateModelPref(
                realEstateType.getText().toString(),
                "$" + realEstatePrice.getText().toString(),
                realEstateSurface.getText().toString(),
                realEstateNumOfRooms.getText().toString(),
                realEstateDescription.getText().toString(),
                realEstateAddress.getText().toString());

        if (!photoIsUploaded) {
            showToast(getContext(), "Photos were not updated successfully, please try again");
        } else {
            someEventListener.someEvent(new Gson().toJson(photos), userData);
            this.openActivity();
        }
    }

    @OnClick(R.id.uploadPhotos)
    void takeImg() {
        Intent gallery = new Intent();
        gallery.setType("image/*");
        gallery.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(gallery, "Select various images"), PICK_IMAGES);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGES && resultCode == RESULT_OK && data != null) {
            ClipData clipData = data.getClipData();

            if (clipData != null) {

                for (int i = 0; i < clipData.getItemCount(); i++) {
                    ClipData.Item item = clipData.getItemAt(i);
                    Uri uri = item.getUri();
                    photos.add(uri.toString());
                }
                uploadPhotos.setText(String.format("%d photos were successful uploaded", photos.size()));
                uploadPhotos.setTextColor(Color.GREEN);
                photoIsUploaded = true;

            } else {

                uploadPhotos.setText("Upload was not successful");
                uploadPhotos.setTextColor(Color.RED);
                photoIsUploaded = false;
            }
        } else {
            uploadPhotos.setText("Upload was not successful");
            uploadPhotos.setTextColor(Color.RED);
            photoIsUploaded = false;
        }

    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = Objects.requireNonNull(getContext()).getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void openActivity() {
        Intent i = new Intent(getActivity(), MainActivity.class);
        Objects.requireNonNull(getActivity()).startActivity(i);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        someEventListener = (onSomeEventListener) context;
    }

}

