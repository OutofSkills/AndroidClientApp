package com.intelligentcarmanagement.carmanagementclientapp.models;

import java.io.Serializable;
import java.util.Date;

public class Ride implements Serializable {
    private int rideId;
    private int driverId;
    private String pickUpPlaceAddress;
    private String pickUpPlaceName;
    private String destinationPlaceAddress;
    private String destinationPlaceName;
    private String pickUpLat;
    private String pickUpLng;
    private String destinationLat;
    private String destinationLng;
    private double rideDistance;
    private double averageTime;
    private Date date;

    public Ride() {
    }

    public Ride(int rideId, int driverId, String pickUpPlaceAddress, String pickUpPlaceName, String destinationPlaceAddress, String destinationPlaceName, String pickUpLat, String pickUpLng, String destinationLat, String destinationLng, double rideDistance, Date date) {
        this.rideId = rideId;
        this.driverId = driverId;
        this.pickUpPlaceAddress = pickUpPlaceAddress;
        this.pickUpPlaceName = pickUpPlaceName;
        this.destinationPlaceAddress = destinationPlaceAddress;
        this.destinationPlaceName = destinationPlaceName;
        this.pickUpLat = pickUpLat;
        this.pickUpLng = pickUpLng;
        this.destinationLat = destinationLat;
        this.destinationLng = destinationLng;
        this.rideDistance = rideDistance;
        this.date = date;
    }

    public double getAverageTime() {
        return averageTime;
    }

    public void setAverageTime(double averageTime) {
        this.averageTime = averageTime;
    }

    public double getRideDistance() {
        return rideDistance;
    }

    public void setRideDistance(double rideDistance) {
        this.rideDistance = rideDistance;
    }

    public int getRideId() {
        return rideId;
    }

    public void setRideId(int rideId) {
        this.rideId = rideId;
    }

    public String getPickUpPlaceName() {
        return pickUpPlaceName;
    }

    public void setPickUpPlaceName(String pickUpPlaceName) {
        this.pickUpPlaceName = pickUpPlaceName;
    }

    public String getDestinationPlaceName() {
        return destinationPlaceName;
    }

    public void setDestinationPlaceName(String destinationPlaceName) {
        this.destinationPlaceName = destinationPlaceName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getPickUpPlaceAddress() {
        return pickUpPlaceAddress;
    }

    public void setPickUpPlaceAddress(String pickUpPlaceAddress) {
        this.pickUpPlaceAddress = pickUpPlaceAddress;
    }

    public String getDestinationPlaceAddress() {
        return destinationPlaceAddress;
    }

    public void setDestinationPlaceAddress(String destinationPlaceAddress) {
        this.destinationPlaceAddress = destinationPlaceAddress;
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
