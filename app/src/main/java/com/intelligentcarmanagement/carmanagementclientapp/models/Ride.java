package com.intelligentcarmanagement.carmanagementclientapp.models;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class Ride implements Serializable {
    private int driverId;
    private String pickUpAddress;
    private String destinationAddress;
    private String pickUpLat;
    private String pickUpLng;
    private String destinationLat;
    private String destinationLng;

    public Ride() {
    }

    public Ride(String pickUpAddress, String destinationAddress, String pickUpLat, String pickUpLng, String destinationLat, String destinationLng) {
        this.pickUpAddress = pickUpAddress;
        this.destinationAddress = destinationAddress;
        this.pickUpLat = pickUpLat;
        this.pickUpLng = pickUpLng;
        this.destinationLat = destinationLat;
        this.destinationLng = destinationLng;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getPickUpAddress() {
        return pickUpAddress;
    }

    public void setPickUpAddress(String pickUpAddress) {
        this.pickUpAddress = pickUpAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public String getPickUpLat() {
        return pickUpLat;
    }

    public void setPickUpLat(String pickUpLat) {
        this.pickUpLat = pickUpLat;
    }

    public String getPickUpLng() {
        return pickUpLng;
    }

    public void setPickUpLng(String pickUpLng) {
        this.pickUpLng = pickUpLng;
    }

    public String getDestinationLat() {
        return destinationLat;
    }

    public void setDestinationLat(String destinationLat) {
        this.destinationLat = destinationLat;
    }

    public String getDestinationLng() {
        return destinationLng;
    }

    public void setDestinationLng(String destinationLng) {
        this.destinationLng = destinationLng;
    }
}
