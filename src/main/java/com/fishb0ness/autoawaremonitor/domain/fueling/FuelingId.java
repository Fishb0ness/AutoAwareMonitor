package com.fishb0ness.autoawaremonitor.domain.fueling;

import java.util.UUID;

public record FuelingId(UUID id) {

    public FuelingId() {
        this(UUID.randomUUID());
    }
}
