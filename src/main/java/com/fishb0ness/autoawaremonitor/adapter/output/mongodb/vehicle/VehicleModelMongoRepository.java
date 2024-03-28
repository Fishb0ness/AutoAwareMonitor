package com.fishb0ness.autoawaremonitor.adapter.output.mongodb.vehicle;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VehicleModelMongoRepository extends MongoRepository<VehicleModelMongoOutDTO, Long> {

    Optional<VehicleModelMongoOutDTO> findByModelId(UUID vehicleModelId);

    List<VehicleModelMongoOutDTO> findByBrand(String brand);

    long deleteByModelId(UUID vehicleModelId);
}
