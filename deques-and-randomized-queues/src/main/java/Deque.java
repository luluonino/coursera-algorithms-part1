import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

  private Node first = null;
  private Node last = null;
  private int size = 0;

  /**
   * Node class to represent each item in the deque
   */
  private class Node {
    Item item;
    Node next;
    Node previous;
  }

  /**
   * Construct an empty deque
   */
  public Deque() { }

  /**
   * Is the deque empty?
   * @return true if the deque is empty, false otherwise
   */
  public boolean isEmpty() {
    return (first == null && last == null);
  }

  /**
   * Return the number of items on the deque
   * @return the number of items on the deque
   */
  public int size() {
    return size;
  }

  /**
   * Add the item to the front
   * @param item the item to add
   */
  public void addFirst(Item item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }

    if (isEmpty()) {
      Node newNode = new Node();
      newNode.item = item;
      newNode.next = null;
      newNode.previous = null;
      first = newNode;
      last = newNode;
      size = 1;
    } else {
      Node oldFirst = first;
      first = new Node();
      first.item = item;
      first.next = oldFirst;
      first.previous = null;
      oldFirst.previous = first;
      size += 1;
    }
  }

  /**
   * Add the item to the end
   * @param item the item to add
   */
  public void addLast(Item item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }

    if (isEmpty()) {
      Node newNode = new Node();
      newNode.item = item;
      newNode.next = null;
      newNode.previous = null;
      first = newNode;
      last = newNode;
      size = 1;
    } else {
      Node oldLast = last;
      last = new Node();
      last.item = item;
      last.next = null;
      last.previous = oldLast;
      oldLast.next = last;
      size += 1;
    }
  }

  /**
   * Remove and return the item from the front
   * @return the item from the front
   */
  public Item removeFirst() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    Item item = first.item;
    if (size == 1) {
      first = null;
      last = null;
      size = 0;
    } else {
      Node oldFirst = first;
      first = first.next;
      first.previous = null;
      oldFirst.next = null;
      size -= 1;
    }
    return item;
  }

  /**
   * Remove and return the item from the end
   * @return the item from the end
   */
  public Item removeLast() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    Item item = last.item;
    if (size == 1) {
      last = null;
      first = null;
      size = 0;
    } else {
      Node oldLast = last;
      last = last.previous;
      last.next = null;
      oldLast.previous = null;
      size -= 1;
    }
    return item;
  }

  /**
   * Return an iterator over items in order from front to end
   * @return an iterator over items in order from front to end
   */
  public Iterator<Item> iterator() {
    return new DequeIterator();
  }

  /**
   * Iterator class to iterate over the deque
   */
  private class DequeIterator implements Iterator<Item> {
    private Node current = first;

    public boolean hasNext() {
      return current != null;
    }

    public Item next() {
      if (current == null) {
        throw new NoSuchElementException();
      }
      Item item = current.item;
      current = current.next;
      return item;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

  public static void main(String[] args) {
    Deque<String> myDeque = new Deque<>();
    System.out.println(myDeque.isEmpty());
    System.out.println(myDeque.size());
    myDeque.addFirst("apple");
    myDeque.addLast("orange");
    myDeque.addLast("banana");
    System.out.println(myDeque.isEmpty());
    System.out.println(myDeque.size());
    for (String item: myDeque) {
      System.out.println(item);
    }
    System.out.println(myDeque.removeFirst());
    System.out.println(myDeque.removeLast());
    System.out.println(myDeque.isEmpty());
    System.out.println(myDeque.size());
  }
}

