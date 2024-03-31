package com.fishb0ness.autoawaremonitor.adapter.output.mongodb.fueling;

import com.fishb0ness.autoawaremonitor.domain.fueling.Fueling;
import com.fishb0ness.autoawaremonitor.domain.fueling.FuelingId;
import com.fishb0ness.autoawaremonitor.domain.fueling.FuelingRepository;
import com.fishb0ness.autoawaremonitor.domain.measures.*;
import com.fishb0ness.autoawaremonitor.domain.vehicle.VehicleId;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Currency;

public class MongoFuelingRepository implements FuelingRepository {

    private final FuelingMongoRespository fuelingMongoRespository;

    public MongoFuelingRepository(FuelingMongoRespository fuelingMongoRespository) {
        this.fuelingMongoRespository = fuelingMongoRespository;
    }

    @Override
    public Fueling saveFueling(Fueling fueling) {
        FuelingMongoOutDTO vehicleMongoOutDTO = toFuelingDTO(fueling);
        return toFueling(fuelingMongoRespository.insert(vehicleMongoOutDTO));
    }

    @Override
    public Optional<Fueling> getFueling(FuelingId fuelingId) {
        Optional<FuelingMongoOutDTO> optionalFuelingMongoOutDto = fuelingMongoRespository.findByFuelingId(fuelingId.id());
        return optionalFuelingMongoOutDto.map(this::toFueling);
    }

    @Override
    public List<Fueling> getAllFuelingByVehicleId(VehicleId vehicleId) {
        return fuelingMongoRespository.findByVehicleId(vehicleId.id()).stream().map(this::toFueling).toList();
    }

    @Override
    public Optional<Fueling> updateFueling(Fueling fueling) {
        Optional<FuelingMongoOutDTO> optionalFuelingMongoOutDto = fuelingMongoRespository.findByFuelingId(fueling.getId().id());
        if (optionalFuelingMongoOutDto.isEmpty()) {
            return Optional.empty();
        } else if (optionalFuelingMongoOutDto.get().getVehicleId().equals(fueling.getVehicleId().id())) {
            return Optional.of(toFueling(fuelingMongoRespository.save(toFuelingDTO(fueling))));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public long deleteFueling(FuelingId fuelingId) {
        return fuelingMongoRespository.deleteByFuelingId(fuelingId.id());
    }

    private Fueling toFueling(FuelingMongoOutDTO fuelingMongoOutDto) {
        FuelingId fuelingId = new FuelingId(fuelingMongoOutDto.getFuelingId());
        VehicleId vehicleId = new VehicleId(fuelingMongoOutDto.getVehicleId());
        Instant date = fuelingMongoOutDto.getFuelingDate();
        boolean isFullTank = fuelingMongoOutDto.isFullFueling();
        boolean isFirstTank = fuelingMongoOutDto.isFirstFueling();
        Distance mileage = new Distance(fuelingMongoOutDto.getDistance(), DistanceMeasure.valueOf(fuelingMongoOutDto.getDistanceUnit()));
        Volume refuelVolume = new Volume(fuelingMongoOutDto.getFuelingAmount(), VolumeMeasure.valueOf(fuelingMongoOutDto.getFuelingAmountUnit()));
        Money paidPrice = new Money(fuelingMongoOutDto.getFuelingPrice(), Currency.getInstance(fuelingMongoOutDto.getFuelingPriceUnit()));
        return new Fueling(fuelingId, vehicleId, date, isFullTank, isFirstTank, mileage, refuelVolume, paidPrice);
    }

    private FuelingMongoOutDTO toFuelingDTO(Fueling fueling) {
        FuelingMongoOutDTO fuelingDTO = new FuelingMongoOutDTO();
        fuelingDTO.setFuelingId(fueling.getId().id());
        fuelingDTO.setVehicleId(fueling.getVehicleId().id());
        fuelingDTO.setFuelingDate(fueling.getDate());
        fuelingDTO.setFullFueling(fueling.isFullTank());
        fuelingDTO.setFirstFueling(fueling.isFirstTank());
        fuelingDTO.setDistance(fueling.getMileage().getDistance());
        fuelingDTO.setDistanceUnit(fueling.getMileage().getDistanceMeasure().name());
        fuelingDTO.setFuelingAmount(fueling.getRefuelVolume().getVolume());
        fuelingDTO.setFuelingAmountUnit(fueling.getRefuelVolume().getVolumeMeasure().name());
        fuelingDTO.setFuelingPrice(fueling.getPaidPrice().getAmount());
        fuelingDTO.setFuelingPriceUnit(fueling.getPaidPrice().getCurrency().getCurrencyCode());
        return fuelingDTO;
    }
}
