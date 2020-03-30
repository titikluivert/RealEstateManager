package com.openclassrooms.realestatemanager.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "Real_Estate", foreignKeys = @ForeignKey(entity = RealEstateAgent.class, parentColumns = "id", childColumns = "realEstateAgentId"), indices = @Index("realEstateAgentId"))
public class RealEstateModel {


    @PrimaryKey(autoGenerate = true)
    private long id;

    private String type;
    private String price;
    private String surface;
    private String roomNumbers;
    private String description;
    private String address;
    private List<String> photos;
    private String status;
    private String dateOfEntrance;
    private String dateOfSale;
    private long realEstateAgentId;


    @Ignore
    public RealEstateModel() {
    }

    public RealEstateModel(String type, String price, String surface, String roomNumbers, String description, String address, List<String> photos, String status, String dateOfEntrance, String dateOfSale, long realEstateAgentId) {
        this.type = type;
        this.price = price;
        this.surface = surface;
        this.roomNumbers = roomNumbers;
        this.description = description;
        this.address = address;
        this.photos = photos;
        this.status = status;
        this.dateOfEntrance = dateOfEntrance;
        this.dateOfSale = dateOfSale;
        this.realEstateAgentId = realEstateAgentId;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public long getRealEstateAgentId() {
        return realEstateAgentId;
    }

    public void setRealEstateAgentId(long realEstateAgentId) {
        this.realEstateAgentId = realEstateAgentId;
    }

}
