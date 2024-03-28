package com.fishb0ness.autoawaremonitor.domain.vehicle;

import org.springframework.util.StringUtils;

public class VehicleModel {

    private final VehicleModelId vehicleModelId;
    private final String brand;
    private final String model;

    public VehicleModel(String brand, String model) {
        this.vehicleModelId = new VehicleModelId(brand, model);
        this.brand = StringUtils.capitalize(brand);
        this.model = StringUtils.capitalize(model);
    }

    public VehicleModel(VehicleModelId vehicleModelId, String brand, String model) throws IllegalArgumentException {
        if (!vehicleModelId.isValid()) {
            throw new IllegalArgumentException("VehicleModelId cannot be null");
        }
        if (brand == null || brand.isBlank()) {
            throw new IllegalArgumentException("Brand cannot be null or empty");
        }
        if (model == null || model.isBlank()) {
            throw new IllegalArgumentException("Model cannot be null or empty");
        }
        if (!VehicleModel.isValid(vehicleModelId, brand, model)) {
            throw new IllegalArgumentException(String.format("VehicleModelId %s does not match brand %s and model %s (%s)", vehicleModelId.id(), brand, model, new VehicleModelId(brand, model).id()));
        }
        this.vehicleModelId = vehicleModelId;
        this.brand = StringUtils.capitalize(brand);
        this.model = StringUtils.capitalize(model);
    }

    public static boolean isValid(VehicleModelId vehicleModelId, String brand, String model) {
        boolean result;
        result = brand != null &&
                !brand.isBlank() &&
                model != null &&
                !model.isBlank() &&
                vehicleModelId.isValid() &&
                vehicleModelId.equals(new VehicleModelId(brand, model));
        return result;
    }

    public VehicleModelId getVehicleModelId() {
        return vehicleModelId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VehicleModel that = (VehicleModel) o;

        if (!vehicleModelId.equals(that.vehicleModelId)) return false;
        if (!brand.equals(that.brand)) return false;
        return model.equals(that.model);
    }
}

