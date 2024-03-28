package com.fishb0ness.autoawaremonitor.domain.vehicle;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VehicleModelIdTest {

    @Test
    public void testGenerateVehicleModelIdUUID() {
        String brand = "brand";
        String model = "model";

        VehicleModelId result = new VehicleModelId(brand, model);

        assertEquals(result.id().toString(), "571c8e8a-4044-3796-be09-bab54720eedf");
    }

    @Test
    public void testIsValid() {
        VehicleModelId vehicleModelId = new VehicleModelId("brand", "model");

        boolean result = vehicleModelId.isValid();

        assertTrue(result);
    }
}
