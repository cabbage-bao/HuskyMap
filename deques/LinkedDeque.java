package deques;

public class LinkedDeque<T> implements Deque<T> {
    private int size;
    private Node first;        // beginning of the linkedlist
    private Node last;          // ending of the linkedlist

    public LinkedDeque() {      // constructor
        first = new Node(null);
        last = new Node(null);
        first.next = last;
        last.pre = first;
        size = 0;
    }

    private class Node {
        private T value;      //value
        private Node next;    // pointer to next node
        private Node pre;       // pointer to previous node
        Node(T value) {
            this.value = value;
            this.pre = null;
            this.next = null;
        }
    }

    public void addFirst(T item) {
        Node newNode = new Node(item);
        /*if (size == 0){
            newNode.next = last;
            newNode.pre = first;
            first.next = newNode;
            last.pre = newNode;
        }*/

        newNode.next = first.next;
        first.next.pre = newNode;
        newNode.pre = first;
        first.next = newNode;

        size += 1;
        //throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void addLast(T item) {
        Node newNode = new Node(item);

        newNode.pre = last.pre;
        last.pre.next = newNode;

        last.pre = newNode;
        newNode.next = last;
        size += 1;
        //throw new UnsupportedOperationException("Not implemented yet.");
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T ans = first.next.value;

        first.next.pre = null;
        first = first.next;
        first.pre = null;
        first.value = null;

        size -= 1;
        return ans;
        //throw new UnsupportedOperationException("Not implemented yet.");
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T ans = last.pre.value;

        last.pre.next = null;
        last = last.pre;
        last.value = null;
        last.next = null;

        size -= 1;
        return ans;
        //throw new UnsupportedOperationException("Not implemented yet.");
    }

    public T get(int index) {
        if ((index > size) || (index < 0)) {
            return null;
        }
        Node cur = first;
        for (int i = 0; i <= index; i++) {
            cur = cur.next;
        }
        return cur.value;
        //throw new UnsupportedOperationException("Not implemented yet.");
    }

    public int size() {
        return size;
    }
}
