/**
 * Utility functions for cooling schedule tests.
 */

package com.ohzero.sa.schedule;

import com.ohzero.sa.state.State;

import static org.junit.Assert.*;

class Common {

    public static void testSequence (CoolingSchedule cs, double[] sequence, State[] states) {
        if (states != null) {
            assertEquals(sequence.length, states.length);
        }
        State curState = null, prevState = null;
        for (int i = 0; i < sequence.length; ++i) {
            if (states != null) {
                curState = states[i];
            }
            assertEquals(sequence[i], cs.getNextTemperature(curState, prevState),
                1E-3);
            prevState = curState;
        }
        for (int i = 0; i < 3; ++i) {
            assertTrue(cs.getNextTemperature(null, null) < -Double.MIN_NORMAL);
        }

    }

}
