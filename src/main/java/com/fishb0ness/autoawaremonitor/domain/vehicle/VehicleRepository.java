package com.fishb0ness.autoawaremonitor.domain.vehicle;

import com.fishb0ness.autoawaremonitor.domain.user.UserId;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository {

    Vehicle saveVehicle(Vehicle vehicle);

    Optional<Vehicle> getVehicle(VehicleId vehicleId);

    List<Vehicle> getAllVehiclesByOwner(UserId userId);

    List<Vehicle> getAllVehicles();

    Optional<Vehicle> updateVehicleModel(Vehicle vehicle);

    long deleteVehicle(VehicleId vehicleId);

    VehicleModel saveVehicleModel(String brand, String model);

    List<VehicleModel> getAllVehicleModels();

    List<VehicleModel> getAllVehicleModelsByBrand(String brand);

    Optional<VehicleModel> getVehicleModel(VehicleModelId vehicleModelId);

    long deleteVehicleModel(VehicleModelId vehicleModelId);
}
