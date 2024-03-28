package com.fishb0ness.autoawaremonitor.application;

import com.fishb0ness.autoawaremonitor.domain.vehicle.VehicleModel;
import com.fishb0ness.autoawaremonitor.domain.vehicle.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class VehicleModelServiceTest {

    VehicleRepository vehicleRepositoryMock = Mockito.mock(VehicleRepository.class);
    VehicleModelService vehicleModelService = new VehicleModelService(vehicleRepositoryMock);

    @Test
    public void testCreateVehicleModel() {
        String brand = "brand";
        String model = "model";
        VehicleModel vehicleModel = new VehicleModel(brand, model);
        when(vehicleRepositoryMock.saveVehicleModel(brand, model)).thenReturn(vehicleModel);

        VehicleModel result = vehicleModelService.createVehicleModel(brand, model);

        assertEquals(vehicleModel, result);
        verify(vehicleRepositoryMock, times(1)).saveVehicleModel(brand, model);
    }

    @Test
    public void testGetVehicleModelById() {
        String brand = "brand";
        String model = "model";
        VehicleModel vehicleModel = new VehicleModel(brand, model);
        Optional<VehicleModel> vehicleModelOptional = Optional.of(vehicleModel);
        when(vehicleRepositoryMock.getVehicleModel(vehicleModel.getVehicleModelId())).thenReturn(vehicleModelOptional);

        Optional<VehicleModel> result = vehicleModelService.getVehicleModelById(vehicleModel.getVehicleModelId());

        assertEquals(vehicleModelOptional, result);
        verify(vehicleRepositoryMock, times(1)).getVehicleModel(vehicleModel.getVehicleModelId());
    }

    @Test
    public void testGetVehicleModelByIdWhenVehicleModelDoesNotExist() {
        String brand = "brand";
        String model = "model";
        VehicleModel vehicleModel = new VehicleModel(brand, model);
        Optional<VehicleModel> vehicleModelOptional = Optional.empty();
        when(vehicleRepositoryMock.getVehicleModel(vehicleModel.getVehicleModelId())).thenReturn(Optional.empty());

        Optional<VehicleModel> result = vehicleModelService.getVehicleModelById(vehicleModel.getVehicleModelId());

        assertEquals(vehicleModelOptional, result);
        verify(vehicleRepositoryMock, times(1)).getVehicleModel(vehicleModel.getVehicleModelId());
    }

    @Test
    public void getAllVehicleModelBrands() {
        String brand = "brand";
        String model = "model";
        VehicleModel vehicleModel = new VehicleModel(brand, model);
        String brand2 = "brand2";
        String model2 = "model2";
        VehicleModel vehicleModel2 = new VehicleModel(brand2, model2);
        when(vehicleRepositoryMock.getAllVehicleModels()).thenReturn(List.of(vehicleModel, vehicleModel2));

        List<String> result = vehicleModelService.getAllVehicleModelBrands();

        assertEquals(List.of(StringUtils.capitalize(brand), StringUtils.capitalize(brand2)), result);
        verify(vehicleRepositoryMock, times(1)).getAllVehicleModels();
    }

    @Test
    public void getVehicleModelByBrand() {
        String brand = "brand";
        String model = "model";
        VehicleModel vehicleModel = new VehicleModel(brand, model);
        String brand2 = "brand";
        String model2 = "model2";
        VehicleModel vehicleModel2 = new VehicleModel(brand2, model2);
        when(vehicleRepositoryMock.getAllVehicleModelsByBrand(brand)).thenReturn(List.of(vehicleModel, vehicleModel2));

        var result = vehicleModelService.getVehicleModelByBrand(brand);

        assertEquals(List.of(vehicleModel, vehicleModel2), result);
        verify(vehicleRepositoryMock, times(1)).getAllVehicleModelsByBrand(brand);
    }
}
