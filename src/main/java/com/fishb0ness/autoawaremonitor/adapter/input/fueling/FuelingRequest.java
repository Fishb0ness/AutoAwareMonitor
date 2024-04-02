package com.fishb0ness.autoawaremonitor.adapter.input.fueling;

import java.time.Instant;

public class FuelingRequest {

    private String vehicleId;
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ")
    private Instant date;
    private boolean isFullTank;
    private boolean isFirstTank;
    private double mileage;
    private String mileageUnit;
    private double refuelQuantity;
    private String refuelQuantityUnit;
    private String paidPrice;
    private String paidPriceCurrency;

    /*public static Fueling toFueling(FuelingRequest fuelingRequest) {
        FuelingId fuelingId = new FuelingId();
        VehicleId vehicleId = new VehicleId(UUID.fromString(fuelingRequest.getVehicleId()));
        Distance mileage = new Distance(fuelingRequest.getMileage(), DistanceMeasure.valueOf(fuelingRequest.getMileageUnit()));
        Volume refuelVolume = new Volume(fuelingRequest.getRefuelQuantity(), VolumeMeasure.valueOf(fuelingRequest.getRefuelQuantityUnit()));
        Money paidPrice = new Money(new BigDecimal(fuelingRequest.getPaidPrice()), Currency.getInstance(fuelingRequest.getPaidPriceCurrency()));
        return new Fueling(fuelingId, vehicleId, fuelingRequest.getDate(), fuelingRequest.isFullTank(), fuelingRequest.isFirstTank(), mileage, refuelVolume, paidPrice);
    }*/

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
