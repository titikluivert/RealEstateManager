package com.openclassrooms.realestatemanager.api;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.openclassrooms.realestatemanager.utils.mainUtils.REAL_ESTATE;

public class RealEstateHelper {


    public static DatabaseReference getRealEstateCollection() {
        return FirebaseDatabase.getInstance().getReference(REAL_ESTATE);
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

/*    public static Query getAllMessageForChat(String uIdBetween2Chats) {
        return ChatHelper.getChatCollection()
                .document(mainUtils.COLLECTION_NAME)
                .collection(uIdBetween2Chats)
                .orderBy("dateCreated")
                .limit(50);
    }

    public static void createMessageForChat(String textMessage, Go4LunchUsers userSender, String uIdBetween2Chats) {

        // 1 - Create the Message object
        Message message = new Message(textMessage, userSender, uIdBetween2Chats);

        // 2 - Store Message to Firestore
        ChatHelper.getChatCollection()
                .document(mainUtils.COLLECTION_NAME)
                .collection(uIdBetween2Chats)
                .add(message);
    }*/

}
