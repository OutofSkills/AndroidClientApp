package com.intelligentcarmanagement.carmanagementclientapp.models;

import com.google.gson.annotations.SerializedName;

public class Driver {
    @SerializedName("id")
    private int id;
    @SerializedName("firstName")
    private String firstName;
    @SerializedName("lastName")
    private String lastName;
    @SerializedName("age")
    private int age;
    @SerializedName("phoneNumber")
    private String phoneNumber;
    @SerializedName("registrationDate")
    private String registrationDate;
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("email")
    private String email;
    @SerializedName("userName")
    private String userName;
    @SerializedName("addressId")
    private int addressId;
    @SerializedName("statusId")
    private int statusId;
    @SerializedName("deservedClients")
    private int deservedClients;
    @SerializedName("currentLat")
    private String currentLat;
    @SerializedName("currentLong")
    private String currentLong;
    @SerializedName("rating")
    private float rating;
    @SerializedName("isAvailable")
    private boolean isAvailable;

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public void setDeservedClients(int deservedClients) {
        this.deservedClients = deservedClients;
    }

    public void setCurrentLat(String currentLat) {
        this.currentLat = currentLat;
    }

    public void setCurrentLong(String currentLong) {
        this.currentLong = currentLong;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public int getAddressId() {
        return addressId;
    }

    public int getStatusId() {
        return statusId;
    }

    public int getDeservedClients() {
        return deservedClients;
    }

    public String getCurrentLat() {
        return currentLat;
    }

    public String getCurrentLong() {
        return currentLong;
    }

    public float getRating() {
        return rating;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
}
