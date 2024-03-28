package com.fishb0ness.autoawaremonitor.domain.vehicle;

import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VehicleModelTest {

    @Test
    public void testConstructorWithValidInputs() {
        VehicleModelId vehicleModelId = new VehicleModelId("brand", "model");
        String brand = "brand";
        String model = "model";

        VehicleModel vehicleModel = new VehicleModel(vehicleModelId, brand, model);

        assertEquals(vehicleModelId, vehicleModel.getVehicleModelId());
        assertEquals(StringUtils.capitalize(brand), vehicleModel.getBrand());
        assertEquals(StringUtils.capitalize(model), vehicleModel.getModel());
    }

    @Test
    public void testConstructorWithInvalidVehicleModelId() {
        VehicleModelId vehicleModelId = new VehicleModelId("invalid", "invalid");
        String brand = "brand";
        String model = "model";

        assertThrows(IllegalArgumentException.class, () -> new VehicleModel(vehicleModelId, brand, model));
    }

    @Test
    public void testConstructorWithNullBrand() {
        VehicleModelId vehicleModelId = new VehicleModelId("brand", "model");
        String brand = null;
        String model = "model";

        assertThrows(IllegalArgumentException.class, () -> new VehicleModel(vehicleModelId, brand, model));
    }

    @Test
    public void testConstructorWithEmptyBrand() {
        VehicleModelId vehicleModelId = new VehicleModelId("brand", "model");
        String brand = "";
        String model = "model";

        assertThrows(IllegalArgumentException.class, () -> new VehicleModel(vehicleModelId, brand, model));
    }

    @Test
    public void testConstructorWithNullModel() {
        VehicleModelId vehicleModelId = new VehicleModelId("brand", "model");
        String brand = "brand";
        String model = null;

        assertThrows(IllegalArgumentException.class, () -> new VehicleModel(vehicleModelId, brand, model));
    }

    @Test
    public void testConstructorWithEmptyModel() {
        VehicleModelId vehicleModelId = new VehicleModelId("brand", "model");
        String brand = "brand";
        String model = "";

        assertThrows(IllegalArgumentException.class, () -> new VehicleModel(vehicleModelId, brand, model));
    }
}
