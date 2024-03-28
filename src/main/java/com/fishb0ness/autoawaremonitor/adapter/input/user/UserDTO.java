package com.fishb0ness.autoawaremonitor.adapter.input.user;

import com.fishb0ness.autoawaremonitor.domain.user.User;
import com.fishb0ness.autoawaremonitor.domain.user.UserId;
import com.fishb0ness.autoawaremonitor.domain.user.UserName;

import java.util.UUID;

public record UserDTO(String id, String name) {
    static UserDTO fromDomain(User user) {
        return new UserDTO(user.getId().id().toString(), user.getUserName().name());
    }

    static User toDomain(UserDTO userDto) {
        UserId userId = new UserId(UUID.fromString(userDto.id()));
        UserName userName = new UserName(userDto.name());
        return new User(userId, userName);
    }
}
