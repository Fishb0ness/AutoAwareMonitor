package com.fishb0ness.autoawaremonitor.domain.measures;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DistanceTest {

    @Test
    public void distanceInKilometersIsStoredCorrectly() {
        Distance distance = new Distance(10.0, DistanceMeasure.KILOMETERS);
        Assertions.assertEquals(10.0, distance.getDistance());
    }

    @Test
    public void distanceInMilesIsStoredCorrectly() {
        Distance distance = new Distance(1.0, DistanceMeasure.MILES);
        Assertions.assertEquals(1.0, distance.getDistance());
    }

    @Test
    public void distanceMeasureIsStoredCorrectly() {
        Distance distance = new Distance(10.0, DistanceMeasure.KILOMETERS);
        Assertions.assertEquals(DistanceMeasure.KILOMETERS, distance.getDistanceMeasure());
    }

    @Test
    public void distanceCanBeUpdated() {
        Distance distance = new Distance(10.0, DistanceMeasure.KILOMETERS);
        distance.setDistance(20.0);
        Assertions.assertEquals(20.0, distance.getDistance());
    }

    @Test
    public void distanceMeasureCanBeUpdated() {
        Distance distance = new Distance(10.0, DistanceMeasure.KILOMETERS);
        distance.setDistanceMeasure(DistanceMeasure.MILES);
        Assertions.assertEquals(DistanceMeasure.MILES, distance.getDistanceMeasure());
    }

    @Test
    public void distanceInKilometersIsConvertedToMilesCorrectly() {
        Distance distance = new Distance(10.0, DistanceMeasure.KILOMETERS);
        Assertions.assertEquals(6.21371, distance.getDistanceInMiles(), 0.00001);
    }

    @Test
    public void distanceInMilesIsConvertedToKilometersCorrectly() {
        Distance distance = new Distance(1.0, DistanceMeasure.MILES);
        Assertions.assertEquals(1.60934, distance.getDistanceInKilometers(), 0.00001);
    }

    @Test
    public void distanceConversionNotSupported() {
        Distance distance = new Distance(10.0, null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> distance.getDistanceInMiles());
    }

    @Test
    public void distanceCannotBeLessThanZero() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Distance(-1.0, DistanceMeasure.KILOMETERS));
    }

    @Test
    public void distanceInKilometersIsNotConvertedWhenToKilometers() {
        Distance distance = new Distance(10.0, DistanceMeasure.KILOMETERS);
        Assertions.assertEquals(10.0, distance.getDistanceInKilometers());
    }

    @Test
    public void distanceInMilesIsNotConvertedWhenToMiles() {
        Distance distance = new Distance(1.0, DistanceMeasure.MILES);
        Assertions.assertEquals(1.0, distance.getDistanceInMiles());
    }

    @Test
    public void distanceCannotBeLessThanZeroWhenUpdated() {
        Distance distance = new Distance(10.0, DistanceMeasure.KILOMETERS);
        Assertions.assertThrows(IllegalArgumentException.class, () -> distance.setDistance(-1.0));
    }
}
