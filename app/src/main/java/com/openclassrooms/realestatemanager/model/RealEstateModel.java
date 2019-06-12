package com.openclassrooms.realestatemanager.model;

public class RealEstateModel {


    private String type;
    private String price;
    private float surface;
    private int roomNumbers;
    private String description;
    private String[] photos;
    private String address;
    private boolean status;
    private String dateOfEntrance;
    private String dateOfSale;
    private RealEstateAgent realEstateAgent;


    public RealEstateModel(String type, String price, float surface, int roomNumbers, String description, String[] photos, String address, boolean status, String dateOfEntrance, String dateOfSale, RealEstateAgent realEstateAgent) {
        this.type = type;
        this.price = price;
        this.surface = surface;
        this.roomNumbers = roomNumbers;
        this.description = description;
        this.photos = photos;
        this.address = address;
        this.status = status;
        this.dateOfEntrance = dateOfEntrance;
        this.dateOfSale = dateOfSale;
        this.realEstateAgent = realEstateAgent;
    }




    public RealEstateModel(String type, String price, String surface, String numOfRooms, String description, String s, boolean b, String dateOfEntrance, String s1, Object o) {

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

    public float getSurface() {
        return surface;
    }

    public void setSurface(float surface) {
        this.surface = surface;
    }

    public int getRoomNumbers() {
        return roomNumbers;
    }

    public void setRoomNumbers(int roomNumbers) {
        this.roomNumbers = roomNumbers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getPhotos() {
        return photos;
    }

    public void setPhotos(String[] photos) {
        this.photos = photos;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDateOfEntrance() {
        return dateOfEntrance;
    }

    public void setDateOfEntrance(String dateOfEntrance) {
        this.dateOfEntrance = dateOfEntrance;
    }

    public String getDateOfSale() {
        return dateOfSale;
    }

    public void setDateOfSale(String dateOfSale) {
        this.dateOfSale = dateOfSale;
    }

    public RealEstateAgent getRealEstateAgent() {
        return realEstateAgent;
    }

    public void setRealEstateAgent(RealEstateAgent realEstateAgent) {
        this.realEstateAgent = realEstateAgent;
    }
}
