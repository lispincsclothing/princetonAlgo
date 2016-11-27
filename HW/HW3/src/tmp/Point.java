/*************************************************************************
 *  Author:        Tihomir Kit
 *  Written:       11/02/2014
 *  Last updated:  11/02/2014
 *
 *  Compilation:   javac Point.java
 *  Execution:
 *  Dependencies:  StdDraw.java
 *
 *  An immutable data type for points in the plane.
 *    http://coursera.cs.princeton.edu/algs4/assignments/collinear.html
 *
 *************************************************************************/

import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {
    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER;       // YOUR DEFINITION HERE

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // Create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
        this.SLOPE_ORDER = new SlopeOrder();
    }

    // Compares points by the slopes they make with the invoking point (x0, y0)
    private class SlopeOrder implements Comparator<Point> {
        public int compare(Point point1, Point point2) {
            double slope1 = slopeTo(point1);
            double slope2 = slopeTo(point2);

            if (slope1 > slope2)
                return 1;
            else if (slope1 < slope2)
                return -1;

            return 0;
        }
    }

    // Plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // Draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // Slope between this point and that point
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        if (that.x == this.x) {
            if (that.y == this.y)
                return Double.NEGATIVE_INFINITY;   // point with itself
            return Double.POSITIVE_INFINITY;       // vertical line
        }
        else if (that.y == this.y)
            return 0.0;                            // horizontal line

        return (double) (that.y - this.y) / (that.x - this.x);
    }

    // Is this point lexicographically smaller than that one?
    // Comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        if (this.y == that.y && this.x == that.x)
            return 0;
        else if (this.y < that.y || (this.y == that.y && this.x < that.x))
            return -1;

        return 1;
    }

    // Return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // Unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
        // Brute.java and Fast.java are the best unit tests ;)
    }
}
