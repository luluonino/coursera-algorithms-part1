import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class KdTree {

    private Node root = null;
    private int size = 0;

    public void KdTree() {} // construct an empty set of points

    public boolean isEmpty() { // is the set empty?
        return root == null;
    }

    public int size() { // number of points in the set
        return this.size;
    }

    public void insert(Point2D p) { // add the point to the set (if it is not already in the set)
        if (p == null) throw new IllegalArgumentException();
        if (root == null) root = new Node(p, true, new RectHV(0., 0., 1., 1.));
        else root = insert(root, p);
        this.size++;
    }

    private Node insert(Node node, Point2D p) { // insert point to the tree rooted at node
        if (node == null || p == null) throw new IllegalArgumentException();
        if (node.axis() && p.x() < node.point().x() || !node.axis() && p.y() < node.point().y()) { // move to the left subtree
            if (node.left == null) {
                RectHV newRect = node.axis()
                        ? new RectHV(node.rect().xmin(), node.rect().ymin(), node.point().x(), node.rect().ymax())
                        : new RectHV(node.rect().xmin(), node.rect().ymin(), node.rect().xmax(), node.point().y());
                node.left = new Node(p, !node.axis(), newRect);
            } else {
                node.left = insert(node.left, p);
            }
        } else { // move to the right subtree
            if (node.right == null) {
                RectHV newRect = node.axis()
                        ? new RectHV(node.point().x(), node.rect().ymin(), node.rect().xmax(), node.rect().ymax())
                        : new RectHV(node.rect().xmin(), node.point().y(), node.rect().xmax(), node.rect().ymax());
                node.right = new Node(p, !node.axis(), newRect);
            } else {
                node.right = insert(node.right, p);
            }
        }
        return node;
    }

    public void draw() { // draw all points to standard draw
        draw(root);
    }

    private void draw(Node node) {
        if (node == null) return;
        else {
            node.draw();
            draw(node.left);
            draw(node.right);
        }
    }

    public boolean contains(Point2D p) { // does the set contain point p?
        if (p == null) throw new IllegalArgumentException();
        return search(root, p) != null;
    }

    private Node search(Node node, Point2D p) {
        if (node == null) return null;
        if (node.point().equals(p)) return node;
        if (node.axis() && p.x() < node.point().x() || !node.axis() && p.y() < node.point().y()) {
            return search(node.left, p);
        } else {
            return search(node.right, p);
        }
    }

    public Iterable<Point2D> range(RectHV rect) { // all points that are inside the rectangle (or on the boundary)
        if (rect == null) throw new IllegalArgumentException();
        TreeSet<Point2D> resultSet = new TreeSet<>();
        range(root, rect, resultSet);
        return resultSet;
    }

    private void range(Node node, RectHV rect, TreeSet<Point2D> resultSet) {
        if (rect == null || resultSet == null) throw new IllegalArgumentException();
        if (node == null) return;
        if (!node.rect().intersects(rect)) return;
        if (rect.contains(node.point())) resultSet.add(node.point());
        if (node.left != null) range(node.left, rect, resultSet);
        if (node.right != null) range(node.right, rect, resultSet);
    }

    public Point2D nearest(Point2D p) { // a nearest neighbor in the set to point p; null if the set is empty
        if (p == null) throw new IllegalArgumentException();
        return nearest(root, p).point();
    }

    private Node nearest(Node node, Point2D p) {
        double dist = node.point().distanceTo(p);
        double distLeft = dist;
        double distRight = dist;
        Node nodeLeft = null;
        Node nodeRight = null;
        if (node.left != null && node.left.rect().contains(p)) {
            nodeLeft = nearest(node.left, p);
            distLeft = nodeLeft.point().distanceTo(p);
            if (node.right != null && distLeft > node.right.rect().distanceTo(p)) {
                nodeRight = nearest(node.right, p);
            }
        } else if (node.right != null && node.right.rect().contains(p)) {
            nodeRight = nearest(node.right, p);
            distRight = nodeRight.point().distanceTo(p);
            if (node.left != null && distRight > node.left.rect().distanceTo(p)) {
                nodeLeft = nearest(node.left, p);
            }
        } else if (node.left != null) {
            nodeLeft = nearest(node.left, p);
        } else if (node.right != null) {
            nodeRight = nearest(node.right, p);
        }
        if (nodeLeft != null) distLeft = nodeLeft.point().distanceTo(p);
        if (nodeRight != null) distRight = nodeRight.point().distanceTo(p);
        return (dist <= Math.min(distLeft, distRight)) ? node : (distLeft < distRight ? nodeLeft : nodeRight);
    }

    private class Node {
        private final Point2D point;
        private final RectHV rect;
        private final boolean axis; // true for x-axis and false for y-axis
        public Node left = null;
        public Node right = null;

        public Node(Point2D point, boolean axis, RectHV rect) {
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
                this.point().drawTo(new Point2D(this.point().x(), this.rect().ymin()));
                this.point().drawTo(new Point2D(this.point().x(), this.rect().ymax()));
            } else {
                this.point().drawTo(new Point2D(this.rect().xmin(), this.point().y()));
                this.point().drawTo(new Point2D(this.rect().xmax(), this.point().y()));
            }
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.setPenRadius(0.02);
            this.point().draw();
        }
    }
}
