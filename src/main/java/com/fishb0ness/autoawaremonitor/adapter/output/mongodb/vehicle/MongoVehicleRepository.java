package com.fishb0ness.autoawaremonitor.adapter.output.mongodb.vehicle;

import com.fishb0ness.autoawaremonitor.domain.user.UserId;
import com.fishb0ness.autoawaremonitor.domain.vehicle.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MongoVehicleRepository implements VehicleRepository {

    private final VehicleMongoRepository vehicleMongoRepository;
    private final VehicleModelMongoRepository vehicleModelMongoRepository;

    public MongoVehicleRepository(VehicleMongoRepository vehicleMongoRepository, VehicleModelMongoRepository vehicleModelMongoRepository) {
        this.vehicleMongoRepository = vehicleMongoRepository;
        this.vehicleModelMongoRepository = vehicleModelMongoRepository;
    }

    public Vehicle saveVehicle(Vehicle vehicle) {
        VehicleMongoOutDTO vehicleMongoOutDto = toVehicleDTO(vehicle);
        return toVehicle(vehicleMongoRepository.insert(vehicleMongoOutDto));
    }

    public Optional<Vehicle> getVehicle(VehicleId vehicleId) {
        Optional<VehicleMongoOutDTO> optionalVehicleMongoOutDto = vehicleMongoRepository.findByVehicleId(vehicleId.id());
        return optionalVehicleMongoOutDto.map(this::toVehicle);
    }

    public List<Vehicle> getAllVehiclesByOwner(UserId userId) {
        return vehicleMongoRepository.findByOwnerId(userId.id()).stream().map(this::toVehicle).toList();
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleMongoRepository.findAll().stream().map(this::toVehicle).toList();
    }

    public Optional<Vehicle> updateVehicleModel(Vehicle vehicle) {
        Optional<VehicleMongoOutDTO> optionalVehicleMongoOutDto = vehicleMongoRepository.findByVehicleId(vehicle.id().id());
        if (optionalVehicleMongoOutDto.isEmpty()) {
            return Optional.empty();
        } else if (optionalVehicleMongoOutDto.get().getOwnerId().equals(vehicle.ownerId().id())) {
            return Optional.of(toVehicle(vehicleMongoRepository.save(toVehicleDTO(vehicle))));
        } else {
            return Optional.empty();
        }
    }

    public long deleteVehicle(VehicleId vehicleId) {
        return vehicleMongoRepository.deleteByVehicleId(vehicleId.id());
    }

    private Vehicle toVehicle(VehicleMongoOutDTO vehicleMongoOutDto) {
        VehicleId vehicleId = new VehicleId(vehicleMongoOutDto.getVehicleId());
        UserId ownerId = new UserId(vehicleMongoOutDto.getOwnerId());
        VehicleModel vehicleModel = vehicleModelMongoRepository.findByModelId(vehicleMongoOutDto.getVehicleModelId()).map(this::toVehicleModel).orElseThrow(IllegalAccessError::new);
        String exactVehicleModel = vehicleMongoOutDto.getExactVehicleModel();
        return new Vehicle(vehicleId, ownerId, vehicleModel, exactVehicleModel);
    }

    private VehicleMongoOutDTO toVehicleDTO(Vehicle vehicle) {
        VehicleMongoOutDTO vehicleMongoOutDto = new VehicleMongoOutDTO();
        vehicleMongoOutDto.setOwnerId(vehicle.ownerId().id());
        vehicleMongoOutDto.setVehicleId(vehicle.id().id());
        vehicleMongoOutDto.setVehicleModelId(vehicle.vehicleModel().getVehicleModelId().id());
        vehicleMongoOutDto.setExactVehicleModel(vehicle.exactVehicleModel());
        return vehicleMongoOutDto;
    }

    public VehicleModel saveVehicleModel(String brand, String model) {
        VehicleModel vehicleModel = new VehicleModel(brand, model);
        VehicleModelMongoOutDTO vehicleModelMongoOutDto = toVehicleModelDto(vehicleModel);
        return toVehicleModel(vehicleModelMongoRepository.insert(vehicleModelMongoOutDto));
    }

    @Override
    public List<VehicleModel> getAllVehicleModels() {
        return vehicleModelMongoRepository.findAll().stream().map(this::toVehicleModel).toList();
    }

    public List<VehicleModel> getAllVehicleModelsByBrand(String brand) {
        return vehicleModelMongoRepository.findByBrand(brand).stream().map(this::toVehicleModel).toList();
    }

    public Optional<VehicleModel> getVehicleModel(VehicleModelId vehicleModelId) {
        Optional<VehicleModelMongoOutDTO> optionalVehicleModelMongoOutDto = vehicleModelMongoRepository.findByModelId(vehicleModelId.id());
        return optionalVehicleModelMongoOutDto.map(this::toVehicleModel);
    }

    @Override
    public long deleteVehicleModel(VehicleModelId vehicleModelId) {
        return vehicleModelMongoRepository.deleteByModelId(vehicleModelId.id());
    }

    private VehicleModel toVehicleModel(VehicleModelMongoOutDTO vehicleModelMongoOutDto) {
        VehicleModelId vehicleModelId = new VehicleModelId(vehicleModelMongoOutDto.getModelId());
        return new VehicleModel(vehicleModelId, vehicleModelMongoOutDto.getBrand(), vehicleModelMongoOutDto.getModel());
    }

    private VehicleModelMongoOutDTO toVehicleModelDto(VehicleModel vehicleModel) {
        VehicleModelMongoOutDTO vehicleModelMongoOutDto = new VehicleModelMongoOutDTO();
        vehicleModelMongoOutDto.setModelId(vehicleModel.getVehicleModelId().id());
        vehicleModelMongoOutDto.setBrand(vehicleModel.getBrand());
        vehicleModelMongoOutDto.setModel(vehicleModel.getModel());
        return vehicleModelMongoOutDto;
    }
}
