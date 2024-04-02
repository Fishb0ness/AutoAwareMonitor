package com.fishb0ness.autoawaremonitor.domain.fueling;

import com.fishb0ness.autoawaremonitor.domain.measures.*;
import com.fishb0ness.autoawaremonitor.domain.vehicle.VehicleId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.UUID;

public class FuelingTest {

    @Test
    public void fuelingIsCreatedCorrectly() {
        Fueling fueling = new Fueling(new FuelingId(), new VehicleId(UUID.randomUUID()), Instant.now(), true, true, new Distance(100.0, DistanceMeasure.KILOMETERS), new Volume(50.0, VolumeMeasure.LITERS), new Money(new BigDecimal("50.0"), Currency.getInstance("USD")));
        Assertions.assertNotNull(fueling);
    }

    @Test
    public void fuelingIdIsStoredCorrectly() {
        FuelingId fuelingId = new FuelingId();
        Fueling fueling = new Fueling(fuelingId, new VehicleId(UUID.randomUUID()), Instant.now(), true, true, new Distance(100.0, DistanceMeasure.KILOMETERS), new Volume(50.0, VolumeMeasure.LITERS), new Money(new BigDecimal("50.0"), Currency.getInstance("USD")));
        Assertions.assertEquals(fuelingId, fueling.getId());
    }

    @Test
    public void fuelingDateIsStoredCorrectly() {
        Instant now = Instant.now();
        Fueling fueling = new Fueling(new FuelingId(), new VehicleId(UUID.randomUUID()), now, true, true, new Distance(100.0, DistanceMeasure.KILOMETERS), new Volume(50.0, VolumeMeasure.LITERS), new Money(new BigDecimal("50.0"), Currency.getInstance("USD")));
        Assertions.assertEquals(now, fueling.getDate());
    }

    @Test
    public void fuelingIsFullTankIsStoredCorrectly() {
        Fueling fueling = new Fueling(new FuelingId(), new VehicleId(UUID.randomUUID()), Instant.now(), true, true, new Distance(100.0, DistanceMeasure.KILOMETERS), new Volume(50.0, VolumeMeasure.LITERS), new Money(new BigDecimal("50.0"), Currency.getInstance("USD")));
        Assertions.assertTrue(fueling.getIsFullTank());
    }

    @Test
    public void fuelingIsFirstTankIsStoredCorrectly() {
        Fueling fueling = new Fueling(new FuelingId(), new VehicleId(UUID.randomUUID()), Instant.now(), true, true, new Distance(100.0, DistanceMeasure.KILOMETERS), new Volume(50.0, VolumeMeasure.LITERS), new Money(new BigDecimal("50.0"), Currency.getInstance("USD")));
        Assertions.assertTrue(fueling.getIsFirstTank());
    }

    @Test
    public void fuelingMileageIsStoredCorrectly() {
        Distance distance = new Distance(100.0, DistanceMeasure.KILOMETERS);
        Fueling fueling = new Fueling(new FuelingId(), new VehicleId(UUID.randomUUID()), Instant.now(), true, true, distance, new Volume(50.0, VolumeMeasure.LITERS), new Money(new BigDecimal("50.0"), Currency.getInstance("USD")));
        Assertions.assertEquals(distance, fueling.getMileage());
    }

    @Test
    public void fuelingRefuelVolumeIsStoredCorrectly() {
        Volume volume = new Volume(50.0, VolumeMeasure.LITERS);
        Fueling fueling = new Fueling(new FuelingId(), new VehicleId(UUID.randomUUID()), Instant.now(), true, true, new Distance(100.0, DistanceMeasure.KILOMETERS), volume, new Money(new BigDecimal("50.0"), Currency.getInstance("USD")));
        Assertions.assertEquals(volume, fueling.getRefuelVolume());
    }

    @Test
    public void fuelingPaidPriceIsStoredCorrectly() {
        Money money = new Money(new BigDecimal("50.0"), Currency.getInstance("USD"));
        Fueling fueling = new Fueling(new FuelingId(), new VehicleId(UUID.randomUUID()), Instant.now(), true, true, new Distance(100.0, DistanceMeasure.KILOMETERS), new Volume(50.0, VolumeMeasure.LITERS), money);
        Assertions.assertEquals(money, fueling.getPaidPrice());
    }
}
