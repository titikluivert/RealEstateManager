package com.openclassrooms.realestatemanager.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.List;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "Real_Estate", foreignKeys = @ForeignKey(entity = RealEstateAgent.class, parentColumns = "id", childColumns = "realEstateAgentId",onDelete = CASCADE), indices = @Index("realEstateAgentId"))
public class RealEstateModel {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String type;
    private double price;
    private float surface;
    private int roomNumbers;
    private String description;
    private String address;
    private List<String> photos;
    private boolean status;
    private Date dateOfEntrance;
    private Date dateOfSale;
    private String poi;
    private long realEstateAgentId;


    @Ignore
    public RealEstateModel() {
    }


    public RealEstateModel(String type, double price, float surface, int roomNumbers, String description, String address, List<String> photos, boolean status, Date dateOfEntrance, Date dateOfSale, String poi, long realEstateAgentId) {
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
        this.poi = poi;
        this.realEstateAgentId = realEstateAgentId;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

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

    public String getPoi() {
        return poi;
    }

    public void setPoi(String poi) {
        this.poi = poi;
    }


    public long getRealEstateAgentId() {
        return realEstateAgentId;
    }

    public void setRealEstateAgentId(long realEstateAgentId) {
        this.realEstateAgentId = realEstateAgentId;
    }


}
