import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
    private static final int R = 256;

    /**
     * apply move-to-front encoding, reading from standard input and writing to standard output
     */
    public static void encode() {
        int[] charToIndex = new int[R];
        int[] indexToChar = new int[R];
        for (int i = 0; i < R; i++) {
            charToIndex[i] = i;
            indexToChar[i] = i;
        }
        while (!BinaryStdIn.isEmpty()) {
            int c = BinaryStdIn.readChar();
            BinaryStdOut.write(charToIndex[c], 8);
            for (int i = 0; i < charToIndex[c]; i++) {
                int ch = indexToChar[i];
                charToIndex[ch] = i + 1;
            }
            for (int i = charToIndex[c]; i > 0; i--) {
                indexToChar[i] = indexToChar[i - 1];
            }
            charToIndex[c] = 0;
            indexToChar[0] = c;
        }
        BinaryStdOut.flush();
    }

    /**
     * apply move-to-front decoding, reading from standard input and writing to standard output
     */
    public static void decode() {
        int[] indexToChar = new int[R];
        for (int i = 0; i < R; i++) {
            indexToChar[i] = i;
        }
        while (!BinaryStdIn.isEmpty()) {
            int code = BinaryStdIn.readChar();
            char c = (char) indexToChar[code];
            BinaryStdOut.write(indexToChar[code], 8);
            for (int i = code; i > 0; i--) {
                indexToChar[i] = indexToChar[i - 1];
            }
            indexToChar[0] = c;
        }
        BinaryStdOut.flush();
    }

    /**
     * if args[0] is "-", apply move-to-front encoding
     * if args[0] is "+", apply move-to-front decoding
     * @param args command line arguments
     */
    public static void main(String[] args) {
        if (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
        else throw new IllegalArgumentException();
    }
}
