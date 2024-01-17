import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {

  private final List<LineSegment> segments;

  // finds all line segments containing 4 points
  public FastCollinearPoints(Point[] points) {
    // check arguments
    if (points == null) {
      throw new IllegalArgumentException();
    }
    for (Point point: points) {
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
    this.segments = new ArrayList<>();

    final Point[] myCopy = new Point[myPoints.length];
    System.arraycopy(myPoints, 0, myCopy, 0, myPoints.length);


    for (int i = 0; i < myPoints.length; i++) {

      final Comparator<Point> bySlope = myPoints[i].slopeOrder();
      Arrays.sort(myCopy, bySlope);

      final List<Point> collinearPoints = new ArrayList<>();
      for (int j = 1; j < myCopy.length - 1;) {
        if (collinearPoints.isEmpty()) {
          collinearPoints.add(myCopy[j]);
        }
        double slopej = myPoints[i].slopeTo(myCopy[j]);
        double slopejp1 = myPoints[i].slopeTo(myCopy[j + 1]);
        if (Double.compare(slopej, slopejp1) != 0) {
          if (collinearPoints.size() >= 3) {
            collinearPoints.add(myPoints[i]);
            final Point[] copy = new Point[collinearPoints.size()];
            collinearPoints.toArray(copy);
            Arrays.sort(copy);
            if (copy[0].compareTo(myPoints[i]) == 0) {
              this.segments.add(
                      new LineSegment(copy[0], copy[copy.length - 1])
              );
            }
          }
          collinearPoints.clear();
        }
        collinearPoints.add(myCopy[j + 1]);
        j++;
      }

      if (collinearPoints.size() >= 3) {
        collinearPoints.add(myPoints[i]);
        final Point[] copy = new Point[collinearPoints.size()];
        collinearPoints.toArray(copy);
        Arrays.sort(copy);
        if (copy[0].compareTo(myPoints[i]) == 0) {
          this.segments.add(new LineSegment(copy[0], copy[copy.length - 1]));
        }
      }

    }
  }

  public int numberOfSegments() {       // the number of line segments
    return this.segments.size();
  }

  public LineSegment[] segments() {               // the line segments
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
    FastCollinearPoints collinear = new FastCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
      StdOut.println(segment);
      segment.draw();
    }
    StdDraw.show();
  }

}
