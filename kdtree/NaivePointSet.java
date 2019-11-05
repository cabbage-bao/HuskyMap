package kdtree;
import java.util.List;

/**
 * Naive nearest neighbor implementation using a linear scan.
 */
public class NaivePointSet implements PointSet {
    private List<Point> set;
    /**
     * Instantiates a new NaivePointSet with the given points.
     * @param points a non-null, non-empty list of points to include
     *               (makes a defensive copy of points, so changes to the list
     *               after construction don't affect the point set)
     */
    public NaivePointSet(List<Point> points) {
        set = points;
    }
    /**
     * Returns the point in this set closest to (x, y) in O(N) time,
     * where N is the number of points in this set.
     */
    @Override
    public Point nearest(double x, double y) {
        double min = Integer.MAX_VALUE;
        double cur = 0;
        double tempX = 0;
        double tempY = 0;
        for (int i = 0; i < set.size(); i++) {
            cur = set.get(i).distanceSquaredTo(x, y);
            if (cur < min) {
                min = cur;
                tempX = set.get(i).x();
                tempY = set.get(i).y();
            }
        }
        return new Point(tempX, tempY);
    }
}
