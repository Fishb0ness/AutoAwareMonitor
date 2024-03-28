package com.fishb0ness.autoawaremonitor.application;

import com.fishb0ness.autoawaremonitor.domain.user.User;
import com.fishb0ness.autoawaremonitor.domain.user.UserId;
import com.fishb0ness.autoawaremonitor.domain.user.UserName;
import com.fishb0ness.autoawaremonitor.domain.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserUseCases {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(UserName userName) {
        UserId userId = new UserId(UUID.randomUUID());
        return userRepository.saveUser(new User(userId, userName));
    }

    @Override
    public Optional<User> getUserById(UserId userId) {
        return userRepository.getUser(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public Optional<User> updateUser(User user) {
        return userRepository.updateUser(user);
    }

    @Override
    public long deleteUser(UserId userId) {
        return userRepository.deleteUser(userId);
    }
}
