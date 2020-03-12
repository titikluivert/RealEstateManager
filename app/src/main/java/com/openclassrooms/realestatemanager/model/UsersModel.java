package com.openclassrooms.realestatemanager.model;

public class UsersModel {

    private String urlPicture ;
    private String username ;
    private String uid ;
    private boolean isConnected ;
    private String email ;
    private String phoneNumber ;

    public UsersModel(String username,  String email, String phoneNumber)
    {
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;

    }


    public UsersModel(String urlPicture, String username, String uid, boolean isConnected, String email, String phoneNumber) {
        this.urlPicture = urlPicture;
        this.username = username;
        this.uid = uid;
        this.isConnected = isConnected;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }


    public String getUrlPicture() {
        return urlPicture;
    }

    public void setUrlPicture(String urlPicture) {
        this.urlPicture = urlPicture;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
