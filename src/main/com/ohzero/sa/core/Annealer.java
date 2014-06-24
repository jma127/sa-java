/**
 * Class for running the SA algorithm given an initial state, acceptance
 * probability function, and cooling schedule.
 */

package com.ohzero.sa.core;

import java.util.Random;
import com.ohzero.sa.acceptance.AcceptanceProbabilityFunction;
import com.ohzero.sa.schedule.CoolingSchedule;
import com.ohzero.sa.state.State;

public class Annealer<T extends State<T>> implements Runnable {

    private final T initialState;

    private final AcceptanceProbabilityFunction acceptanceProbabilityFunction;

    private final CoolingSchedule coolingSchedule;

    // If we find a state w/ energy below this, terminate annealing
    private final double cutoffEnergyLevel;

    private final Random random;

    private T bestState;

    private double bestEnergyLevel;

    public Annealer (
            T initialState,
            AcceptanceProbabilityFunction acceptanceProbabilityFunction,
            CoolingSchedule coolingSchedule) {
        this(
            initialState,
            acceptanceProbabilityFunction,
            coolingSchedule,
            Double.NEGATIVE_INFINITY);
    }

    public Annealer (
            T initialState,
            AcceptanceProbabilityFunction acceptanceProbabilityFunction,
            CoolingSchedule coolingSchedule,
            double cutoffEnergyLevel) {
        this(
            initialState,
            acceptanceProbabilityFunction,
            coolingSchedule,
            cutoffEnergyLevel,
            new Random());
    }

    public Annealer (
            T initialState,
            AcceptanceProbabilityFunction acceptanceProbabilityFunction,
            CoolingSchedule coolingSchedule,
            double cutoffEnergyLevel,
            Random random) {
        this.initialState = initialState;
        this.acceptanceProbabilityFunction = acceptanceProbabilityFunction;
        this.coolingSchedule = coolingSchedule;
        this.cutoffEnergyLevel = cutoffEnergyLevel;
        this.random = random;
        this.bestState = null;
        this.bestEnergyLevel = Double.MAX_VALUE;
    }

    public void run () {
        T curState = bestState = initialState;
        T prevState = null;
        double curEnergyLevel = bestEnergyLevel = initialState.getEnergyLevel();
        double temperature;
        while ((temperature =
                    coolingSchedule.getNextTemperature(initialState, prevState))
               > Double.MIN_NORMAL &&
               bestEnergyLevel > cutoffEnergyLevel) {
            T nextState = curState.getNeighbor(temperature);
            double nextEnergyLevel = nextState.getEnergyLevel();
            if (nextEnergyLevel < bestEnergyLevel) {
                bestEnergyLevel = nextEnergyLevel;
                bestState = nextState;
            }
            double acceptanceProb = acceptanceProbabilityFunction.calculate(
                nextEnergyLevel, curEnergyLevel, temperature);
            if (random.nextDouble() < acceptanceProb) {
                prevState = curState;
                curState = nextState;
                curEnergyLevel = nextEnergyLevel;
            }
        }
    }

    public T getBestState () {
        if (bestState == null) {
            run();
        }
        return bestState;
    }

    public double getBestEnergyLevel () {
        if (bestState == null) {
            run();
        }
        return bestEnergyLevel;
    }

}
