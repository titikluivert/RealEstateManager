package com.openclassrooms.realestatemanager.controler.fragments;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.api.RealEstateHelper;
import com.openclassrooms.realestatemanager.model.RealEstateModel;
import com.openclassrooms.realestatemanager.model.UsersModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import static com.openclassrooms.realestatemanager.api.RealEstateHelper.getCurrentUser;
import static com.openclassrooms.realestatemanager.api.RealEstateHelper.isCurrentUserLogged;


public abstract class BaseFragment extends Fragment {


    public BaseFragment() {
        // Required empty public constructor
    }
    // -----------------
    // CONFIGURATION
    // -----------------

    protected void replaceFragment(Fragment someFragment) {

        assert getFragmentManager() != null;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
       // transaction.replace(R.id.fragment_container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    protected void showToast(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
    }

    protected void writeNewRealEstate(String uid, RealEstateModel user) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        String format = simpleDateFormat.format(new Date());

        String myNewFormat = format.replace("-", "");
        RealEstateHelper.getRealEstateCollection().child(uid).child(myNewFormat).setValue(user);
    }

    private void deleteNewUser() {
        RealEstateHelper.getRealEstateCollection().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> keys = dataSnapshot.getChildren();
                for (DataSnapshot key : keys) {
                    Iterable<DataSnapshot> keys2 = key.getChildren();
                    for (DataSnapshot key0 : keys2) {
                       /* if (!nameRestaurant.equals(key.getKey()) || dataChanged) {
                            if (Objects.requireNonNull(key0.getKey()).equals(uid))
                                RealEstateHelper.getRestaurantCollection().child(Objects.requireNonNull(key.getKey())).child(uid).removeValue();
                        }*/
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }


        });
    }

    protected UsersModel getUserInFireStore() {

        if (getCurrentUser() != null) {

            return new UsersModel((Objects.requireNonNull(getCurrentUser()).getPhotoUrl() != null) ? getCurrentUser().getPhotoUrl().toString() : null,
                    getCurrentUser().getDisplayName(),
                    getCurrentUser().getUid(),
                    isCurrentUserLogged(),
                    getCurrentUser().getEmail(),
                    getCurrentUser().getPhoneNumber());

        } else {
            return null;
        }

    }

     /* private void updateRestaurantSelectedParams(RealEstateModel realEstateModel) {

        String urlPicture = (this.getCurrentUser().getPhotoUrl() != null) ? this.getCurrentUser().getPhotoUrl().toString() : null;
        String username = this.getCurrentUser().getDisplayName();
        String uid = this.getCurrentUser().getUid();
        boolean isConnected = this.isCurrentUserLogged();

        // Go4LunchUserHelper.createUser(uid, username, urlPicture, isConnected,realEstateModel).addOnFailureListener(this.onFailureListener());
    }*/
}

