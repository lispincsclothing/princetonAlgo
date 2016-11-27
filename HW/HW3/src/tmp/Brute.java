/*----------------------------------------------------------------
 *  Author:        Tihomir Kit
 *  Written:       11/02/2014
 *  Last updated:  11/03/2014
 *
 *  Compilation:   javac Brute.java
 *  Execution:     java Brute input/inputFileName.txt
 *
 *  Brute points collinearitz check implementation as per the specification
 *  available at:
 *    http://coursera.cs.princeton.edu/algs4/assignments/collinear.html
 *
 *----------------------------------------------------------------*/

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import ('Point.java');

public class Brute {
    private static Point[] points;
    private static int pointCount;

    // Initializes drawing dimensions
    private static void initializeDrawing() {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
    }

    // Reads input from file, loads all the data into points array and
    // draws bare points onto the canvas
    private static void readInput(String filename) {
        In input = new In(filename);
        pointCount = input.readInt();
        points = new Point[pointCount];

        for (int i = 0; i < pointCount; i++) {
            int x = input.readInt();
            int y = input.readInt();
            points[i] = new Point(x, y);
            points[i].draw();
        }
    }

    // Goes through all possible 4 point connection combinations (brute
    // force) and checks whether they lie on the same line
    private static void checkCollinearity() {
        for (int p = 0; p < pointCount; p++) {
            for (int q = p + 1; q < pointCount; q++) {
                double slopeToQ = points[p].slopeTo(points[q]);
                for (int r = q + 1; r < pointCount; r++) {
                    double slopeToR = points[p].slopeTo(points[r]);
                    if (slopeToQ != slopeToR)
                        continue;

                    for (int s = r + 1; s < pointCount; s++) {
                        double slopeToS = points[p].slopeTo(points[s]);
                        if (slopeToQ != slopeToS)
                            continue;

                        List<Point> collinearPoints = new ArrayList<Point>(4);
                        collinearPoints.add(points[p]);
                        collinearPoints.add(points[q]);
                        collinearPoints.add(points[r]);
                        collinearPoints.add(points[s]);
                        Collections.sort(collinearPoints);
                        outputCollinearPoints(collinearPoints);
                    }
                }
            }
        }
    }

    // Outputs the connection to terminal and draws the line onto the canvas
    private static void outputCollinearPoints(List<Point> collinearPoints) {
        for (int i = 0; i < 3; i++) {
            StdOut.print(collinearPoints.get(i) + " -> ");
        }
        StdOut.println(Collections.max(collinearPoints));

        Point min = Collections.min(collinearPoints);
        Point max = Collections.max(collinearPoints);
        min.drawTo(max);
    }

    public static void main(String[] args) {
        initializeDrawing();
        readInput(args[0]);
        checkCollinearity();
    }
}


