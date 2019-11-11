package heap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    private ArrayList<PriorityNode> list;
    private HashMap<T, PriorityNode> cache = new HashMap<>();
    private int size;

    public ArrayHeapMinPQ() {
        this.size = 0;
        list = new ArrayList<>();
        list.add(null);
    }

    /**
     * A helper method for swapping the items at two indices of the array heap.
     */
    private void swap(int a, int b) {
        PriorityNode tempA = list.get(a);
        tempA.setIndex(b);

        PriorityNode tempB = list.get(b);
        tempB.setIndex(a);

        list.set(a, tempB);
        list.set(b, tempA);
    }

    /**
     * Adds an item with the given priority value.
     * Assumes that item is never null.
     * Runs in O(log N) time (except when resizing).
     * @throws IllegalArgumentException if item is already present in the PQ
     */
    @Override
    public void add(T item, double priority) {
        if (cache.containsKey(item)) {
            throw new IllegalArgumentException();
        }
        PriorityNode newNode = new PriorityNode(item, priority);
        newNode.setIndex(++size);

        list.add(newNode);
        cache.put(item, newNode);

        swim(size);
    }

    /**
     * Returns true if the PQ contains the given item; false otherwise.
     * Runs in O(log N) time.
     */
    @Override
    public boolean contains(T item) {
        return cache.containsKey(item);
    }

    /**
     * Returns the item with the smallest priority.
     * Runs in O(log N) time.
     * @throws NoSuchElementException if the PQ is empty
     */
    @Override
    public T getSmallest() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return list.get(1).item;
    }

    /**
     * Removes and returns the item with the smallest priority.
     * Runs in O(log N) time (except when resizing).
     * @throws NoSuchElementException if the PQ is empty
     */
    @Override
    public T removeSmallest() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        T min = list.get(1).item;
        swap(1, size);
        cache.remove(min);
        list.remove(size--);
        sink(1);
        return min;
    }

    /**
     * Changes the priority of the given item.
     * Runs in O(log N) time.
     * @throws NoSuchElementException if the item is not present in the PQ
     */
    @Override
    public void changePriority(T item, double priority) {
        PriorityNode node = cache.get(item);
        if (node == null) {
            throw new NoSuchElementException();
        }

        double oldPriority = node.getPriority();
        node.setPriority(priority);

        if (priority > oldPriority) {
            sink(node.getIndex());
        } else if (priority < oldPriority) {
            swim(node.getIndex());
        }
    }

    /**
     * Returns the number of items in the PQ.
     * Runs in O(log N) time.
     */
    @Override
    public int size() {
        return this.size;
    }

    public double getPriority(T item) {
        PriorityNode node = cache.get(item);
        if (node == null) {
            throw new NoSuchElementException();
        }
        return node.getPriority();
    }
    /**
     * *****************************************************************************************
     * *****************************************************************************************
     * Two helper functions swim and sink
     */
    private void swim(int k) {
        while (k > 1 && (list.get(k).getPriority() < list.get(k / 2).getPriority())) {
            swap(k, k / 2);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (2 * k <= size) {
            int j = 2 * k;
            if (j < size && list.get(j).getPriority() > list.get(j + 1).getPriority()) {
                j++;
            }
            if (list.get(k).getPriority() > list.get(j).getPriority()) {
                swap(k, j);
                k = j;
            } else {
                break;
            }
        }
    }

    private class PriorityNode implements Comparable<PriorityNode> {
        private T item;
        private double priority;
        private int index;

        PriorityNode(T e, double p) {
            this.item = e;
            this.priority = p;
        }

        T getItem() {
            return item;
        }


        int getIndex() {
            return index;
        }

        void setIndex(int newIndex) {
            this.index = newIndex;
        }

        double getPriority() {
            return priority;
        }

        void setPriority(double priority) {
            this.priority = priority;
        }

        @Override
        public int compareTo(PriorityNode other) {
            if (other == null) {
                return -1;
            }
            return Double.compare(this.getPriority(), other.getPriority());
        }

        @Override
        //@SuppressWarnings("unchecked")
        public boolean equals(Object o) {
            if (o == null || o.getClass() != this.getClass()) {
                return false;
            } else {
                return ((PriorityNode) o).getItem().equals(getItem());
            }
        }

        @Override
        public int hashCode() {
            return item.hashCode();
        }
    }
}
