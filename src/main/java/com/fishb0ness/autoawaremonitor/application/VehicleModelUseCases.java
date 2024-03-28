package com.fishb0ness.autoawaremonitor.application;

import com.fishb0ness.autoawaremonitor.domain.vehicle.VehicleModel;
import com.fishb0ness.autoawaremonitor.domain.vehicle.VehicleModelId;

import java.util.List;
import java.util.Optional;

public interface VehicleModelUseCases {

    VehicleModel createVehicleModel(String brand, String model);

    Optional<VehicleModel> getVehicleModelById(VehicleModelId vehicleModelId);

    List<String> getAllVehicleModelBrands();

    List<VehicleModel> getVehicleModelByBrand(String brand);

    List<VehicleModel> getAllVehicleModels();
}
