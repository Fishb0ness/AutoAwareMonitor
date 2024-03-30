package com.fishb0ness.autoawaremonitor.domain.measures;

import com.fishb0ness.autoawaremonitor.domain.measures.DistanceMeasure;

public class Distance {

        private double distance;
        private DistanceMeasure distanceMeasure;

        public Distance(double distance, DistanceMeasure distanceMeasure) {
            this.distanceMeasure = distanceMeasure;
            if (distanceMeasure == DistanceMeasure.KILOMETERS) {
                this.distance = distance;
            } else if(distanceMeasure == DistanceMeasure.MILES){
                this.distance = distance * 1.60934;
            }
        }
}
