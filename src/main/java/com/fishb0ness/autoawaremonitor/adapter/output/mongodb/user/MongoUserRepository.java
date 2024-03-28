package com.fishb0ness.autoawaremonitor.adapter.output.mongodb.user;


import com.fishb0ness.autoawaremonitor.domain.user.User;
import com.fishb0ness.autoawaremonitor.domain.user.UserId;
import com.fishb0ness.autoawaremonitor.domain.user.UserName;
import com.fishb0ness.autoawaremonitor.domain.user.UserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary //Annotation to pick by default between possible multiple UserRepository implementations
public class MongoUserRepository implements UserRepository {

    private final UserMongoRepository userMongoRepository;

    public MongoUserRepository(UserMongoRepository userMongoRepository) {
        this.userMongoRepository = userMongoRepository;
    }

    public User saveUser(User user) {
        UserMongoOutDTO userMongoOutDto = toUserDto(user);
        return toUser(userMongoRepository.insert(userMongoOutDto));
    }

    public Optional<User> getUser(UserId userId) {
        Optional<UserMongoOutDTO> optionalUserMongoOutDto = userMongoRepository.findByUserId(userId.id());
        return optionalUserMongoOutDto.map(this::toUser);
    }

    public List<User> getAllUsers() {
        return userMongoRepository.findAll().stream().map(this::toUser).toList();
    }

    public Optional<User> updateUser(User user) {
        return userMongoRepository.findAndReplaceByUserId(user.getId().id(), toUserDto(user)).map(this::toUser);
    }

    public long deleteUser(UserId userId) {
        return userMongoRepository.deleteByUserId(userId.id());
    }

    private User toUser(UserMongoOutDTO userMongoOutDto) {
        UserId userId = new UserId(userMongoOutDto.getUserId());
        UserName name = new UserName(userMongoOutDto.getName());
        return new User(userId, name);
    }

    private UserMongoOutDTO toUserDto(User user) {
        UserMongoOutDTO userMongoOutDto = new UserMongoOutDTO();
        userMongoOutDto.setUserId(user.getId().id());
        userMongoOutDto.setName(user.getUserName().name());
        return userMongoOutDto;
    }


}
