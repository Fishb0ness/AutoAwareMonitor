package com.fishb0ness.autoawaremonitor.domain.vehicle;

import java.util.UUID;

public record VehicleId(UUID id) {

    public VehicleId() {
        this(UUID.randomUUID());
    }
}
