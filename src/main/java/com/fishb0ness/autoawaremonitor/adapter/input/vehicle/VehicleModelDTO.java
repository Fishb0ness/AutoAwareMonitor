package com.fishb0ness.autoawaremonitor.adapter.input.vehicle;

import com.fishb0ness.autoawaremonitor.domain.vehicle.VehicleModel;

public class VehicleModelDTO {

    private String brand;
    private String model;

    public VehicleModelDTO(String brand, String model) {
        this.brand = brand;
        this.model = model;
    }

    public static VehicleModelDTO fromDomain(VehicleModel vehicleModel) {
        return new VehicleModelDTO(vehicleModel.getBrand(), vehicleModel.getModel());
    }

    public static VehicleModel toDomain(VehicleModelDTO vehicleModelDto) {
        return new VehicleModel(vehicleModelDto.getBrand(), vehicleModelDto.getModel());
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
}
