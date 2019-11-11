package astar;

//import edu.princeton.cs.algs4.In;

import edu.princeton.cs.algs4.Stopwatch;
import heap.ArrayHeapMinPQ;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @see ShortestPathsSolver for more method documentation
 */
public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private double timeSpent;
    private double solutionWeight;
    private SolverOutcome outcome;
    private int numStatesExplored;

    private ArrayHeapMinPQ<Vertex> pq;
    private HashMap<Vertex, Double> distTo;
    private HashMap<Vertex, Vertex> edgeTo;
    private List<Vertex> solution;
    /**
     * Immediately solves and stores the result of running memory optimized A*
     * search, computing everything necessary for all other methods to return
     * their results in constant time. The timeout is given in seconds.
     */
    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        /*
        Initialization
         */
        Stopwatch sw = new Stopwatch();
        Vertex current;
        List<WeightedEdge<Vertex>> neighbors;
        distTo = new HashMap<>();
        edgeTo = new HashMap<>();
        solution = new LinkedList<>();
        pq = new ArrayHeapMinPQ<>();

        solutionWeight = 0;
        distTo.put(start, 0.0);
        numStatesExplored = 0;
        /*
         * Begin searching
         * */
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
                Add vertex to Solution in reverse order
                 */
                Vertex last = end;
                solution.add(last);
                while (last != start) {
                    solution.add(0, edgeTo.get(last));
                    last = edgeTo.get(last);
                }
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
