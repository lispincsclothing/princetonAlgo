/*----------------------------------------------------------------
 *  Author:        Tihomir Kit
 *  Written:       10/26/2014
 *  Last updated:  10/26/2014
 *
 *  Compilation:   javac RandomizedQueue.java
 *  Execution:     java RandomizedQueue
 *
 *  Randomized queue implementation as per the specification available at:
 *    http://coursera.cs.princeton.edu/algs4/assignments/queues.html
 *
 *----------------------------------------------------------------*/

import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int INITIAL_CAPACITY = 10;

    private Item[] queue;
    private int count;
    private int capacity;

    // Creates an empty randomized queue
    //@SuppressWarnings("unchecked")
    public RandomizedQueue() {
        capacity = RandomizedQueue.INITIAL_CAPACITY;
        queue = (Item[]) new Object[capacity];
        count = 0;
    }

    // Checks whether the queue is empty
    public boolean isEmpty() {
        return count == 0;
    }

    // Returns the number of items on the queue
    public int size() {
        return count;
    }

    // Adds the item to the queue
    public void enqueue(Item item) {
        if (item == null)
            throw new NullPointerException();

        if (count == capacity)
            resizeQueue(capacity * 2);

        queue[count++] = item;
    }

    // Deletes and returns a random item from the queue
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException();

        int randomIndex = StdRandom.uniform(count);

        Item item = queue[randomIndex];
        queue[randomIndex] = queue[--count];
        queue[count] = null;

        if (shouldCutQueueInHalf())
            resizeQueue(capacity / 2);

        return item;
    }

    // Resizes the queue to a new capacity
    //@SuppressWarnings("unchecked")
    private void resizeQueue(int newCapacity) {
        Item[] newQueue = (Item[]) new Object[newCapacity];

        for (int i = 0; i < count; i++) {
            newQueue[i] = queue[i];
        }

        capacity = newCapacity;
        queue = newQueue;
    }

    // Checks if queue size should be halved
    private boolean shouldCutQueueInHalf() {
        return count > 1 && count * 4 < capacity;
    }

    // Returns (but does not delete) a random item from the queue
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException();

        int randomIndex = StdRandom.uniform(count);
        return queue[randomIndex];
    }

    // Returns an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    // Private DequeIterator class with haxNext, remove and next logic
    private class RandomizedQueueIterator implements Iterator<Item> {
        private int iteratorCount = count;
        private int[] indexes;

        public RandomizedQueueIterator() {
            indexes = new int[iteratorCount];

            for (int i = 0; i < iteratorCount; i++) {
                indexes[i] = i;
            }

            StdRandom.shuffle(indexes);
        }

        public boolean hasNext() {
            return iteratorCount > 0;
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();

            return queue[indexes[--iteratorCount]];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // Runs a sample of RandomizedQueue, optional
    public static void main(String[] args) {

    }
}
