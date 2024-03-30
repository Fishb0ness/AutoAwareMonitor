package com.fishb0ness.autoawaremonitor.application;

import com.fishb0ness.autoawaremonitor.domain.fueling.Fueling;
import com.fishb0ness.autoawaremonitor.domain.fueling.FuelingId;
import com.fishb0ness.autoawaremonitor.domain.vehicle.VehicleId;
import com.fishb0ness.autoawaremonitor.domain.vehicle.VehicleModelId;

import java.util.List;
import java.util.Optional;

public interface FuelingUseCases {

    //
    Fueling createFueling(Fueling fueling);

    Optional<Fueling> getFuelingById(FuelingId fuelingId);

    List<Fueling> getAllFuelingByVehicleId(VehicleId vehicleId);

    Optional<Fueling> updateFuelingInfo(Fueling newfueling);

    long deleteFueling(FuelingId fuelingId);
}
