import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class KdTree {

    private Node root = null;
    private int size = 0;

    /**
     * Construct an empty KdTree of points.
     */
    public KdTree() {
    }

    /**
     * Is the tree empty?
     * @return true if the tree is empty, false otherwise.
     */
    public boolean isEmpty() { // is the tree empty?
        return root == null;
    }

    /**
     * Number of points in the tree
     * @return number of points in the tree
     */
    public int size() {
        return this.size;
    }

    /**
     * Insert point if not already in tree
     * @param p point to insert
     */
    public void insert(final Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (root == null) root = new Node(p, true, new RectHV(0., 0., 1., 1.));
        else if (this.contains(p)) return;
        else root = insert(root, p);
        this.size++;
    }

    /**
     * Draw all points to standard draw
     */
    public void draw() {
        draw(root);
    }

    /**
     * Does the tree contain point p?
     * @param p point to check
     * @return true if the tree contains point p, false otherwise
     */
    public boolean contains(final Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return search(root, p) != null;
    }

    /**
     * All points that are inside the rectangle (or on the boundary)
     * @param rect rectangle to check
     * @return all points that are inside the rectangle (or on the boundary)
     */
    public Iterable<Point2D> range(final RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        ArrayList<Point2D> resultList  = new ArrayList<>();
        range(root, rect, resultList);
        return resultList;
    }

    /**
     * A nearest neighbor in the tree to point p; null if the tree is empty
     * @param p point to check
     * @return a nearest neighbor in the tree to point p; null if the tree is empty
     */
    public Point2D nearest(final Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (root == null) return null;
        return nearest(root, p, 2.).point(); // largest dist^2 <= 2.
    }

    /**
     * Insert point to the tree rooted at node
     * @param node root of the tree
     * @param p point to insert
     * @return root of the tree
     */
    private Node insert(final Node node, final Point2D p) {
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

    /**
     * Draw the tree rooted at node
     * @param node root of the tree
     */
    private void draw(final Node node) {
        if (node == null) return;
        else {
            node.draw();
            draw(node.left);
            draw(node.right);
        }
    }

    /**
     * Search for point p in the tree rooted at node
     * @param node root of the tree
     * @param p point to search
     * @return node containing point p
     */
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

    /**
     * Range search for points in the rectangle rooted at node
     * @param node root of the tree
     * @param rect rectangle to search
     * @param resultList list of points in the rectangle
     */
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

    /**
     * Find the nearest point to p in the tree rooted at node
     * @param node root of the tree
     * @param p point to search
     * @param distPar smallest distance so far
     * @return node containing the nearest point to p
     */
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

    /**
     * Node class for KdTree
     */
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

        /**
         * Point of the node
         * @return point of the node
         */
        public Point2D point() {
            return this.point;
        }

        /**
         * Rectangle of the node
         * @return rectangle of the node
         */
        public RectHV rect() {
            return this.rect;
        }

        /**
         * Axis of the node
         * @return axis of the node
         */
        public boolean axis() {
            return this.axis;
        }

        /**
         * Draw the node
         */
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
