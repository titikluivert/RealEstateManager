package com.openclassrooms.realestatemanager.model;

import java.util.List;

public class RealEstateModelPref {

    private String type;
    private String price;
    private String surface;
    private String roomNumbers;
    private String description;
    private String poi;
    private String address;
    private List<UploadImage> photos;


    public RealEstateModelPref(String type, String price, String surface, String roomNumbers, String description, String poi, String address, List<UploadImage> photos) {
        this.type = type;
        this.price = price;
        this.surface = surface;
        this.roomNumbers = roomNumbers;
        this.description = description;
        this.poi = poi;
        this.address = address;
        this.photos = photos;


    }


    public RealEstateModelPref() {

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

    public String getPoi() {
        return poi;
    }

    public void setPoi(String poi) {
        this.poi = poi;
    }

    public List<UploadImage> getPhotos() {
        return photos;
    }

    public void setPhotos(List<UploadImage> photos) {
        this.photos = photos;
    }
}
