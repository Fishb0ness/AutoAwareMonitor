package com.fishb0ness.autoawaremonitor.application;

import com.fishb0ness.autoawaremonitor.domain.user.User;
import com.fishb0ness.autoawaremonitor.domain.user.UserId;
import com.fishb0ness.autoawaremonitor.domain.user.UserName;

import java.util.List;
import java.util.Optional;

public interface UserUseCases {
    User createUser(UserName userName);

    Optional<User> getUserById(UserId userId);

    List<User> getAllUsers();

    Optional<User> updateUser(User user);

    long deleteUser(UserId userId);
}
