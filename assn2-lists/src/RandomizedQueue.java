import java.util.Iterator;

/**
 * Created by bfelder on 2/7/15.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int itemCount;
    private int head;
    private int tail;
    private final static double sparsityThreshold = 0.25;

    /**
     * construct an empty randomized queue
     */
    public RandomizedQueue() {
        items = (Item[])new Object[1];
        head = 0;
        tail = 0;
        itemCount = 0;

    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        int itemIndex = 0;
        for (int i = head; i < tail; i++) {
            if (items[i] != null) {
                copy[itemIndex] = items[i];
                itemIndex++;
            }
        }
        // Reset head and tail.
        tail = itemIndex;
        head = 0;
        itemCount = itemIndex;
    }

    /**
     * returns the width of the distance between tail and head.
     * @return
     */
    private int span() {
        return tail - head;
    }

    private double sparsity() {
        int span = span();
        if (span == 0)
            return 1.0;
        return itemCount / span;
    }

    /**
     * is the queue empty?
     * @return
     */
    public boolean isEmpty() {
        return itemCount == 0;
    }

    /**
     * return the number of items on the queue
     * @return
     */
    public int size() {
        return itemCount;
    }

    /**
     * add the item
     * @param item
     */
    public void enqueue(Item item) {
        if (tail == items.length)
            resize(2 * items.length);
        items[tail++] = item;
        itemCount++;
    }

    private int nextRandomIndex() {

    }

    /**
     * delete and return a random item
     * @return
     */
    public Item dequeue() {
        Item toReturn = items[--tail];
        items[tail] = null;
        if ((size() > 0) &&
                (size() <= (items.length / 4)) ||
                 sparsity() <= sparsityThreshold)
            resize(items.length / 2);
        itemCount--;
        return toReturn;
    }

    /**
     * return (but do not delete) a random item
     * @return
     */
    public Item sample() {

    }

    /**
     * return an independent iterator over items in random order
     * @return
     */
    public Iterator<Item> iterator() {

    }

    /**
     * unit testing
     * @param args
     */
    public static void main(String[] args) {

    }
}