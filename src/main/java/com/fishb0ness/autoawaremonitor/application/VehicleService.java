package com.fishb0ness.autoawaremonitor.application;

import com.fishb0ness.autoawaremonitor.domain.user.UserId;
import com.fishb0ness.autoawaremonitor.domain.user.UserRepository;
import com.fishb0ness.autoawaremonitor.domain.vehicle.Vehicle;
import com.fishb0ness.autoawaremonitor.domain.vehicle.VehicleId;
import com.fishb0ness.autoawaremonitor.domain.vehicle.VehicleModel;
import com.fishb0ness.autoawaremonitor.domain.vehicle.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VehicleService implements VehicleUseCases {

    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    public VehicleService(VehicleRepository vehicleRepository, UserRepository userRepository) {
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Vehicle createVehicle(UserId owner, VehicleModel vehicleModel, String exactVehicleModel) throws IllegalArgumentException {
        userRepository.getUser(owner).orElseThrow(() -> new IllegalArgumentException("User not found"));

        vehicleRepository.getVehicleModel(vehicleModel.getVehicleModelId()).orElseThrow(() -> new IllegalArgumentException("Vehicle model not found"));

        Vehicle vehicle = new Vehicle(new VehicleId(UUID.randomUUID()), owner, vehicleModel, exactVehicleModel);
        return vehicleRepository.saveVehicle(vehicle);
    }

    @Override
    public Optional<Vehicle> getVehicleById(VehicleId vehicleId) {
        return vehicleRepository.getVehicle(vehicleId);
    }

    @Override
    public List<Vehicle> getAllVehiclesByOwner(UserId owner) {
        userRepository.getUser(owner).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return vehicleRepository.getAllVehiclesByOwner(owner);
    }

    @Override
    public Optional<Vehicle> updateVehicleModel(VehicleId vehicleId, VehicleModel newVehicleModel, String newExactVehicleModel) {
        Optional<Vehicle> optionalOldVehicle = vehicleRepository.getVehicle(vehicleId);

        if (optionalOldVehicle.isEmpty()) {
            return Optional.empty();
        } else {
            vehicleRepository.getVehicleModel(newVehicleModel.getVehicleModelId()).orElseThrow(() -> new IllegalArgumentException("Vehicle model not found"));
            Vehicle newVehicle = new Vehicle(vehicleId, optionalOldVehicle.get().ownerId(), newVehicleModel, newExactVehicleModel);
            return vehicleRepository.updateVehicleModel(newVehicle);
        }
    }

    @Override
    public long deleteVehicle(VehicleId vehicleId) {
        return vehicleRepository.deleteVehicle(vehicleId);
    }
}
