/**
 * Interface defining a function for calculating the acceptance probability of
 * a new energy level given the original energy level and the current
 * temperature.
 */

package com.ohzero.sa.acceptance;

public interface AcceptanceProbabilityFunction {

    double calculate (double newEnergy, double origEnergy, double temp);

}
