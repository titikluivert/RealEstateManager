package com.openclassrooms.realestatemanager.api;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.openclassrooms.realestatemanager.model.RealEstateModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.openclassrooms.realestatemanager.utils.mainUtils.AGENT_ID;
import static com.openclassrooms.realestatemanager.utils.mainUtils.REAL_ESTATE;

public class RealEstateHelper {

    public static DatabaseReference getRealEstateCollection() {
        return FirebaseDatabase.getInstance().getReference(REAL_ESTATE);
    }

    public static void writeNewRealEstate(RealEstateModel user) {
        RealEstateHelper.getRealEstateCollection().child("AgentID_" + AGENT_ID).child(user.getAddress()).setValue(user);
    }

    // --- UPDATE ---

    public static void updateRealEstateChildren(RealEstateModel realEstateModel) {

        final boolean[] toBeUpdate = {true};

        Map<String, Object> updates = new HashMap<>();
        updates.put("type", realEstateModel.getType());
        updates.put("price", realEstateModel.getPrice());
        updates.put("surface", realEstateModel.getSurface());
        updates.put("roomNumbers", realEstateModel.getRoomNumbers());
        updates.put("description", realEstateModel.getDescription());
        updates.put("address", realEstateModel.getAddress());
        updates.put("photos", realEstateModel.getPhotos());
        updates.put("status", realEstateModel.getStatus());
        updates.put("dateOfEntrance", realEstateModel.getDateOfEntrance());
        updates.put("dateOfSale", realEstateModel.getDateOfSale());
        updates.put("poi", realEstateModel.getPoi());
        updates.put("realEstateAgentId", realEstateModel.getRealEstateAgentId());


        DatabaseReference reference = RealEstateHelper.getRealEstateCollection().child("AgentID_" + AGENT_ID);
        DatabaseReference ref = reference.child(realEstateModel.getAddress());

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot datas : dataSnapshot.getChildren()) {
                    if (Objects.equals(datas.getKey(), realEstateModel.getAddress())) {
                        // adresse is the same, Adresse were found data need to change
                        toBeUpdate[0] = false;
                        ref.updateChildren(updates);
                        break;
                    }
                }
                if (toBeUpdate[0]) {
                    RealEstateHelper.writeNewRealEstate(realEstateModel);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public static FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public static Boolean isCurrentUserLogged() {
        return (getCurrentUser() != null);
    }

    public static OnFailureListener onFailureListener() {
        return new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //showToast(getString(R.string.error_unknown_error));
            }
        };
    }
}
