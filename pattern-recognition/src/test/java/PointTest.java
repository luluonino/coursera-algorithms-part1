import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;

public class PointTest {
  private final Point point;

  public PointTest() {
    this.point = new Point(0, 0);
  }

  @Test
  public void testSlope() {
    assertEquals(this.point.slopeTo(new Point(1, 1)), 1, 0);
    assertEquals(this.point.slopeTo(new Point(-1, -1)), 1, 0);
    assertEquals(this.point.slopeTo(new Point(-1, 1)), -1, 0);
    assertEquals(this.point.slopeTo(new Point(1, -1)), -1, 0);
    assertEquals(this.point.slopeTo(new Point(1, 2)), 2, 0);
    assertEquals(this.point.slopeTo(new Point(2, 1)), 0.5, 0);
    assertEquals(this.point.slopeTo(new Point(1, 32767)), 32767, 0);
    assertEquals(this.point.slopeTo(new Point(0, 32767)), Double.POSITIVE_INFINITY, 0);
    assertEquals(this.point.slopeTo(new Point(0, 0)), Double.NEGATIVE_INFINITY, 0);
    assertEquals(this.point.slopeTo(new Point(5, 0)), 0.0, 0);
  }

  @Test
  public void testCompare() {
    assertEquals(this.point.compareTo(new Point(0, 0)), 0);
    assertEquals(this.point.compareTo(new Point(0, 1)), -1);
    assertEquals(this.point.compareTo(new Point(0, -1)), 1);
    assertEquals(this.point.compareTo(new Point(1, 0)), -1);
    assertEquals(this.point.compareTo(new Point(-1, 0)), 1);
  }

  @Test
  public void testComparator() {
    final Comparator<Point> bySlope = this.point.slopeOrder();
    final Point[] a = new Point[] {
      new Point(1, 0),
      new Point(2, 0),
      new Point(3, 0),
      new Point(3, 1),
      new Point(2, 1),
      new Point(1, 1),
      new Point(0, 1)
    };
    final Point[] b = new Point[] {
      new Point(0, 1),
      new Point(1, 1),
      new Point(2, 1),
      new Point(3, 1),
      new Point(1, 0),
      new Point(2, 0),
      new Point(3, 0)
    };
    Arrays.sort(b, bySlope);
    for (int i=0; i<a.length; i++) {
      assertEquals(a[i].compareTo(b[i]), 0);
    }
  }

  @Test
  public void testComparable() {
    final Point[] a = new Point[] {
      new Point(0, 0),
      new Point(1, 0),
      new Point(2, 0),
      new Point(3, 0),
      new Point(0, 1),
      new Point(1, 1),
      new Point(2, 1),
      new Point(3, 1)
    };
    final Point[] b = new Point[] {
      new Point(3, 1),
      new Point(0, 1),
      new Point(2, 1),
      new Point(1, 1),
      new Point(2, 0),
      new Point(1, 0),
      new Point(0, 0),
      new Point(3, 0)
    };
    Arrays.sort(b);
    for (int i=0; i<a.length; i++) {
      System.out.println(b[i]);
      assertEquals(a[i].compareTo(b[i]), 0);
    }
  }
}
