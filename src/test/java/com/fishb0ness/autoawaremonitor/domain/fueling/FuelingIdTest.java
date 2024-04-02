package com.fishb0ness.autoawaremonitor.domain.fueling;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class FuelingIdTest {

    @Test
    public void fuelingIdIsGeneratedCorrectly() {
        FuelingId fuelingId = new FuelingId();
        Assertions.assertNotNull(fuelingId.id());
    }

    @Test
    public void fuelingIdIsStoredCorrectly() {
        UUID uuid = UUID.randomUUID();
        FuelingId fuelingId = new FuelingId(uuid);
        Assertions.assertEquals(uuid, fuelingId.id());
    }
}
