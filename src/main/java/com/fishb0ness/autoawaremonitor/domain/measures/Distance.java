package com.fishb0ness.autoawaremonitor.domain.measures;

public class Distance {

    private double distance;
    private DistanceMeasure distanceMeasure;

    public Distance(double distance, DistanceMeasure distanceMeasure) {
        setDistance(distance);
        this.distanceMeasure = distanceMeasure;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        if (distance < 0) {
            throw new IllegalArgumentException("Distance cannot be less than 0");
        }
        this.distance = distance;
    }

    public DistanceMeasure getDistanceMeasure() {
        return distanceMeasure;
    }

    public void setDistanceMeasure(DistanceMeasure distanceMeasure) {
        this.distanceMeasure = distanceMeasure;
    }

    public double getDistanceInKilometers() {
        return convertTo(DistanceMeasure.KILOMETERS);
    }

    public double getDistanceInMiles() {
        return convertTo(DistanceMeasure.MILES);
    }

    private double convertTo(DistanceMeasure distanceMeasure) {
        if (this.distanceMeasure == distanceMeasure) {
            return distance;
        }
        if (this.distanceMeasure == DistanceMeasure.KILOMETERS && distanceMeasure == DistanceMeasure.MILES) {
            return distance * 0.621371;
        }
        if (this.distanceMeasure == DistanceMeasure.MILES && distanceMeasure == DistanceMeasure.KILOMETERS) {
            return distance * 1.60934;
        }
        throw new IllegalArgumentException("Conversion from " + this.distanceMeasure + " to " + distanceMeasure + " is not supported.");
    }
}
