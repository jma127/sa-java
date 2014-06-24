/**
 * Classical implementation of AcceptanceProbabilityFunction as described in
 * Kirkpatrick's paper.
 */

package com.ohzero.sa.acceptance;

public class ClassicalAcceptanceProbabilityFunction
        implements AcceptanceProbabilityFunction {

    public double calculate (double newEnergy, double origEnergy, double temp) {
        if (temp < Double.MIN_NORMAL) {
            throw new IllegalArgumentException("temp must be positive");
        }
        return newEnergy <= origEnergy
            ? 1.0
            : Math.exp((origEnergy - newEnergy) / temp);
    }

}
