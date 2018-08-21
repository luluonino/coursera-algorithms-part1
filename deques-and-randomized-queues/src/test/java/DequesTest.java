import static org.junit.Assert.*;
import java.util.Iterator;
import org.junit.Test;

public class DequesTest {
  private Deque<String> deque;

  public DequesTest() {
    this.deque = new Deque<>();
  }

  @Test
  public void testConstructor () {
    assertEquals(true, deque.isEmpty());
  }

  @Test
  public void testAll() {
    deque.addFirst("abc");
    assertEquals(false, deque.isEmpty());
    deque.addFirst("def");
    deque.addFirst("ghi");
    assertEquals(3, deque.size());
    assertEquals("ghidefabc", getAllStrings(deque));
    assertEquals("ghi", deque.removeFirst());
    assertEquals("defabc", getAllStrings(deque));
    assertEquals("abc", deque.removeLast());
    assertEquals("def", getAllStrings(deque));
    assertEquals("def", deque.removeLast());
    assertEquals(true, deque.isEmpty());
    assertEquals(0, deque.size());
  }

  private String getAllStrings(Deque deque) {
    String result = "";
    Iterator<String> iterator = deque.iterator();
    while (iterator.hasNext()) {
      result += iterator.next();
    }
    return result;
  }
}
