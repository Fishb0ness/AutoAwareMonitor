package com.fishb0ness.autoawaremonitor.adapter.output.mongodb.vehicle;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VehicleMongoRepository extends MongoRepository<VehicleMongoOutDTO, Long> {
    Optional<VehicleMongoOutDTO> findByVehicleId(UUID vehicleId);

    List<VehicleMongoOutDTO> findByOwnerId(UUID ownerId);

    long deleteByVehicleId(UUID vehicleId);
}
