/**
 * Example implementation of the traveling salesman problem compatible with
 * simulated annealing.
 */

package com.ohzero.sa.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.awt.Point;
import com.ohzero.sa.acceptance.ClassicalAcceptanceProbabilityFunction;
import com.ohzero.sa.core.Annealer;
import com.ohzero.sa.schedule.ExponentialCoolingSchedule;
import com.ohzero.sa.state.State;
import com.ohzero.sa.util.InitialTemperatureHelpers;

public class TravelingSalesmanProblem {

    private static final Point[] POINTS = {
        new Point(247483332, 508400000),
        new Point(247588889, 512119444),
        new Point(248272222, 513947221),
        new Point(249044444, 511750000),
        new Point(249961110, 515488889),
        new Point(250100000, 510394444),
        new Point(250308332, 512752778),
        new Point(250677778, 510775000),
        new Point(251000000, 515166667),
        new Point(251033332, 515216667),
        new Point(251219444, 512183333),
        new Point(251508332, 515377778),
        new Point(251583332, 511636111),
        new Point(251622222, 512208333),
        new Point(251677778, 516069444),
        new Point(251688889, 510863889),
        new Point(251738889, 512694444),
        new Point(252108332, 513941667),
        new Point(252113889, 516191667),
        new Point(252141667, 508072221),
        new Point(252144444, 513788889),
        new Point(252233332, 514516667),
        new Point(252241667, 511744444),
        new Point(252333332, 513333333),
        new Point(252341667, 512030556),
        new Point(252355556, 513300000),
        new Point(252355556, 514955556),
        new Point(252427778, 514288889),
        new Point(252430556, 514525000),
        new Point(252525000, 515591667),
        new Point(252538889, 515352778),
        new Point(252538889, 515497221),
        new Point(252569444, 513988889),
        new Point(252636110, 515163889),
        new Point(252658332, 515452778),
        new Point(252666667, 509691667),
        new Point(252666667, 514833333),
        new Point(252705556, 515327778),
        new Point(252708332, 515058333),
        new Point(252708332, 515230556),
        new Point(252758332, 515336111),
        new Point(252772222, 515477778),
        new Point(252783332, 515255556),
        new Point(252783332, 515413889),
        new Point(252791667, 514455556),
        new Point(252811110, 515350000),
        new Point(252813889, 515125000),
        new Point(252833332, 515333333),
        new Point(252836110, 515466667),
        new Point(252847222, 515552778),
        new Point(252861110, 515041667),
        new Point(252861110, 515341667),
        new Point(252866667, 515333333),
        new Point(252875000, 515377778),
        new Point(252880556, 515466667),
        new Point(252908332, 515283333),
        new Point(252919444, 514244444),
        new Point(252925000, 515208333),
        new Point(252986110, 510016667),
        new Point(253008332, 513944444),
        new Point(253069444, 515077778),
        new Point(253119444, 510030556),
        new Point(253138889, 508833333),
        new Point(253152778, 514386111),
        new Point(253166667, 507666667),
        new Point(253205556, 514955556),
        new Point(253225000, 515077778),
        new Point(253252778, 514700000),
        new Point(253266667, 513502778),
        new Point(253375000, 514250000),
        new Point(253391667, 511733333),
        new Point(253405556, 512936111),
        new Point(253419444, 515075000),
        new Point(253588889, 513336111),
        new Point(253636110, 512811111),
        new Point(253686110, 512263889),
        new Point(253744444, 514366667),
        new Point(253777778, 512947221),
        new Point(253969444, 514225000),
        new Point(254000000, 511833333),
        new Point(254000000, 514250000),
        new Point(254047222, 510730556),
        new Point(254169444, 514038889),
        new Point(254169444, 514577778),
        new Point(254194444, 507936111),
        new Point(254297222, 507858333),
        new Point(254333332, 512200000),
        new Point(254408332, 513780556),
        new Point(254444444, 509583333),
        new Point(254513889, 509250000),
        new Point(254591667, 513166667),
        new Point(254697222, 513975000),
        new Point(254780556, 513625000),
        new Point(254805556, 509388889),
        new Point(254833332, 513833333),
        new Point(254905556, 513736111),
        new Point(254922222, 514002778),
        new Point(254950000, 508466667),
        new Point(254950000, 509652778),
        new Point(254975000, 514852778),
        new Point(255008332, 509805556),
        new Point(255105556, 512422221),
        new Point(255319444, 513044444),
        new Point(255333332, 509772221),
        new Point(255388889, 514083333),
        new Point(255458332, 513875000),
        new Point(255497222, 514319444),
        new Point(255500000, 514333333),
        new Point(255602778, 511586111),
        new Point(255669444, 514847221),
        new Point(255675000, 509588889),
        new Point(255747222, 514863889),
        new Point(255855556, 511513889),
        new Point(256094444, 510922221),
        new Point(256102778, 514752778),
        new Point(256225000, 514544444),
        new Point(256458332, 514500000),
        new Point(256500000, 513722221),
        new Point(256669444, 511744444),
        new Point(256838889, 515058333),
        new Point(256863889, 514688889),
        new Point(256961110, 512608333),
        new Point(257008332, 515847221),
        new Point(257083332, 515916667),
        new Point(257166667, 510500000),
        new Point(257175000, 510577778),
        new Point(257230556, 510041667),
        new Point(257347222, 515475000),
        new Point(257511110, 514491667),
        new Point(257519444, 509208333),
        new Point(257583332, 513958333),
        new Point(257652778, 510197221),
        new Point(257722222, 514833333),
        new Point(257758332, 510230556),
        new Point(257791667, 514497221),
        new Point(257933332, 514094444),
        new Point(258083332, 510605556),
        new Point(258166667, 511333333),
        new Point(258236110, 511525000),
        new Point(258266667, 510438889),
        new Point(258297222, 512452778),
        new Point(258333332, 510722221),
        new Point(258391667, 514652778),
        new Point(258477778, 512058333),
        new Point(258500000, 510333333),
        new Point(258566667, 510833333),
        new Point(258575000, 512988889),
        new Point(258575000, 514413889),
        new Point(258666667, 510666667),
        new Point(258677778, 512055556),
        new Point(258719444, 513547221),
        new Point(258725000, 512583333),
        new Point(258808332, 512213889),
        new Point(258830556, 511852778),
        new Point(258880556, 513863889),
        new Point(259000000, 510000000),
        new Point(259041667, 512016667),
        new Point(259283332, 513375000),
        new Point(259375000, 513133333),
        new Point(259447222, 514563889),
        new Point(259500000, 510666667),
        new Point(259516667, 513497221),
        new Point(259577778, 510752778),
        new Point(259583332, 510994444),
        new Point(259666667, 512833333),
        new Point(259833332, 514000000),
        new Point(259836110, 513280556),
        new Point(260002778, 512944444),
        new Point(260086110, 510836111),
        new Point(260166667, 513333333),
        new Point(260216667, 513669444),
        new Point(260333332, 511166667),
        new Point(260333332, 511666667),
        new Point(260336110, 511638889),
        new Point(260336110, 512002778),
        new Point(260488889, 510569444),
        new Point(260500000, 512500000),
        new Point(260502778, 512975000),
        new Point(260505556, 511358333),
        new Point(260550000, 513161111),
        new Point(260672222, 512586111),
        new Point(260747222, 510836111),
        new Point(260766667, 511669444),
        new Point(260772222, 512222221),
        new Point(260780556, 513616667),
        new Point(260836110, 511472221),
        new Point(260997222, 511611111),
        new Point(261080556, 512447221),
        new Point(261166667, 512166667),
        new Point(261236110, 511691667),
        new Point(261236110, 512227778),
        new Point(261333332, 512166667),
        new Point(261333332, 513000000),
        new Point(261502778, 511080556),
    };

