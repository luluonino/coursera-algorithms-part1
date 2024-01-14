import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class RandomizedQueueTest {
  private RandomizedQueue<String> queue;

  public RandomizedQueueTest() {
    queue = new RandomizedQueue<>();
  }

  @Test
  public void testConstructor() {
    assertTrue(queue.isEmpty());
    assertEquals(0, queue.size());
  }

  @Test
  public void testAll() {
    queue.enqueue("abc");
    assertFalse(queue.isEmpty());
    assertEquals(1, queue.size());
    assertArrayEquals(new String[] {"abc"}, getAllStrings(queue));
    assertEquals("abc", queue.dequeue());
    assertTrue(queue.isEmpty());
    assertEquals(0, queue.size());

    queue.enqueue("abc");
    assertEquals(1, queue.size());
    assertEquals("abc", queue.sample());
    queue.enqueue("def");
    assertEquals(2, queue.size());
  }

  private String[] getAllStrings(RandomizedQueue<String> queue) {
    String[] result = new String[queue.size()];
    int index = 0;
    for (String value: queue) {
      result[index++] = value;
    }
    return result;
  }
}
