import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

  private Node first = null;
  private Node last = null;
  private int size = 0;

  private class Node {
    Item item;
    Node next;
    Node previous;
  }

  public Deque() { }

  public boolean isEmpty() {
    return (first == null && last == null);
  }

  public int size() {
    return size;
  }

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
    }
    else {
      Node oldFirst = first;
      first = new Node();
      first.item = item;
      first.next = oldFirst;
      first.previous = null;
      oldFirst.previous = first;
      size += 1;
    }
  }

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
    }
    else {
      Node oldLast = last;
      last = new Node();
      last.item = item;
      last.next = null;
      last.previous = oldLast;
      oldLast.next = last;
      size += 1;
    }
  }

  public Item removeFirst() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    if (size == 1) {
      Item item = first.item;
      first = null;
      last = null;
      size = 0;
      return item;
    }
    else {
      Item item = first.item;
      Node oldFirst = first;
      first = first.next;
      first.previous = null;
      oldFirst.next = null;
      size -= 1;
      return item;
    }
  }

  public Item removeLast() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    if (size == 1) {
      Item item = last.item;
      last = null;
      first = null;
      size = 0;
      return item;
    }
    else {
      Item item = last.item;
      Node oldLast = last;
      last = last.previous;
      last.next = null;
      oldLast.previous = null;
      size -= 1;
      return item;
    }
  }

  public Iterator<Item> iterator() {
    return new DequeIterator();
  }

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
}
