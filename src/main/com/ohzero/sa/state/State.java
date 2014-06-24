/**
 * Interface representing a problem state in simulated annealing.
 */

package com.ohzero.sa.state;

public interface State<T extends State<T>> {

    /**
     * Energy level, or cost, of the current state. Lower energy levels
     * represent more optimal states.
     */
    double getEnergyLevel ();

    /**
     * Return the next state to try in simulated annealing given the current
     * temperature.
     *
     * It is perfectly acceptable to disregard the current temperature in
     * generating the next state.
     */
    T getNeighbor (double temp);

}
