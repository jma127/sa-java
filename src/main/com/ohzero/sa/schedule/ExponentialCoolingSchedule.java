/**
 * Cooling schedule in which temperature decreases exponentially/geometrically
 * from an initial value and ending after a specific number of iterations.
 */

package com.ohzero.sa.schedule;

import com.ohzero.sa.state.State;

public class ExponentialCoolingSchedule implements CoolingSchedule {

    private final double initialValue;

    private final double decayRate;

    private final int numSteps;

    private int curStep;

    private double curValue;

    public ExponentialCoolingSchedule (
            double initialValue, double decayRate, int numSteps) {
        if (initialValue < Double.MIN_NORMAL) {
            throw new IllegalArgumentException("initialValue must be positive");
        }
        if (decayRate >= 1.0 - Double.MIN_NORMAL || decayRate < Double.MIN_NORMAL) {
            throw new IllegalArgumentException(
                "decayRate must be between 0 and 1 (exclusive)");
        }
        if (numSteps < 1) {
            throw new IllegalArgumentException("numSteps must be positive");
        }
        this.initialValue = initialValue;
        this.decayRate = decayRate;
        this.numSteps = numSteps;
        this.curStep = 0;
        this.curValue = initialValue;
    }

    public double getNextTemperature (State curState, State prevState) {
        if (curStep >= numSteps) {
            return -1;
        }
        double ret = curValue;
        curValue *= decayRate;
        ++curStep;
        return ret;
    }

}
