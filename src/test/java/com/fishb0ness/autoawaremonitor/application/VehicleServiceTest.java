package com.fishb0ness.autoawaremonitor.application;

import com.fishb0ness.autoawaremonitor.domain.user.User;
import com.fishb0ness.autoawaremonitor.domain.user.UserId;
import com.fishb0ness.autoawaremonitor.domain.user.UserName;
import com.fishb0ness.autoawaremonitor.domain.user.UserRepository;
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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VehicleServiceTest {

    VehicleRepository vehicleRepositoryMock = Mockito.mock(VehicleRepository.class);
    UserRepository userRepositoryMock = Mockito.mock(UserRepository.class);
    VehicleService vehicleService = new VehicleService(vehicleRepositoryMock, userRepositoryMock);

    @Captor
    ArgumentCaptor<Vehicle> vehicleArgumentCaptor;

    @Test
    public void testCreateVehicle() {
        UserId userId = new UserId();
        UserName userName = new UserName("John Doe");
        User user = new User(userId, userName);
        VehicleModel vehicleModel = new VehicleModel("Brand", "Model");
        String exactVehicleModel = "Exact Vehicle Model";
        Vehicle vehicle = new Vehicle(new VehicleId(), userId, vehicleModel, exactVehicleModel);
        when(userRepositoryMock.getUser(userId)).thenReturn(Optional.of(user));
        when(vehicleRepositoryMock.getVehicleModel(vehicleModel.getVehicleModelId())).thenReturn(Optional.of(vehicleModel));
        when(vehicleRepositoryMock.saveVehicle(any(Vehicle.class))).thenReturn(vehicle);

        Vehicle result = vehicleService.createVehicle(userId, vehicleModel, exactVehicleModel);

        assertEquals(vehicle, result);
        verify(userRepositoryMock, times(1)).getUser(userId);
        verify(vehicleRepositoryMock, times(1)).getVehicleModel(vehicleModel.getVehicleModelId());
        verify(vehicleRepositoryMock, times(1)).saveVehicle(vehicleArgumentCaptor.capture());
        Vehicle savedVehicle = vehicleArgumentCaptor.getValue();
        assertThat(savedVehicle).usingRecursiveComparison().ignoringFields("id").isEqualTo(vehicle);
    }

    @Test
    public void testCreateVehicleWithInvalidUser() {
        UserId userId = new UserId();
        VehicleModel vehicleModel = new VehicleModel("Brand", "Model");
        String exactVehicleModel = "Exact Vehicle Model";
        when(userRepositoryMock.getUser(userId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> vehicleService.createVehicle(userId, vehicleModel, exactVehicleModel));
        assertEquals("User not found", exception.getMessage());
        verify(userRepositoryMock, times(1)).getUser(userId);
        verify(vehicleRepositoryMock, never()).getVehicleModel(any());
        verify(vehicleRepositoryMock, never()).saveVehicle(any());
    }

    @Test
    public void testCreateVehicleWithInvalidVehicleModel() {
        UserId userId = new UserId();
        UserName userName = new UserName("John Doe");
        User user = new User(userId, userName);
        VehicleModel vehicleModel = new VehicleModel("Brand", "Model");
        String exactVehicleModel = "Exact Vehicle Model";
        when(userRepositoryMock.getUser(userId)).thenReturn(Optional.of(user));
        when(vehicleRepositoryMock.getVehicleModel(vehicleModel.getVehicleModelId())).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> vehicleService.createVehicle(userId, vehicleModel, exactVehicleModel));
        assertEquals("Vehicle model not found", exception.getMessage());
        verify(userRepositoryMock, times(1)).getUser(userId);
        verify(vehicleRepositoryMock, times(1)).getVehicleModel(vehicleModel.getVehicleModelId());
        verify(vehicleRepositoryMock, never()).saveVehicle(any());
    }

    @Test
    public void testGetVehicleById() {
        VehicleId vehicleId = new VehicleId();
        Vehicle vehicle = new Vehicle(vehicleId, new UserId(), new VehicleModel("Brand", "Model"), "Exact Vehicle Model");
        when(vehicleRepositoryMock.getVehicle(vehicleId)).thenReturn(Optional.of(vehicle));

        Optional<Vehicle> result = vehicleService.getVehicleById(vehicleId);

        assertEquals(Optional.of(vehicle), result);
        verify(vehicleRepositoryMock, times(1)).getVehicle(vehicleId);
    }

    @Test
    public void testGetVehicleByIdWithInvalidVehicle() {
        VehicleId vehicleId = new VehicleId();
        when(vehicleRepositoryMock.getVehicle(vehicleId)).thenReturn(Optional.empty());

        Optional<Vehicle> result = vehicleService.getVehicleById(vehicleId);

        assertEquals(Optional.empty(), result);
        verify(vehicleRepositoryMock, times(1)).getVehicle(vehicleId);
    }

    @Test
    public void testGetAllVehiclesByOwner() {
        UserId userId = new UserId();
        Vehicle vehicle1 = new Vehicle(new VehicleId(), userId, new VehicleModel("Brand", "Model"), "Exact Vehicle Model");
        Vehicle vehicle2 = new Vehicle(new VehicleId(), userId, new VehicleModel("Brand", "Model"), "Exact Vehicle Model");
        when(userRepositoryMock.getUser(userId)).thenReturn(Optional.of(new User(userId, new UserName("John Doe"))));
        when(vehicleRepositoryMock.getAllVehiclesByOwner(userId)).thenReturn(List.of(vehicle1, vehicle2));

        List<Vehicle> result = vehicleService.getAllVehiclesByOwner(userId);

        assertEquals(List.of(vehicle1, vehicle2), result);
        verify(userRepositoryMock, times(1)).getUser(userId);
        verify(vehicleRepositoryMock, times(1)).getAllVehiclesByOwner(userId);
    }

    @Test
    public void testGetAllVehiclesByOwnerWithNoVehicles() {
        UserId userId = new UserId();
        when(userRepositoryMock.getUser(userId)).thenReturn(Optional.of(new User(userId, new UserName("John Doe"))));
        when(vehicleRepositoryMock.getAllVehiclesByOwner(userId)).thenReturn(List.of());

        List<Vehicle> result = vehicleService.getAllVehiclesByOwner(userId);

        assertEquals(List.of(), result);
        verify(userRepositoryMock, times(1)).getUser(userId);
        verify(vehicleRepositoryMock, times(1)).getAllVehiclesByOwner(userId);
    }

    @Test
    public void testGetAllVehiclesByOwnerWithInvalidUser() {
        UserId userId = new UserId();
        when(userRepositoryMock.getUser(userId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> vehicleService.getAllVehiclesByOwner(userId));
        assertEquals("User not found", exception.getMessage());
        verify(userRepositoryMock, times(1)).getUser(userId);
        verify(vehicleRepositoryMock, never()).getAllVehiclesByOwner(any());
    }

    @Test
    public void testUpdateVehicleModel() {
        VehicleId vehicleId = new VehicleId();
        UserId userId = new UserId();
        VehicleModel vehicleModel = new VehicleModel("Brand", "Model");
        String exactVehicleModel = "Exact Vehicle Model";
        Vehicle oldVehicle = new Vehicle(vehicleId, userId, new VehicleModel("Old Brand", "Old Model"), "Old Exact Vehicle Model");
        Vehicle newVehicle = new Vehicle(vehicleId, userId, vehicleModel, exactVehicleModel);
        when(vehicleRepositoryMock.getVehicle(vehicleId)).thenReturn(Optional.of(oldVehicle));
        when(vehicleRepositoryMock.getVehicleModel(vehicleModel.getVehicleModelId())).thenReturn(Optional.of(vehicleModel));
        when(vehicleRepositoryMock.updateVehicleModel(newVehicle)).thenReturn(Optional.of(newVehicle));

        Optional<Vehicle> result = vehicleService.updateVehicleModel(vehicleId, vehicleModel, exactVehicleModel);

        assertEquals(Optional.of(newVehicle), result);
        verify(vehicleRepositoryMock, times(1)).getVehicle(vehicleId);
        verify(vehicleRepositoryMock, times(1)).getVehicleModel(vehicleModel.getVehicleModelId());
        verify(vehicleRepositoryMock, times(1)).updateVehicleModel(newVehicle);
    }

    @Test
    public void testUpdateVehicleModelWithInvalidVehicleId() {
        VehicleId vehicleId = new VehicleId();
        VehicleModel vehicleModel = new VehicleModel("Brand", "Model");
        String exactVehicleModel = "Exact Vehicle Model";
        when(vehicleRepositoryMock.getVehicle(vehicleId)).thenReturn(Optional.empty());

        Optional<Vehicle> result = vehicleService.updateVehicleModel(vehicleId, vehicleModel, exactVehicleModel);

        assertEquals(Optional.empty(), result);
        verify(vehicleRepositoryMock, times(1)).getVehicle(vehicleId);
        verify(vehicleRepositoryMock, never()).getVehicleModel(any());
        verify(vehicleRepositoryMock, never()).updateVehicleModel(any());
    }

    @Test
    public void testUpdateVehicleModelWithInvalidModel() {
        VehicleId vehicleId = new VehicleId();
        UserId userId = new UserId();
        VehicleModel vehicleModel = new VehicleModel("Invalid", "Invalid");
        String exactVehicleModel = "Exact Vehicle Model";
        Vehicle oldVehicle = new Vehicle(vehicleId, userId, new VehicleModel("Old Brand", "Old Model"), "Old Exact Vehicle Model");
        when(vehicleRepositoryMock.getVehicle(vehicleId)).thenReturn(Optional.of(oldVehicle));
        when(vehicleRepositoryMock.getVehicleModel(vehicleModel.getVehicleModelId())).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> vehicleService.updateVehicleModel(vehicleId, vehicleModel, exactVehicleModel));
        assertEquals("Vehicle model not found", exception.getMessage());
        verify(vehicleRepositoryMock, times(1)).getVehicle(vehicleId);
        verify(vehicleRepositoryMock, times(1)).getVehicleModel(vehicleModel.getVehicleModelId());
        verify(vehicleRepositoryMock, never()).updateVehicleModel(any());
    }

    @Test
    public void testDeleteVehicle() {
        VehicleId vehicleId = new VehicleId();
        long deletedVehicles = 1L;
        when(vehicleRepositoryMock.deleteVehicle(vehicleId)).thenReturn(deletedVehicles);

        long result = vehicleService.deleteVehicle(vehicleId);

        assertEquals(deletedVehicles, result);
        verify(vehicleRepositoryMock, times(1)).deleteVehicle(vehicleId);
    }

    @Test
    public void testDeleteVehicleWithNoMatchVehicleId() {
        VehicleId vehicleId = new VehicleId();
        when(vehicleRepositoryMock.deleteVehicle(vehicleId)).thenReturn(0L);

        long result = vehicleService.deleteVehicle(vehicleId);

        assertEquals(0L, result);
        verify(vehicleRepositoryMock, times(1)).deleteVehicle(vehicleId);
    }
}
