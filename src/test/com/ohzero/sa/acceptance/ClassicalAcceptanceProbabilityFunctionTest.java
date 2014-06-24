/**
 * Tests for ClassicalAcceptanceProbabilityFunction.
 */

package com.ohzero.sa.acceptance;

import org.junit.*;

import static org.junit.Assert.*;

public class ClassicalAcceptanceProbabilityFunctionTest {

    private ClassicalAcceptanceProbabilityFunction function;

    @Before
    public void setUp () {
        function = new ClassicalAcceptanceProbabilityFunction();
    }

    @Test(expected=IllegalArgumentException.class)
    public void testCalculateInvalidTemperature () {
        function.calculate(1.0, 2.0, 0.0);
    }

    @Test
    public void testCalculate () {
        assertEquals(1.0, function.calculate(0.5, 0.6, 0.1), 1E-3);
        assertEquals(1.0, function.calculate(2.3, 2.3, 10.5), 1E-3);
        assertEquals(Math.exp(-1), function.calculate(0.8, 0.6, 0.2), 1E-3);
        assertEquals(Math.exp(-2.5), function.calculate(-1.0, -6.25, 2.1), 1E-3);
    }

}
