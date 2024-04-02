package com.fishb0ness.autoawaremonitor.adapter.input.fueling;

import com.fishb0ness.autoawaremonitor.application.FuelingUseCases;
import com.fishb0ness.autoawaremonitor.domain.fueling.FuelingId;
import com.fishb0ness.autoawaremonitor.domain.measures.*;
import com.fishb0ness.autoawaremonitor.domain.vehicle.VehicleId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class FuelingController {

    private final FuelingUseCases fuelingUseCase;

    public FuelingController(FuelingUseCases fuelingUseCase) {
        this.fuelingUseCase = fuelingUseCase;
    }

    @PostMapping("/fuelings")
    public FuelingDTO createFueling(@RequestBody FuelingRequest fuelingRequest) {
        Distance mileage = new Distance(fuelingRequest.getMileage(), DistanceMeasure.valueOf(fuelingRequest.getMileageUnit()));
        Volume refuelVolume = new Volume(fuelingRequest.getRefuelQuantity(), VolumeMeasure.valueOf(fuelingRequest.getRefuelQuantityUnit()));
        Money paidPrice = new Money(new BigDecimal(fuelingRequest.getPaidPrice()), Currency.getInstance(fuelingRequest.getPaidPriceCurrency()));
        return FuelingDTO.fromDomain(fuelingUseCase.createFueling(new VehicleId(UUID.fromString(fuelingRequest.getVehicleId())), fuelingRequest.getDate(), fuelingRequest.getIsFullTank(), fuelingRequest.getIsFirstTank(), mileage, refuelVolume, paidPrice));
    }

    @GetMapping("/fueling")
    public ResponseEntity<FuelingDTO> getFuelingById(@RequestParam(value = "id") String id) {
        Optional<FuelingDTO> fuelingDTO = fuelingUseCase.getFuelingById(new FuelingId(UUID.fromString(id))).map(FuelingDTO::fromDomain);
        return fuelingDTO.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/fuelingByVehicle")
    public List<FuelingDTO> getFuelingByVehicleId(@RequestParam(value = "id") String vehicleId) {
        return fuelingUseCase.getAllFuelingByVehicleId(new VehicleId(UUID.fromString(vehicleId))).stream().map(FuelingDTO::fromDomain).collect(Collectors.toList());
    }

    @PostMapping("/fuelingDelete")
    public ResponseEntity<String> deleteFueling(@RequestBody FuelingDTO fuelingDto) {
        long fuelingsDeleted = fuelingUseCase.deleteFueling(FuelingDTO.toDomain(fuelingDto).getId());
        return fuelingsDeleted > 0 ? ResponseEntity.ok("Nº of Fueling deleted: " + fuelingsDeleted) : ResponseEntity.notFound().build();
    }

    @PutMapping("/fuelingUpdate")
    public ResponseEntity<FuelingDTO> updateFueling(@RequestBody FuelingDTO fuelingDto) {
        Optional<FuelingDTO> optionalFuelingDTO = fuelingUseCase.updateFuelingInfo(FuelingDTO.toDomain(fuelingDto)).map(FuelingDTO::fromDomain);
        return optionalFuelingDTO.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
