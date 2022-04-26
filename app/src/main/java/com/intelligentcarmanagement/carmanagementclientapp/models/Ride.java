package com.intelligentcarmanagement.carmanagementclientapp.models;

import java.io.Serializable;
import java.util.Date;

public class Ride implements Serializable {
    private int id;
    private int driverId;
    private int clientId;
    private String pickUpPlaceAddress;
    private String pickUpPlaceName;
    private String destinationPlaceAddress;
    private String destinationPlaceName;
    private String pickUpPlaceLat;
    private String pickUpPlaceLong;
    private String destinationPlaceLat;
    private String destinationPlaceLong;
    private double distance;
    private double averageTime;
    private Date pickUpTime;

    public Ride() {
    }

    public Ride(int rideId, int driverId, String pickUpPlaceAddress, String pickUpPlaceName, String destinationPlaceAddress, String destinationPlaceName, String pickUpLat, String pickUpLng, String destinationLat, String destinationLng, double rideDistance, Date date) {
        this.id = rideId;
        this.driverId = driverId;
        this.pickUpPlaceAddress = pickUpPlaceAddress;
        this.pickUpPlaceName = pickUpPlaceName;
        this.destinationPlaceAddress = destinationPlaceAddress;
        this.destinationPlaceName = destinationPlaceName;
        this.pickUpPlaceLat = pickUpLat;
        this.pickUpPlaceLong = pickUpLng;
        this.destinationPlaceLat = destinationLat;
        this.destinationPlaceLong = destinationLng;
        this.distance = rideDistance;
        this.pickUpTime = date;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public double getAverageTime() {
        return averageTime;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        double averageSpeed = 40; // Km/h

        this.distance = distance;
        this.averageTime = distance /averageSpeed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getPickUpTime() {
        return pickUpTime;
    }

    public void setPickUpTime(Date pickUpTime) {
        this.pickUpTime = pickUpTime;
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
        return pickUpPlaceLat;
    }

    public void setPickUpLat(String pickUpLat) {
        this.pickUpPlaceLat = pickUpLat;
    }

    public String getPickUpPlaceLong() {
        return pickUpPlaceLong;
    }

    public void setPickUpPlaceLong(String pickUpPlaceLong) {
        this.pickUpPlaceLong = pickUpPlaceLong;
    }

    public String getDestinationPlaceLat() {
        return destinationPlaceLat;
    }

    public void setDestinationPlaceLat(String destinationPlaceLat) {
        this.destinationPlaceLat = destinationPlaceLat;
    }

    public String getDestinationPlaceLong() {
        return destinationPlaceLong;
    }

    public void setDestinationPlaceLong(String destinationPlaceLong) {
        this.destinationPlaceLong = destinationPlaceLong;
    }
}
