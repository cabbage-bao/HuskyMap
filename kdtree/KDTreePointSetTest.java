package kdtree;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KDTreePointSetTest {

    //private static KDTreePointSet kdSet;

    @Test
    public void simpleTest() {
        for (int j = 1; j < 50; j++) {
            int seed = j;
            Random random = new Random(seed);
            List<Point> points = new ArrayList<>();
            for (int i = 0; i < 1000000; i++) {
                points.add(new Point(random.nextDouble(), random.nextDouble()));
            }
            KDTreePointSet test = new KDTreePointSet(points);
            NaivePointSet naive = new NaivePointSet(points);
            double x = random.nextDouble();
            double y = random.nextDouble();

            assertEquals(test.nearest(x, y), naive.nearest(x, y));
        }

    }
}


