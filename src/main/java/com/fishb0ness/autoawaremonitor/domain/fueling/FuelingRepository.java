package com.fishb0ness.autoawaremonitor.domain.fueling;

import com.fishb0ness.autoawaremonitor.domain.vehicle.VehicleId;
import com.fishb0ness.autoawaremonitor.domain.vehicle.VehicleModelId;

import java.util.List;
import java.util.Optional;

public interface FuelingRepository {

    Fueling saveFueling(Fueling fueling);

    Optional<Fueling> getFueling(FuelingId fuelingId);

    List<Fueling> getAllFuelingByVehicleId(VehicleId vehicleId);

    Optional<Fueling> updateFueling(Fueling fueling);

    long deleteFueling(FuelingId fuelingId);
}
