package com.fishb0ness.autoawaremonitor.adapter.output.mongodb.vehicle;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "models")
public class VehicleModelMongoOutDTO {

    @Id
    private UUID modelId;
    private String brand;
    private String model;

    public VehicleModelMongoOutDTO() {
    }

    public VehicleModelMongoOutDTO(UUID modelId, String brand, String model) {
        this.modelId = modelId;
        this.brand = brand;
        this.model = model;
    }

    public UUID getModelId() {
        return modelId;
    }

    public void setModelId(UUID modelId) {
        this.modelId = modelId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
