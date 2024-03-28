package com.fishb0ness.autoawaremonitor.domain.vehicle;

import com.fishb0ness.autoawaremonitor.domain.user.UserId;

public record Vehicle(VehicleId id, UserId ownerId, VehicleModel vehicleModel, String exactVehicleModel) {
}
