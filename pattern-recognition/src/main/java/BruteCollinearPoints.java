import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {

    private final List<LineSegment> segments;

    /**
     * Constructor
     * @param points points
     */
    public BruteCollinearPoints(Point[] points) {
        // check arguments
        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException();
            }
        }

        final Point[] myPoints = new Point[points.length];
        System.arraycopy(points, 0, myPoints, 0, points.length);
        Arrays.sort(myPoints);

        for (int i = 0; i < myPoints.length - 1; i++) {
            if (myPoints[i].compareTo(myPoints[i + 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }

        // find segments
        this.segments = new ArrayList<LineSegment>();

        for (int i = 0; i < myPoints.length - 3; i++) {
            for (int j = i + 1; j < myPoints.length - 2; j++) {
                for (int k = j + 1; k < myPoints.length - 1; k++) {
                    for (int m = k + 1; m < myPoints.length; m++) {
                        double slope1 = myPoints[i].slopeTo(myPoints[j]);
                        double slope2 = myPoints[i].slopeTo(myPoints[k]);
                        double slope3 = myPoints[i].slopeTo(myPoints[m]);
                        if (Double.compare(slope1, slope2) == 0
                                && Double.compare(slope1, slope3) == 0) {
                            this.segments.add(new LineSegment(myPoints[i], myPoints[m]));
                        }
                    }
                }
            }
        }
    }

    /**
     * Number of segments
     * @return number of segments
     */
    public int numberOfSegments() {
        return this.segments.size();
    }

    /**
     * Segments
     * @return segments
     */
    public LineSegment[] segments() {
        LineSegment[] result = new LineSegment[this.segments.size()];
        this.segments.toArray(result);
        return result;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
