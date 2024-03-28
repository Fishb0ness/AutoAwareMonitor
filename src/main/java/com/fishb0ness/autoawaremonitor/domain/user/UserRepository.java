package com.fishb0ness.autoawaremonitor.domain.user;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User saveUser(User user);

    Optional<User> getUser(UserId userId);

    List<User> getAllUsers();

    Optional<User> updateUser(User user);

    long deleteUser(UserId userId);
}
