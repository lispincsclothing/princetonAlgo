/*----------------------------------------------------------------
 *  Author:        Tihomir Kit
 *  Written:       10/26/2014
 *  Last updated:  10/26/2014
 *
 *  Compilation:   javac Subset.java
 *  Execution:     echo AA BB BB BB BB BB CC CC | java Subset 8
 *
 *  Prints randomized subset of strings as per the specification available at:
 *    http://coursera.cs.princeton.edu/algs4/assignments/queues.html
 *
 *----------------------------------------------------------------*/
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

public class Subset {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            queue.enqueue(StdIn.readString());
        }

        for (int i = 0; i < k; i++) {
            System.out.println(queue.dequeue());
        }
    }
}
