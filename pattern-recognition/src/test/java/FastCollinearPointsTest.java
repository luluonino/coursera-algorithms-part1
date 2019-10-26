import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FastCollinearPointsTest {

  private static final Point[] points1 = new Point[] {
    new Point(0, 0),
    new Point(1, 0),
    new Point(2, 0),
    new Point(3, 0),
    new Point(0, 1),
    new Point(0, 2),
    new Point(0, 3),
    new Point(1, 1),
    new Point(2, 2),
    new Point(3, 3),
    new Point(4, 4),
    new Point(1, -1),
    new Point(2, -2),
    new Point(3, -3),
  };

  private static final Point[] points2 = new Point[] {
    new Point(0, 0),
    new Point(1, 1),
    new Point(2, 2),
  };

  private static final Point[] points3 = new Point[] {
    new Point(0, 0),
    new Point(1, 1),
    new Point(2, 2),
    null
  };

  private static final Point[] points4 = new Point[] {
    new Point(0, 0),
    new Point(1, 1),
    new Point(2, 2),
    new Point(2, 2),
  };

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor0() {
    final FastCollinearPoints fastCollinearPoints = new FastCollinearPoints(null);
  }

  @Test
  public void testConstructor1() {
    final FastCollinearPoints fastCollinearPoints = new FastCollinearPoints(points1);
  }

  @Test
  public void testConstructor2() {
    final FastCollinearPoints fastCollinearPoints = new FastCollinearPoints(points2);
    assertEquals(0, fastCollinearPoints.numberOfSegments());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor3() {
    final FastCollinearPoints fastCollinearPoints = new FastCollinearPoints(points3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor4() {
    final FastCollinearPoints fastCollinearPoints = new FastCollinearPoints(points4);
  }

  @Test
  public void testFastForce() {
    final FastCollinearPoints fastCollinearPoints = new FastCollinearPoints(points1);
    assertEquals(4, fastCollinearPoints.numberOfSegments());
    LineSegment[] result = fastCollinearPoints.segments();
    for (LineSegment seg: result) {
      System.out.println(seg.toString());
    }
  }
}
