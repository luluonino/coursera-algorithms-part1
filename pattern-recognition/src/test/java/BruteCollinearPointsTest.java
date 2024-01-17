import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class BruteCollinearPointsTest {

  private static final Point[] points1 = new Point[]{
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
          new Point(1, -1),
          new Point(2, -2),
          new Point(3, -3),
  };

  private static final Point[] points2 = new Point[]{
          new Point(0, 0),
          new Point(1, 1),
          new Point(2, 2),
  };

  private static final Point[] points3 = new Point[]{
          new Point(0, 0),
          new Point(1, 1),
          new Point(2, 2),
          null
  };

  private static final Point[] points4 = new Point[]{
          new Point(0, 0),
          new Point(1, 1),
          new Point(2, 2),
          new Point(2, 2),
  };

  @Test
  public void testConstructor0() {
    assertThrows(
            IllegalArgumentException.class, () -> {
              final BruteCollinearPoints bruteCollinearPoints
                      = new BruteCollinearPoints(null);
            }
    );
  }

  @Test
  public void testConstructor1() {
    final BruteCollinearPoints bruteCollinearPoints
            = new BruteCollinearPoints(points1);
  }

  @Test
  public void testConstructor2() {
    final BruteCollinearPoints bruteCollinearPoints
            = new BruteCollinearPoints(points2);
    assertEquals(0, bruteCollinearPoints.numberOfSegments());
  }

  @Test
  public void testConstructor3() {
    assertThrows(
            IllegalArgumentException.class, () -> {
              final BruteCollinearPoints bruteCollinearPoints
                      = new BruteCollinearPoints(points3);
            }
    );
  }

  @Test
  public void testConstructor4() {
    assertThrows(
            IllegalArgumentException.class, () -> {
              final BruteCollinearPoints bruteCollinearPoints
                      = new BruteCollinearPoints(points4);
            }
    );
  }

  @Test
  public void testBruteForce() {
    final BruteCollinearPoints bruteCollinearPoints = new BruteCollinearPoints(points1);
    assertEquals(4, bruteCollinearPoints.numberOfSegments());
    LineSegment[] result = bruteCollinearPoints.segments();
    for (LineSegment seg: result) {
      System.out.println(seg.toString());
    }
  }
}
