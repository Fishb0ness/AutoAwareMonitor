package com.fishb0ness.autoawaremonitor.application;

import com.fishb0ness.autoawaremonitor.domain.fueling.Fueling;
import com.fishb0ness.autoawaremonitor.domain.fueling.FuelingId;
import com.fishb0ness.autoawaremonitor.domain.fueling.FuelingRepository;
import com.fishb0ness.autoawaremonitor.domain.measures.*;
import com.fishb0ness.autoawaremonitor.domain.user.UserId;
import com.fishb0ness.autoawaremonitor.domain.vehicle.Vehicle;
import com.fishb0ness.autoawaremonitor.domain.vehicle.VehicleId;
import com.fishb0ness.autoawaremonitor.domain.vehicle.VehicleModel;
import com.fishb0ness.autoawaremonitor.domain.vehicle.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FuelingServiceTest {

    private final FuelingRepository fuelingRepositoryMock = Mockito.mock(FuelingRepository.class);
    private final VehicleRepository vehicleRepositoryMock = Mockito.mock(VehicleRepository.class);
    private final FuelingService fuelingService = new FuelingService(fuelingRepositoryMock, vehicleRepositoryMock);

    @Captor
    ArgumentCaptor<Fueling> fuelingArgumentCaptor;

    @Test
    public void testCreateFueling() {
        Vehicle vehicle = new Vehicle(new VehicleId(), new UserId(), new VehicleModel("model", "brand"), "exactModel");
        Fueling fueling = new Fueling(new FuelingId(), vehicle.id(), Instant.now(), true, true, new Distance(100.0, DistanceMeasure.KILOMETERS), new Volume(50.0, VolumeMeasure.LITERS), new Money(new BigDecimal("50.0"), Currency.getInstance("USD")));
        when(vehicleRepositoryMock.getVehicle(vehicle.id())).thenReturn(Optional.of(vehicle));
        when(fuelingRepositoryMock.saveFueling(any(Fueling.class))).thenReturn(fueling);

        Fueling createdFueling = fuelingService.createFueling(fueling.getVehicleId(), fueling.getDate(), fueling.getIsFullTank(), fueling.getIsFirstTank(), fueling.getMileage(), fueling.getRefuelVolume(), fueling.getPaidPrice());

        assertEquals(fueling, createdFueling);
        verify(vehicleRepositoryMock, times(1)).getVehicle(vehicle.id());
        verify(fuelingRepositoryMock, times(1)).saveFueling(fuelingArgumentCaptor.capture());
        Fueling capturedFueling = fuelingArgumentCaptor.getValue();
        assertThat(capturedFueling).usingRecursiveComparison().ignoringFields("id").isEqualTo(fueling);
    }

    @Test
    public void testCreateFuelingWithInvalidVehicle() {
        Vehicle vehicle = new Vehicle(new VehicleId(), new UserId(), new VehicleModel("model", "brand"), "exactModel");
        Fueling fueling = new Fueling(new FuelingId(), vehicle.id(), Instant.now(), true, true, new Distance(100.0, DistanceMeasure.KILOMETERS), new Volume(50.0, VolumeMeasure.LITERS), new Money(new BigDecimal("50.0"), Currency.getInstance("USD")));
        when(vehicleRepositoryMock.getVehicle(vehicle.id())).thenReturn(Optional.empty());

        try {
            fuelingService.createFueling(fueling.getVehicleId(), fueling.getDate(), fueling.getIsFullTank(), fueling.getIsFirstTank(), fueling.getMileage(), fueling.getRefuelVolume(), fueling.getPaidPrice());
        } catch (IllegalArgumentException e) {
            assertEquals("Vehicle not found", e.getMessage());
        }
        verify(vehicleRepositoryMock, times(1)).getVehicle(vehicle.id());
        verify(fuelingRepositoryMock, never()).saveFueling(any(Fueling.class));
    }

    @Test
    public void testGetFuelingById() {
        Fueling fueling = new Fueling(new FuelingId(), new VehicleId(), Instant.now(), true, true, new Distance(100.0, DistanceMeasure.KILOMETERS), new Volume(50.0, VolumeMeasure.LITERS), new Money(new BigDecimal("50.0"), Currency.getInstance("USD")));
        when(fuelingRepositoryMock.getFueling(fueling.getId())).thenReturn(Optional.of(fueling));

        Optional<Fueling> foundFueling = fuelingService.getFuelingById(fueling.getId());

        assertEquals(fueling, foundFueling.get());
        verify(fuelingRepositoryMock, times(1)).getFueling(fueling.getId());
    }

    @Test
    public void testGetFuelingByIdNotFound() {
        FuelingId fuelingId = new FuelingId();
        when(fuelingRepositoryMock.getFueling(fuelingId)).thenReturn(Optional.empty());

        Optional<Fueling> foundFueling = fuelingService.getFuelingById(fuelingId);

        assertEquals(Optional.empty(), foundFueling);
        verify(fuelingRepositoryMock, times(1)).getFueling(fuelingId);
    }

    @Test
    public void testGetAllFuelingByVehicleId() {
        VehicleId vehicleId = new VehicleId();
        Fueling fueling1 = new Fueling(new FuelingId(), vehicleId, Instant.now(), false, true, new Distance(100.0, DistanceMeasure.KILOMETERS), new Volume(50.0, VolumeMeasure.LITERS), new Money(new BigDecimal("50.0"), Currency.getInstance("USD")));
        Fueling fueling2 = new Fueling(new FuelingId(), vehicleId, Instant.now(), true, false, new Distance(200.0, DistanceMeasure.KILOMETERS), new Volume(100.0, VolumeMeasure.LITERS), new Money(new BigDecimal("100.0"), Currency.getInstance("USD")));
        when(fuelingRepositoryMock.getAllFuelingByVehicleId(vehicleId)).thenReturn(List.of(fueling1, fueling2));

        List<Fueling> result = fuelingService.getAllFuelingByVehicleId(vehicleId);

        assertEquals(List.of(fueling1, fueling2), result);
        verify(fuelingRepositoryMock, times(1)).getAllFuelingByVehicleId(vehicleId);
    }

    @Test
    public void testUpdateFuelingInfo() {
        Fueling fueling = new Fueling(new FuelingId(), new VehicleId(), Instant.now(), false, true, new Distance(100.0, DistanceMeasure.KILOMETERS), new Volume(50.0, VolumeMeasure.LITERS), new Money(new BigDecimal("50.0"), Currency.getInstance("USD")));
        when(fuelingRepositoryMock.updateFueling(fueling)).thenReturn(Optional.of(fueling));

        Optional<Fueling> updatedFueling = fuelingService.updateFuelingInfo(fueling);

        assertEquals(fueling, updatedFueling.get());
        verify(fuelingRepositoryMock, times(1)).updateFueling(fueling);
    }

    @Test
    public void testUpdateFuelingInfoNotFound() {
        Fueling fueling = new Fueling(new FuelingId(), new VehicleId(), Instant.now(), false, true, new Distance(100.0, DistanceMeasure.KILOMETERS), new Volume(50.0, VolumeMeasure.LITERS), new Money(new BigDecimal("50.0"), Currency.getInstance("USD")));
        when(fuelingRepositoryMock.updateFueling(fueling)).thenReturn(Optional.empty());

        Optional<Fueling> updatedFueling = fuelingService.updateFuelingInfo(fueling);

        assertEquals(Optional.empty(), updatedFueling);
        verify(fuelingRepositoryMock, times(1)).updateFueling(fueling);
    }

    @Test
    public void testDeleteFueling() {
        FuelingId fuelingId = new FuelingId();
        when(fuelingRepositoryMock.deleteFueling(fuelingId)).thenReturn(1L);

        long result = fuelingService.deleteFueling(fuelingId);

        assertEquals(1L, result);
        verify(fuelingRepositoryMock, times(1)).deleteFueling(fuelingId);
    }
}
