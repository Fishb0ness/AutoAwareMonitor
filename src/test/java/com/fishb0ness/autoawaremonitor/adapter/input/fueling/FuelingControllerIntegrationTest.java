package com.fishb0ness.autoawaremonitor.adapter.input.fueling;

import com.fishb0ness.autoawaremonitor.adapter.IntegrationTest;
import com.fishb0ness.autoawaremonitor.application.FuelingUseCases;
import com.fishb0ness.autoawaremonitor.domain.fueling.Fueling;
import com.fishb0ness.autoawaremonitor.domain.fueling.FuelingId;
import com.fishb0ness.autoawaremonitor.domain.measures.*;
import com.fishb0ness.autoawaremonitor.domain.vehicle.VehicleId;
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

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;

public class FuelingControllerIntegrationTest extends IntegrationTest {

    @SpyBean
    private FuelingUseCases fuelingUseCase;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void testCreateFueling() throws Exception {
        VehicleId vehicleId = new VehicleId();
        Instant instant = Instant.now();
        boolean isFullTank = true;
        boolean isFirstTank = false;
        Distance distance = new Distance(1000, DistanceMeasure.KILOMETERS);
        Volume volume = new Volume(50, VolumeMeasure.LITERS);
        Money money = new Money(new BigDecimal(50), Currency.getInstance("EUR"));
        Fueling fueling = new Fueling(new FuelingId(), vehicleId, instant, isFullTank, isFirstTank, distance, volume, money);
        doReturn(fueling).when(fuelingUseCase).createFueling(fueling.getVehicleId(), fueling.getDate(), fueling.getIsFullTank(), fueling.getIsFirstTank(), fueling.getMileage(), fueling.getRefuelVolume(), fueling.getPaidPrice());

        mockMvc.perform(MockMvcRequestBuilders.post("/fuelings")
                        .contentType("application/json")
                        .content("{\"vehicleId\":\"" + fueling.getVehicleId().id()
                                + "\",\"date\":\"" + fueling.getDate()
                                + "\",\"isFullTank\":\"" + fueling.getIsFullTank()
                                + "\",\"isFirstTank\":\"" + fueling.getIsFirstTank()
                                + "\",\"mileage\":\"" + fueling.getMileage().getDistance()
                                + "\",\"mileageUnit\":\"" + fueling.getMileage().getDistanceMeasure().name()
                                + "\",\"refuelQuantity\":\"" + fueling.getRefuelVolume().getQuantity()
                                + "\",\"refuelQuantityUnit\":\"" + fueling.getRefuelVolume().getVolumeMeasure().name()
                                + "\",\"paidPrice\":\"" + fueling.getPaidPrice().getAmount().toString()
                                + "\",\"paidPriceCurrency\":\"" + fueling.getPaidPrice().getCurrency().getCurrencyCode() + "\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetFuelingById() throws Exception {
        Fueling fueling = new Fueling(new FuelingId(), new VehicleId(), Instant.now(), false, true, new Distance(1000, DistanceMeasure.KILOMETERS), new Volume(50, VolumeMeasure.LITERS), new Money(new BigDecimal(50), Currency.getInstance("EUR")));
        doReturn(Optional.of(fueling)).when(fuelingUseCase).getFuelingById(fueling.getId());

        mockMvc.perform(MockMvcRequestBuilders.get("/fueling?id=" + fueling.getId().id())
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fuelingId").value(fueling.getId().id().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vehicleId").value(fueling.getVehicleId().id().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.date").value(fueling.getDate().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isFullTank").value(fueling.getIsFullTank()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isFirstTank").value(fueling.getIsFirstTank()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mileage").value(fueling.getMileage().getDistance()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mileageUnit").value(fueling.getMileage().getDistanceMeasure().name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.refuelQuantity").value(fueling.getRefuelVolume().getQuantity()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.refuelQuantityUnit").value(fueling.getRefuelVolume().getVolumeMeasure().name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paidPrice").value(fueling.getPaidPrice().getAmount().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paidPriceCurrency").value(fueling.getPaidPrice().getCurrency().getCurrencyCode()));
    }

    @Test
    public void testGetFuelingByIdNotFound() throws Exception {
        FuelingId fuelingId = new FuelingId();
        doReturn(Optional.empty()).when(fuelingUseCase).getFuelingById(fuelingId);

        mockMvc.perform(MockMvcRequestBuilders.get("/fueling?id=" + fuelingId.id())
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testGetFuelingByVehicleId() throws Exception {
        Fueling fueling = new Fueling(new FuelingId(), new VehicleId(), Instant.now(), false, true, new Distance(1000, DistanceMeasure.KILOMETERS), new Volume(50, VolumeMeasure.LITERS), new Money(new BigDecimal(50), Currency.getInstance("EUR")));
        doReturn(List.of(fueling)).when(fuelingUseCase).getAllFuelingByVehicleId(fueling.getVehicleId());

        mockMvc.perform(MockMvcRequestBuilders.get("/fuelingByVehicle?id=" + fueling.getVehicleId().id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].fuelingId").value(fueling.getId().id().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].vehicleId").value(fueling.getVehicleId().id().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].date").value(fueling.getDate().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].isFullTank").value(fueling.getIsFullTank()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].isFirstTank").value(fueling.getIsFirstTank()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].mileage").value(fueling.getMileage().getDistance()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].mileageUnit").value(fueling.getMileage().getDistanceMeasure().name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].refuelQuantity").value(fueling.getRefuelVolume().getQuantity()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].refuelQuantityUnit").value(fueling.getRefuelVolume().getVolumeMeasure().name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].paidPrice").value(fueling.getPaidPrice().getAmount().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].paidPriceCurrency").value(fueling.getPaidPrice().getCurrency().getCurrencyCode()));
    }

    @Test
    public void testDeleteFueling() throws Exception {
        Fueling fueling = new Fueling(new FuelingId(), new VehicleId(), Instant.now(), false, true, new Distance(1000, DistanceMeasure.KILOMETERS), new Volume(50, VolumeMeasure.LITERS), new Money(new BigDecimal(50), Currency.getInstance("EUR")));
        doReturn(1L).when(fuelingUseCase).deleteFueling(fueling.getId());

        mockMvc.perform(MockMvcRequestBuilders.post("/fuelingDelete")
                        .contentType("application/json")
                        .content("{\"fuelingId\":\"" + fueling.getId().id()
                                + "\",\"vehicleId\":\"" + fueling.getVehicleId().id()
                                + "\",\"date\":\"" + fueling.getDate()
                                + "\",\"isFullTank\":\"" + fueling.getIsFullTank()
                                + "\",\"isFirstTank\":\"" + fueling.getIsFirstTank()
                                + "\",\"mileage\":\"" + fueling.getMileage().getDistance()
                                + "\",\"mileageUnit\":\"" + fueling.getMileage().getDistanceMeasure().name()
                                + "\",\"refuelQuantity\":\"" + fueling.getRefuelVolume().getQuantity()
                                + "\",\"refuelQuantityUnit\":\"" + fueling.getRefuelVolume().getVolumeMeasure().name()
                                + "\",\"paidPrice\":\"" + fueling.getPaidPrice().getAmount().toString()
                                + "\",\"paidPriceCurrency\":\"" + fueling.getPaidPrice().getCurrency().getCurrencyCode() + "\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateFueling() throws Exception {
        Fueling fueling = new Fueling(new FuelingId(), new VehicleId(), Instant.now(), false, true, new Distance(1000, DistanceMeasure.KILOMETERS), new Volume(50, VolumeMeasure.LITERS), new Money(new BigDecimal(50), Currency.getInstance("EUR")));
        doReturn(Optional.of(fueling)).when(fuelingUseCase).updateFuelingInfo(fueling);

        mockMvc.perform(MockMvcRequestBuilders.put("/fuelingUpdate")
                        .contentType("application/json")
                        .content("{\"fuelingId\":\"" + fueling.getId().id()
                                + "\",\"vehicleId\":\"" + fueling.getVehicleId().id()
                                + "\",\"date\":\"" + fueling.getDate()
                                + "\",\"isFullTank\":\"" + fueling.getIsFullTank()
                                + "\",\"isFirstTank\":\"" + fueling.getIsFirstTank()
                                + "\",\"mileage\":\"" + fueling.getMileage().getDistance()
                                + "\",\"mileageUnit\":\"" + fueling.getMileage().getDistanceMeasure().name()
                                + "\",\"refuelQuantity\":\"" + fueling.getRefuelVolume().getQuantity()
                                + "\",\"refuelQuantityUnit\":\"" + fueling.getRefuelVolume().getVolumeMeasure().name()
                                + "\",\"paidPrice\":\"" + fueling.getPaidPrice().getAmount().toString()
                                + "\",\"paidPriceCurrency\":\"" + fueling.getPaidPrice().getCurrency().getCurrencyCode() + "\"}"))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.fuelingId").value(fueling.getId().id().toString()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.vehicleId").value(fueling.getVehicleId().id().toString()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.date").value(fueling.getDate().toString()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.isFullTank").value(fueling.getIsFullTank()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.isFirstTank").value(fueling.getIsFirstTank()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.mileage").value(fueling.getMileage().getDistance()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.mileageUnit").value(fueling.getMileage().getDistanceMeasure().name()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.refuelQuantity").value(fueling.getRefuelVolume().getQuantity()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.refuelQuantityUnit").value(fueling.getRefuelVolume().getVolumeMeasure().name()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.paidPrice").value(fueling.getPaidPrice().getAmount().toString()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.paidPriceCurrency").value(fueling.getPaidPrice().getCurrency().getCurrencyCode()));
    }

    @Test
    public void testUpdateFuelingNotFound() throws Exception {
        Fueling fueling = new Fueling(new FuelingId(), new VehicleId(), Instant.now(), false, true, new Distance(1000, DistanceMeasure.KILOMETERS), new Volume(50, VolumeMeasure.LITERS), new Money(new BigDecimal(50), Currency.getInstance("EUR")));
        doReturn(Optional.empty()).when(fuelingUseCase).updateFuelingInfo(fueling);

        mockMvc.perform(MockMvcRequestBuilders.put("/fuelingUpdate")
                        .contentType("application/json")
                        .content("{\"fuelingId\":\"" + fueling.getId().id()
                                + "\",\"vehicleId\":\"" + fueling.getVehicleId().id()
                                + "\",\"date\":\"" + fueling.getDate()
                                + "\",\"isFullTank\":\"" + fueling.getIsFullTank()
                                + "\",\"isFirstTank\":\"" + fueling.getIsFirstTank()
                                + "\",\"mileage\":\"" + fueling.getMileage().getDistance()
                                + "\",\"mileageUnit\":\"" + fueling.getMileage().getDistanceMeasure().name()
                                + "\",\"refuelQuantity\":\"" + fueling.getRefuelVolume().getQuantity()
                                + "\",\"refuelQuantityUnit\":\"" + fueling.getRefuelVolume().getVolumeMeasure().name()
                                + "\",\"paidPrice\":\"" + fueling.getPaidPrice().getAmount().toString()
                                + "\",\"paidPriceCurrency\":\"" + fueling.getPaidPrice().getCurrency().getCurrencyCode() + "\"}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
