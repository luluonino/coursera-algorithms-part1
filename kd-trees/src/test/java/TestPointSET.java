import static org.junit.jupiter.api.Assertions.*;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import org.junit.jupiter.api.Test;

import java.awt.*;

public class TestPointSET {
    private static final Point2D[] points = new Point2D[]{
        new Point2D(0., 0.),
        new Point2D(0.1, 0.1),
        new Point2D(0.2, 0.2),
        new Point2D(0.3, 0.4),
        new Point2D(0.5, 0.4),
        new Point2D(0.5, 0.7),
        new Point2D(0.7, 0.2),
        new Point2D(0.9, 0.9),
        new Point2D(0.85, 0.7),
        new Point2D(0.1, 0.85),
    };

    private static final RectHV rect = new RectHV(0.2, 0.3, 0.55, 0.74);

    private static final Point2D ref = new Point2D(0.51, 0.78);

    @Test
    public void testConstructor() {
        PointSET pointSET = new PointSET();
        assertEquals(0, pointSET.size());
        assertTrue(pointSET.isEmpty());
    }

    @Test
    public void testInsert() {
        PointSET pointSET = new PointSET();
        for (Point2D point: points) {
            pointSET.insert(point);
        }
        assertEquals(10, pointSET.size());
        assertFalse(pointSET.isEmpty());
        for (Point2D point: points) {
            pointSET.insert(point);
        }
        assertEquals(10, pointSET.size());
    }

    @Test
    public void testDraw() {
        PointSET pointSET = new PointSET();
        for (Point2D point: points) {
            pointSET.insert(point);
        }
        StdDraw.setCanvasSize(300, 300);
        StdDraw.setXscale(-0.1, 1.1);
        StdDraw.setYscale(-0.1, 1.1);
        StdDraw.setTitle("testDraw");

        RectHV frame = new RectHV(0., 0., 1., 1.);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.setPenRadius(0.001);
        frame.draw();

        StdDraw.setPenColor(Color.GREEN);
        rect.draw();
        StdDraw.setPenRadius(0.005);
        ref.draw();

        StdDraw.setPenColor(Color.RED);
        StdDraw.setPenRadius(0.005);
        pointSET.draw();


        StdDraw.show();
        StdDraw.save("testDraw.jpg");
    }

    @Test
    public void testContains() {
        PointSET pointSET = new PointSET();
        Point2D point = new Point2D(0.5, 0.4);
        assertThrowsExactly(IllegalArgumentException.class, () -> pointSET.contains(null));
        assertFalse(pointSET.contains(point));
        pointSET.insert(point);
        assertTrue(pointSET.contains(point));
    }

    @Test
    public void testRange() {
        PointSET pointSET = new PointSET();
        for (Point2D point: points) {
            pointSET.insert(point);
        }

        Iterable<Point2D> pointsIn = pointSET.range(rect);
        pointsIn.forEach(point -> {System.out.println(point.toString());});
    }

    @Test
    public void testNearest() {
        PointSET pointSET = new PointSET();
        for (Point2D point: points) {
            pointSET.insert(point);
        }
        Point2D nearest = pointSET.nearest(ref);
        assertTrue(nearest.equals(new Point2D(0.5, 0.7)));
    }
}
