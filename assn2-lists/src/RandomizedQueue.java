import java.util.Iterator;
import java.util.Random;

/**
 * Created by bfelder on 2/7/15.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int itemCount;
    private int head;
    private int tail;
    private final static double sparsityThreshold = 0.25;
    private Random random;

    /**
     * construct an empty randomized queue
     */
    public RandomizedQueue() {
        items = (Item[])new Object[1];
        head = 0;
        tail = 0;
        itemCount = 0;
        random = new Random();
    }

    private void resize(int capacity) {
        // System.out.println("resize -- capacity: " + capacity);
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
        items = copy;
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
        if (item == null)
            throw new NullPointerException();
        if (tail == items.length)
            resize(2 * items.length);
        // System.out.println("enqueue: tail: " + tail);
        // System.out.println("enqueue: items.length: " + items.length);
        items[tail] = item;
        tail++;
        itemCount++;
    }

    private int nextRandomIndex() {
        return random.nextInt(itemCount);
    }

    /**
     *
     * @param emptyIndex
     */
    private void compress(int emptyIndex) {
        if (emptyIndex < head || emptyIndex > tail)
            throw new ArrayIndexOutOfBoundsException();
        for (int i = emptyIndex; i < tail; i++) {
            if ((i+1) < items.length)
                items[i] = items[i+1];
        }
        items[tail] = null;
        tail--;
        itemCount--;
    }

    /**
     * delete and return a random item
     * @return
     */
    public Item dequeue() {
        if (size() == 0)
            throw new java.util.NoSuchElementException();
        int randomIndex = nextRandomIndex();
        Item toReturn = items[randomIndex];
        items[randomIndex] = null;
        if ((size() > 0) &&
            (size() <= (items.length / 4)))
            resize(items.length / 2);
        else
            compress(randomIndex);
        return toReturn;
    }

    /**
     * return (but do not delete) a random item
     * @return
     */
    public Item sample() {
        if (size() == 0)
            throw new java.util.NoSuchElementException();
        int randomIndex = nextRandomIndex();
        Item toReturn = items[randomIndex];
        return toReturn;
    }

    private String itemsAsString() {
        String toReturn = "";
        for (int i = 0; i < items.length; i++) {
            toReturn += items[i] + " ";
        }
        toReturn += "\n";
        return toReturn;
    }

    private String membersAsString() {
        String toReturn = "";
        toReturn +=   "items.length: " + items.length;
        toReturn += "\nitemCount:    " + itemCount;
        toReturn += "\nhead:         " + head;
        toReturn += "\ntail:         " + tail;
        toReturn += "\n";
        return toReturn;
    }

    /**
     * return an independent iterator over items in random order
     * @return
     */
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private Item[] randomizedItems;
        private int currentIndex;
        private int arraySize;

        public RandomizedQueueIterator() {
            synchronized (items) {
                arraySize = size();
                randomizedItems = (Item[]) new Object[arraySize];
                for (int i = 0; i < arraySize; i++) {
                    randomizedItems[i] = items[i];
                }
            }
            currentIndex = 0;
            shuffle();
        }

        private void shuffle() {
            for (int i = arraySize - 1; i > 0; i--) {
                int randomIndex = random.nextInt(i + 1);
                Item temp = randomizedItems[randomIndex];
                randomizedItems[randomIndex] = randomizedItems[i];
                randomizedItems[i] = temp;
            }
        }

        public boolean hasNext() {
            return currentIndex != arraySize;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (isEmpty())
                throw new java.util.NoSuchElementException();
            // System.out.println("next(): " + randomizedItems);
            // System.out.println("next(): " + currentIndex);
            Item item = randomizedItems[currentIndex];
            // System.out.println("next(): " + item);
            currentIndex++;
            return item;
        }
    }

    /**
     * unit testing
     * @param args
     */
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        System.out.println(rq.itemsAsString());
        System.out.println(rq.membersAsString());
        System.out.println();
        for (int i = 0; i < 10; i++) {
            rq.enqueue(i);
            System.out.println(rq.itemsAsString());
        }

        System.out.println("--------- Iterator ------------");
        for (int i = 0; i < 10; i++) {
            Iterator<Integer> itor = rq.iterator();
            while (itor.hasNext()) {
                int nextInt = itor.next();
                System.out.print(nextInt + " ");
            }
            System.out.println();
        }
        System.out.println("--------- /Iterator ------------");

        System.out.println(rq.membersAsString());
        for (int i = 0; i < 10; i++) {
            int theInt = rq.dequeue();
            System.out.println(theInt + " -- " + rq.itemsAsString());
        }
        System.out.println(rq.membersAsString());
    }
}