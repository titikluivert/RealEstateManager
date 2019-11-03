package com.openclassrooms.realestatemanager.controler.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.api.RealEstateHelper;
import com.openclassrooms.realestatemanager.model.RealEstateModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AddNewEstateFragment extends Fragment {


    @BindView(R.id.realEstateType)
    EditText realEstateType;

    @BindView(R.id.realEstatePrice)
    EditText realEstatePrice;

    @BindView(R.id.realEstateSurface)
    EditText realEstateSurface;

    @BindView(R.id.realEstateNumOfRooms)
    EditText realEstateNumOfRooms;

    @BindView(R.id.realEstateDescription)
    EditText realEstateDescription;

    public AddNewEstateFragment() {
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
    public void nextAdd() {

        RealEstateModel userInfo = new RealEstateModel(realEstateType.getText().toString(),

                realEstatePrice.getText().toString(),
                Float.parseFloat(realEstateSurface.getText().toString()),
                Integer.parseInt(realEstateNumOfRooms.getText().toString()),
                realEstateDescription.getText().toString(),
                null,
                null,
                true,
                null,
                null);
        writeNewRealEstate(realEstateType.getText().toString(), userInfo);


    }


    private void writeNewRealEstate(String realEstateType, RealEstateModel user) {
        RealEstateHelper.getRealEstateCollection().child(realEstateType).setValue(user);

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

    private void replaceFragment(Fragment someFragment) {

        assert getFragmentManager() != null;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
