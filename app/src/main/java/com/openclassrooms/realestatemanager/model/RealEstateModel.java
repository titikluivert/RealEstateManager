package com.openclassrooms.realestatemanager.model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class RealEstateModel {


    private String type;
    private String price;
    private String surface;
    private String roomNumbers;
    private String description;
    private String address;
   // private String status;
    //private Date dateOfEntrance;
   // private Date dateOfSale;


  /**  public RealEstateModel(String type, String price, String surface, String roomNumbers, String description, String address, String status) {
        this.type = type;
        this.price = price;
        this.surface = surface;
        this.roomNumbers = roomNumbers;
        this.description = description;
        this.address = address;
        this.status = status;
    }*/

    public RealEstateModel(String type, String price, String surface, String roomNumbers, String description, String address) {
        this.type = type;
        this.price = price;
        this.surface = surface;
        this.roomNumbers = roomNumbers;
        this.description = description;
        this.address = address;
    }


    public RealEstateModel(String type, String price, String surface, String numOfRooms, String description, String s, String b, String dateOfEntrance, String s1, String  o) {

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

/**
 public String isStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @ServerTimestamp
    public Date getDateOfEntrance() {
        return dateOfEntrance;
    }


    public void setDateOfEntrance(Date dateOfEntrance) {
        this.dateOfEntrance = dateOfEntrance;
    }

    public Date getDateOfSale() {
        return dateOfSale;
    }

    public void setDateOfSale(Date dateOfSale) {
        this.dateOfSale = dateOfSale;
    }
*/
}
