import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
  public static void main(String[] args) {
    int n = 0;
    if (args.length == 1) {
      n = Integer.parseInt(args[0]);
    }
    RandomizedQueue<String> queue = new RandomizedQueue<>();
    while (!StdIn.isEmpty()) {
      String s = StdIn.readString();
      queue.enqueue(s);
    }

    for (String item: queue) {
      StdOut.println(item);
    }
  }
}
