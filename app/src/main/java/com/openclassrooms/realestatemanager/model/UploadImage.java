package com.openclassrooms.realestatemanager.model;

public class UploadImage {
    private String mName;
    private String mImageUrl;

    public UploadImage() {
        //empty constructor needed
    }

    public UploadImage(String name, String imageUrl) {
        if (name == null || name.trim().equals("")) {
            name = "No description";
        }

        mName = name;
        mImageUrl = imageUrl;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }
}