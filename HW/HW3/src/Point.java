
/**
 * An immutable data type Point that represents a point in the plane
 * @author Vinh Tran
 * @version 1.0
 */

import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;
import java.util.Arrays;

public class Point implements Comparable<Point> {
    private final int x; // x-coordinate of this point
    private final int y; // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Draw this point
     */
    public void draw() {
        StdDraw.point(this.x, this.y);
    }

    /**
     * Draws the line segment from this point to that point
     * @param the end point to draw this point to
     */
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        if (this.y != that.y) {
            return this.y - that.y;
        } else {
            return this.x - that.x;
        }
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        if ((this.y == that.y) && (this.x == that.x)) { // degenerate segment
            return Double.NEGATIVE_INFINITY;
        }
        if (this.x == that.x) { // vertical segment
            return Double.POSITIVE_INFINITY;
        }
        if (this.y == that.y) { // horizontal segment
            return +0.0;
        }
        return (double) (that.y - this.y) / (double) (that.x - this.x);
    }

    /**
     * compare two points by slopes they make with this point
     * The slope is defined as in the slopeTo() method.
     */
    public Comparator<Point> slopeOrder() {
        return new SortBySlope();
    }
    private class SortBySlope implements Comparator<Point> {
        public int compare(Point p, Point q) {
            return Double.compare(slopeTo(p), slopeTo(q));
        }
    }

    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public static void main(String[] args) {
        Point[] points = new Point[5];
        points[0] = new Point(1, 1);
        points[4] = new Point(2, 1);
        points[1] = new Point(4, 4);
        points[3] = new Point(5, 5);
        points[2] = new Point(6, 5);
        System.out.println("before sorting, a = " + Arrays.asList(points));

        //
        Arrays.sort(points, points[0].slopeOrder());
        System.out.println("After Arrays.sort(points)");
        System.out.println("before sorting, a = " + Arrays.asList(points));
    }
}