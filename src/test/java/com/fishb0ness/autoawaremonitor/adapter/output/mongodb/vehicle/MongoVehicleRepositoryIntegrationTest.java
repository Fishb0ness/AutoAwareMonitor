package com.fishb0ness.autoawaremonitor.adapter.output.mongodb.vehicle;

import com.fishb0ness.autoawaremonitor.adapter.IntegrationTest;
import com.fishb0ness.autoawaremonitor.domain.user.User;
import com.fishb0ness.autoawaremonitor.domain.user.UserId;
import com.fishb0ness.autoawaremonitor.domain.user.UserName;
import com.fishb0ness.autoawaremonitor.domain.vehicle.Vehicle;
import com.fishb0ness.autoawaremonitor.domain.vehicle.VehicleId;
import com.fishb0ness.autoawaremonitor.domain.vehicle.VehicleModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

public class MongoVehicleRepositoryIntegrationTest extends IntegrationTest {

    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    MongoVehicleRepository mongoVehicleRepository;

    @BeforeEach
    public void setup() {
        //drop all data in the database
        mongoTemplate.getCollectionNames().forEach(mongoTemplate::dropCollection);
    }

    @Test
    void saveVehicleTest() {
        User user = new User(new UserId(), new UserName("John"));
        Vehicle vehicle = new Vehicle(new VehicleId(), user.getId(), new VehicleModel("Toyota", "Corolla"), "Exact Vehicle");
        mongoVehicleRepository.saveVehicleModel(vehicle.vehicleModel().getBrand(), vehicle.vehicleModel().getModel());

        Vehicle result = mongoVehicleRepository.saveVehicle(vehicle);

        assertAll(
                () -> assertTrue(mongoTemplate.exists(query(where("_id").is(vehicle.id().id())), "vehicles")),
                () -> assertTrue(mongoTemplate.exists(query(where("ownerId").is(vehicle.ownerId().id())), "vehicles")),
                () -> assertTrue(mongoTemplate.exists(query(where("vehicleModelId").is(vehicle.vehicleModel().getVehicleModelId().id())), "vehicles")),
                () -> assertTrue(mongoTemplate.exists(query(where("exactVehicleModel").is(vehicle.exactVehicleModel())), "vehicles")),
                () -> assertNotNull(result)
        );
    }

    @Test
    void getVehicleTest() {
        User user = new User(new UserId(), new UserName("John"));
        Vehicle vehicle = new Vehicle(new VehicleId(), user.getId(), new VehicleModel("Toyota", "Corolla"), "Exact Vehicle");
        mongoVehicleRepository.saveVehicleModel(vehicle.vehicleModel().getBrand(), vehicle.vehicleModel().getModel());
        mongoVehicleRepository.saveVehicle(vehicle);

        Optional<Vehicle> optionalResult = mongoVehicleRepository.getVehicle(vehicle.id());

        assertAll(
                () -> assertTrue(optionalResult.isPresent()),
                () -> assertEquals(vehicle.ownerId(), optionalResult.get().ownerId()),
                () -> assertEquals(vehicle.vehicleModel().getBrand(), optionalResult.get().vehicleModel().getBrand()),
                () -> assertEquals(vehicle.vehicleModel().getModel(), optionalResult.get().vehicleModel().getModel()),
                () -> assertEquals(vehicle.exactVehicleModel(), optionalResult.get().exactVehicleModel())
        );
    }

    @Test
    void getVehicleNotFoundTest() {
        Optional<Vehicle> optionalResult = mongoVehicleRepository.getVehicle(new VehicleId());

        assertTrue(optionalResult.isEmpty());
    }

