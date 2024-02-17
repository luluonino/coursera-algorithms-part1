import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class KdTree {

    private Node root = null;
    private int size = 0;

    public KdTree() { // construct an empty KdTree of points
    }

    public boolean isEmpty() { // is the tree empty?
        return root == null;
    }

    public int size() { // number of points in the tree
        return this.size;
    }

    public void insert(final Point2D p) {
        // add the point to the tree (if it is not already in the set)
        if (p == null) throw new IllegalArgumentException();
        if (root == null) root = new Node(p, true, new RectHV(0., 0., 1., 1.));
        else if (this.contains(p)) return;
        else root = insert(root, p);
        this.size++;
    }

    public void draw() { // draw all points to standard draw
        draw(root);
    }

    public boolean contains(final Point2D p) { // does the set contain point p?
        if (p == null) throw new IllegalArgumentException();
        return search(root, p) != null;
    }

    public Iterable<Point2D> range(final RectHV rect) {
        // all points that are inside the rectangle (or on the boundary)
        if (rect == null) throw new IllegalArgumentException();
        ArrayList<Point2D> resultList  = new ArrayList<>();
        range(root, rect, resultList);
        return resultList;
    }

    public Point2D nearest(final Point2D p) {
        // a nearest neighbor in the set to point p; null if the set is empty
        if (p == null) throw new IllegalArgumentException();
        if (root == null) return null;
        return nearest(root, p, 2.).point(); // largest dist^2 <= 2.
    }

    private Node insert(final Node node, final Point2D p) {
        // insert point to the tree rooted at node
        if (node == null || p == null) throw new IllegalArgumentException();
        // move to the left subtree
        if (node.axis() && p.x() < node.point().x()
                || !node.axis() && p.y() < node.point().y()) {
            if (node.left == null) {
                RectHV newRect = node.axis()
                    ? new RectHV(
                        node.rect().xmin(),
                        node.rect().ymin(),
                        node.point().x(),
                        node.rect().ymax()
                    )
                    : new RectHV(
                        node.rect().xmin(),
                        node.rect().ymin(),
                        node.rect().xmax(),
                        node.point().y()
                    );
                node.left = new Node(p, !node.axis(), newRect);
            } else {
                node.left = insert(node.left, p);
            }
        } else { // move to the right subtree
            if (node.right == null) {
                RectHV newRect = node.axis()
                    ? new RectHV(
                        node.point().x(),
                        node.rect().ymin(),
                        node.rect().xmax(),
                        node.rect().ymax()
                    ) : new RectHV(
                        node.rect().xmin(),
                        node.point().y(),
                        node.rect().xmax(),
                        node.rect().ymax()
                    );
                node.right = new Node(p, !node.axis(), newRect);
            } else {
                node.right = insert(node.right, p);
            }
        }
        return node;
    }

    private void draw(final Node node) {
        if (node == null) return;
        else {
            node.draw();
            draw(node.left);
            draw(node.right);
        }
    }

    private Node search(final Node node, final Point2D p) {
        if (node == null) return null;
        if (node.point().equals(p)) return node;
        if (node.axis() && p.x() < node.point().x()
                || !node.axis() && p.y() < node.point().y()) {
            return search(node.left, p);
        } else {
            return search(node.right, p);
        }
    }

    private void range(
        final Node node,
        final RectHV rect,
        final List<Point2D> resultList
    ) {
        if (rect == null || resultList == null)
            throw new IllegalArgumentException();
        if (node == null) return;
        if (!node.rect().intersects(rect)) return;
        if (rect.contains(node.point())) resultList.add(node.point());
        if (node.left != null) range(node.left, rect, resultList);
        if (node.right != null) range(node.right, rect, resultList);
    }

    private Node nearest(final Node node, final Point2D p, double distPar) {
        double dist = node.point().distanceSquaredTo(p);
        if (dist < distPar) distPar = dist; // smallest dist so far
        double distLeft = dist;
        double distRight = dist;
        Node nodeLeft = null;
        Node nodeRight = null;
        if (node.left != null
                && node.left.rect().distanceSquaredTo(p) < distPar
                && (node.left.rect().contains(p)
                || node.right == null
                || node.left.rect().distanceSquaredTo(p)
                    <= node.right.rect().distanceSquaredTo(p)
                )
        ) {
            nodeLeft = nearest(node.left, p, distPar);
            distLeft = nodeLeft.point().distanceSquaredTo(p);
            if (node.right != null
                    && node.right.rect().distanceSquaredTo(p) < distPar
                    && node.right.rect().distanceSquaredTo(p) < distLeft
            ) {
                nodeRight = nearest(node.right, p, Math.min(distPar, distLeft));
                distRight = nodeRight.point().distanceSquaredTo(p);
            }
        } else if (node.right != null
                && node.right.rect().distanceSquaredTo(p) < distPar
                && (node.right.rect().contains(p)
                || node.left == null
                || node.right.rect().distanceSquaredTo(p)
                    < node.left.rect().distanceSquaredTo(p)
                )
        ) {
            nodeRight = nearest(node.right, p, distPar);
            distRight = nodeRight.point().distanceSquaredTo(p);
            if (node.left != null
                    && node.left.rect().distanceSquaredTo(p) < distPar
                    && node.left.rect().distanceSquaredTo(p) < distRight) {
                nodeLeft = nearest(node.left, p, Math.min(distPar, distRight));
                distLeft = nodeLeft.point().distanceSquaredTo(p);
            }
        }
        return (dist <= Math.min(distLeft, distRight))
                ? node : (distLeft < distRight ? nodeLeft : nodeRight);
    }

    private class Node {
        public Node left = null;
        public Node right = null;
        private final Point2D point;
        private final RectHV rect;
        private final boolean axis; // true for x-axis and false for y-axis

        public Node(
            final Point2D point,
            final boolean axis,
            final RectHV rect
        ) {
            this.point = point;
            this.axis = axis;
            this.rect = rect;
        }

        public Point2D point() {
            return this.point;
        }

        public RectHV rect() {
            return this.rect;
        }

        public boolean axis() {
            return this.axis;
        }

        public void draw() {
            StdDraw.setPenColor(this.axis() ? Color.RED : Color.BLUE);
            StdDraw.setPenRadius(0.005);
            if (this.axis()) {
                this.point().drawTo(
                    new Point2D(this.point().x(), this.rect().ymin())
                );
                this.point().drawTo(
                    new Point2D(this.point().x(), this.rect().ymax())
                );
            } else {
                this.point().drawTo(
                   new Point2D(this.rect().xmin(), this.point().y())
                );
                this.point().drawTo(
                   new Point2D(this.rect().xmax(), this.point().y())
                );
            }
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.setPenRadius(0.02);
            this.point().draw();
        }
    }
}
