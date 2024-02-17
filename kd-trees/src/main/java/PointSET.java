import java.util.TreeSet;
import java.util.stream.Collectors;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class PointSET {
    private TreeSet<Point2D> pointSet;

    public PointSET() { // construct an empty set of points
        this.pointSet = new TreeSet<>();
    }

    public boolean isEmpty() { // is the set empty?
        return this.pointSet.isEmpty();
    }

    public int size() { // number of points in the set
        return this.pointSet.size();
    }

    public void insert(Point2D p) { // add the point to the set (if it is not already in the set)
        if (p == null) throw new IllegalArgumentException();
        this.pointSet.add(p);
    }

    public boolean contains(Point2D p) { // does the set contain point p?
        if (p == null) throw new IllegalArgumentException();
        for (Point2D point: this.pointSet) {
            if (point.equals(p)) return true;
        }
        return false;
    }

    public void draw() { // draw all points to standard draw
        this.pointSet.forEach(Point2D::draw);
    }

    public Iterable<Point2D> range(RectHV rect) { // all points that are inside the rectangle (or on the boundary)
        if (rect == null) throw new IllegalArgumentException();
        return this.pointSet.stream().filter(rect::contains).collect(Collectors.toSet());
    }

    public Point2D nearest(Point2D p) { // a nearest neighbor in the set to point p; null if the set is empty
        if (p == null) throw new IllegalArgumentException();

        Point2D nearestPoint = null;
        double smallestDist = 2.;
        for (Point2D point: this.pointSet) {
            double dist = point.distanceTo(p);
            if (nearestPoint == null || smallestDist > dist) {
                nearestPoint = point;
                smallestDist = dist;
            }
        }
        return nearestPoint;
    }

    public static void main(String[] args) { // unit testing of the methods (optional)
    }

}