    @Test
    void getAllVehiclesByOwnerTest() {
        User user1 = new User(new UserId(), new UserName("John"));
        User user2 = new User(new UserId(), new UserName("Jane"));
        Vehicle vehicle1 = new Vehicle(new VehicleId(), user1.getId(), new VehicleModel("Toyota", "Corolla"), "Exact Vehicle 1");
        Vehicle vehicle2 = new Vehicle(new VehicleId(), user1.getId(), new VehicleModel("Toyota", "Camry"), "Exact Vehicle 2");
        Vehicle vehicle3 = new Vehicle(new VehicleId(), user2.getId(), new VehicleModel("Honda", "Civic"), "Exact Vehicle 3");
        mongoVehicleRepository.saveVehicleModel(vehicle1.vehicleModel().getBrand(), vehicle1.vehicleModel().getModel());
        mongoVehicleRepository.saveVehicleModel(vehicle2.vehicleModel().getBrand(), vehicle2.vehicleModel().getModel());
        mongoVehicleRepository.saveVehicleModel(vehicle3.vehicleModel().getBrand(), vehicle3.vehicleModel().getModel());
        mongoVehicleRepository.saveVehicle(vehicle1);
        mongoVehicleRepository.saveVehicle(vehicle2);
        mongoVehicleRepository.saveVehicle(vehicle3);

        List<Vehicle> result = mongoVehicleRepository.getAllVehiclesByOwner(user1.getId());

        assertAll(
                () -> assertEquals(2, result.size()),
                () -> assertTrue(result.stream().anyMatch(vehicle -> vehicle.ownerId().equals(user1.getId()) && vehicle.vehicleModel().getBrand().equals("Toyota") && vehicle.vehicleModel().getModel().equals("Corolla"))),
                () -> assertTrue(result.stream().anyMatch(vehicle -> vehicle.ownerId().equals(user1.getId()) && vehicle.vehicleModel().getBrand().equals("Toyota") && vehicle.vehicleModel().getModel().equals("Camry")))
        );
    }

    @Test
    void getAllVehiclesByOwnerNotFoundTest() {
        User user = new User(new UserId(), new UserName("John"));
        List<Vehicle> result = mongoVehicleRepository.getAllVehiclesByOwner(user.getId());

        assertTrue(result.isEmpty());
    }

    @Test
    void getAllVehiclesTest() {
        User user1 = new User(new UserId(), new UserName("John"));
        User user2 = new User(new UserId(), new UserName("Jane"));
        Vehicle vehicle1 = new Vehicle(new VehicleId(), user1.getId(), new VehicleModel("Toyota", "Corolla"), "Exact Vehicle 1");
        Vehicle vehicle2 = new Vehicle(new VehicleId(), user1.getId(), new VehicleModel("Toyota", "Camry"), "Exact Vehicle 2");
        Vehicle vehicle3 = new Vehicle(new VehicleId(), user2.getId(), new VehicleModel("Honda", "Civic"), "Exact Vehicle 3");
        mongoVehicleRepository.saveVehicleModel(vehicle1.vehicleModel().getBrand(), vehicle1.vehicleModel().getModel());
        mongoVehicleRepository.saveVehicleModel(vehicle2.vehicleModel().getBrand(), vehicle2.vehicleModel().getModel());
        mongoVehicleRepository.saveVehicleModel(vehicle3.vehicleModel().getBrand(), vehicle3.vehicleModel().getModel());
        mongoVehicleRepository.saveVehicle(vehicle1);
        mongoVehicleRepository.saveVehicle(vehicle2);
        mongoVehicleRepository.saveVehicle(vehicle3);

        List<Vehicle> result = mongoVehicleRepository.getAllVehicles();

        assertAll(
                () -> assertEquals(3, result.size()),
                () -> assertTrue(result.stream().anyMatch(vehicle -> vehicle.ownerId().equals(user1.getId()) && vehicle.vehicleModel().getBrand().equals("Toyota") && vehicle.vehicleModel().getModel().equals("Corolla"))),
                () -> assertTrue(result.stream().anyMatch(vehicle -> vehicle.ownerId().equals(user1.getId()) && vehicle.vehicleModel().getBrand().equals("Toyota") && vehicle.vehicleModel().getModel().equals("Camry"))),
                () -> assertTrue(result.stream().anyMatch(vehicle -> vehicle.ownerId().equals(user2.getId()) && vehicle.vehicleModel().getBrand().equals("Honda") && vehicle.vehicleModel().getModel().equals("Civic")))
        );
    }

