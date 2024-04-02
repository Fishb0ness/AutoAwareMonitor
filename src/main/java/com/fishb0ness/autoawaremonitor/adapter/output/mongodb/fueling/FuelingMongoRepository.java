package com.fishb0ness.autoawaremonitor.adapter.output.mongodb.fueling;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FuelingMongoRepository extends MongoRepository<FuelingMongoOutDTO, Long> {

    Optional<FuelingMongoOutDTO> findByFuelingId(UUID fuelingId);

    List<FuelingMongoOutDTO> findByVehicleId(UUID vehicleId);

    long deleteByFuelingId(UUID fuelingId);
}
