/**
 * Various utilities for calculating an initial temperature.
 */

package com.ohzero.sa.util;

import java.util.ArrayList;
import java.util.Arrays;
import com.ohzero.sa.state.State;

public class InitialTemperatureHelpers {

    /**
     * Calculates an appropriate initial temperature based on a desired initial
     * acceptance probability. Uses a set of random states to perform this
     * calculation.
     *
     * desiredProbability: desired initial acceptance probability for positive
     *   (i.e. worse) transitions.
     * Either:
     *   states: set of random states. There must be at least one positive
     *     transition (i.e. state where state.getNeighbor(1.0).getEnergyLevel() -
     *     state.getEnergyLevel() is positive). States which do not meet this
     *     criteria may be included, but will not be used in calculating the
     *     initial temperature.
     *   OR
     *   positiveEnergyDeltas: an array of positive energy deltas
     *     (i.e. state.getNeighbor(1.0).getEnergyLevel() -
     *     state.getEnergyLevel()). Set by default to
     *     (0.01 * desiredProbability).
     * errorThreshold: how close within the desired probability we should get
     *   before stopping. A lower value will mean more accuracy, but longer
     *   runtime.
     *
     * This method only works on states whose neighbor generation is
     * irrespective of the temperature.
     *
     * The algorithm used by this method is from:
     * Computing the Initial Temperature of Simulated Annealing
     * Walid Ben-Ameur, 2004
     */
    public static double calculateFromDesiredProbability (
            double desiredProbability,
            double[] positiveEnergyDeltas,
            double errorThreshold) {
        double highestDelta = positiveEnergyDeltas[0];
        for (int i = 0; i < positiveEnergyDeltas.length; ++i) {
            if (positiveEnergyDeltas[i] < Double.MIN_NORMAL) {
                throw new IllegalArgumentException(
                    "All energy deltas must be positive");
            }
            highestDelta = Math.max(
                highestDelta, positiveEnergyDeltas[i]);
        }

        double temperature = highestDelta;
        double probability = estimateAcceptanceProbability(
            positiveEnergyDeltas, temperature);
        while (Math.abs(probability - desiredProbability) > errorThreshold) {
            temperature *=
                Math.sqrt(Math.log(probability) / Math.log(desiredProbability));
            probability = estimateAcceptanceProbability(
                positiveEnergyDeltas, temperature);
        }
        return temperature;
    }

    public static double calculateFromDesiredProbability (
            double desiredProbability,
            double[] positiveEnergyDeltas) {
        return calculateFromDesiredProbability(
            desiredProbability,
            positiveEnergyDeltas,
            desiredProbability / 100.0);
    }

    public static double calculateFromDesiredProbability (
            double desiredProbability,
            Iterable<State> states,
            double errorThreshold) {
        ArrayList<Double> energyDeltasList = new ArrayList<Double>();
        for (State state : states) {
            double energyDelta = (state.getNeighbor(1.0).getEnergyLevel() -
                                  state.getEnergyLevel());
            if (energyDelta > Double.MIN_NORMAL) {
                energyDeltasList.add(energyDelta);
            }
        }
        if (energyDeltasList.isEmpty()) {
            throw new IllegalArgumentException(
                "states must have at least one positive transition");
        }

        double[] energyDeltas = new double[energyDeltasList.size()];
        for (int i = 0; i < energyDeltasList.size(); ++i) {
            energyDeltas[i] = energyDeltasList.get(i);
        }
        return calculateFromDesiredProbability(
            desiredProbability,
            energyDeltas,
            errorThreshold);
    }

    public static double calculateFromDesiredProbability (
            double desiredProbability,
            Iterable<State> states) {
        return calculateFromDesiredProbability(
            desiredProbability,
            states,
            desiredProbability / 100.0);
    }

    private static double estimateAcceptanceProbability (
            double[] energyDeltas,
            double temperature) {
        double sumProbs = 0.0;
        for (double d : energyDeltas) {
            sumProbs += Math.min(1.0, Math.exp(-d / temperature));
        }
        return sumProbs / energyDeltas.length;
    }

}
