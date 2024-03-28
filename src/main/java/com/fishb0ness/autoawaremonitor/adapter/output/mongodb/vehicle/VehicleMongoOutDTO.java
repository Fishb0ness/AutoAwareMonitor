package com.fishb0ness.autoawaremonitor.adapter.output.mongodb.vehicle;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "vehicles")
public class VehicleMongoOutDTO {

    @Id
    private UUID vehicleId;
    private UUID ownerId;
    private UUID vehicleModelId;
    private String exactVehicleModel;

    public UUID getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(UUID vehicleId) {
        this.vehicleId = vehicleId;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public UUID getVehicleModelId() {
        return vehicleModelId;
    }

    public void setVehicleModelId(UUID vehicleModelId) {
        this.vehicleModelId = vehicleModelId;
    }

    public String getExactVehicleModel() {
        return exactVehicleModel;
    }

    public void setExactVehicleModel(String exactVehicleModel) {
        this.exactVehicleModel = exactVehicleModel;
    }
}
