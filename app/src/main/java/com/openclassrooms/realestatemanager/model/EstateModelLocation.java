package com.openclassrooms.realestatemanager.model;


import java.util.List;

public class EstateModelLocation {

    private RealEstateModel realEstateModelList ;
    private double lat;
    private double lon;

    public EstateModelLocation() {

    }

    public EstateModelLocation(RealEstateModel realEstateModelList, double lat, double lon) {
        this.realEstateModelList = realEstateModelList;
        this.lat = lat;
        this.lon = lon;
    }

    public RealEstateModel getRealEstateModelList() {
        return realEstateModelList;
    }

    public void setRealEstateModelList(RealEstateModel realEstateModelList) {
        this.realEstateModelList = realEstateModelList;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
