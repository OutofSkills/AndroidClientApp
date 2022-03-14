package com.intelligentcarmanagement.carmanagementclientapp.models;

import com.google.android.gms.maps.model.LatLng;

public class Ride {
    private String pickUpAddress;
    private String destinationAddress;
    private LatLng pickUpLatLng;
    private LatLng destinationLatLng;

    public Ride() {
    }

    public Ride(String pickUpAddress, String destinationAddress, LatLng pickUpLatLng, LatLng destinationLatLng) {
        this.pickUpAddress = pickUpAddress;
        this.destinationAddress = destinationAddress;
        this.pickUpLatLng = pickUpLatLng;
        this.destinationLatLng = destinationLatLng;
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

    public LatLng getPickUpLatLng() {
        return pickUpLatLng;
    }

    public void setPickUpLatLng(LatLng pickUpLatLng) {
        this.pickUpLatLng = pickUpLatLng;
    }

    public LatLng getDestinationLatLng() {
        return destinationLatLng;
    }

    public void setDestinationLatLng(LatLng destinationLatLng) {
        this.destinationLatLng = destinationLatLng;
    }
}
