/**
 * Interface representing a sequence of temperatures ("cooling schedule") for
 * simulated annealing.
 */

package com.ohzero.sa.schedule;

import com.ohzero.sa.state.State;

public interface CoolingSchedule {

    /**
     * Returns a positive value representing the next scheduled temperature.
     * Returns a negative value if the schedule has terminated.
     *
     * curState is the last accepted state, while prevState is the last
     * accepted state before curState (null if first iteration).
     *
     * It is perfectly acceptable to disregard curState and prevState in
     * calculating the next temperature.
     */
    double getNextTemperature (State curState, State prevState);

}
