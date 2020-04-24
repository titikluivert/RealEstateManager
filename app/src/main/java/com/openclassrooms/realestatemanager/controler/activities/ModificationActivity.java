package com.openclassrooms.realestatemanager.controler.activities;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import com.google.gson.Gson;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.RealEstateModel;
import com.openclassrooms.realestatemanager.model.UploadImage;
import com.openclassrooms.realestatemanager.utils.Utils;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.openclassrooms.realestatemanager.controler.activities.SecondActivity.EXTRA_MODIFY_REAL_ESTATE;

public class ModificationActivity extends BaseActivity {

    public static final String EXTRA_ID_CURRENT_ESTATE = "com.openclassrooms.realestatemanager.controler.activities.EXTRA_ID_CURRENT_ESTATE";
    public static final String EXTRA_RESULT_MODIFICATION = "com.openclassrooms.realestatemanager.controler.activities.EXTRA_RESULT_MODIFICATION";

    private RealEstateModel realEstateModel, realEstateModelUpdated;
    private boolean photoIsUploaded = false;
    private static final int PICK_IMAGES_MODIFICATION = 2;
    private List<UploadImage> photos_modification = new ArrayList<>();
    private Bitmap bitmap;

    @BindView(R.id.realEstateTypeEdit)
    Spinner realEstateTypeEdit;

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

    @BindView(R.id.realEstate_dateEntranceEdit)
    EditText realEstate_dateEntranceEdit;

    @BindView(R.id.statusEdit)
    EditText statusEdit;

    @BindView(R.id.realEstate_dateOfSaleEdit)
    EditText realEstate_dateOfSaleEdit;

    @BindView(R.id.uploadConfirmationEdit)
    TextView uploadConfirmationEdit;

    private String photoName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modification);
        ButterKnife.bind(this);
        this.configureToolbar();
        //take over the existent photos
        realEstateModel = restoreRealEstateModel(getIntent().getStringExtra(EXTRA_MODIFY_REAL_ESTATE));
        photos_modification = realEstateModel.getPhotos();
        this.updateUI(realEstateModel);
    }

    @OnClick(R.id.btnSendEdit)
    void modifiedUI() {
        this.getUpdatedUI();
        this.finishModification(new Gson().toJson(realEstateModelUpdated));
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
                    photoName = "";
                    ClipData.Item item = clipData.getItemAt(i);
                    Uri uri = item.getUri();
                    String[] temp = uri.toString().split("/");

                    try {
                        bitmap = BitmapFactory.decodeStream(Objects.requireNonNull(getContentResolver().openInputStream(uri)));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    Utils.saveImageToInternalStorage(this, bitmap, temp[temp.length - 1].replace("%", ""));
                    photos_modification.add(new UploadImage(photoName, String.valueOf(getFileStreamPath(temp[temp.length - 1].replace("%", "")))));
                    uploadConfirmationEdit.setText(String.format("%d photos successfully uploaded", photos_modification.size()));
                    uploadConfirmationEdit.setTextColor(Color.GREEN);
                    photoIsUploaded = true;
                }
            } else {

                if (selectedImage != null) {
                    photoName = "";
                    try {
                        bitmap = BitmapFactory.decodeStream(Objects.requireNonNull(getContentResolver().openInputStream(selectedImage)));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    String[] tempSelectedImage = selectedImage.toString().split("/");
                    Utils.saveImageToInternalStorage(this, bitmap, tempSelectedImage[tempSelectedImage.length - 1].replace("%", ""));
                    photos_modification.add(new UploadImage(photoName, String.valueOf(getFileStreamPath(tempSelectedImage[tempSelectedImage.length - 1].replace("%", "")))));
                    uploadConfirmationEdit.setText(String.format("%d photos successfully uploaded", photos_modification.size()));
                    uploadConfirmationEdit.setTextColor(Color.GREEN);
                    photoIsUploaded = true;
                } else {

                    uploadConfirmationEdit.setText(R.string.image_upload_unsuccessful);
                    uploadConfirmationEdit.setTextColor(Color.RED);
                    photoIsUploaded = false;
                }
            }
        } else {
             uploadConfirmationEdit.setText(R.string.image_upload_unsuccessful);
             uploadConfirmationEdit.setTextColor(Color.RED);
            photoIsUploaded = false;
        }

    }

    private void updateUI(RealEstateModel estateModel) {

        int spinnerPosition = 0;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.places, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        realEstateTypeEdit.setAdapter(adapter);
        if (estateModel.getType() != null) {
            spinnerPosition = adapter.getPosition(estateModel.getType());
        }
        realEstateTypeEdit.setSelection(spinnerPosition);
        realEstateSurfaceEdit.setText(String.valueOf(estateModel.getSurface()));
        realEstatePriceEdit.setText(String.valueOf(estateModel.getPrice()));
        realEstateAddressEdit.setText(estateModel.getAddress() == null ? "" : estateModel.getAddress());
        realEstateNumOfRoomsEdit.setText(String.valueOf(estateModel.getRoomNumbers()));
        realEstateDescriptionEdit.setText(estateModel.getDescription() == null ? "" : estateModel.getDescription());
        realEstate_dateOfSaleEdit.setText(estateModel.getDateOfSale() == null ? "" : Utils.getConvertDate(Utils.DateConverters.dateToTimestamp(estateModel.getDateOfSale())));
        realEstate_dateEntranceEdit.setText(Utils.getConvertDate(Utils.DateConverters.dateToTimestamp(estateModel.getDateOfEntrance())));
        realEstatePOIEdit.setText(estateModel.getPoi() == null ? "" : estateModel.getPoi());
        statusEdit.setText(estateModel.getStatus() ? "Available" : "Sold");

    }

    private void getUpdatedUI() {

        this.realEstateModelUpdated = new RealEstateModel(
                realEstateTypeEdit.getSelectedItem().toString().trim(),
                Double.parseDouble(realEstatePriceEdit.getText().toString()),
                Float.parseFloat(realEstateSurfaceEdit.getText().toString()),
                Integer.parseInt(realEstateNumOfRoomsEdit.getText().toString()),
                realEstateDescriptionEdit.getText().toString(),
                realEstateAddressEdit.getText().toString(),
                photos_modification,
                photos_modification.size(),
                statusEdit.getText().toString().equals("Available"),
                Utils.DateConverters.fromTimestamp(realEstate_dateEntranceEdit.getText().toString()),
                Utils.DateConverters.fromTimestamp(realEstate_dateOfSaleEdit.getText().toString()),
                realEstatePOIEdit.getText().toString(),
                1
        );


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void finishModification(String param_config) {

        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_RESULT_MODIFICATION, param_config);
        resultIntent.putExtra(EXTRA_ID_CURRENT_ESTATE, realEstateModel.getId());
        setResult(RESULT_OK, resultIntent);
        finish();

    }

    private void configureToolbar() {
        // Get the toolbar view inside the activity layout
        Toolbar toolbar = findViewById(R.id.toolbar);
        // Sets the Toolbar
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        assert ab != null;
        ab.setTitle("Modify Real Estate");
        Objects.requireNonNull(ab).setDisplayHomeAsUpEnabled(true);
    }

}
