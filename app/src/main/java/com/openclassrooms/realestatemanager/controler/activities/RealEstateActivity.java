package com.openclassrooms.realestatemanager.controler.activities;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.RealEstateModelPref;
import com.openclassrooms.realestatemanager.model.UploadImage;
import com.openclassrooms.realestatemanager.utils.Utils;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.openclassrooms.realestatemanager.controler.activities.MainActivity.EXTRA_ADD_NEW_ESTATE;
import static com.openclassrooms.realestatemanager.utils.Utils.AGENT_ID;

public class RealEstateActivity extends BaseActivity {

    List<Uri> photoForFirebase = new ArrayList<>();

    private static final int STORAGE_PERMISSION_CODE = 100;
    private static final int PICK_IMAGES = 1;
    List<UploadImage> photos = new ArrayList<>();
    private static boolean photoIsUploaded = false;

    private RealEstateModelPref realEstateData;

    @BindView(R.id.realEstateType)
    Spinner realEstateType;

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

    Bitmap bitmap;

    @BindView(R.id.realEstatePOI)
    EditText realEstatePOI;

    @BindView(R.id.uploadConfirmation)
    TextView uploadConfirmation;


    //Sync with Firebase
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private String photoName;

    String[] temp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_estate);
        ButterKnife.bind(this);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        this.requestStoragePermission();
    }


    @OnClick(R.id.closeForm)
    void closeForm() {
        Intent intent = new Intent(RealEstateActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btnSend)
    void UploadRealEstate() {
        realEstateData = new RealEstateModelPref(
                realEstateType.getSelectedItem().toString().trim(),
                realEstatePrice.getText().toString().trim(),
                realEstateSurface.getText().toString().trim(),
                realEstateNumOfRooms.getText().toString().trim(),
                realEstateDescription.getText().toString().trim(),
                realEstatePOI.getText().toString().trim(),
                realEstateAddress.getText().toString().trim(),
                photos);

        if (!photoIsUploaded) {
            showToast("Photos were not updated successfully, please try again");
        } else {
            if (isDataValid(realEstateData)) {
                // Sync with Firebase
                for (int i = 0; i < photoForFirebase.size(); i++) {
                    Utils.uploadFile(this, photoForFirebase.get(i), mStorageRef, mDatabaseRef, "AgentID_" + AGENT_ID, realEstateData);
                }
                this.openActivity();
            } else {
                showToast("Please correctly all the data");
            }
        }
    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED) {

            return;
        }

        //And finally ask for the permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE
                    , Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED || grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Permission Denied.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean isDataValid(RealEstateModelPref estateData) {
        return !TextUtils.isEmpty(estateData.getType()) && !TextUtils.isEmpty(estateData.getPrice()) && !TextUtils.isEmpty(estateData.getSurface()) &&
                !TextUtils.isEmpty(estateData.getRoomNumbers()) && !TextUtils.isEmpty(estateData.getDescription()) && !TextUtils.isEmpty(estateData.getPoi())
                && !TextUtils.isEmpty(estateData.getAddress());
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
            Uri selectedImage = data.getData();
            if (clipData != null) {

                for (int i = 0; i < clipData.getItemCount(); i++) {
                    photoName = "";
                    ClipData.Item item = clipData.getItemAt(i);
                    Uri uri = item.getUri();
                    photoForFirebase.add(uri);
                    temp = uri.toString().split("/");

                    try {
                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    Utils.saveImageToInternalStorage(this, bitmap, temp[temp.length - 1].replace("%", ""));
                    photos.add(new UploadImage(photoName, String.valueOf(getFileStreamPath(temp[temp.length - 1].replace("%", "")))));

                    uploadConfirmation.setText(String.format("%d photos successfully uploaded", photos.size()));
                    uploadConfirmation.setTextColor(Color.GREEN);
                    photoIsUploaded = true;
                }

            } else {

                if (selectedImage != null) {
                    photoName = "";
                    photoForFirebase.add(selectedImage);
                    try {
                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    String[] tempSelectedImage = selectedImage.toString().split("/");
                    Utils.saveImageToInternalStorage(this, bitmap, tempSelectedImage[tempSelectedImage.length - 1].replace("%", ""));
                    photos.add(new UploadImage(photoName, String.valueOf(getFileStreamPath(tempSelectedImage[tempSelectedImage.length - 1].replace("%", "")))));
                    photoIsUploaded = true;
                    uploadConfirmation.setText(String.format("%d photos successfully uploaded", photos.size()));
                    uploadConfirmation.setTextColor(Color.GREEN);
                } else {

                    uploadConfirmation.setText(R.string.image_upload_unsuccessful);
                    uploadConfirmation.setTextColor(Color.RED);
                    photoIsUploaded = false;
                }
            }
        } else {
            uploadConfirmation.setText(R.string.image_upload_unsuccessful);
            uploadConfirmation.setTextColor(Color.RED);
            photoIsUploaded = false;
        }

    }

    private void openActivity() {

        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_ADD_NEW_ESTATE, new Gson().toJson(realEstateData));
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
