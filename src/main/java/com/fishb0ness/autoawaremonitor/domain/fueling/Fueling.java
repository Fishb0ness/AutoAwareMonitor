package com.fishb0ness.autoawaremonitor.domain.fueling;

import com.fishb0ness.autoawaremonitor.domain.measures.Distance;
import com.fishb0ness.autoawaremonitor.domain.measures.Money;
import com.fishb0ness.autoawaremonitor.domain.measures.Volume;
import com.fishb0ness.autoawaremonitor.domain.vehicle.VehicleId;

import java.time.Instant;

public class Fueling {

    private FuelingId id;
    private VehicleId vehicleId;
    private Instant date;
    private boolean isFullTank;
    private boolean isFirstTank;
    private Distance mileage;
    private Volume refuelVolume;
    private Money paidPrice;
}