    private static final double OPTIMAL_TOUR_LENGTH = 9352.0 * 10000;

    private static final double DESIRED_INITIAL_ACCEPTANCE_PROBABILITY = 0.925;

    private static final double DECAY_RATE = 1.0 - Math.exp(-14.0);

    private static final int NUM_ITERS = 25000000;

    public static void main (String[] args) {
        System.out.printf(
            "Running TSP on dataset of %d cities (optimal tour length %.2f)\n",
            POINTS.length, OPTIMAL_TOUR_LENGTH);
        long startTime = System.currentTimeMillis();
        TSPState initialState = new TSPState(POINTS);

        ArrayList<State> tempGenerationStates = new ArrayList<State>();
        TSPState prevState = initialState;
        while (tempGenerationStates.size() < 10000) {
            TSPState curState = initialState.getNeighbor(1.0);
            tempGenerationStates.add(curState);
            prevState = curState;
        }
        double initialTemperature =
            InitialTemperatureHelpers.calculateFromDesiredProbability(
                DESIRED_INITIAL_ACCEPTANCE_PROBABILITY,
                tempGenerationStates,
                0.0001);
        System.out.printf(
            "Using init temp %.2f (target init acceptance prob %.2f)\n",
            initialTemperature, DESIRED_INITIAL_ACCEPTANCE_PROBABILITY);

        System.out.println(
            "Running annealer with exponentially decaying cooling schedule");
        System.out.printf("Decay rate: %.8f\tIterations: %d\n",
            DECAY_RATE, NUM_ITERS);
        ClassicalAcceptanceProbabilityFunction capf =
            new ClassicalAcceptanceProbabilityFunction();
        ExponentialCoolingSchedule lcs = new ExponentialCoolingSchedule(
            initialTemperature, DECAY_RATE, NUM_ITERS);
        Annealer<TSPState> annealer = new Annealer(initialState, capf, lcs);

        TSPState bestState = annealer.getBestState();
        System.out.printf("Best state found has cost %.2f (%.2f-approximation)\n",
            bestState.getEnergyLevel(),
            bestState.getEnergyLevel() / OPTIMAL_TOUR_LENGTH);
        System.out.printf("Took %d seconds\n",
            (System.currentTimeMillis() - startTime) / 1000);
        for (Point point : bestState.getOrderedPoints()) {
            System.out.printf("%f\t%f\n", point.getX(), point.getY());
        }
    }

}