    @Test
    void updateVehicleModelTest() {
        User user = new User(new UserId(), new UserName("John"));
        Vehicle vehicle = new Vehicle(new VehicleId(), user.getId(), new VehicleModel("Toyota", "Corolla"), "Exact Vehicle");
        mongoVehicleRepository.saveVehicleModel(vehicle.vehicleModel().getBrand(), vehicle.vehicleModel().getModel());
        mongoVehicleRepository.saveVehicle(vehicle);
        VehicleModel newVehicleModel = new VehicleModel("Toyota", "Camry");
        Vehicle newVehicle = new Vehicle(vehicle.id(), vehicle.ownerId(), newVehicleModel, "New Exact Vehicle");
        mongoVehicleRepository.saveVehicleModel(newVehicle.vehicleModel().getBrand(), newVehicle.vehicleModel().getModel());


        Optional<Vehicle> optionalResult = mongoVehicleRepository.updateVehicleModel(newVehicle);

        assertAll(
                () -> assertTrue(optionalResult.isPresent()),
                () -> assertEquals(vehicle.ownerId(), optionalResult.get().ownerId()),
                () -> assertEquals(newVehicle.vehicleModel().getBrand(), optionalResult.get().vehicleModel().getBrand()),
                () -> assertEquals(newVehicle.vehicleModel().getModel(), optionalResult.get().vehicleModel().getModel()),
                () -> assertEquals(newVehicle.exactVehicleModel(), optionalResult.get().exactVehicleModel())
        );
    }

    @Test
    void updateVehicleModelModelNotFoundTest() {
        User user = new User(new UserId(), new UserName("John"));
        Vehicle vehicle = new Vehicle(new VehicleId(), user.getId(), new VehicleModel("Toyota", "Corolla"), "Exact Vehicle");
        mongoVehicleRepository.saveVehicleModel(vehicle.vehicleModel().getBrand(), vehicle.vehicleModel().getModel());
        mongoVehicleRepository.saveVehicle(vehicle);

        VehicleModel newVehicleModel = new VehicleModel("Toyota", "Camry");
        Vehicle newVehicle = new Vehicle(new VehicleId(), user.getId(), newVehicleModel, "New Exact Vehicle");

        Optional<Vehicle> optionalResult = mongoVehicleRepository.updateVehicleModel(newVehicle);

        assertTrue(optionalResult.isEmpty());
    }

    @Test
    void updateVehicleModelVehicleNotFoundTest() {
        User user = new User(new UserId(), new UserName("John"));
        VehicleModel newVehicleModel = new VehicleModel("Toyota", "Camry");
        Vehicle newVehicle = new Vehicle(new VehicleId(), user.getId(), newVehicleModel, "New Exact Vehicle");
        mongoVehicleRepository.saveVehicleModel(newVehicle.vehicleModel().getBrand(), newVehicle.vehicleModel().getModel());

        Optional<Vehicle> optionalResult = mongoVehicleRepository.updateVehicleModel(newVehicle);

        assertTrue(optionalResult.isEmpty());
    }

    @Test
    void deleteVehicleTest() {
        User user = new User(new UserId(), new UserName("John"));
        Vehicle vehicle = new Vehicle(new VehicleId(), user.getId(), new VehicleModel("Toyota", "Corolla"), "Exact Vehicle");
        mongoVehicleRepository.saveVehicleModel(vehicle.vehicleModel().getBrand(), vehicle.vehicleModel().getModel());
        mongoVehicleRepository.saveVehicle(vehicle);

        long deletedCount = mongoVehicleRepository.deleteVehicle(vehicle.id());

        assertAll(
                () -> assertEquals(1, deletedCount),
                () -> assertFalse(mongoTemplate.exists(query(where("_id").is(vehicle.id().id())), "vehicles"))
        );
    }

    //MODEL TESTS
    @Test
    void saveVehicleModelTest() {
        VehicleModel vehicleModel = new VehicleModel("Toyota", "Corolla");

        VehicleModel result = mongoVehicleRepository.saveVehicleModel(vehicleModel.getBrand(), vehicleModel.getModel());

        assertAll(
                () -> assertTrue(mongoTemplate.exists(query(where("_id").is(vehicleModel.getVehicleModelId().id())), "models")),
                () -> assertTrue(mongoTemplate.exists(query(where("brand").is(vehicleModel.getBrand())), "models")),
                () -> assertTrue(mongoTemplate.exists(query(where("model").is(vehicleModel.getModel())), "models")),
                () -> assertNotNull(result)
        );
    }

