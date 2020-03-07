package com.openclassrooms.realestatemanager.model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class RealEstateModelPref {


    private String type;
    private String price;
    private String surface;
    private String roomNumbers;
    private String description;
    private String address;


    public RealEstateModelPref(String type, String price, String surface, String roomNumbers, String description,  String address) {
        this.type = type;
        this.price = price;
        this.surface = surface;
        this.roomNumbers = roomNumbers;
        this.description = description;
        this.address = address;


    }


    public RealEstateModelPref(String type, String price, String surface, String numOfRooms, String description, String s, boolean b, String dateOfEntrance, String s1, Object o) {

    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSurface() {
        return surface;
    }

    public void setSurface(String surface) {
        this.surface = surface;
    }

    public String getRoomNumbers() {
        return roomNumbers;
    }

    public void setRoomNumbers(String roomNumbers) {
        this.roomNumbers = roomNumbers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



}
