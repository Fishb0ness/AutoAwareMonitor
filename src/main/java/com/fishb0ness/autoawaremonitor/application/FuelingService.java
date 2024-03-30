package com.fishb0ness.autoawaremonitor.application;

import com.fishb0ness.autoawaremonitor.domain.fueling.Fueling;
import com.fishb0ness.autoawaremonitor.domain.fueling.FuelingId;
import com.fishb0ness.autoawaremonitor.domain.fueling.FuelingRepository;
import com.fishb0ness.autoawaremonitor.domain.vehicle.VehicleId;

import java.util.List;
import java.util.Optional;

public class FuelingService implements FuelingUseCases{

    private final FuelingRepository fuelingRepository;

    public FuelingService(FuelingRepository fuelingRepository) {
        this.fuelingRepository = fuelingRepository;
    }

    @Override
    public Fueling createFueling(Fueling fueling) {
        return fuelingRepository.saveFueling(fueling);
    }

    @Override
    public Optional<Fueling> getFuelingById(FuelingId fuelingId) {
        return fuelingRepository.getFueling(fuelingId);
    }

    @Override
    public List<Fueling> getAllFuelingByVehicleId(VehicleId vehicleId) {
        return fuelingRepository.getAllFuelingByVehicleId(vehicleId);
    }

    @Override
    public Optional<Fueling> updateFuelingInfo(Fueling newFueling) {
        return fuelingRepository.updateFueling(newFueling);
    }

    @Override
    public long deleteFueling(FuelingId fuelingId) {
        return fuelingRepository.deleteFueling(fuelingId);
    }
}
