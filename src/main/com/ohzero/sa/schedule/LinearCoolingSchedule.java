/**
 * Cooling schedule in which temperature decreases linearly from an initial
 * value to zero.
 */

package com.ohzero.sa.schedule;

import com.ohzero.sa.state.State;

public class LinearCoolingSchedule implements CoolingSchedule {

    private final double initialValue;

    private final int numSteps;

    private int curStep;

    public LinearCoolingSchedule () {
        this(1.0);
    }

    public LinearCoolingSchedule (double initialValue) {
        this(initialValue, 100);
    }

    public LinearCoolingSchedule (double initialValue, int numSteps) {
        if (initialValue < Double.MIN_NORMAL) {
            throw new IllegalArgumentException("initialValue must be positive");
        }
        if (numSteps < 1) {
            throw new IllegalArgumentException("numSteps must be positive");
        }
        this.initialValue = initialValue;
        this.numSteps = numSteps;
        this.curStep = 0;
    }

    public double getNextTemperature (State curState, State prevState) {
        if (curStep >= numSteps) {
            return -1;
        }
        return (numSteps - (curStep ++)) * initialValue / numSteps;
    }

}
