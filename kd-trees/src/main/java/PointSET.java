import java.util.TreeSet;
import java.util.stream.Collectors;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class PointSET {
    private TreeSet<Point2D> pointSet;

    /**
     * Construct an empty set of points.
     */
    public PointSET() {
        this.pointSet = new TreeSet<>();
    }

    /**
     * Is the set empty?
     * @return true if the set is empty, false otherwise.
     */
    public boolean isEmpty() {
        return this.pointSet.isEmpty();
    }

    /**
     * Number of points in the set.
     * @return the number of points in the set.
     */
    public int size() {
        return this.pointSet.size();
    }

    /**
     * Add the point to the set (if it is not already in the set).
     * @param p the point to be added.
     */
    public void insert(final Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        this.pointSet.add(p);
    }

    /**
     * Does the set contain point p?
     * @param p the point to be checked.
     * @return true if the set contains point p, false otherwise.
     */
    public boolean contains(final Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return this.pointSet.contains(p);
    }

    /**
     * Draw all points to standard draw.
     */
    public void draw() {
        this.pointSet.forEach(Point2D::draw);
    }

    /**
     * All points that are inside the rectangle (or on the boundary).
     * @param rect the rectangle to be checked.
     * @return all points that are inside the rectangle (or on the boundary).
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        return this.pointSet.stream().filter(rect::contains)
                .collect(Collectors.toList());
    }

    /**
     * A nearest neighbor in the set to point p; null if the set is empty.
     * @param p the point to be checked.
     * @return a nearest neighbor in the set to point p; null if the set is empty.
     */
    public Point2D nearest(final Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        Point2D nearestPoint = null;
        double smallestDist = 2.;
        for (Point2D point: this.pointSet) {
            double dist = point.distanceSquaredTo(p);
            if (nearestPoint == null || smallestDist > dist) {
                nearestPoint = point;
                smallestDist = dist;
            }
        }
        return nearestPoint;
    }

    public static void main(String[] args) {
        // unit testing of the methods (optional)
    }
}
