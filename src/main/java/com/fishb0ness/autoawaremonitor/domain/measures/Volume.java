package com.fishb0ness.autoawaremonitor.domain.measures;

import com.fishb0ness.autoawaremonitor.domain.measures.VolumeMeasure;

public class Volume {

    private double volume;
    private VolumeMeasure volumeMeasure;

    public Volume(double volume, VolumeMeasure volumeMeasure) {
        this.volumeMeasure = volumeMeasure;
        if (volumeMeasure == VolumeMeasure.LITERS) {
            this.volume = volume;
        } else if(volumeMeasure == VolumeMeasure.GALLONS){
            this.volume = volume * 3.78541;
        }
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public VolumeMeasure getVolumeMeasure() {
        return volumeMeasure;
    }

    public void setVolumeMeasure(VolumeMeasure volumeMeasure) {
        this.volumeMeasure = volumeMeasure;
    }
}
