package astar;

//import edu.princeton.cs.algs4.In;

import edu.princeton.cs.algs4.Stopwatch;
import heap.ArrayHeapMinPQ;
import heap.ExtrinsicMinPQ;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @see ShortestPathsSolver for more method documentation
 */
public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private double timeSpent;
    private double solutionWeight;
    private SolverOutcome outcome;
    private int numStatesExplored;

    private ExtrinsicMinPQ<Vertex> pq;
    private HashMap<Vertex, Double> distTo;
    private HashMap<Vertex, Vertex> edgeTo;
    private List<Vertex> solution;
    /**
     * Immediately solves and stores the result of running memory optimized A*
     * search, computing everything necessary for all other methods to return
     * their results in constant time. The timeout is given in seconds.
     */
    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch sw = new Stopwatch();
        Vertex current;
        List<WeightedEdge<Vertex>> neighbors;
        distTo = new HashMap<>();
        edgeTo = new HashMap<>();
        solution = new ArrayList<>();
        pq = new ArrayHeapMinPQ<>();            //made change here
        solutionWeight = 0;
        distTo.put(start, 0.0);
        numStatesExplored = 0;

        pq.add(start, input.estimatedDistanceToGoal(start, end));
        while (pq.size() > 0) {
            if (sw.elapsedTime() > timeout) {
                outcome = SolverOutcome.TIMEOUT;
                timeSpent = sw.elapsedTime();
                return;
            }
            numStatesExplored++;
            current = pq.removeSmallest();
            if (current.equals(end) && sw.elapsedTime() < timeout) {
                outcome = SolverOutcome.SOLVED;
                timeSpent = sw.elapsedTime();
                solutionWeight = distTo.get(end);
                /*
                Output solution in reverse order
                 */
                Vertex last = end;
                while (last != null) {
                    solution.add(last);
                    last = edgeTo.get(last);
                }
                Collections.reverse(solution);
                //solution.add(0, start);
                return;
            }

            /*
             *** Searching neighbours
             */
            neighbors = input.neighbors(current);
            for (WeightedEdge<Vertex> e : neighbors) {
                Vertex f = e.from();
                Vertex v = e.to();
                double wei = e.weight();
                double heuristic = input.estimatedDistanceToGoal(v, end);

                if (distTo.containsKey(v)) {
                    double potentialWeight = distTo.get(f) + wei;
                    if (potentialWeight < distTo.get(v)) {
                        edgeTo.replace(v, f);
                        distTo.replace(v, potentialWeight);
                        pq.changePriority(v, potentialWeight + heuristic);
                    }
                } else {
                    edgeTo.put(v, f);
                    distTo.put(v, wei + distTo.get(f));
                    pq.add(v, wei + distTo.get(f) + heuristic);
                }
            }
        }
        if (pq.size() == 0 && outcome != SolverOutcome.SOLVED) {
            outcome = SolverOutcome.UNSOLVABLE;
            timeSpent = sw.elapsedTime();
            return;
        }
        //timeSpent = sw.elapsedTime();
    }

    @Override
    public SolverOutcome outcome() {
        return outcome;
    }

    @Override
    public List<Vertex> solution() {
        return solution;
    }

    @Override
    public double solutionWeight() {
        if (outcome == SolverOutcome.SOLVED) {
            return solutionWeight;
        } else {
            return 0;
        }
    }

    /** The total number of priority queue removeSmallest operations. */
    @Override
    public int numStatesExplored() {
        return numStatesExplored;
    }

    @Override
    public double explorationTime() {
        return timeSpent;
    }
}
