import java.util.ArrayList;
import java.util.Collections;

import edu.princeton.cs.algs4.StdOut;

public class CircularSuffixArray {
    private final String originalString;
    private final int[] indices;

    /**
     * circular suffix array of s
     * @param s Original string
     */
    public CircularSuffixArray(String s) {
        this.originalString = s;
        ArrayList<CircularSuffix> circularSuffixes = new ArrayList<CircularSuffix>();
        for (int i = 0; i < s.length(); i++) {
            circularSuffixes.add(new CircularSuffix(this.originalString, i));
        }
        Collections.sort(circularSuffixes);
        this.indices = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            this.indices[i] = circularSuffixes.get(i).getCharIndex();
        }
    }

    /**
     * length of original string
     * @return length
     */
    public int length() {
        return this.originalString.length();
    }

    /**
     * returns index of ith sorted suffix
     * @param i index of sorted suffix
     * @return index of original suffix
     */
    public int index(int i) {
        return this.indices[i];
    }

    // unit testing (required)
    public static void main(String[] args) {
        CircularSuffixArray csa = new CircularSuffixArray("abracadabra!");
        for (int i = 0; i < csa.length(); i++) {
            StdOut.println(csa.index(i));
        }
    }

    private class CircularSuffix implements Comparable<CircularSuffix> {
        private String original;
        private int charIndex;

        /**
         * Constructor
         * @param s Reference to original string
         * @param i index of starting char of the suffix in original string
         */
        public CircularSuffix (String s, int i) {
            this.original = s;
            this.charIndex = i;
        }

        /**
         * Get index of starting char
         * @return charIndex
         */
        public int getCharIndex() {
            return charIndex;
        }

        @Override
        public int compareTo(CircularSuffix circularSuffix) {
            int thisCharIndex = this.charIndex;
            int thatCharIndex = circularSuffix.getCharIndex();
            int counter = 0;
            while (counter < this.original.length()) {
                char thisChar = this.original.charAt(thisCharIndex++);
                char thatChar = this.original.charAt(thatCharIndex++);
                if (thisChar < thatChar)
                    return -1;
                else if (thisChar > thatChar)
                    return 1;
                else {
                    if (thisCharIndex == this.original.length()) thisCharIndex = 0;
                    if (thatCharIndex == this.original.length()) thatCharIndex = 0;
                    counter++;
                }
            }
            return 0;
        }
    }
}
