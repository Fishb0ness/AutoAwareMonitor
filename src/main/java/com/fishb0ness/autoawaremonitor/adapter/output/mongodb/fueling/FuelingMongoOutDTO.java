package com.fishb0ness.autoawaremonitor.adapter.output.mongodb.fueling;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Document(collection = "fueling")
public class FuelingMongoOutDTO {

    @Id
    private UUID fuelingId;
    private UUID vehicleId;
    private Instant fuelingDate;
    private boolean isFullFueling;
    private boolean isFirstFueling;
    private double distance;
    private String distanceUnit;
    private double fuelingAmount;
    private String fuelingAmountUnit;
    private BigDecimal fuelingPrice;
    private String fuelingPriceUnit;

    public UUID getFuelingId() {
        return fuelingId;
    }

    public void setFuelingId(UUID fuelingId) {
        this.fuelingId = fuelingId;
    }

    public UUID getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(UUID vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Instant getFuelingDate() {
        return fuelingDate;
    }

    public void setFuelingDate(Instant fuelingDate) {
        this.fuelingDate = fuelingDate;
    }

    public boolean isFullFueling() {
        return isFullFueling;
    }

    public void setFullFueling(boolean fullFueling) {
        isFullFueling = fullFueling;
    }

    public boolean isFirstFueling() {
        return isFirstFueling;
    }

    public void setFirstFueling(boolean firstFueling) {
        isFirstFueling = firstFueling;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getDistanceUnit() {
        return distanceUnit;
    }

    public void setDistanceUnit(String distanceUnit) {
        this.distanceUnit = distanceUnit;
    }

    public double getFuelingAmount() {
        return fuelingAmount;
    }

    public void setFuelingAmount(double fuelingAmount) {
        this.fuelingAmount = fuelingAmount;
    }

    public String getFuelingAmountUnit() {
        return fuelingAmountUnit;
    }

    public void setFuelingAmountUnit(String fuelingAmountUnit) {
        this.fuelingAmountUnit = fuelingAmountUnit;
    }

    public BigDecimal getFuelingPrice() {
        return fuelingPrice;
    }

    public void setFuelingPrice(BigDecimal fuelingPrice) {
        this.fuelingPrice = fuelingPrice;
    }

    public String getFuelingPriceUnit() {
        return fuelingPriceUnit;
    }

    public void setFuelingPriceUnit(String fuelingPriceUnit) {
        this.fuelingPriceUnit = fuelingPriceUnit;
    }
}
