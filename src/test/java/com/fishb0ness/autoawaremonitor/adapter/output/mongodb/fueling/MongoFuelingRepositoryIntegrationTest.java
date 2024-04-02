package com.fishb0ness.autoawaremonitor.adapter.output.mongodb.fueling;

import com.fishb0ness.autoawaremonitor.adapter.IntegrationTest;
import com.fishb0ness.autoawaremonitor.domain.fueling.Fueling;
import com.fishb0ness.autoawaremonitor.domain.fueling.FuelingId;
import com.fishb0ness.autoawaremonitor.domain.measures.*;
import com.fishb0ness.autoawaremonitor.domain.vehicle.VehicleId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Criteria.where;


public class MongoFuelingRepositoryIntegrationTest extends IntegrationTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MongoFuelingRepository mongoFuelingRepository;

    @BeforeEach
    public void setUp() {
        mongoTemplate.getCollectionNames().forEach(mongoTemplate::dropCollection);
    }

    @Test
    void saveFuelingTest() {
        Fueling fueling = new Fueling(
                new FuelingId(),
                new VehicleId(),
                Instant.now(),
                false,
                true,
                new Distance(1000, DistanceMeasure.KILOMETERS),
                new Volume(50, VolumeMeasure.LITERS),
                new Money(new BigDecimal("50"), Currency.getInstance("EUR"))
        );

        Fueling savedFueling = mongoFuelingRepository.saveFueling(fueling);

        assertAll(
                () -> assertTrue(mongoTemplate.exists(query(where("_id").is(fueling.getId().id())),"fueling")),
                () -> assertTrue(mongoTemplate.exists(query(where("vehicleId").is(fueling.getVehicleId().id())),"fueling")),
                () -> assertTrue(mongoTemplate.exists(query(where("fuelingDate").is(fueling.getDate())),"fueling")),
                () -> assertTrue(mongoTemplate.exists(query(where("isFullFueling").is(fueling.getIsFullTank())),"fueling")),
                () -> assertTrue(mongoTemplate.exists(query(where("isFirstFueling").is(fueling.getIsFirstTank())),"fueling")),
                () -> assertTrue(mongoTemplate.exists(query(where("distance").is(fueling.getMileage().getDistance())),"fueling")),
                () -> assertTrue(mongoTemplate.exists(query(where("distanceUnit").is(fueling.getMileage().getDistanceMeasure().name())),"fueling")),
                () -> assertTrue(mongoTemplate.exists(query(where("fuelingAmount").is(fueling.getRefuelVolume().getQuantity())),"fueling")),
                () -> assertTrue(mongoTemplate.exists(query(where("fuelingAmountUnit").is(fueling.getRefuelVolume().getVolumeMeasure().name())),"fueling")),
                () -> assertTrue(mongoTemplate.exists(query(where("fuelingPrice").is(fueling.getPaidPrice().getAmount())),"fueling")),
                () -> assertTrue(mongoTemplate.exists(query(where("fuelingPriceUnit").is(fueling.getPaidPrice().getCurrency().getCurrencyCode())),"fueling")),
                () -> assertNotNull(savedFueling),
                () -> assertEquals(fueling, savedFueling)
        );
    }

    @Test
    void getFuelingTest() {
        Fueling fueling = new Fueling(
                new FuelingId(),
                new VehicleId(),
                Instant.now().truncatedTo(ChronoUnit.MILLIS),
                false,
                true,
                new Distance(1000, DistanceMeasure.KILOMETERS),
                new Volume(50, VolumeMeasure.LITERS),
                new Money(new BigDecimal("50"), Currency.getInstance("EUR"))
        );

        mongoFuelingRepository.saveFueling(fueling);

        Fueling foundFueling = mongoFuelingRepository.getFueling(fueling.getId()).orElse(null);

        assertAll(
                () -> assertNotNull(foundFueling),
                () -> assertEquals(fueling, foundFueling)
        );
    }

    @Test
    void getFuelingNotFoundTest() {
        FuelingId fuelingId = new FuelingId();

        Optional<Fueling> foundFueling = mongoFuelingRepository.getFueling(fuelingId);

        assertTrue(foundFueling.isEmpty());
    }

    @Test
    void getAllFuelingByVehicleIdTest() {
        VehicleId vehicleId = new VehicleId();
        Instant date = Instant.now().truncatedTo(ChronoUnit.MILLIS);
        Fueling fueling1 = new Fueling(
                new FuelingId(),
                vehicleId,
                date,
                false,
                true,
                new Distance(1000, DistanceMeasure.KILOMETERS),
                new Volume(50, VolumeMeasure.LITERS),
                new Money(new BigDecimal("50"), Currency.getInstance("EUR"))
        );
        Fueling fueling2 = new Fueling(
                new FuelingId(),
                vehicleId,
                date,
                true,
                false,
                new Distance(2000, DistanceMeasure.KILOMETERS),
                new Volume(48.55, VolumeMeasure.LITERS),
                new Money(new BigDecimal("100"), Currency.getInstance("EUR"))
        );

        mongoFuelingRepository.saveFueling(fueling1);
        mongoFuelingRepository.saveFueling(fueling2);

        List<Fueling> foundFueling = mongoFuelingRepository.getAllFuelingByVehicleId(vehicleId);

        assertAll(
                () -> assertEquals(2, foundFueling.size()),
                () -> assertTrue(foundFueling.stream().anyMatch(fueling -> fueling.equals(fueling1))),
                () -> assertTrue(foundFueling.stream().anyMatch(fueling -> fueling.equals(fueling2)))
        );
    }

    @Test
    void getAllFuelingByVehicleIdNotFoundTest() {
        VehicleId vehicleId = new VehicleId();

        List<Fueling> foundFueling = mongoFuelingRepository.getAllFuelingByVehicleId(vehicleId);

        assertTrue(foundFueling.isEmpty());
    }

    @Test
    void updateFuelingTest() {
        Fueling fueling = new Fueling(
                new FuelingId(),
                new VehicleId(),
                Instant.now().truncatedTo(ChronoUnit.MILLIS),
                false,
                true,
                new Distance(1000, DistanceMeasure.KILOMETERS),
                new Volume(50, VolumeMeasure.LITERS),
                new Money(new BigDecimal("50"), Currency.getInstance("EUR"))
        );

        Fueling savedFueling = mongoFuelingRepository.saveFueling(fueling);

        Fueling updatedFueling = new Fueling(
                savedFueling.getId(),
                savedFueling.getVehicleId(),
                savedFueling.getDate(),
                true,
                false,
                new Distance(2000, DistanceMeasure.KILOMETERS),
                new Volume(48.55, VolumeMeasure.LITERS),
                new Money(new BigDecimal("100"), Currency.getInstance("EUR"))
        );

        Optional<Fueling> updatedOptionalFueling = mongoFuelingRepository.updateFueling(updatedFueling);

        Fueling updatedFuelingFromDB = mongoFuelingRepository.getFueling(updatedFueling.getId()).orElse(null);

        assertAll(
                () -> assertTrue(updatedOptionalFueling.isPresent()),
                () -> assertNotNull(updatedFuelingFromDB),
                () -> assertEquals(updatedFueling, updatedFuelingFromDB)
        );
    }

    @Test
    void updateFuelingNotFoundTest() {
        Fueling fueling = new Fueling(
                new FuelingId(),
                new VehicleId(),
                Instant.now(),
                false,
                true,
                new Distance(1000, DistanceMeasure.KILOMETERS),
                new Volume(50, VolumeMeasure.LITERS),
                new Money(new BigDecimal("50"), Currency.getInstance("EUR"))
        );

        Optional<Fueling> updatedFueling = mongoFuelingRepository.updateFueling(fueling);

        assertTrue(updatedFueling.isEmpty());
    }

    @Test
    void deleteFuelingTest() {
        Fueling fueling = new Fueling(
                new FuelingId(),
                new VehicleId(),
                Instant.now(),
                false,
                true,
                new Distance(1000, DistanceMeasure.KILOMETERS),
                new Volume(50, VolumeMeasure.LITERS),
                new Money(new BigDecimal("50"), Currency.getInstance("EUR"))
        );

        mongoFuelingRepository.saveFueling(fueling);

        long deletedCount = mongoFuelingRepository.deleteFueling(fueling.getId());

        assertAll(
                () -> assertEquals(1, deletedCount),
                () -> assertTrue(mongoTemplate.find(new Query(where("_id").is(fueling.getId().id())), Fueling.class).isEmpty())
        );
    }

    @Test
    void deleteFuelingNotFoundTest() {
        FuelingId fuelingId = new FuelingId();

        long deletedCount = mongoFuelingRepository.deleteFueling(fuelingId);

        assertEquals(0, deletedCount);
    }
}
