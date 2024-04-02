package com.fishb0ness.autoawaremonitor.domain.measures;

public class Volume {

    private double quantity;
    private VolumeMeasure volumeMeasure;

    public Volume(double quantity, VolumeMeasure volumeMeasure) {
        setQuantity(quantity);
        this.volumeMeasure = volumeMeasure;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be less than 0");
        }
        this.quantity = quantity;
    }

    public VolumeMeasure getVolumeMeasure() {
        return volumeMeasure;
    }

    public void setVolumeMeasure(VolumeMeasure volumeMeasure) {
        this.volumeMeasure = volumeMeasure;
    }

    public double getVolumeInGallons() {
        return convertTo(VolumeMeasure.GALLONS);
    }

    public double getVolumeInLiters() {
        return convertTo(VolumeMeasure.LITERS);
    }

    private double convertTo(VolumeMeasure volumeMeasure) {
        if (this.volumeMeasure == volumeMeasure) {
            return quantity;
        }
        if (this.volumeMeasure == VolumeMeasure.LITERS && volumeMeasure == VolumeMeasure.GALLONS) {
            return quantity * 0.264172;
        }
        if (this.volumeMeasure == VolumeMeasure.GALLONS && volumeMeasure == VolumeMeasure.LITERS) {
        return quantity * 3.78541;
        }
        throw new IllegalArgumentException("Conversion from " + this.volumeMeasure + " to " + volumeMeasure + " is not supported.");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Volume volume = (Volume) o;
        return Double.compare(volume.quantity, quantity) == 0 && volumeMeasure == volume.volumeMeasure;
    }
}
