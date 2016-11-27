/**
 * A program that examines 4 points at a time and checks whether they all lie
 * on the same line segment, returning all such line segments
 */

import edu.princeton.cs.algs4.ResizingArrayQueue;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import java.util.Iterator;
import java.util.Arrays;

public class BruteCollinearPoints {
    private ResizingArrayQueue<LineSegment> segments = new ResizingArrayQueue<LineSegment>();
    private int N;
    private Point[] pointArr;
    /**
     * Find all line segments containing 4 points
     */
    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new NullPointerException("invalid input points");

        this.pointArr = new Point[points.length];

        // check for duplicate
        for (int i = 0; i < points.length; i++) {
            this.pointArr[i] = points[i];
            for (int j = i + 1; j < points.length; j++) {
                if (i != j && points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException("duplicate points");
            }
        }

        findSegments(this.pointArr);
    }

    private void findSegments(Point[] points) {
        Arrays.sort(points);
        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                for (int k = j + 1; k < points.length - 1; k++) {
                    for (int l = k + 1; l < points.length; l++) {
                        Point p = points[i];
                        Point q = points[j];
                        Point r = points[k];
                        Point s = points[l];
                        if (p == null || q == null || r == null || s == null)
                            throw new NullPointerException("invalid input point");
                        if (p.slopeTo(q) == p.slopeTo(r) 
                            && p.slopeTo(r) == p.slopeTo(s)) {
                            segments.enqueue(new LineSegment(p, points[l]));
                            N++;
                        }
                    }
                }
            }
        }
    }

    /**
     * The number of line segments
     * @return the number of line segments
     */
    public int numberOfSegments() {
        return segments.size();
    }

    /**
     * Returns an array of the line segments that contains 4 collinear points
     * @return an array of the line segments that contains 4 collinear points
     */
    public LineSegment[] segments() {
        LineSegment[] lineSegments = new LineSegment[segments.size()];

        Iterator<LineSegment> it = segments.iterator();
        int i = 0;
        while (it.hasNext()) {
            lineSegments[i++] = it.next();
        }

        return lineSegments;
    }

    /**
     * Unit tests the <tt>ResizingArrayQueue</tt> data type.
     */
    public static void main(String[] args) {

        // read the N points from a file
        In in = new In(args[0]);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.show(0);
        StdDraw.setXscale(0, 32000);
        StdDraw.setYscale(0, 32000);
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.setPenRadius(0.005);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        StdDraw.setPenRadius(0.001);
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        System.out.println(collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
    }
}