import static org.junit.jupiter.api.Assertions.*;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import org.junit.jupiter.api.Test;

import java.awt.*;

public class TestKdTree {
    private static final Point2D[] points = new Point2D[]{
            new Point2D(0.5, 0.4),
            new Point2D(0.1, 0.1),
            new Point2D(0.7, 0.2),
            new Point2D(0.3, 0.4),
            new Point2D(0.2, 0.2),
            new Point2D(0.9, 0.9),
            new Point2D(0.85, 0.7),
            new Point2D(0.1, 0.85),
    };

    private static final RectHV rect = new RectHV(0.2, 0.3, 0.55, 0.74);

    private static final Point2D ref = new Point2D(0.51, 0.78);

    @Test
    public void testConstructor() {
        KdTree kdTree = new KdTree();
        assertEquals(0, kdTree.size());
        assertTrue(kdTree.isEmpty());
    }

    @Test
    public void testDraw() {
        KdTree kdTree = new KdTree();
        for (Point2D p: points) {
            kdTree.insert(p);
        }

        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(-0.1, 1.1);
        StdDraw.setYscale(-0.1, 1.1);
        StdDraw.setTitle("testDraw");

        RectHV frame = new RectHV(0., 0., 1., 1.);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.setPenRadius(0.001);
        frame.draw();

        kdTree.draw();

        StdDraw.setPenColor(Color.GREEN);
        StdDraw.setPenRadius(0.005);
        rect.draw();
        StdDraw.setPenRadius(0.02);
        ref.draw();

        StdDraw.show();
        StdDraw.save("kdTree-testDraw1.jpg");

    }

    @Test
    public void testDraw1() {
        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        StdDraw.enableDoubleBuffering();
        KdTree kdtree = new KdTree();
        while (true) {
            if (StdDraw.isMousePressed()) {
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                StdOut.printf("%8.6f %8.6f\n", x, y);
                Point2D p = new Point2D(x, y);
                if (rect.contains(p) && !kdtree.contains(p)) {
                    StdOut.printf("%8.6f %8.6f\n", x, y);
                    kdtree.insert(p);
                    StdDraw.clear();
                    kdtree.draw();
                    StdDraw.show();
                }
            }
            StdDraw.pause(20);
        }
    }

    @Test
    public void testRange() {
        KdTree kdTree = new KdTree();
        for (Point2D point: points) {
            kdTree.insert(point);
        }

        Iterable<Point2D> pointsIn = kdTree.range(rect);
        pointsIn.forEach(point -> {System.out.println(point.toString());});
    }

    @Test
    public void testRange1() {
        // initialize the data structures from file
        KdTree kdTree = new KdTree();
        for (Point2D point: points) {
            kdTree.insert(point);
        }

        double x0 = 0.0, y0 = 0.0;      // initial endpoint of rectangle
        double x1 = 0.0, y1 = 0.0;      // current location of mouse
        boolean isDragging = false;     // is the user dragging a rectangle

        // draw the points
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        kdTree.draw();
        StdDraw.show();

        // process range search queries
        StdDraw.enableDoubleBuffering();
        while (true) {

            // user starts to drag a rectangle
            if (StdDraw.isMousePressed() && !isDragging) {
                x0 = x1 = StdDraw.mouseX();
                y0 = y1 = StdDraw.mouseY();
                isDragging = true;
            }

            // user is dragging a rectangle
            else if (StdDraw.isMousePressed() && isDragging) {
                x1 = StdDraw.mouseX();
                y1 = StdDraw.mouseY();
            }

            // user stops dragging rectangle
            else if (!StdDraw.isMousePressed() && isDragging) {
                isDragging = false;
            }

            // draw the points
            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            kdTree.draw();

            // draw the rectangle
            RectHV rect = new RectHV(Math.min(x0, x1), Math.min(y0, y1),
                    Math.max(x0, x1), Math.max(y0, y1));
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius();
            rect.draw();

            // draw the range search results for kd-tree in blue
            StdDraw.setPenRadius(0.02);
            StdDraw.setPenColor(StdDraw.GREEN);
            for (Point2D p : kdTree.range(rect))
                p.draw();

            StdDraw.show();
            StdDraw.pause(20);
        }
    }

    @Test
    public void testNearest() {
        KdTree kdTree = new KdTree();
        for (Point2D point: points) {
            kdTree.insert(point);
        }
        Point2D nearest = kdTree.nearest(ref);
        assertEquals(new Point2D(0.85, 0.7), nearest);
    }

    @Test
    public void testNearest1() {
        // initialize the two data structures with point from file
        KdTree kdTree = new KdTree();
        for (Point2D point: points) {
            kdTree.insert(point);
        }

        StdDraw.clear();
        kdTree.draw();
        StdDraw.show();

        // process nearest neighbor queries
        StdDraw.enableDoubleBuffering();
        while (true) {

            // the location (x, y) of the mouse
            double x = StdDraw.mouseX();
            double y = StdDraw.mouseY();
            Point2D query = new Point2D(x, y);

            // draw all of the points
            StdDraw.clear();
            kdTree.draw();

            // draw in red the nearest neighbor (using brute-force algorithm)
            StdDraw.setPenRadius(0.03);
            StdDraw.setPenColor(StdDraw.GREEN);
            kdTree.nearest(query).draw();

            StdDraw.show();
            StdDraw.pause(40);
        }
    }
}
