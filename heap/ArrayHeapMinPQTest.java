package heap;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ArrayHeapMinPQTest {
    /* Be sure to write randomized tests that can handle millions of items. To
     * test for runtime, compare the runtime of NaiveMinPQ vs ArrayHeapMinPQ on
     * a large input of millions of items. */
    private static ArrayHeapMinPQ minPQ = new ArrayHeapMinPQ();

    @Test
    public void testSimpleCase() {

        for (int i = 0; i < 100; i++) {
            minPQ.add(i, i);
        }

        minPQ.changePriority(3, -1);
        assertEquals(3, minPQ.getSmallest());

        minPQ.changePriority(50, -2);
        assertEquals(50, minPQ.getSmallest());

        minPQ.removeSmallest();
        assertEquals(3, minPQ.getSmallest());

        minPQ.changePriority(99, -50);
        assertEquals(99, minPQ.getSmallest());

        minPQ.changePriority(99, 50);
        assertEquals(3, minPQ.getSmallest());


    }

}