    @Test
    public void getAllVehicleModelsTest() {
        VehicleModel vehicleModel1 = new VehicleModel("Toyota", "Corolla");
        VehicleModel vehicleModel2 = new VehicleModel("Toyota", "Camry");
        VehicleModel vehicleModel3 = new VehicleModel("Honda", "Civic");

        mongoVehicleRepository.saveVehicleModel(vehicleModel1.getBrand(), vehicleModel1.getModel());
        mongoVehicleRepository.saveVehicleModel(vehicleModel2.getBrand(), vehicleModel2.getModel());
        mongoVehicleRepository.saveVehicleModel(vehicleModel3.getBrand(), vehicleModel3.getModel());

        List<VehicleModel> result = mongoVehicleRepository.getAllVehicleModels();

        assertAll(
                () -> assertEquals(3, result.size()),
                () -> assertTrue(result.stream().anyMatch(vehicleModel -> vehicleModel.getBrand().equals("Toyota") && vehicleModel.getModel().equals("Corolla"))),
                () -> assertTrue(result.stream().anyMatch(vehicleModel -> vehicleModel.getBrand().equals("Toyota") && vehicleModel.getModel().equals("Camry"))),
                () -> assertTrue(result.stream().anyMatch(vehicleModel -> vehicleModel.getBrand().equals("Honda") && vehicleModel.getModel().equals("Civic")))
        );
    }

    @Test
    public void getAllVehicleModelsByBrandTest() {
        VehicleModel vehicleModel1 = new VehicleModel("Toyota", "Corolla");
        VehicleModel vehicleModel2 = new VehicleModel("Toyota", "Camry");
        VehicleModel vehicleModel3 = new VehicleModel("Honda", "Civic");

        mongoVehicleRepository.saveVehicleModel(vehicleModel1.getBrand(), vehicleModel1.getModel());
        mongoVehicleRepository.saveVehicleModel(vehicleModel2.getBrand(), vehicleModel2.getModel());
        mongoVehicleRepository.saveVehicleModel(vehicleModel3.getBrand(), vehicleModel3.getModel());

        List<VehicleModel> result = mongoVehicleRepository.getAllVehicleModelsByBrand("Toyota");

        assertAll(
                () -> assertEquals(2, result.size()),
                () -> assertTrue(result.stream().anyMatch(vehicleModel -> vehicleModel.getBrand().equals("Toyota") && vehicleModel.getModel().equals("Corolla"))),
                () -> assertTrue(result.stream().anyMatch(vehicleModel -> vehicleModel.getBrand().equals("Toyota") && vehicleModel.getModel().equals("Camry")))
        );
    }

    @Test
    public void getVehicleModelTest() {
        VehicleModel vehicleModel = new VehicleModel("Toyota", "Corolla");

        VehicleModel result = mongoVehicleRepository.saveVehicleModel(vehicleModel.getBrand(), vehicleModel.getModel());

        Optional<VehicleModel> optionalResult = mongoVehicleRepository.getVehicleModel(result.getVehicleModelId());

        assertAll(
                () -> assertTrue(optionalResult.isPresent()),
                () -> assertEquals(vehicleModel.getBrand(), optionalResult.get().getBrand()),
                () -> assertEquals(vehicleModel.getModel(), optionalResult.get().getModel())
        );
    }

    @Test
    public void deleteVehicleModelTest() {
        VehicleModel vehicleModel = new VehicleModel("Toyota", "Corolla");

        VehicleModel result = mongoVehicleRepository.saveVehicleModel(vehicleModel.getBrand(), vehicleModel.getModel());

        long deletedCount = mongoVehicleRepository.deleteVehicleModel(result.getVehicleModelId());

        assertAll(
                () -> assertEquals(1, deletedCount),
                () -> assertFalse(mongoTemplate.exists(query(where("_id").is(result.getVehicleModelId().id())), "models"))
        );
    }

    @Test
    void getVehicleModelByModelIdTest() {
        VehicleModel vehicleModel = new VehicleModel("Toyota", "Corolla");

        VehicleModel result = mongoVehicleRepository.saveVehicleModel(vehicleModel.getBrand(), vehicleModel.getModel());

        Optional<VehicleModel> optionalResult = mongoVehicleRepository.getVehicleModel(result.getVehicleModelId());

        assertAll(
                () -> assertTrue(optionalResult.isPresent()),
                () -> assertEquals(vehicleModel.getBrand(), optionalResult.get().getBrand()),
                () -> assertEquals(vehicleModel.getModel(), optionalResult.get().getModel())
        );
    }

}
