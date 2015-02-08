import java.util.Iterator;

/**
 * Created by bfelder on 2/7/15.
 */
public class Deque<Item> implements Iterable<Item> {


    private Node first = null;
    private Node last = null;
    private int size = 0;

    private class Node
    {
        Item item;
        Node next;
        Node previous;
    }


    /**
     * construct an empty deque
     */
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    /**
     * is the deque empty?
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * return the number of items on the deque
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * insert the item at the front
     * @param item
     */
    public void addFirst(Item item) {
        if (item == null)
            throw new java.lang.NullPointerException();
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        first.previous = null;
        if (oldFirst != null)
            oldFirst.previous = first;
        if (last == null)
            last = first;
        size++;
    }

    /**
     * insert the item at the end
     * @param item
     */
    public void addLast(Item item) {
        if (item == null)
            throw new java.lang.NullPointerException();
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.previous = oldLast;
        if (oldLast != null)
            oldLast.next = last;
        if (first == null)
            first = last;
        size++;
    }

    /**
     * delete and return the item at the front
     * @return
     */
    public Item removeFirst() {
        if ((size == 0) || (first == null))
            throw new java.util.NoSuchElementException();
        Item item = first.item;
        first = first.next;
        if (first != null)
            first.previous = null;
        size--;
        return item;
    }

    /**
     * delete and return the item at the end
     * @return
     */
    public Item removeLast() {
        if ((size == 0) || (last == null))
            throw new java.util.NoSuchElementException();
        Item item = last.item;
        last = last.previous;
        if (last != null)
            last.next = null;
        size--;
        return item;
    }


    /**
     * return an iterator over items in order from front to end
     * @return
     */
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (isEmpty())
                throw new java.util.NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    /**
     * unit testing
     * @param args
     */
    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            // System.out.println("You said: " + s);
            if(s.equals("-"))
                StdOut.print(deque.removeFirst() + " ");
            else
                deque.addFirst(s);
        }
    }
}
