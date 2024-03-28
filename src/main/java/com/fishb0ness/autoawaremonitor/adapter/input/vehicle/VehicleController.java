package com.fishb0ness.autoawaremonitor.adapter.input.vehicle;

import com.fishb0ness.autoawaremonitor.application.VehicleModelUseCases;
import com.fishb0ness.autoawaremonitor.application.VehicleUseCases;
import com.fishb0ness.autoawaremonitor.domain.user.UserId;
import com.fishb0ness.autoawaremonitor.domain.vehicle.VehicleId;
import com.fishb0ness.autoawaremonitor.domain.vehicle.VehicleModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class VehicleController {

    private final VehicleUseCases vehicleUseCase;
    private final VehicleModelUseCases vehicleModelUseCase;

    public VehicleController(VehicleUseCases vehicleUseCase, VehicleModelUseCases vehicleModelUseCase) {
        this.vehicleUseCase = vehicleUseCase;
        this.vehicleModelUseCase = vehicleModelUseCase;
    }

    @PostMapping("/vehicles")
    public VehicleDTO createVehicle(@RequestBody VehicleRequest vehicleRequest) {
        UserId ownerId = new UserId(UUID.fromString(vehicleRequest.getUserId()));
        VehicleModel vehicleModel = new VehicleModel(vehicleRequest.getBrand(), vehicleRequest.getModel());
        return VehicleDTO.fromDomain(vehicleUseCase.createVehicle(ownerId, vehicleModel, vehicleRequest.getExactModel()));
    }

    @GetMapping("/vehicle")
    public ResponseEntity<VehicleDTO> getVehicleById(@RequestParam(value = "id") String id) {
        Optional<VehicleDTO> vehicleDTO = vehicleUseCase.getVehicleById(new VehicleId(UUID.fromString(id))).map(VehicleDTO::fromDomain);
        return vehicleDTO.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/vehiclesByOwner")
    public List<VehicleDTO> getVehicleByOwnerId(@RequestParam(value = "id") String ownerId) {
        return vehicleUseCase.getAllVehiclesByOwner(new UserId(UUID.fromString(ownerId))).stream().map(VehicleDTO::fromDomain).collect(Collectors.toList());
    }

    @PostMapping("/vehiclesDelete")
    public VehicleDeleteResponse deleteVehicle(@RequestBody VehicleDTO vehicleDto) {
        long vehiclesDeleted = vehicleUseCase.deleteVehicle(VehicleDTO.toDomain(vehicleDto).id());
        return new VehicleDeleteResponse(vehiclesDeleted);
    }

    @PostMapping("/vehicleModelsFetch")
    public List<VehicleModelDTO> fetchAllVehicleModels(@RequestBody List<VehicleModelDTO> vehicleModelDtos) {
        return vehicleModelDtos.stream()
                .map(vehicleModelDto -> vehicleModelUseCase.createVehicleModel(vehicleModelDto.getBrand(), vehicleModelDto.getModel()))
                .map(VehicleModelDTO::fromDomain)
                .toList();
    }

    @GetMapping("/vehicleModels")
    public List<VehicleModelDTO> getAllVehicleModels() {
        return vehicleModelUseCase.getAllVehicleModels().stream().map(VehicleModelDTO::fromDomain).collect(Collectors.toList());
    }
}
