package com.fishb0ness.autoawaremonitor.adapter.output.mongodb.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserMongoRepository extends MongoRepository<UserMongoOutDTO, Long> {

    Optional<UserMongoOutDTO> findByUserId(UUID userId);

    Optional<UserMongoOutDTO> findAndReplaceByUserId(UUID userId, UserMongoOutDTO userMongoOutDTO);

    long deleteByUserId(UUID id);

}
