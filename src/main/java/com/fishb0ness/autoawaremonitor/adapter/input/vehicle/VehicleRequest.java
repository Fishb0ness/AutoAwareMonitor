package com.fishb0ness.autoawaremonitor.adapter.input.vehicle;

public class VehicleRequest {
    private String userId;
    private String brand;
    private String model;
    private String exactModel;

    // getters and setters

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getExactModel() {
        return exactModel;
    }

    public void setExactModel(String exactModel) {
        this.exactModel = exactModel;
    }
}
