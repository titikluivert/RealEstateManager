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


public class AddNewEstateFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    @BindView(R.id.realEstateType)
    EditText realEstateType;

    @BindView(R.id.realEstatePrice)
    EditText realEstatePrice ;

    @BindView(R.id.realEstateSurface)
    EditText realEstateSurface ;

    @BindView(R.id.realEstateNumOfRooms)
    EditText realEstateNumOfRooms ;

    @BindView(R.id.realEstateDescription)
    EditText realEstateDescription ;

    public AddNewEstateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddNewEstateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddNewEstateFragment newInstance(String param1, String param2) {
        AddNewEstateFragment fragment = new AddNewEstateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_new_real_estate_, container, false);
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
