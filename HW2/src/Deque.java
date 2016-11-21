/*----------------------------------------------------------------
 *  Author:        Tihomir Kit
 *  Written:       10/25/2014
 *  Last updated:  10/25/2014
 *
 *  Compilation:   javac Deque.java
 *  Execution:     java Deque
 *
 *  Generic Deque ('Deck') implementation as per the specification available at:
 *    http://coursera.cs.princeton.edu/algs4/assignments/queues.html
 *
 *----------------------------------------------------------------*/

import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int count;

    private class Node {
        private Item item;
        private Node next;
        private Node previous;

        Node(Item newItem) {
            item = newItem;
            next = null;
            previous = null;
        }
    }

    // Creates an empty deque queue ('Deck')
    public Deque() {
        first = null;
        last = null;
        count = 0;
    }

    // Checks whether the deque is empty
    public boolean isEmpty() {
        return first == null;
    }

    // Returns the number of items on the deque
    public int size() {
        return count;
    }

    // Inserts the item at the front
    public void addFirst(Item item) {
        if (item == null)
            throw new NullPointerException();

        if (isEmpty()) {
            first = new Node(item);
            last = first;
        } else {
            Node node = new Node(item);
            node.next = first;
            first.previous = node;
            first = node;
        }

        count++;
    }

    // Inserts the item at the end
    public void addLast(Item item) {
        if (item == null)
            throw new NullPointerException();

        if (isEmpty()) {
            first = new Node(item);
            last = first;
        } else {
            Node node = new Node(item);
            last.next = node;
            node.previous = last;
            last = node;
        }

        count++;
    }

    // Deletes and returns the item at the front
    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException();

        Node node = first;

        if (size() == 1) {
            first = null;
            last = null;
        } else {
            first.next.previous = null;
            first = first.next;
        }

        node.next = null;
        count--;

        return node.item;
    }

    // Deletes and returns the item at the end
    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException();

        Node node = last;

        if (size() == 1) {
            first = null;
            last = null;
        } else {
            last.previous.next = null;
            last = last.previous;
        }

        node.previous = null;
        count--;

        return node.item;
    }

    // Returns an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // Private DequeIterator class with haxNext, remove and next logic
    private class DequeIterator implements Iterator<Item> {
        private Node currentNode;

        public DequeIterator() {
            currentNode = first;
        }

        public boolean hasNext() {
            return currentNode != null;
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();

            Node node = currentNode;
            currentNode = currentNode.next;
            return node.item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // Runs a sample of Deque, optional
    public static void main(String[] args) {
        Deque<Integer> queue = new Deque<Integer>();

        queue.addFirst(3);
        queue.addFirst(2);
        queue.addFirst(1);
        queue.addLast(4);
        queue.addLast(5);
        queue.addLast(6);

        StdOut.println(queue.first.item);      // should print out 1
        StdOut.println(queue.last.item);       // should print out 6
        StdOut.println(queue.count);           // should print out 6

        Iterator<Integer> iterator = queue.iterator();
        StdOut.println(iterator.hasNext());    // should print out true
        StdOut.println(iterator.next());       // should print out 1

        StdOut.println(queue.removeFirst());   // should print out 1
        StdOut.println(queue.removeLast());    // should print out 6
    }
}
