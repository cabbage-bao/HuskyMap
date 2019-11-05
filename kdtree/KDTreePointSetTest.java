package kdtree;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.List;

public class KDTreePointSetTest {

    //private static KDTreePointSet kdSet;

    @Test
    public void simpleTest() {
        Point p1 = new Point(1.1, 2.2); // Point with x = 1.1, y = 2.2
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);
        Point p4 = new Point(1.0, 2.0);
        Point p5 = new Point(-4.0, -6.0);
        PointSet nn = new KDTreePointSet(List.of(p1, p2, p3, p4, p5));
        Point ret = nn.nearest(3.0, 4.0);

        assertEquals(new Point(3.3, 4.4), ret);
    }
}


