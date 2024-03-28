package com.fishb0ness.autoawaremonitor.adapter.input.vehicle;

import com.fishb0ness.autoawaremonitor.adapter.IntegrationTest;
import com.fishb0ness.autoawaremonitor.application.VehicleModelUseCases;
import com.fishb0ness.autoawaremonitor.application.VehicleUseCases;
import com.fishb0ness.autoawaremonitor.domain.user.User;
import com.fishb0ness.autoawaremonitor.domain.user.UserId;
import com.fishb0ness.autoawaremonitor.domain.user.UserName;
import com.fishb0ness.autoawaremonitor.domain.vehicle.Vehicle;
import com.fishb0ness.autoawaremonitor.domain.vehicle.VehicleId;
import com.fishb0ness.autoawaremonitor.domain.vehicle.VehicleModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;

public class VehicleControllerIntegrationTest extends IntegrationTest {

    @SpyBean
    private VehicleUseCases vehicleUseCase;
    @SpyBean
    private VehicleModelUseCases vehicleModelUseCase;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void testCreateVehicle() throws Exception {
        User user = new User(new UserId(), new UserName("John"));
        VehicleModel vehicleModel = new VehicleModel("brand", "model");
        String exactModel = "exactModel";
        Vehicle vehicle = new Vehicle(new VehicleId(), user.getId(), vehicleModel, exactModel);
        doReturn(vehicle).when(vehicleUseCase).createVehicle(user.getId(), vehicleModel, exactModel);

        mockMvc.perform(MockMvcRequestBuilders.post("/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":\"" + user.getId().id()
                                + "\",\"brand\":\"" + vehicleModel.getBrand()
                                + "\",\"model\":\"" + vehicleModel.getModel()
                                + "\",\"exactModel\":\"" + exactModel + "\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetVehicleByIdFound() throws Exception {
        Vehicle vehicle = new Vehicle(new VehicleId(), new UserId(), new VehicleModel("brand", "model"), "exactModel");
        doReturn(Optional.of(vehicle)).when(vehicleUseCase).getVehicleById(vehicle.id());

        mockMvc.perform(MockMvcRequestBuilders.get("/vehicle?id=" + vehicle.id().id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.vehicleId", is(vehicle.id().id().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ownerId", is(vehicle.ownerId().id().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand", is(vehicle.vehicleModel().getBrand())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model", is(vehicle.vehicleModel().getModel())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.exactVehicleModel", is(vehicle.exactVehicleModel())));
    }

    @Test
    public void testGetVehicleByIdNotFound() throws Exception {
        VehicleId vehicleId = new VehicleId();
        doReturn(Optional.empty()).when(vehicleUseCase).getVehicleById(vehicleId);

        mockMvc.perform(MockMvcRequestBuilders.get("/vehicle?id=" + vehicleId.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testGetVehicleByOwnerId() throws Exception {
        User user = new User(new UserId(), new UserName("John"));
        Vehicle vehicle = new Vehicle(new VehicleId(), user.getId(), new VehicleModel("brand", "model"), "exactModel");
        doReturn(List.of(vehicle)).when(vehicleUseCase).getAllVehiclesByOwner(user.getId());

        mockMvc.perform(MockMvcRequestBuilders.get("/vehiclesByOwner?id=" + user.getId().id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].vehicleId", is(vehicle.id().id().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].ownerId", is(vehicle.ownerId().id().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].brand", is(vehicle.vehicleModel().getBrand())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].model", is(vehicle.vehicleModel().getModel())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].exactVehicleModel", is(vehicle.exactVehicleModel())));
    }

    @Test
    public void testDeleteVehicle() throws Exception {
        Vehicle vehicle = new Vehicle(new VehicleId(), new UserId(), new VehicleModel("brand", "model"), "exactModel");
        doReturn(1L).when(vehicleUseCase).deleteVehicle(vehicle.id());

        mockMvc.perform(MockMvcRequestBuilders.post("/vehiclesDelete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"vehicleId\":\"" + vehicle.id().id()
                                + "\",\"ownerId\":\"" + vehicle.ownerId().id()
                                + "\",\"brand\":\"" + vehicle.vehicleModel().getBrand()
                                + "\",\"model\":\"" + vehicle.vehicleModel().getModel()
                                + "\",\"exactVehicleModel\":\"" + vehicle.exactVehicleModel() + "\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedVehicles", is(1)));
    }

    @Test
    public void testDeleteVehicleNotFound() throws Exception {
        VehicleId vehicleId = new VehicleId();
        doReturn(0L).when(vehicleUseCase).deleteVehicle(vehicleId);

        mockMvc.perform(MockMvcRequestBuilders.post("/vehiclesDelete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"vehicleId\":\"" + vehicleId.id()
                                + "\",\"ownerId\":\"" + UUID.randomUUID()
                                + "\",\"brand\":\"brand\",\"model\":\"model\",\"exactVehicleModel\":\"exactModel\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedVehicles", is(0)));
    }

    @Test
    public void testFetchAllVehicleModels() throws Exception {
        VehicleModel vehicleModel = new VehicleModel("brand", "model");
        VehicleModel vehicleModel2 = new VehicleModel("brand2", "model2");
        VehicleModelDTO vehicleModelDto = new VehicleModelDTO(vehicleModel.getBrand(), vehicleModel.getModel());
        VehicleModelDTO vehicleModelDto2 = new VehicleModelDTO(vehicleModel2.getBrand(), vehicleModel2.getModel());
        doReturn(vehicleModel).when(vehicleModelUseCase).createVehicleModel(vehicleModelDto.getBrand(), vehicleModelDto.getModel());
        doReturn(vehicleModel2).when(vehicleModelUseCase).createVehicleModel(vehicleModelDto2.getBrand(), vehicleModelDto2.getModel());

        mockMvc.perform(MockMvcRequestBuilders.post("/vehicleModelsFetch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{\"brand\":\"" + vehicleModelDto.getBrand() + "\",\"model\":\"" + vehicleModelDto.getModel() + "\"},"
                                + "{\"brand\":\"" + vehicleModelDto2.getBrand() + "\",\"model\":\"" + vehicleModelDto2.getModel() + "\"}]"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].brand", is(vehicleModel.getBrand())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].model", is(vehicleModel.getModel())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].brand", is(vehicleModel2.getBrand())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].model", is(vehicleModel2.getModel())));
    }

    @Test
    public void testGetAllVehicleModels() throws Exception {
        VehicleModel vehicleModel = new VehicleModel("brand", "model");
        VehicleModel vehicleModel2 = new VehicleModel("brand2", "model2");
        doReturn(List.of(vehicleModel, vehicleModel2)).when(vehicleModelUseCase).getAllVehicleModels();

        mockMvc.perform(MockMvcRequestBuilders.get("/vehicleModels")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].brand", is(vehicleModel.getBrand())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].model", is(vehicleModel.getModel())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].brand", is(vehicleModel2.getBrand())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].model", is(vehicleModel2.getModel())));
    }
}
