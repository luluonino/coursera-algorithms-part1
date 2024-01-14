import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

  private Item[] s;
  private int size;

  public RandomizedQueue() { // construct an empty randomized queue
    s = (Item[]) new Object[1];
    size = 0;
  }

  public boolean isEmpty() { // is the randomized queue empty?
    return size == 0;
  }

  public int size() { // return the number of items on the randomized queue
    return size;
  }

  public void enqueue(Item item) { // add the item
    if (item == null) {
      throw new IllegalArgumentException();
    }

    if (s.length == size) {
      resize(s.length * 2);
    }
    s[size++] = item;
  }

  public Item dequeue() { // remove and return a random item
    if (isEmpty()) {
      throw new NoSuchElementException();
    }

    int randomIndex = StdRandom.uniformInt(size);
    Item item = s[randomIndex];
    s[randomIndex] = s[size - 1];
    size--;
    if (size > 0 && size == s.length / 4) {
      resize(s.length / 2);
    }

    return item;
  }

  public Item sample() { // return a random item (but do not remove it)
    if (isEmpty()) {
      throw new NoSuchElementException();
    }

    int randomIndex = StdRandom.uniformInt(size);
    return s[randomIndex];
  }

  public Iterator<Item> iterator() {
    // return an independent iterator over items in random order
    return new RandomizedQueueIterator();
  }

  private class RandomizedQueueIterator implements Iterator<Item> {
    final int[] indices = new int[size];
    int current = -1;

    public RandomizedQueueIterator() {
      for (int i = 0; i < size; i++) {
        indices[i] = i;
      }
      StdRandom.shuffle(indices);
      current = 0;
    }

    public boolean hasNext() {
      return current < size;
    }

    public Item next() {
      if (current == size) {
        throw new NoSuchElementException();
      }
      return s[indices[current++]];
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

  private void resize(int newCapacity) {
    Item[] copy = (Item[]) new Object[newCapacity];
    for (int i = 0; i < size; i++) {
      copy[i] = s[i];
    }
    s = copy;
  }

  // unit testing (required)
  public static void main(String[] args) {
    RandomizedQueue<String> myQueue = new RandomizedQueue<>();
    System.out.println(myQueue.isEmpty());
    System.out.println(myQueue.size());
    myQueue.enqueue("apple");
    myQueue.enqueue("orange");
    myQueue.enqueue("banana");
    System.out.println(myQueue.isEmpty());
    System.out.println(myQueue.size());
    for (String item: myQueue) {
      System.out.println(item);
    }
    System.out.println(myQueue.sample());
    System.out.println(myQueue.sample());
    System.out.println(myQueue.sample());
    System.out.println(myQueue.dequeue());
    System.out.println(myQueue.isEmpty());
    System.out.println(myQueue.size());
  }
}
