/*
package com.openclassrooms.realestatemanager.controler.fragments;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.lifecycle.ViewModelProviders;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controler.activities.SecondActivity;
import com.openclassrooms.realestatemanager.model.RealEstateModel;
import com.openclassrooms.realestatemanager.model.RealEstateModelPref;
import com.openclassrooms.realestatemanager.utils.mainUtils;
import com.openclassrooms.realestatemanager.viewmodel.RealEstateViewModel;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;


public class EstateModificationFragment extends BaseFragment {


    private RealEstateModel realEstateModel, realEstateModelUpdated;

    private static final int PICK_IMAGES_MODIFICATION = 2;
    private List<String> photos_modification = new ArrayList<>();
    private static boolean photoIsUploaded = false;
    private Bitmap bitmap;

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

    @BindView(R.id.realEstatePOIEdit)
    EditText realEstatePOIEdit;

    @BindView(R.id.realEstateDescriptionEdit)
    EditText realEstateDescriptionEdit;

    @BindView(R.id.dateOfSaleEdit)
    EditText dateOfSaleEdit;

    @BindView(R.id.statusEdit)
    EditText statusEdit;

    @BindView(R.id.realEstate_dateEntranceEdit)
    EditText realEstate_dateEntranceEdit;

    @BindView(R.id.btnSendEdit)
    Button btnSendEdit;

    private ShareViewModel shareViewModel;

    // FOR DESIGN
    private RealEstateViewModel realEstateViewModel;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
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

        // view model
        shareViewModel = ViewModelProviders.of(this).get(ShareViewModel.class);
        shareViewModel.init();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            realEstateModel = new Gson().fromJson(mParam1, new TypeToken<RealEstateModel>() {
            }.getType());
            //take over the existent photos
            photos_modification = realEstateModel.getPhotos();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_realestate_modif, container, false);
        ButterKnife.bind(this, view);
        // view model
        realEstateViewModel = ViewModelProviders.of(this).get(RealEstateViewModel.class);
        this.updateUI(realEstateModel);
        return view;
    }

    @OnClick(R.id.closeFormEdit)
    void closeFormEdit() {
        shareViewModel.setRealEstateModelViewModel(realEstateModel);
        //saveRealEstateModel(getContext(), );
        this.openActivity();
    }

    @OnClick(R.id.btnSendEdit)
    void modifiedUI() {
        this.getUpdatedUI();
        //saveRealEstateModel(getContext(), this.realEstateModelUpdated);
        this.openActivity();
    }

    @OnClick(R.id.addImageEdit)
    void takeImg() {
        Intent gallery = new Intent();
        gallery.setType("image/*");
        gallery.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(gallery, "Select various images"), PICK_IMAGES_MODIFICATION);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGES_MODIFICATION && resultCode == RESULT_OK && data != null) {
            ClipData clipData = data.getClipData();
            Uri selectedImage = data.getData();

            if (clipData != null) {

                for (int i = 0; i < clipData.getItemCount(); i++) {

                    ClipData.Item item = clipData.getItemAt(i);
                    Uri uri = item.getUri();
                    String[] temp = uri.toString().split("/");

                    try {
                        bitmap = BitmapFactory.decodeStream(Objects.requireNonNull(getContext()).getContentResolver().openInputStream(uri));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    mainUtils.saveImageToInternalStorage(getContext(), bitmap, temp[temp.length - 1].replace("%", ""));
                    //user.setThumbnail();
                    photos_modification.add(String.valueOf(getContext().getFileStreamPath(temp[temp.length - 1].replace("%", ""))));

                    //uploadPhotos.setText(String.format("%d photos were successful uploaded", photos.size()));
                    // uploadPhotos.setTextColor(Color.GREEN);
                    photoIsUploaded = true;
                }
            } else {

                if (selectedImage != null) {
                    try {
                        bitmap = BitmapFactory.decodeStream(Objects.requireNonNull(getContext()).getContentResolver().openInputStream(selectedImage));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    String[] tempSelectedImage = selectedImage.toString().split("/");
                    mainUtils.saveImageToInternalStorage(getContext(), bitmap, tempSelectedImage[tempSelectedImage.length - 1].replace("%", ""));
                    photos_modification.add(String.valueOf(getContext().getFileStreamPath(tempSelectedImage[tempSelectedImage.length - 1].replace("%", ""))));
                    // uploadPhotos.setText(String.format("%d photos were successful uploaded", photos.size()));
                    //  uploadPhotos.setTextColor(Color.GREEN);
                    photoIsUploaded = true;
                } else {

                    // uploadPhotos.setText("Upload was not successful");
                    // uploadPhotos.setTextColor(Color.RED);
                    photoIsUploaded = false;
                }
            }
        } else {
            // uploadPhotos.setText("Upload was not successful");
            // uploadPhotos.setTextColor(Color.RED);
            photoIsUploaded = false;
        }

    }

    private void openActivity() {
        Intent i = new Intent(getActivity(), SecondActivity.class);
        startActivity(i);
    }

    private void updateUI(RealEstateModel estateModel) {

        realEstateTypeEdit.setText(estateModel.getType() == null ? "" : estateModel.getType());
        realEstateSurfaceEdit.setText(String.valueOf(estateModel.getSurface()));
        realEstatePriceEdit.setText(String.valueOf(estateModel.getPrice()));
        realEstateAddressEdit.setText(estateModel.getAddress() == null ? "" : estateModel.getAddress());
        realEstateNumOfRoomsEdit.setText(String.valueOf(estateModel.getRoomNumbers()));
        realEstateDescriptionEdit.setText(estateModel.getDescription() == null ? "" : estateModel.getDescription());
        dateOfSaleEdit.setText(mainUtils.changeStringToDate(String.valueOf(estateModel.getDateOfSale())));
        realEstate_dateEntranceEdit.setText(mainUtils.changeStringToDate(String.valueOf(estateModel.getDateOfEntrance())));
        realEstatePOIEdit.setText(estateModel.getPoi() == null ? "" : estateModel.getPoi());
        statusEdit.setText(String.valueOf(estateModel.getStatus()));

    }

    private void getUpdatedUI() {

        this.realEstateModelUpdated = new RealEstateModel(
                realEstateTypeEdit.getText().toString(),
                Double.parseDouble(realEstatePriceEdit.getText().toString()),
                Float.parseFloat(realEstateSurfaceEdit.getText().toString()),
                Integer.parseInt(realEstateNumOfRoomsEdit.getText().toString()),
                realEstateDescriptionEdit.getText().toString(),
                realEstateAddressEdit.getText().toString(),
                photos_modification,
                statusEdit.getText().toString().equals("Available"),
                mainUtils.DateConverters.fromTimestamp(realEstate_dateEntranceEdit.getText().toString()),
                mainUtils.DateConverters.fromTimestamp(dateOfSaleEdit.getText().toString()),
                realEstatePOIEdit.getText().toString(),
                1
        );

        shareViewModel.setRealEstateModelViewModel(realEstateModelUpdated);
        //SaveMyRealEstateDatabase.getInstance(getContext()).realEstateModelDao().updateRealEstateModel(realEstateModelUpdated);
    }


}

*/
