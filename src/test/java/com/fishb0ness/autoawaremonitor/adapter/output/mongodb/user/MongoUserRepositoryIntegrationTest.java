package com.fishb0ness.autoawaremonitor.adapter.output.mongodb.user;

import com.fishb0ness.autoawaremonitor.adapter.IntegrationTest;
import com.fishb0ness.autoawaremonitor.domain.user.User;
import com.fishb0ness.autoawaremonitor.domain.user.UserId;
import com.fishb0ness.autoawaremonitor.domain.user.UserName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

public class MongoUserRepositoryIntegrationTest extends IntegrationTest {

    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    MongoUserRepository mongoUserRepository;

    @BeforeEach
    public void setup() {
        //drop all data in the database
        mongoTemplate.getCollectionNames().forEach(mongoTemplate::dropCollection);
    }

    @Test
    void saveUserTest() {
        User user = new User(new UserId(), new UserName("John"));

        User result = mongoUserRepository.saveUser(user);

        assertAll(
                () -> assertTrue(mongoTemplate.exists(query(where("_id").is(user.getId().id())), "users")),
                () -> assertTrue(mongoTemplate.exists(query(where("name").is("John")), "users")),
                () -> assertNotNull(result),
                () -> assertEquals(user.getId(), result.getId()),
                () -> assertEquals(user.getUserName(), result.getUserName())
        );
    }

    @Test
    void getUserWithUserFoundTest() {
        User user = new User(new UserId(), new UserName("John"));
        mongoUserRepository.saveUser(user);

        Optional<User> result = mongoUserRepository.getUser(user.getId());

        assertAll(
                () -> assertTrue(result.isPresent()),
                () -> assertEquals(user.getId(), result.get().getId()),
                () -> assertEquals(user.getUserName(), result.get().getUserName())
        );
    }

    @Test
    void getUserWithUserNotFoundTest() {
        Optional<User> result = mongoUserRepository.getUser(new UserId());

        assertTrue(result.isEmpty());
    }

    @Test
    void getAllUsersTest() {
        User user1 = new User(new UserId(), new UserName("John"));
        User user2 = new User(new UserId(), new UserName("Jane"));
        mongoUserRepository.saveUser(user1);
        mongoUserRepository.saveUser(user2);

        List<User> result = mongoUserRepository.getAllUsers();

        assertAll(
                () -> assertEquals(2, result.size()),
                () -> assertTrue(result.contains(user1)),
                () -> assertTrue(result.contains(user2))
        );
    }

    @Test
    void updateUserWithUserFoundTest() {
        User user = new User(new UserId(), new UserName("John"));
        mongoUserRepository.saveUser(user);
        User updatedUser = new User(user.getId(), new UserName("Jane"));

        Optional<User> result = mongoUserRepository.updateUser(updatedUser);

        assertAll(
                () -> assertTrue(result.isPresent()),
                () -> assertEquals(user.getId(), result.get().getId()),
                () -> assertEquals(user.getUserName(), result.get().getUserName())
        );
    }

    @Test
    void updateUserWithUserNotFoundTest() {
        User user = new User(new UserId(), new UserName("John"));

        Optional<User> result = mongoUserRepository.updateUser(user);

        assertTrue(result.isEmpty());
    }

    @Test
    void deleteUserWithUserFoundTest() {
        User user = new User(new UserId(), new UserName("John"));
        User savedUser = mongoUserRepository.saveUser(user);

        long result = mongoUserRepository.deleteUser(user.getId());

        assertAll(
                () -> assertEquals(1, result),
                () -> assertTrue(mongoUserRepository.getAllUsers().isEmpty())
        );
    }

    @Test
    void deleteUserWithUserNotFoundTest() {
        long result = mongoUserRepository.deleteUser(new UserId());

        assertEquals(0, result);
    }
}
