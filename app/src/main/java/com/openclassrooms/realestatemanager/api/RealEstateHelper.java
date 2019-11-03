package com.openclassrooms.realestatemanager.api;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.Query;

import static com.openclassrooms.realestatemanager.utils.mainUtils.REAL_ESTATE;

public class RealEstateHelper {

    public static DatabaseReference getRealEstateCollection() {
        return FirebaseDatabase.getInstance().getReference(REAL_ESTATE);
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
