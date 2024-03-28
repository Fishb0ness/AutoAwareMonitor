package com.fishb0ness.autoawaremonitor.adapter.input.vehicle;

public class VehicleDeleteResponse {

    private long deletedVehicles;

    public VehicleDeleteResponse(long deletedVehicles) {
        this.deletedVehicles = deletedVehicles;
    }

    public long getDeletedVehicles() {
        return deletedVehicles;
    }

    public void setDeletedVehicles(long deletedVehicles) {
        this.deletedVehicles = deletedVehicles;
    }
}
