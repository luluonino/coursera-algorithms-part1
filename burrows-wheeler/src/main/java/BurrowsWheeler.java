import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {
    private static final int R = 256;

    /**
     * apply Burrows-Wheeler transform
     * reading from standard input and writing to standard output
     */
    public static void transform() {
        String s = BinaryStdIn.readString();
        CircularSuffixArray csa = new CircularSuffixArray(s);
        int first = -1;
        for (int i = 0; i < csa.length(); i++) {
            if (csa.index(i) == 0) {
                first = i;
                break;
            }
        }
        BinaryStdOut.write(first);
        for (int i = 0; i < csa.length(); i++) {
            int index = csa.index(i);
            if (index == 0) {
                index = csa.length();
            }
            BinaryStdOut.write(s.charAt(index - 1), 8);
        }
        BinaryStdOut.flush();
    }

    /**
     * apply Burrows-Wheeler inverse transform,
     * reading from standard input and writing to standard output
     */
    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();
        String encoded = BinaryStdIn.readString();
        int[] next = new int[encoded.length()];
        int[] counts = new int[R + 1];

        for (int i = 0; i < encoded.length(); i++) {
            counts[encoded.charAt(i) + 1]++;
        }
        for (int i = 0; i < R; i++) {
            counts[i + 1] += counts[i];
        }

        char[] sorted = new char[encoded.length()];
        for (int i = 0; i < encoded.length(); i++) {
            sorted[counts[encoded.charAt(i)]] = encoded.charAt(i);
            next[counts[encoded.charAt(i)]] = i;
            counts[encoded.charAt(i)]++;
        }

        int index = first;
        int counter = 0;
        while (counter < encoded.length()) {
            BinaryStdOut.write(sorted[index], 8);
            index = next[index];
            counter++;
        }
        BinaryStdOut.flush();
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) transform();
        else if (args[0].equals("+")) inverseTransform();
        else throw new IllegalArgumentException();
    }
}
