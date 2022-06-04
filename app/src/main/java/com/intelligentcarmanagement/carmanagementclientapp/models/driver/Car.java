package com.intelligentcarmanagement.carmanagementclientapp.models.driver;

public class Car {
    private int id;
    private String brand;
    private String model;
    private String fuelType;

    public Car(int id, String brand, String model, String fuelType) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.fuelType = fuelType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }
}
