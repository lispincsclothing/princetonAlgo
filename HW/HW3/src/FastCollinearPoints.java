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

public class FastCollinearPoints {
    private ResizingArrayQueue<LineSegment> segments = new ResizingArrayQueue<LineSegment>();
    private int N; // number of line segments
    private Point[] pointArr;
    /**
     * Find all line segments containing 4 points
     */
    public FastCollinearPoints(Point[] points) {
        // check for null input
        if (points == null)
            throw new NullPointerException("invalid input points");

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new NullPointerException("invalid input points");
        }

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

        for (int pIndex = 0; pIndex < points.length - 3; pIndex++) {
            Arrays.sort(points);
            
            Point p = points[pIndex];
            Arrays.sort(points, p.slopeOrder());

            int i = 1;
            while (i < points.length - 2) {
                if (p.slopeTo(points[i]) == p.slopeTo(points[i + 1])
                    && p.slopeTo(points[i]) == p.slopeTo(points[i + 2])) {

                    int lastIndex = i + 2;

                    while ((lastIndex < points.length - 1)
                         && p.slopeTo(points[i]) == p.slopeTo(points[lastIndex + 1])) {
                        lastIndex++;
                    }
                    if (isMinimum(p, points, i, lastIndex)) {
                        segments.enqueue(new LineSegment(p, points[lastIndex]));
                        N++;
                    }
                    i = lastIndex;
                } else {
                    i++;
                }
            }
        }
    }

    private boolean isMinimum(Point p, Point[] points, int start, int end) {
        for (int i = start; i <= end; i++) {
            if (p.compareTo(points[i]) > 0)
                return false;
        }
        return true;
    }


    /**
     * The number of line segments
     * @return the number of line segments
     */
    public int numberOfSegments() {
        return N;
    }

    /**
     * Returns an array of the line segments that contains 4 collinear points
     * @return an array of the line segments that contains 4 collinear points
     */
    public LineSegment[] segments() {
        LineSegment[] lineSegments = new LineSegment[N];

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
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.setPenRadius(0.005);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();


        // print and draw the line segments
        StdDraw.setPenRadius(0.001);
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        System.out.println(collinear.numberOfSegments());

        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
    }
}