package com.openclassrooms.realestatemanager.api;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.openclassrooms.realestatemanager.utils.mainUtils.REAL_ESTATE;

public class RealEstateHelper {

    public static DatabaseReference getRealEstateCollection() {
        return FirebaseDatabase.getInstance().getReference(REAL_ESTATE);
    }
}
