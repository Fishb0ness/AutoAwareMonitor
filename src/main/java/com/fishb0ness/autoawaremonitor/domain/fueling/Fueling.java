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

    public Fueling(FuelingId id, VehicleId vehicleId, Instant date, boolean isFullTank, boolean isFirstTank, Distance mileage, Volume refuelVolume, Money paidPrice) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.date = date;
        this.isFullTank = isFullTank;
        this.isFirstTank = isFirstTank;
        this.mileage = mileage;
        this.refuelVolume = refuelVolume;
        this.paidPrice = paidPrice;
    }

    public FuelingId getId() {
        return id;
    }

    public void setId(FuelingId id) {
        this.id = id;
    }

    public VehicleId getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(VehicleId vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public boolean getIsFullTank() {
        return isFullTank;
    }

    public void setIsFullTank(boolean fullTank) {
        isFullTank = fullTank;
    }

    public boolean getIsFirstTank() {
        return isFirstTank;
    }

    public void setIsFirstTank(boolean firstTank) {
        isFirstTank = firstTank;
    }

    public Distance getMileage() {
        return mileage;
    }

    public void setMileage(Distance mileage) {
        this.mileage = mileage;
    }

    public Volume getRefuelVolume() {
        return refuelVolume;
    }

    public void setRefuelVolume(Volume refuelVolume) {
        this.refuelVolume = refuelVolume;
    }

    public Money getPaidPrice() {
        return paidPrice;
    }

    public void setPaidPrice(Money paidPrice) {
        this.paidPrice = paidPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fueling fueling = (Fueling) o;
        return isFullTank == fueling.isFullTank && isFirstTank == fueling.isFirstTank && id.equals(fueling.id) && vehicleId.equals(fueling.vehicleId) && date.equals(fueling.date) && mileage.equals(fueling.mileage) && refuelVolume.equals(fueling.refuelVolume) && paidPrice.equals(fueling.paidPrice);
    }
}
