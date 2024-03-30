package com.fishb0ness.autoawaremonitor.domain.fueling;

import com.fishb0ness.autoawaremonitor.domain.vehicle.VehicleId;
import com.fishb0ness.autoawaremonitor.domain.vehicle.VehicleModelId;

import java.util.List;

public interface FuelingRepository {

    Fueling saveFueling(Fueling fueling);

    Fueling getFueling(FuelingId fuelingId);

    List<Fueling> getAllFuelingByVehicleId(VehicleId vehicleId);

    Fueling updateFueling(Fueling fueling);

    long deleteFueling(FuelingId fuelingId);
}
