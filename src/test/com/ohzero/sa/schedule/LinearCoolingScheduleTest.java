/**
 * Tests for LinearCoolingSchedule.
 */

package com.ohzero.sa.schedule;

import org.junit.Test;

public class LinearCoolingScheduleTest {

    @Test(expected=IllegalArgumentException.class)
    public void testInvalidInitialValue () {
        new LinearCoolingSchedule(0.0, 100);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testInvalidNumSteps () {
        new LinearCoolingSchedule(1.0, 0);
    }

    @Test
    public void testSequence () {
        Common.testSequence(
            new LinearCoolingSchedule(8.0, 4),
            new double[]{8.0, 6.0, 4.0, 2.1},
            null);
    }

}
