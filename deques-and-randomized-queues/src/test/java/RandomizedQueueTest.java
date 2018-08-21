import static org.junit.Assert.*;
import java.util.Iterator;
import org.junit.Test;

public class RandomizedQueueTest {
  private RandomizedQueue<String> queue;

  public RandomizedQueueTest() {
    queue = new RandomizedQueue<>();
  }

  @Test
  public void testConstructor() {
    assertEquals(true, queue.isEmpty());
    assertEquals(0, queue.size());
  }

  @Test
  public void testAll() {
    queue.enqueue("abc");
    assertEquals(false, queue.isEmpty());
    assertEquals(1, queue.size());
    assertArrayEquals(new String[] {"abc"}, getAllStrings(queue));
    assertEquals("abc", queue.dequeue());
    assertEquals(true, queue.isEmpty());
    assertEquals(0, queue.size());

    queue.enqueue("abc");
    assertEquals(1, queue.size());
    assertEquals("abc", queue.sample());
    queue.enqueue("def");
    assertEquals(2, queue.size());
  }

  private String[] getAllStrings(RandomizedQueue queue) {
    Iterator<String> iterator = queue.iterator();
    String[] result = new String[queue.size()];
    int index = 0;
    while(iterator.hasNext()) {
      result[index++] = iterator.next();
    }
    return result;
  }
}
