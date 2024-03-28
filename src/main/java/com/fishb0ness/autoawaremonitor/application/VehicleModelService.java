package com.fishb0ness.autoawaremonitor.application;

import com.fishb0ness.autoawaremonitor.domain.vehicle.VehicleModel;
import com.fishb0ness.autoawaremonitor.domain.vehicle.VehicleModelId;
import com.fishb0ness.autoawaremonitor.domain.vehicle.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleModelService implements VehicleModelUseCases {

    private final VehicleRepository vehicleRepository;

    public VehicleModelService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public VehicleModel createVehicleModel(String brand, String model) {
        return vehicleRepository.saveVehicleModel(brand, model);
    }

    @Override
    public Optional<VehicleModel> getVehicleModelById(VehicleModelId vehicleModelId) {
        return vehicleRepository.getVehicleModel(vehicleModelId);
    }

    @Override
    public List<String> getAllVehicleModelBrands() {
        return vehicleRepository.getAllVehicleModels().stream().map(VehicleModel::getBrand).toList();
    }

    @Override
    public List<VehicleModel> getVehicleModelByBrand(String brand) {
        return vehicleRepository.getAllVehicleModelsByBrand(brand);
    }

    @Override
    public List<VehicleModel> getAllVehicleModels() {
        return vehicleRepository.getAllVehicleModels();
    }
}
