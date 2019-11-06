package kdtree;
import java.util.List;

public class KDTreePointSet implements PointSet {

    private KDNode kdRoot;

    private double bestX;
    private double bestY;
    private double minDistance = Double.MAX_VALUE;
    /**
     * Instantiates a new KDTree with the given points.
     * @param points a non-null, non-empty list of points to include
     *               (makes a defensive copy of points, so changes to the list
     *               after construction don't affect the point set)
     **/
    public KDTreePointSet(List<Point> points) {
        int n = points.size();
        if (n == 0 || points == null) {
            throw new IllegalArgumentException();
        }
        kdRoot = new KDNode(points.get(0));
        for (int i = 0; i < n; i++) {
            kdRoot.add(kdRoot, points.get(i), 1);
        }
    }
    /**
     * Returns the point in this set closest to (x, y) in (usually) O(log N) time,
     * where N is the number of points in this set.
     */
    @Override
    public Point nearest(double x, double y) {
        recursion(kdRoot, x, y, 1);
        return new Point(bestX, bestY);
    }

    private void recursion(KDNode root, double x, double y, int layer) {
        if (root == null) {
            return;
        }

        double temp = root.point.distanceSquaredTo(x, y);
        if (temp < minDistance) {
            minDistance = temp;
            this.bestX = root.point.x();
            this.bestY = root.point.y();
        }

        if (layer % 2 == 1) {
            if (x < root.point.x()) {
                recursion(root.left, x, y, layer + 1);
                if (Math.pow((root.point.x() - x), 2) < minDistance) {
                    recursion(root.right, x, y, layer + 1);
                }
            } else {
                recursion(root.right, x, y, layer + 1);
                if (Math.pow((root.point.x() - x), 2) < minDistance) {
                    recursion(root.left, x, y, layer + 1);
                }
            }
        } else if (layer % 2 == 0) {
            if (y < root.point.y()) {
                recursion(root.left, x, y, layer + 1);
                if (Math.pow((root.point.y() - y), 2) < minDistance) {
                    recursion(root.right, x, y, layer + 1);
                }
            } else {
                recursion(root.right, x, y, layer + 1);
                if (Math.pow((root.point.y() - y), 2) < minDistance) {
                    recursion(root.left, x, y, layer + 1);
                }
            }
        }
    }

    private class KDNode {
        Point point;
        KDNode left;
        KDNode right;
        public KDNode(Point point) {
            this.point = point;
            this.left = null;
            this.right = null;
        }

        /**
         * A helper method ADD to add points to the PointSet.
         */
        public KDNode add(KDNode root, Point addPoint, int layer) {
            if (root == null) {
                return new KDNode(addPoint);
            }
            if (root.point.equals(addPoint)) {
                return root;
            }
            if (layer % 2 == 1) {
                if (addPoint.x() < root.point.x()) {
                    root.left = add(root.left, addPoint, layer + 1);
                } else {
                    root.right = add(root.right, addPoint, layer + 1);
                }
            } else if (layer % 2 == 0) {
                if (addPoint.y() < root.point.y()) {
                    root.left = add(root.left, addPoint, layer + 1);
                } else {
                    root.right = add(root.right, addPoint, layer + 1);
                }
            }
            return root;
        }
    }
}
