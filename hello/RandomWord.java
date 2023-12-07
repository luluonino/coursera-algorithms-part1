import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    /*
     * RandomWord reads a sequence of words from standard input and prints one
     * of those words uniformly at random. Do not store the words in an array
     * or list. Instead, use Knuth’s method: when reading the ith word, select
     * it with probability 1/i to be the champion, replacing the previous
     * champion. After reading all of the words, print the surviving champion
     */
    public static void main(final String[] args) {
        String word = "";
        int count = 1;
        while (!StdIn.isEmpty()) {
            String newWord = StdIn.readString();
            word = (StdRandom.bernoulli(1. / count))
                    ? newWord : word;
            count = count + 1;
        }
        StdOut.println(word);
    }
}