class TSPState implements State<TSPState> {

    private static final Random RANDOM = new Random();

    private final Point[] points;

    private final int[] ordering;

    private final double cost;

    public TSPState (Point[] points, int[] ordering) {
        this.points = Arrays.copyOf(points, points.length);
        this.ordering = Arrays.copyOf(ordering, ordering.length);
        this.cost = calculateCost(points, ordering);
    }

    public TSPState (Point[] orderedPoints) {
        this(orderedPoints, range(orderedPoints.length));
    }

    public TSPState (TSPState state, boolean mutateOrdering) {
        this.points = Arrays.copyOf(state.points, state.points.length);
        this.ordering = Arrays.copyOf(state.ordering, state.ordering.length);

        if (mutateOrdering && points.length > 1) {
            int a, b;
            while ((a = RANDOM.nextInt(points.length)) ==
                   (b = RANDOM.nextInt(points.length)));
            Point tmp = points[a];
            points[a] = points[b];
            points[b] = tmp;
        }

        this.cost = calculateCost(points, ordering);
    }

    private static double calculateCost (Point[] points, int[] ordering) {
        double cost = 0.0;
        int prevIndex = ordering[ordering.length - 1];
        for (int curIndex : ordering) {
            cost += points[prevIndex].distance(points[curIndex]);
            prevIndex = curIndex;
        }
        return cost;
    }

    private static int[] range (int len) {
        int[] seq = new int[len];
        for (int i = 0; i < len; ++i) {
            seq[i] = i;
        }
        return seq;
    }

    public Point[] getPoints () {
        return Arrays.copyOf(points, points.length);
    }

    public int[] getOrdering () {
        return Arrays.copyOf(ordering, ordering.length);
    }

    public Point[] getOrderedPoints () {
        Point[] orderedPoints = new Point[points.length];
        for (int i = 0; i < ordering.length; ++i) {
            orderedPoints[i] = points[ordering[i]];
        }
        return orderedPoints;
    }

    public TSPState getNeighbor (double temperature) {
        return new TSPState(this, true);
    }

    public double getEnergyLevel () {
        return cost;
    }

}
