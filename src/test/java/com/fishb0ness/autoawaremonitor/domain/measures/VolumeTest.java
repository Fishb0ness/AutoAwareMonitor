package com.fishb0ness.autoawaremonitor.domain.measures;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VolumeTest {

    @Test
    public void volumeInLitersIsStoredCorrectly() {
        Volume volume = new Volume(10.0, VolumeMeasure.LITERS);
        Assertions.assertEquals(10.0, volume.getQuantity());
    }

    @Test
    public void volumeInGallonsIsStoredCorrectly() {
        Volume volume = new Volume(1.0, VolumeMeasure.GALLONS);
        Assertions.assertEquals(1.0, volume.getQuantity());
    }

    @Test
    public void volumeMeasureIsStoredCorrectly() {
        Volume volume = new Volume(10.0, VolumeMeasure.LITERS);
        Assertions.assertEquals(VolumeMeasure.LITERS, volume.getVolumeMeasure());
    }

    @Test
    public void volumeCanBeUpdated() {
        Volume volume = new Volume(10.0, VolumeMeasure.LITERS);
        volume.setQuantity(20.0);
        Assertions.assertEquals(20.0, volume.getQuantity());
    }

    @Test
    public void volumeMeasureCanBeUpdated() {
        Volume volume = new Volume(10.0, VolumeMeasure.LITERS);
        volume.setVolumeMeasure(VolumeMeasure.GALLONS);
        Assertions.assertEquals(VolumeMeasure.GALLONS, volume.getVolumeMeasure());
    }

    @Test
    public void volumeQuantityInLitersIsConvertedToGallonsCorrectly() {
        Volume volume = new Volume(10.0, VolumeMeasure.LITERS);
        Assertions.assertEquals(2.64172, volume.getVolumeInGallons(), 0.00001);
    }

    @Test
    public void volumeQuantityInLitersIsNotConvertedWhenToLiters() {
        Volume volume = new Volume(10.0, VolumeMeasure.LITERS);
        Assertions.assertEquals(10.0, volume.getVolumeInLiters());
    }

    @Test
    public void volumeQuantityInGallonsIsConvertedToLitersCorrectly() {
        Volume volume = new Volume(1.0, VolumeMeasure.GALLONS);
        Assertions.assertEquals(3.78541, volume.getVolumeInLiters(), 0.00001);
    }

    @Test
    public void volumeQuantityInGallonsIsNotConvertedWhenToGallons() {
        Volume volume = new Volume(1.0, VolumeMeasure.GALLONS);
        Assertions.assertEquals(1.0, volume.getVolumeInGallons());
    }

    @Test
    public void volumeConversionNotSupported() {
        Volume volume = new Volume(10.0, null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> volume.getVolumeInGallons());
    }

    @Test
    public void volumeQuantityCannotBeNegative() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Volume(-1.0, VolumeMeasure.LITERS));
    }

    @Test
    public void volumeQuantityCanBeZero() {
        Volume volume = new Volume(0.0, VolumeMeasure.LITERS);
        Assertions.assertEquals(0.0, volume.getQuantity());
    }

    @Test
    public void volumeQuantityCannotBeNegativeWhenUpdated() {
        Volume volume = new Volume(10.0, VolumeMeasure.LITERS);
        Assertions.assertThrows(IllegalArgumentException.class, () -> volume.setQuantity(-1.0));
    }
}
