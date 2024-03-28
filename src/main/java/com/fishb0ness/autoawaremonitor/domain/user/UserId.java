package com.fishb0ness.autoawaremonitor.domain.user;

import java.util.UUID;

public record UserId(UUID id) {

    public UserId() {
        this(UUID.randomUUID());
    }
}
