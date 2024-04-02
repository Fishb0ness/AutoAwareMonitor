package com.fishb0ness.autoawaremonitor.application;

import com.fishb0ness.autoawaremonitor.domain.fueling.Fueling;
import com.fishb0ness.autoawaremonitor.domain.fueling.FuelingId;
import com.fishb0ness.autoawaremonitor.domain.fueling.FuelingRepository;
import com.fishb0ness.autoawaremonitor.domain.measures.*;
import com.fishb0ness.autoawaremonitor.domain.vehicle.VehicleId;
import com.fishb0ness.autoawaremonitor.domain.vehicle.VehicleRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

@Service
public class FuelingService implements FuelingUseCases{

    private final FuelingRepository fuelingRepository;
    private final VehicleRepository vehicleRepository;

    public FuelingService(FuelingRepository fuelingRepository, VehicleRepository vehicleRepository) {
        this.fuelingRepository = fuelingRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Fueling createFueling(VehicleId vehicleId, Instant date, boolean isFullTank, boolean isFirstTank, Distance mileage, Volume refuelVolume, Money paidPrice) {
        vehicleRepository.getVehicle(vehicleId).orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));
        Fueling fueling = new Fueling(new FuelingId(), vehicleId, date, isFullTank, isFirstTank, mileage, refuelVolume, paidPrice);
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
