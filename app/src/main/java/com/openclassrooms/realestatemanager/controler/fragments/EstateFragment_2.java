package com.openclassrooms.realestatemanager.controler.fragments;

import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ProgressBar;
import android.os.Handler;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.UploadImage;
import com.openclassrooms.realestatemanager.model.UsersModel;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static com.openclassrooms.realestatemanager.utils.mainUtils.REAL_ESTATE;

public class EstateFragment_2 extends BaseFragment {

    private static final int PICK_IMAGES = 1;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    public EstateFragment_2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        View view = inflater.inflate(R.layout.estate_fragment_layout_2, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btnSend)
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

                UsersModel usersModel = getUserInFireStore();

                for (int i = 0; i < clipData.getItemCount(); i++) {
                    ClipData.Item item = clipData.getItemAt(i);
                    Uri uri = item.getUri();
                    uploadFile(uri, usersModel.getUid());
                    Log.e("Imgs", uri.toString());
                }

            } else {
                this.showToast(getContext(), "Please select at least two image");
            }
        }

        this.replaceFragment(new HomeFragment());
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = Objects.requireNonNull(getContext()).getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile( Uri mImageUri, String uid) {
        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(uid).child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            fileReference.putFile(mImageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        Handler handler = new Handler();
                        handler.postDelayed(() -> mProgressBar.setProgress(0), 500);

                        showToast(getContext(), "Upload successful");
                        UploadImage upload = new UploadImage("Image", taskSnapshot.getStorage().getDownloadUrl().toString());
                        String uploadId = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(REAL_ESTATE).child(uid).child(Objects.requireNonNull(uploadId)).setValue(upload);
                    })
                    .addOnFailureListener(e -> showToast(getContext(), e.getMessage()))
                    .addOnProgressListener(taskSnapshot -> {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        mProgressBar.setProgress((int) progress);
                    });
        } else {
            showToast(getContext(), "No file selected");
        }
    }
}




    /*
    @OnClick (R.id.addNewRealEstate_fab)
    public void addNewRealEstate(){
        if(callback!= null)
            callback.newRealEstateAdd();

        replaceFragment(new EstateFragment_2());
    }

        RealEstateModel userInfo = new RealEstateModel(
                true,
                null,
                null);


        writeNewRealEstate(realEstateType.getText().toString(), userInfo);
 */
   /* private void updateRestaurantSelectedParams(RealEstateModel realEstateModel) {

        String urlPicture = (this.getCurrentUser().getPhotoUrl() != null) ? this.getCurrentUser().getPhotoUrl().toString() : null;
        String username = this.getCurrentUser().getDisplayName();
        String uid = this.getCurrentUser().getUid();
        boolean isConnected = this.isCurrentUserLogged();

        // Go4LunchUserHelper.createUser(uid, username, urlPicture, isConnected,realEstateModel).addOnFailureListener(this.onFailureListener());
    }*/

    /*    private void alertDialogAbout()



        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Next", new DialogInterface.OnClickListener()
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String type = realEstateType.getText().toString();
                        String price = realEstatePrice.getText().toString();
                        String surface = realEstateSurface.getText().toString();
                        String numOfRooms = realEstateNumOfRooms.getText().toString();
                        String description = realEstateDescription.getText().toString();
                        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                        String dateOfEntrance = df.format(Calendar.getInstance().getTime());*/

