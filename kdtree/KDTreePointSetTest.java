package kdtree;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KDTreePointSetTest {

    @Test
    public void simpleTest() {
        Random random = new Random(6423);
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            points.add(new Point(random.nextDouble(), random.nextDouble()));
        }

        KDTreePointSet kdtree = new KDTreePointSet(points);
        NaivePointSet naive = new NaivePointSet(points);
        for (int k = 0; k < 100000; k++) {
            double x = random.nextDouble();
            double y = random.nextDouble();
            //System.out.println(x + " " + y);
            Point a = naive.nearest(x, y);
            Point b = kdtree.nearest(x, y);
            assertEquals(a, b);
        }


    }
}


