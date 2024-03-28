package com.fishb0ness.autoawaremonitor.adapter.input.vehicle;

import com.fishb0ness.autoawaremonitor.domain.user.UserId;
import com.fishb0ness.autoawaremonitor.domain.vehicle.Vehicle;
import com.fishb0ness.autoawaremonitor.domain.vehicle.VehicleId;
import com.fishb0ness.autoawaremonitor.domain.vehicle.VehicleModel;

import java.util.UUID;

public class VehicleDTO {
    private String vehicleId;
    private String ownerId;
    //VehicleModel
    private String brand;
    private String model;
    private String exactVehicleModel;

    public VehicleDTO(String vehicleId, String ownerId, String brand, String model, String exactVehicleModel) {
        this.vehicleId = vehicleId;
        this.ownerId = ownerId;
        this.brand = brand;
        this.model = model;
        this.exactVehicleModel = exactVehicleModel;
    }

    public static VehicleDTO fromDomain(Vehicle vehicle) {
        String vehicleId = vehicle.id().id().toString();
        String ownerId = vehicle.ownerId().id().toString();
        String brand = vehicle.vehicleModel().getBrand();
        String model = vehicle.vehicleModel().getModel();
        String exactVehicleModel = vehicle.exactVehicleModel();
        return new VehicleDTO(vehicleId, ownerId, brand, model, exactVehicleModel);
    }

    public static Vehicle toDomain(VehicleDTO vehicleDto) {
        VehicleId vehicleId = new VehicleId(UUID.fromString(vehicleDto.vehicleId));
        UserId userId = new UserId(UUID.fromString(vehicleDto.ownerId));
        VehicleModel vehicleModel = new VehicleModel(vehicleDto.brand, vehicleDto.model);
        String exactVehicleModel = vehicleDto.exactVehicleModel;
        return new Vehicle(vehicleId, userId, vehicleModel, exactVehicleModel);
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
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

    public String getExactVehicleModel() {
        return exactVehicleModel;
    }

    public void setExactVehicleModel(String exactVehicleModel) {
        this.exactVehicleModel = exactVehicleModel;
    }
}
