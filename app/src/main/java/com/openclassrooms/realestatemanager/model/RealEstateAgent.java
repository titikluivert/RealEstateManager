package com.openclassrooms.realestatemanager.model;

public class RealEstateAgent {

    private String name;
    private String phoneNumber;
    private String eMail;

    public RealEstateAgent(String name, String phoneNumber, String eMail) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.eMail = eMail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }


}
