package com.fishb0ness.autoawaremonitor.adapter.input.vehicle;

import com.fishb0ness.autoawaremonitor.domain.vehicle.VehicleId;

import java.util.UUID;

public class VehicleIdDTO {
    private String vehicleId;

    public VehicleIdDTO() {
    }

    public VehicleIdDTO(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public static VehicleIdDTO fromDomain(VehicleId vehicleId) {
        return new VehicleIdDTO(vehicleId.id().toString());
    }

    public static VehicleId toDomain(VehicleIdDTO vehicleIdDTO) {
        return new VehicleId(UUID.fromString(vehicleIdDTO.getVehicleId()));
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }
}
