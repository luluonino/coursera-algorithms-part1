import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Iterator;

public class DequesTest {
  private Deque<String> deque;

  public DequesTest() {
    this.deque = new Deque<>();
  }

  @Test
  public void testConstructor () {
    assertTrue(deque.isEmpty());
  }

  @Test
  public void testAll() {
    deque.addFirst("abc");
    assertFalse(deque.isEmpty());
    deque.addFirst("def");
    deque.addFirst("ghi");
    assertEquals(3, deque.size());
    assertEquals("ghidefabc", getAllStrings(deque));
    assertEquals("ghi", deque.removeFirst());
    assertEquals("defabc", getAllStrings(deque));
    assertEquals("abc", deque.removeLast());
    assertEquals("def", getAllStrings(deque));
    assertEquals("def", deque.removeLast());
    assertTrue(deque.isEmpty());
    assertEquals(0, deque.size());
  }

  private String getAllStrings(Deque<String> deque) {
    StringBuilder result = new StringBuilder();
    for (String value: deque) {
      result.append(value);
    }
    return result.toString();
  }
}
