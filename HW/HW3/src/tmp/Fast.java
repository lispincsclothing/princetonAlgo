/*----------------------------------------------------------------
 *  Author:        Tihomir Kit
 *  Written:       11/02/2014
 *  Last updated:  11/03/2014
 *
 *  Compilation:   javac Fast.java
 *  Execution:     java Fast input/inputFileName.txt
 *
 *  Fast points collinearity check implementation as per the specification
 *  available at:
 *    http://coursera.cs.princeton.edu/algs4/assignments/collinear.html
 *
 *----------------------------------------------------------------*/

import java.util.Collections;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Fast {
    private static Point[] points;
    private static Point[] auxPoints;
    private static int pointCount;

    // Initializes drawing dimensions
    private static void initializeDrawing() {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
    }

    // Reads input from file, loads all the data into points arrays and
    // draws bare points onto the canvas
    private static void readInput(String filename) {
        In input = new In(filename);
        pointCount = input.readInt();
        points = new Point[pointCount];
        auxPoints = new Point[pointCount];

        for (int i = 0; i < pointCount; i++) {
            int x = input.readInt();
            int y = input.readInt();
            Point point = new Point(x, y);
            points[i] = point;
            auxPoints[i] = point;
            point.draw();
        }
    }

    // Finds collinear points for each point and outputs lines made by
    // four or more collinear points
    private static void checkCollinearity() {
        HashSet<String> lineHashes = new HashSet<String>();

        for (int i = 0; i < pointCount; i++) {
            List<Point> collinearPoints = new ArrayList<Point>(pointCount);
            Arrays.sort(points, auxPoints[i].SLOPE_ORDER);

            for (int j = 1; j < pointCount; j++) {
                boolean slopesMatch = slopesMatch(i, j);

                if (slopesMatch)
                    collinearPoints.add(points[j]);

                if (!slopesMatch || j == (pointCount - 1)) {
                    if (collinearPoints.size() >= 3) {
                        collinearPoints.add(auxPoints[i]);
                        Collections.sort(collinearPoints);
                        String lineHash = getLineHash(collinearPoints);

                        if (!lineHashes.contains(lineHash)) {
                            outputCollinearPoints(collinearPoints);
                            lineHashes.add(lineHash);
                        }
                    }
                }

                if (!slopesMatch && j != (pointCount - 1)) {
                    collinearPoints = new ArrayList<Point>(pointCount);
                    collinearPoints.add(points[j]);
                }
            }
        }
    }

    // Checks wheather two conescutive points have the same slope
    private static boolean slopesMatch(int i, int j) {
        double slope1 = auxPoints[i].slopeTo(points[j - 1]);
        double slope2 = auxPoints[i].slopeTo(points[j]);
        return slope1 == slope2;
    }

    // Gets collinear line hash (used to check wheather the line
    // has already been outputted)
    private static String getLineHash(List<Point> collinearPoints) {
        String hash1 = Collections.min(collinearPoints).toString();
        String hash2 = Collections.max(collinearPoints).toString();
        return hash1 + hash2;
    }

    // Outputs the connection to terminal and draws the line onto the canvas
    private static void outputCollinearPoints(List<Point> collinearPoints) {
        for (int i = 0; i < collinearPoints.size() - 1; i++) {
            StdOut.print(collinearPoints.get(i) + " -> ");
        }
        StdOut.println(Collections.max(collinearPoints));

        Point min = Collections.min(collinearPoints);
        Point max = Collections.max(collinearPoints);
        min.drawTo(max);
    }

    public static void main(String[] args) {
        // Rescale the coordinate system.
        initializeDrawing();
        readInput(args[0]);
        Arrays.sort(points);
        Arrays.sort(auxPoints);
        checkCollinearity();
    }
}
