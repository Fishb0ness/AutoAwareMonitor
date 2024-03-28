package com.fishb0ness.autoawaremonitor.domain.vehicle;

import org.springframework.util.StringUtils;

import java.util.UUID;

public record VehicleModelId(UUID id) {

    public VehicleModelId(String brand, String model) {
        this(generateVehicleModelIdUUID(brand, model));
    }

    private static UUID generateVehicleModelIdUUID(String brand, String model) {
        return UUID.nameUUIDFromBytes((StringUtils.capitalize(brand) + StringUtils.capitalize(model)).getBytes());
    }

    public boolean isValid() {
        try {
            UUID.fromString(id.toString());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
