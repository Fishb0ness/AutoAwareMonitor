package com.fishb0ness.autoawaremonitor.application;

import com.fishb0ness.autoawaremonitor.domain.user.UserId;
import com.fishb0ness.autoawaremonitor.domain.vehicle.Vehicle;
import com.fishb0ness.autoawaremonitor.domain.vehicle.VehicleId;
import com.fishb0ness.autoawaremonitor.domain.vehicle.VehicleModel;

import java.util.List;
import java.util.Optional;

public interface VehicleUseCases {

    Vehicle createVehicle(UserId owner, VehicleModel vehicleModel, String exactVehicleModel);

    Optional<Vehicle> getVehicleById(VehicleId vehicleId);

    List<Vehicle> getAllVehiclesByOwner(UserId owner);

    Optional<Vehicle> updateVehicleModel(VehicleId vehicleId, VehicleModel vehicleModel, String exactVehicleModel);

    long deleteVehicle(VehicleId vehicleId);
}
