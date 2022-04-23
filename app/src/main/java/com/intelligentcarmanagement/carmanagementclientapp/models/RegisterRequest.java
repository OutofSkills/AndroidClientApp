package com.intelligentcarmanagement.carmanagementclientapp.models;

import com.google.gson.annotations.SerializedName;

public class RegisterRequest {
    @SerializedName("firstName")
    private String firstName;
    @SerializedName("lastName")
    private String lastName;
    @SerializedName("phoneNumber")
    private String phoneNumber;
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("email")
    private String email;
    @SerializedName("userName")
    private String userName;
    @SerializedName("password")
    private String password;
    @SerializedName("confirmPassword")
    private String confirmPassword;

    public RegisterRequest(){}

    public RegisterRequest(String firstName, String lastName, String phoneNumber, String avatar, String email, String userName, String password, String confirmPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.avatar = avatar;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
