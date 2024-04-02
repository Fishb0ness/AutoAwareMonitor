package com.fishb0ness.autoawaremonitor.adapter.input.fueling;

import com.fishb0ness.autoawaremonitor.domain.fueling.Fueling;
import com.fishb0ness.autoawaremonitor.domain.fueling.FuelingId;
import com.fishb0ness.autoawaremonitor.domain.measures.*;
import com.fishb0ness.autoawaremonitor.domain.vehicle.VehicleId;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.UUID;

public class FuelingDTO {

    private String fuelingId;
    private String vehicleId;
    private Instant date;
    private boolean isFullTank;
    private boolean isFirstTank;
    private double mileage;
    private String mileageUnit;
    private double refuelQuantity;
    private String refuelQuantityUnit;
    private String paidPrice;
    private String paidPriceCurrency;

    public FuelingDTO(String fuelingId, String vehicleId, Instant date, boolean isFullTank, boolean isFirstTank, double mileage, String mileageUnit, double refuelQuantity, String refuelQuantityUnit, String paidPrice, String paidPriceCurrency) {
        this.fuelingId = fuelingId;
        this.vehicleId = vehicleId;
        this.date = date;
        this.isFullTank = isFullTank;
        this.isFirstTank = isFirstTank;
        this.mileage = mileage;
        this.mileageUnit = mileageUnit;
        this.refuelQuantity = refuelQuantity;
        this.refuelQuantityUnit = refuelQuantityUnit;
        this.paidPrice = paidPrice;
        this.paidPriceCurrency = paidPriceCurrency;
    }

    public static FuelingDTO fromDomain(Fueling fueling) {
        return new FuelingDTO(
                fueling.getId().id().toString(),
                fueling.getVehicleId().id().toString(),
                fueling.getDate(),
                fueling.getIsFullTank(),
                fueling.getIsFirstTank(),
                fueling.getMileage().getDistance(),
                fueling.getMileage().getDistanceMeasure().name(),
                fueling.getRefuelVolume().getQuantity(),
                fueling.getRefuelVolume().getVolumeMeasure().name(),
                fueling.getPaidPrice().getAmount().toString(),
                fueling.getPaidPrice().getCurrency().getCurrencyCode()
        );
    }

    public static Fueling toDomain(FuelingDTO fuelingDto) {
        FuelingId fuelingId = new FuelingId(UUID.fromString(fuelingDto.fuelingId));
        VehicleId vehicleId = new VehicleId(UUID.fromString(fuelingDto.vehicleId));
        Distance mileage = new Distance(fuelingDto.mileage, DistanceMeasure.valueOf(fuelingDto.mileageUnit));
        Volume refuelVolume = new Volume(fuelingDto.refuelQuantity, VolumeMeasure.valueOf(fuelingDto.refuelQuantityUnit));
        Money paidPrice = new Money(new BigDecimal(fuelingDto.paidPrice), Currency.getInstance(fuelingDto.paidPriceCurrency));
        return new Fueling(fuelingId, vehicleId, fuelingDto.date, fuelingDto.isFullTank, fuelingDto.isFirstTank, mileage, refuelVolume, paidPrice);
    }

    public String getFuelingId() {
        return fuelingId;
    }

    public void setFuelingId(String fuelingId) {
        this.fuelingId = fuelingId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
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

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public String getMileageUnit() {
        return mileageUnit;
    }

    public void setMileageUnit(String mileageUnit) {
        this.mileageUnit = mileageUnit;
    }

    public double getRefuelQuantity() {
        return refuelQuantity;
    }

    public void setRefuelQuantity(double refuelQuantity) {
        this.refuelQuantity = refuelQuantity;
    }

    public String getRefuelQuantityUnit() {
        return refuelQuantityUnit;
    }

    public void setRefuelQuantityUnit(String refuelQuantityUnit) {
        this.refuelQuantityUnit = refuelQuantityUnit;
    }

    public String getPaidPrice() {
        return paidPrice;
    }

    public void setPaidPrice(String paidPrice) {
        this.paidPrice = paidPrice;
    }

    public String getPaidPriceCurrency() {
        return paidPriceCurrency;
    }

    public void setPaidPriceCurrency(String paidPriceCurrency) {
        this.paidPriceCurrency = paidPriceCurrency;
    }
}
